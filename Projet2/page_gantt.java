import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import java.time.temporal.ChronoUnit;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class page_gantt extends Application {

    private String projetNom;
    private String projetDateDebut;
    private String projetDateFin;

    public page_gantt(String projetNom, String projetDateDebut, String projetDateFin) {
        this.projetNom = projetNom;
        this.projetDateDebut = projetDateDebut;
        this.projetDateFin = projetDateFin;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GestionAPP - Diagramme de Gantt");

        // Barre de menu
        HBox menuBar = createMenuBar(primaryStage);

        // Titre pour le diagramme
        HBox headerBox = new HBox();
        headerBox.setStyle("-fx-background-color: #6A0DAD; -fx-padding: 12;");
        headerBox.setAlignment(Pos.CENTER_LEFT);
        Label headerLabel = new Label("Diagramme de Gantt pour le projet : " + projetNom);
        headerLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18px;");
        headerBox.getChildren().add(headerLabel);

        // Conteneur principal
        VBox mainBox = new VBox();
        mainBox.setPadding(new Insets(20));
        mainBox.setAlignment(Pos.CENTER);

        // Diagramme de Gantt
        BarChart<Number, String> ganttChart = createGanttChart();
        mainBox.getChildren().add(ganttChart);

        // Bouton de retour
        Button backButton = new Button("Retour");
        backButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            page_projet projetPage = new page_projet();
            try {
                projetPage.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox contentBox = new VBox(20);
        contentBox.getChildren().addAll(headerBox, mainBox, backButton);
        contentBox.setPadding(new Insets(10));
        contentBox.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private BarChart<Number, String> createGanttChart() {
        // Axe des abscisses : Nombres (représentant les jours depuis le début du projet)
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Dates (yyyy-MM-dd)");
    
        // Axe des ordonnées : Tâches
        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel("Tâches");
    
        // Création du diagramme de Gantt
        BarChart<Number, String> ganttChart = new BarChart<>(xAxis, yAxis);
        ganttChart.setTitle("Diagramme de Gantt");
        ganttChart.setLegendVisible(false);
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Tache_Tch WHERE Tch_projet = ?")) {
    
            pstmt.setString(1, projetNom);
            ResultSet rs = pstmt.executeQuery();
    
            LocalDate projectStartDate = LocalDate.parse(projetDateDebut, formatter);
    
            while (rs.next()) {
                String taskName = rs.getString("Tch_nom");
                String startDateStr = rs.getString("Tch_date_debut");
                String endDateStr = rs.getString("Tch_date_fin");
    
                try {
                    LocalDate startDate = LocalDate.parse(startDateStr, formatter);
                    LocalDate endDate = LocalDate.parse(endDateStr, formatter);
    
                    // Calculer l'offset relatif à la date de début du projet
                    long startOffset = ChronoUnit.DAYS.between(projectStartDate, startDate);
                    long duration = ChronoUnit.DAYS.between(startDate, endDate) + 1; // Inclut le jour de fin
    
                    // Série pour représenter la tâche
                    XYChart.Series<Number, String> series = new XYChart.Series<>();
                    series.getData().add(new XYChart.Data<>(startOffset, taskName));            // Position de début
                    series.getData().add(new XYChart.Data<>(startOffset + duration, taskName)); // Position de fin
    
                    ganttChart.getData().add(series);
                } catch (Exception ex) {
                    System.out.println("Erreur avec la tâche " + taskName + ": " + ex.getMessage());
                }
            }
    
            // Formatage des étiquettes de l'axe X pour afficher les dates au format yyyy-MM-dd
            xAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(xAxis) {
                @Override
                public String toString(Number value) {
                    return projectStartDate.plusDays(value.longValue()).toString();
                }
            });
    
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des tâches : " + e.getMessage());
        }
    
        return ganttChart;
    }
    


    private HBox createMenuBar(Stage primaryStage) {
        HBox menuBar = new HBox(10);
        menuBar.setPadding(new Insets(10));
        menuBar.setStyle("-fx-background-color: #b0bec5;");
        menuBar.setAlignment(Pos.CENTER_LEFT);

        Button homeButton = new Button("Accueil");
        homeButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        homeButton.setOnAction(e -> {
            page_accueil accueilPage = new page_accueil();
            try {
                accueilPage.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button projectButton = new Button("Projets");
        projectButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        projectButton.setDisable(true);

        Label appTitle = new Label("GestionAPP");
        appTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: purple;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label userProfile = new Label("Utilisateur");
        userProfile.setStyle("-fx-border-color: purple; -fx-padding: 5;");

        menuBar.getChildren().addAll(homeButton, projectButton, spacer, appTitle, userProfile);
        return menuBar;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
