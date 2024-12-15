import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class page_tache extends Application {

    private String nomUtilisateur;
    private String projetNom;

    public page_tache(String projetNom, String nomUtilisateur) {
        this.projetNom = projetNom;
        this.nomUtilisateur = nomUtilisateur;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GestionAPP - Tâches");

        // Barre de Menu en Haut
        HBox menuBar = new HBox(10);
        menuBar.setPadding(new Insets(10));
        menuBar.setStyle("-fx-background-color: #b0bec5;");  // Couleur de fond grise
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
        projectButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        projectButton.setOnAction(e -> {
            page_projet projetPage = new page_projet(nomUtilisateur);
            try {
                projetPage.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button taskButton = new Button("Tâche"); // Garder le bouton actif sans style supplémentaire

        Button timesheetButton = new Button("Feuille de temps");
        timesheetButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        timesheetButton.setOnAction(e -> {
            page_temps tempsPage = new page_temps(nomUtilisateur);
            try {
                tempsPage.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button resourcesButton = new Button("Gantt");
        resourcesButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        Button helpButton = new Button("Aide");
        helpButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        Label appTitle = new Label("GestionAPP");
        appTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: purple;");

        Region spacerLeft = new Region(); // Pour espacer entre les boutons de menu et le titre
        Region spacerRight = new Region(); // Pour espacer entre le titre et le profil utilisateur
        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        HBox.setHgrow(spacerRight, Priority.ALWAYS);

        Label userProfile = new Label(nomUtilisateur);
        userProfile.setStyle("-fx-border-color: purple; -fx-padding: 5;");

        menuBar.getChildren().addAll(homeButton, userButton, projectButton, taskButton, timesheetButton, resourcesButton, helpButton, spacerLeft, appTitle, spacerRight, userProfile);

        // Titre "Liste des tâches"
        HBox taskTitleBox = new HBox(10);
        taskTitleBox.setStyle("-fx-background-color: #6A0DAD; -fx-padding: 12;");
        taskTitleBox.setAlignment(Pos.CENTER_LEFT);

        ImageView menuIcon = new ImageView(new Image("file:images/menu.png")); // Icône de menu
        menuIcon.setFitHeight(20);
        menuIcon.setFitWidth(20);

        Label taskTitleLabel = new Label("Liste des tâches pour le projet: " + projetNom);
        taskTitleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        taskTitleBox.getChildren().addAll(menuIcon, taskTitleLabel);

        // Liste des tâches
        VBox tasksBox = new VBox(10);
        tasksBox.setPadding(new Insets(20));
        tasksBox.setAlignment(Pos.TOP_LEFT);

        // Ajouter les tâches existantes du projet
        try (Connection conn = SQLiteConnection.connect();
             Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT Tch_nom, Tch_description, Tch_date_debut, Tch_date_fin FROM Tache_Tch WHERE Tch_projet = '" + projetNom + "'")) {

            while (rs.next()) {
                String taskName = rs.getString("Tch_nom");
                String taskDescription = rs.getString("Tch_description");
                String taskStartDate = rs.getString("Tch_date_debut");
                String taskEndDate = rs.getString("Tch_date_fin");

                VBox taskBox = new VBox(5);
                taskBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray; -fx-padding: 10; -fx-pref-width: 600px;");

                Label taskNameLabel = new Label(taskName);
                taskNameLabel.setStyle("-fx-font-weight: bold;");

                Label taskDescriptionLabel = new Label(taskDescription);
                Label taskDatesLabel = new Label("Dates: " + taskStartDate + " - " + taskEndDate);
                taskDatesLabel.setStyle("-fx-font-weight: normal;");

                // Bouton de suppression de la tâche
                Button deleteTaskButton = new Button("Supprimer");
                deleteTaskButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
                deleteTaskButton.setOnAction(e -> {
                    supprimerTache(primaryStage, taskName);  // Supprimer la tâche de la base de données et de l'interface
                    tasksBox.getChildren().remove(taskBox); // Retirer la tâche de l'interface
                });

                HBox taskButtonsBox = new HBox(10, deleteTaskButton);
                taskButtonsBox.setAlignment(Pos.CENTER_RIGHT);

                taskBox.getChildren().addAll(taskNameLabel, taskDescriptionLabel, taskDatesLabel, taskButtonsBox);
                tasksBox.getChildren().add(taskBox);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des tâches : " + e.getMessage());
        }

        // Bouton pour ajouter une nouvelle tâche
        Button addTaskButton = new Button("Ajouter une Tâche");
        addTaskButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        addTaskButton.setOnAction(e -> {
            // Logique pour ajouter une nouvelle tâche
            FormulaireNouvelleTache formulaireTache = new FormulaireNouvelleTache(projetNom, "2024-01-01", "2024-12-31"); // Dates d'exemple
            formulaireTache.start(new Stage());
        });

        tasksBox.getChildren().add(addTaskButton);

        // Barre de recherche à l'extrême droite
        HBox searchBox = new HBox(10);
        searchBox.setPadding(new Insets(10));
        searchBox.setAlignment(Pos.CENTER_RIGHT);

        ImageView searchIcon = new ImageView(new Image("file:images/recherche.jpg")); // Icône de recherche
        searchIcon.setFitHeight(20);
        searchIcon.setFitWidth(20);

        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher");
        searchField.setStyle("-fx-border-color: lightgray;");

        searchBox.getChildren().addAll(searchIcon, searchField);

        // Créer un conteneur pour le titre et la barre de recherche (sur la même ligne)
        HBox titleAndSearchBox = new HBox(10);
        titleAndSearchBox.setAlignment(Pos.CENTER_LEFT);
        titleAndSearchBox.setPadding(new Insets(10, 10, 10, 20));
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        titleAndSearchBox.getChildren().addAll(taskTitleBox, searchBox);

        // Conteneur principal
        VBox mainContent = new VBox(10);
        mainContent.getChildren().addAll(titleAndSearchBox, tasksBox);
        mainContent.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(new ScrollPane(mainContent));

        // Créer la scène et l'afficher
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode pour supprimer une tâche de la base de données
    private void supprimerTache(Stage primaryStage, String taskName) {
        String sql = "DELETE FROM Tache_Tch WHERE Tch_nom = ? AND Tch_projet = ?";

        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, taskName);
            pstmt.setString(2, projetNom);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Tâche supprimée avec succès.");
            } else {
                System.out.println("Échec de la suppression de la tâche.");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la tâche : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
