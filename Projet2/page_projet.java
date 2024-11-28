import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class page_projet extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GestionAPP");

        // Barre de Menu en Haut
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

        Button userButton = new Button("utilisateur");
        userButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        userButton.setOnAction(e -> {
            page_utilisateur utilisateurPage = new page_utilisateur();
            try {
                utilisateurPage.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button projectButton = new Button("projet");

        Button taskButton = new Button("tache");
        taskButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        taskButton.setOnAction(e -> {
            page_tache tachePage = new page_tache();
            try {
                tachePage.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button timesheetButton = new Button("Feuille de temps");
        timesheetButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        timesheetButton.setOnAction(e -> {
            page_temps tempsPage = new page_temps();
            try {
                tempsPage.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button resourcesButton = new Button("Ressources");
        resourcesButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        Button helpButton = new Button("Aide");
        helpButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        Label appTitle = new Label("GestionAPP");
        appTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: purple;");

        Region spacerLeft = new Region();
        Region spacerRight = new Region();
        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        HBox.setHgrow(spacerRight, Priority.ALWAYS);

        Label userProfile = new Label("Nom utilisateur");
        userProfile.setStyle("-fx-border-color: purple; -fx-padding: 5;");

        menuBar.getChildren().addAll(homeButton, userButton, projectButton, taskButton, timesheetButton, resourcesButton, helpButton, spacerLeft, appTitle, spacerRight, userProfile);

        // Titre pour la liste des projets
        HBox projectTitleBox = new HBox(10);
        projectTitleBox.setStyle("-fx-background-color: #6A0DAD; -fx-padding: 12;-fx-pref-width: 800px;");
        projectTitleBox.setAlignment(Pos.CENTER_LEFT);

        ImageView menuIcon = new ImageView(new Image("file:images/menu.png"));
        menuIcon.setFitHeight(20);
        menuIcon.setFitWidth(20);

        Label projectTitleLabel = new Label("LISTE DES PROJETS");
        projectTitleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;-fx-pref-width: 800px;");

        projectTitleBox.getChildren().addAll(menuIcon, projectTitleLabel);

        // Barre de recherche à l'extrême droite
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_RIGHT);
        searchBox.setPadding(new Insets(10));

        ImageView searchIcon = new ImageView(new Image("file:images/recherche.jpg"));
        searchIcon.setFitHeight(20);
        searchIcon.setFitWidth(20);

        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher");
        searchField.setStyle("-fx-border-color: lightgray;");

        searchBox.getChildren().addAll(searchIcon, searchField);
        HBox.setHgrow(searchBox, Priority.ALWAYS);

        // Conteneur pour le titre des projets et la barre de recherche
        BorderPane titleAndSearchPane = new BorderPane();
        titleAndSearchPane.setPadding(new Insets(10));
        titleAndSearchPane.setLeft(projectTitleBox);
        titleAndSearchPane.setRight(searchBox);

        // Liste des projets
        VBox projectsBox = new VBox(10);
        projectsBox.setPadding(new Insets(20));
        projectsBox.setAlignment(Pos.CENTER);

        // Charger les projets depuis la base de données
        try (Connection conn = SQLiteConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT Pro_nom FROM Projet_Pro")) {

            while (rs.next()) {
                String projectName = rs.getString("Pro_nom");
                Button projectButtonDynamic = new Button(projectName);
                projectButtonDynamic.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray; -fx-pref-width: 800px;");
                projectsBox.getChildren().add(projectButtonDynamic);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des projets : " + e.getMessage());
        }

        // Barre de progression en bas
        HBox progressBarBox = new HBox(10);
        progressBarBox.setPadding(new Insets(10));
        progressBarBox.setAlignment(Pos.BOTTOM_LEFT);

        Label progressLabel = new Label(" ");
        Region progressBar = new Region();
        progressBar.setStyle("-fx-background-color: #6A0DAD; -fx-pref-width: 300px; -fx-pref-height: 10px;");

        progressBarBox.getChildren().addAll(progressLabel, progressBar);

        // Ajouter le Logo DigiCraft en bas à droite
        ImageView logo = new ImageView(new Image("file:images/logo.png"));
        logo.setFitHeight(40);
        logo.setFitWidth(40);
        StackPane.setAlignment(logo, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(logo, new Insets(10));

        // Ajouter les composants au conteneur principal
        VBox mainBox = new VBox(10);
        mainBox.setPadding(new Insets(10));
        mainBox.getChildren().addAll(titleAndSearchPane, projectsBox, progressBarBox);

        // Assembler tout dans un BorderPane
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(new ScrollPane(mainBox));

        // Ajouter le logo et le reste dans un StackPane
        StackPane stackPane = new StackPane(root, logo);
        Scene scene = new Scene(stackPane, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
