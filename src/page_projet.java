import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class page_projet extends Application {

    private String nomUtilisateur;

    public page_projet() {
        this.nomUtilisateur = "Utilisateur"; // Valeur par défaut
    }

    public page_projet(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GestionAPP - Projets");

        // Barre de menu avec couleur grise pour indiquer l'onglet Projet actif
        HBox menuBar = createMenuBar(primaryStage);

        // Titre pour la liste des projets
        HBox projectTitleBox = new HBox(10);
        projectTitleBox.setStyle("-fx-background-color: #6A0DAD; -fx-padding: 12;");
        projectTitleBox.setAlignment(Pos.CENTER_LEFT);

        ImageView menuIcon = new ImageView(new Image("file:images/menu.png"));
        menuIcon.setFitHeight(20);
        menuIcon.setFitWidth(20);

        Label projectTitleLabel = new Label("LISTE DES PROJETS");
        projectTitleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        projectTitleBox.getChildren().addAll(menuIcon, projectTitleLabel);

        // Liste des projets depuis la base de données
        VBox projectsBox = new VBox(10);
        projectsBox.setPadding(new Insets(20));
        projectsBox.setAlignment(Pos.CENTER);

        try (Connection conn = SQLiteConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT Pro_nom, Pro_description, Pro_date_debut, Pro_date_fin FROM Projet_Pro")) {

            while (rs.next()) {
                String projectName = rs.getString("Pro_nom");
                String projectDescription = rs.getString("Pro_description");
                String projectStartDate = rs.getString("Pro_date_debut");
                String projectEndDate = rs.getString("Pro_date_fin");

                VBox projectBox = new VBox(5);
                projectBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray; -fx-padding: 10; -fx-pref-width: 800px;");

                Label projectNameLabel = new Label(projectName);
                projectNameLabel.setStyle("-fx-font-weight: bold;");

                Text projectDescriptionText = new Text(projectDescription);
                projectDescriptionText.setWrappingWidth(780);

                // Affichage des dates de début et de fin
                Label projectDatesLabel = new Label("Dates: " + projectStartDate + " - " + projectEndDate);
                projectDatesLabel.setStyle("-fx-font-weight: normal;");

                Button viewTasksButton = new Button("Voir les tâches");
                viewTasksButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
                viewTasksButton.setOnAction(e -> {
                    page_tache tachePage = new page_tache(projectName, nomUtilisateur);
                    try {
                        tachePage.start(primaryStage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                // Bouton pour supprimer le projet
                Button deleteProjectButton = new Button("Supprimer");
                deleteProjectButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
                deleteProjectButton.setOnAction(e -> {
                    supprimerProjet(primaryStage, projectName);  // Supprimer le projet de la base de données et de l'interface
                    projectsBox.getChildren().remove(projectBox); // Retirer le projet de l'interface
                });

                HBox buttonsBox = new HBox(10, viewTasksButton, deleteProjectButton);
                buttonsBox.setAlignment(Pos.CENTER_RIGHT);

                projectBox.getChildren().addAll(projectNameLabel, projectDescriptionText, projectDatesLabel, buttonsBox);
                projectsBox.getChildren().add(projectBox);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des projets : " + e.getMessage());
        }

        VBox mainBox = new VBox(10, projectTitleBox, projectsBox);
        mainBox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(new ScrollPane(mainBox));

        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createMenuBar(Stage primaryStage) {
        HBox menuBar = new HBox(10);
        menuBar.setPadding(new Insets(10));
        menuBar.setStyle("-fx-background-color: #b0bec5;");
        menuBar.setAlignment(Pos.CENTER_LEFT);

        Button homeButton = new Button("Accueil");
        homeButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        homeButton.setOnAction(e -> {
            page_accueil accueilPage = new page_accueil(nomUtilisateur);
            try {
                accueilPage.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button userButton = new Button("Utilisateur");
        userButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        userButton.setOnAction(e -> {
            page_utilisateur utilisateurPage = new page_utilisateur(nomUtilisateur);
            try {
                utilisateurPage.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button projectButton = new Button("Projet");
        projectButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");  // Fond blanc et texte noir pour l'onglet Projet
        projectButton.setOnAction(e -> {
            page_projet projetPage = new page_projet(nomUtilisateur);
            try {
                projetPage.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button taskButton = new Button("Tâche");
        taskButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        taskButton.setOnAction(e -> {
            page_tache tachePage = new page_tache("Projet", nomUtilisateur);
            try {
                tachePage.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button timesheetButton = new Button("Feuille de Temps");
        timesheetButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        timesheetButton.setOnAction(e -> {
            page_temps tempsPage = new page_temps(nomUtilisateur);
            try {
                tempsPage.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button ganttButton = new Button("Gantt");
        ganttButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        Label appTitle = new Label("GestionAPP");
        appTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: purple;");

        Region spacerLeft = new Region();
        Region spacerRight = new Region();
        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        HBox.setHgrow(spacerRight, Priority.ALWAYS);

        Label userProfile = new Label(nomUtilisateur);
        userProfile.setStyle("-fx-border-color: purple; -fx-padding: 5;");

        menuBar.getChildren().addAll(homeButton, userButton, projectButton, taskButton, timesheetButton, ganttButton, spacerLeft, appTitle, spacerRight, userProfile);
        return menuBar;
    }

    private void supprimerProjet(Stage primaryStage, String projectName) {
        String sql = "DELETE FROM Projet_Pro WHERE Pro_nom = ?";

        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, projectName);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Projet supprimé avec succès.");
            } else {
                System.out.println("Échec de la suppression du projet.");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du projet : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
