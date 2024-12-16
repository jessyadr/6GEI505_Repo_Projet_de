// Importations nécessaires
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class page_projet extends Application {

    private String nomUtilisateur;
    private VBox projectsBox;

    public page_projet() {
        this.nomUtilisateur = "Utilisateur"; // Valeur par défaut
    }

    public page_projet(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GestionAPP - Projets");

        // Barre de menu
        HBox menuBar = createMenuBar(primaryStage);

        // Titre pour la liste des projets
        HBox projectTitleBox = new HBox(10);
        projectTitleBox.setStyle("-fx-background-color: #6A0DAD; -fx-padding: 12;");
        projectTitleBox.setAlignment(Pos.CENTER_LEFT);

        Label projectTitleLabel = new Label("LISTE DES PROJETS");
        projectTitleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        projectTitleBox.getChildren().add(projectTitleLabel);

        // Conteneur pour les projets
        projectsBox = new VBox(10);
        projectsBox.setPadding(new Insets(20));
        projectsBox.setAlignment(Pos.CENTER);

        loadProjects(primaryStage);

        VBox mainBox = new VBox(10, projectTitleBox, projectsBox);
        mainBox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(new ScrollPane(mainBox));

        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadProjects(Stage primaryStage) {
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT Pro_nom, Pro_description, Pro_date_debut, Pro_date_fin, Pro_personne_assignee FROM Projet_Pro")) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String projectName = rs.getString("Pro_nom");
                String projectDescription = rs.getString("Pro_description");
                String projectStartDate = rs.getString("Pro_date_debut");
                String projectEndDate = rs.getString("Pro_date_fin");
                String assignedPerson = rs.getString("Pro_personne_assignee");

                VBox projectBox = new VBox(5);
                projectBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray; -fx-padding: 10; -fx-pref-width: 800px;");

                Label projectNameLabel = new Label(projectName);
                projectNameLabel.setStyle("-fx-font-weight: bold;");

                Label projectDescriptionLabel = new Label(projectDescription);
                Label projectDatesLabel = new Label("Date de début: " + projectStartDate + " --- Date de fin: " + projectEndDate);
                Label projectAssignedPersonLabel = new Label("Responsable : " + (assignedPerson != null ? assignedPerson : "Non assigné"));

                // Boutons "Voir les tâches", "Modifier", "Supprimer", "Gantt"
                Button viewTasksButton = new Button("Voir les tâches");
                viewTasksButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
                viewTasksButton.setOnAction(e -> {
                    page_tache tachePage = new page_tache(projectName, nomUtilisateur, projectStartDate, projectEndDate);
                    try {
                        tachePage.start(primaryStage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                Button editProjectButton = new Button("Modifier");
                editProjectButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
                editProjectButton.setOnAction(e -> openEditProjectWindow(primaryStage, projectName, projectDescription, projectStartDate, projectEndDate, projectAssignedPersonLabel));

                Button deleteProjectButton = new Button("Supprimer");
                deleteProjectButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
                deleteProjectButton.setOnAction(e -> {
                    supprimerProjet(projectName);
                    projectsBox.getChildren().remove(projectBox);
                });

                // Nouveau bouton "Gantt"
                Button ganttButton = new Button("Gantt");
                ganttButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
                ganttButton.setOnAction(e -> {
                    page_gantt ganttPage = new page_gantt(projectName, projectStartDate, projectEndDate);
                    try {
                        ganttPage.start(primaryStage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                HBox buttonsBox = new HBox(10, viewTasksButton, editProjectButton, deleteProjectButton, ganttButton);
                buttonsBox.setAlignment(Pos.CENTER_RIGHT);

                projectBox.getChildren().addAll(projectNameLabel, projectDescriptionLabel, projectDatesLabel, projectAssignedPersonLabel, buttonsBox);
                projectsBox.getChildren().add(projectBox);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des projets : " + e.getMessage());
        }
    }


    private void openEditProjectWindow(Stage owner, String projectName, String projectDescription, String projectStartDate, String projectEndDate, Label projectAssignedPersonLabel) {
        Stage editProjectStage = new Stage();
        editProjectStage.setTitle("Modifier le Projet : " + projectName);
        editProjectStage.initModality(Modality.APPLICATION_MODAL);
        editProjectStage.initOwner(owner);

        VBox editProjectBox = new VBox(10);
        editProjectBox.setPadding(new Insets(20));
        editProjectBox.setAlignment(Pos.CENTER_LEFT);

        TextField projectNameField = new TextField(projectName);
        TextField projectDescriptionField = new TextField(projectDescription);
        DatePicker startDatePicker = new DatePicker(LocalDate.parse(projectStartDate));
        DatePicker endDatePicker = new DatePicker(LocalDate.parse(projectEndDate));

        Label assignLabel = new Label("Responsable de Projet :");
        ComboBox<String> personComboBox = new ComboBox<>();
        loadEligiblePersons(personComboBox);
        personComboBox.setValue(projectAssignedPersonLabel.getText().replace("Responsable : ", ""));

        Button saveButton = new Button("Enregistrer");
        saveButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        saveButton.setOnAction(e -> {
            String updatedProjectName = projectNameField.getText();
            String updatedDescription = projectDescriptionField.getText();
            LocalDate updatedStartDate = startDatePicker.getValue();
            LocalDate updatedEndDate = endDatePicker.getValue();
            String selectedPerson = personComboBox.getValue();

            if (updatedProjectName.isEmpty() || updatedDescription.isEmpty() || updatedStartDate == null || updatedEndDate == null || selectedPerson == null) {
                showError("Tous les champs doivent être remplis.");
                return;
            }

            if (!updatedStartDate.isBefore(updatedEndDate)) {
                showError("La date de début doit précéder la date de fin.");
                return;
            }

            updateProjectInDatabase(projectName, updatedProjectName, updatedDescription, updatedStartDate.toString(), updatedEndDate.toString(), selectedPerson);
            projectAssignedPersonLabel.setText("Responsable : " + selectedPerson);
            editProjectStage.close();
        });

        editProjectBox.getChildren().addAll(
                new Label("Nom du Projet :"), projectNameField,
                new Label("Description :"), projectDescriptionField,
                new Label("Date de Début :"), startDatePicker,
                new Label("Date de Fin :"), endDatePicker,
                assignLabel, personComboBox,
                saveButton
        );

        Scene editProjectScene = new Scene(editProjectBox, 400, 500);
        editProjectStage.setScene(editProjectScene);
        editProjectStage.show();
    }

    private void loadEligiblePersons(ComboBox<String> comboBox) {
        String sql = "SELECT Utl_nom, Utl_prenom FROM Utilisateur_Utl WHERE Utl_role IN ('administrateur', 'gestionnaire de projet')";
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String fullName = rs.getString("Utl_nom") + " " + rs.getString("Utl_prenom");
                comboBox.getItems().add(fullName);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des responsables éligibles : " + e.getMessage());
        }
    }

    private void updateProjectInDatabase(String oldProjectName, String newProjectName, String description, String startDate, String endDate, String assignedPerson) {
        String sql = "UPDATE Projet_Pro SET Pro_nom = ?, Pro_description = ?, Pro_date_debut = ?, Pro_date_fin = ?, Pro_personne_assignee = ? WHERE Pro_nom = ?";
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newProjectName);
            pstmt.setString(2, description);
            pstmt.setString(3, startDate);
            pstmt.setString(4, endDate);
            pstmt.setString(5, assignedPerson);
            pstmt.setString(6, oldProjectName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du projet : " + e.getMessage());
        }
    }

    private void supprimerProjet(String projectName) {
        String deleteTasksSql = "DELETE FROM Tache_Tch WHERE Tch_projet = ?";
        String deleteProjectSql = "DELETE FROM Projet_Pro WHERE Pro_nom = ?";

        try (Connection conn = SQLiteConnection.connect()) {
            conn.setAutoCommit(false);

            try (PreparedStatement deleteTasksStmt = conn.prepareStatement(deleteTasksSql);
                 PreparedStatement deleteProjectStmt = conn.prepareStatement(deleteProjectSql)) {

                deleteTasksStmt.setString(1, projectName);
                deleteTasksStmt.executeUpdate();

                deleteProjectStmt.setString(1, projectName);
                deleteProjectStmt.executeUpdate();

                conn.commit();
                System.out.println("Projet et ses tâches associées supprimés : " + projectName);

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Erreur lors de la suppression du projet et de ses tâches associées : " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion ou de configuration de la transaction : " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

        Button projectButton = new Button("Projet");
        projectButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        projectButton.setDisable(true);

        Label appTitle = new Label("GestionAPP");
        appTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: purple;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label userProfile = new Label(nomUtilisateur);
        userProfile.setStyle("-fx-border-color: purple; -fx-padding: 5;");

        menuBar.getChildren().addAll(homeButton, projectButton, spacer, appTitle, userProfile);
        return menuBar;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
