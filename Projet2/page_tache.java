import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.plaf.synth.Region;

public class page_tache extends Application {

    private String nomUtilisateur;
    private String projetNom;
    private String projetDateDebut;
    private String projetDateFin;

    public page_tache(String projetNom, String nomUtilisateur, String projetDateDebut, String projetDateFin) {
        this.projetNom = projetNom;
        this.nomUtilisateur = nomUtilisateur;
        this.projetDateDebut = projetDateDebut;
        this.projetDateFin = projetDateFin;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GestionAPP - Tâches");

        // Barre de menu
        HBox menuBar = createMenuBar(primaryStage);

        // Titre "Liste des tâches"
        HBox taskTitleBox = new HBox(10);
        taskTitleBox.setStyle("-fx-background-color: #6A0DAD; -fx-padding: 12;");
        taskTitleBox.setAlignment(Pos.CENTER_LEFT);

        ImageView menuIcon = new ImageView(new Image("file:images/menu.png"));
        menuIcon.setFitHeight(20);
        menuIcon.setFitWidth(20);

        Label taskTitleLabel = new Label("Liste des tâches pour le projet : " + projetNom);
        taskTitleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        taskTitleBox.getChildren().addAll(menuIcon, taskTitleLabel);

        // Liste des tâches
        VBox tasksBox = new VBox(10);
        tasksBox.setPadding(new Insets(20));
        tasksBox.setAlignment(Pos.TOP_LEFT);

        // Bouton Ajouter une tâche
        Button addTaskButton = new Button("Ajouter une tâche");
        addTaskButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        addTaskButton.setOnAction(e -> openAddTaskWindow(primaryStage, tasksBox));

        // Charger les tâches depuis la base de données
        loadTasks(tasksBox, primaryStage);

        VBox mainContent = new VBox(10);
        mainContent.getChildren().addAll(taskTitleBox, tasksBox, addTaskButton);
        mainContent.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(new ScrollPane(mainContent));

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadTasks(VBox tasksBox, Stage primaryStage) {
        tasksBox.getChildren().clear();

        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Tache_Tch WHERE Tch_projet = ?")) {
            pstmt.setString(1, projetNom);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String taskId = rs.getString("Tch_id");
                String taskName = rs.getString("Tch_nom");
                String taskDescription = rs.getString("Tch_description");
                String taskStartDate = rs.getString("Tch_date_debut");
                String taskEndDate = rs.getString("Tch_date_fin");
                String taskAssignedPerson = rs.getString("Tch_affectation");
                String taskStatus = rs.getString("Tch_etat");

                VBox taskBox = new VBox(5);
                taskBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray; -fx-padding: 10; -fx-pref-width: 600px;");

                Label taskNameLabel = new Label("Nom : " + taskName);
                taskNameLabel.setStyle("-fx-font-weight: bold;");

                Label taskDescriptionLabel = new Label("Description : " + taskDescription);
                Label taskDatesLabel = new Label("Dates : " + taskStartDate + " - " + taskEndDate);
                Label taskStatusLabel = new Label("État : " + taskStatus);
                Label taskAssignedPersonLabel = new Label("Assignée à : " + (taskAssignedPerson != null ? taskAssignedPerson : "Non assignée"));

                // Boutons Modifier et Supprimer
                Button editTaskButton = new Button("Modifier");
                editTaskButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
                editTaskButton.setOnAction(e -> openEditTaskWindow(taskId, taskName, taskDescription, taskStartDate, taskEndDate, taskAssignedPerson, taskStatus, tasksBox, primaryStage));

                Button deleteTaskButton = new Button("Supprimer");
                deleteTaskButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
                deleteTaskButton.setOnAction(e -> {
                    deleteTask(taskId);
                    loadTasks(tasksBox, primaryStage);
                });

                HBox buttonsBox = new HBox(10, editTaskButton, deleteTaskButton);
                buttonsBox.setAlignment(Pos.CENTER_RIGHT);

                taskBox.getChildren().addAll(taskNameLabel, taskDescriptionLabel, taskDatesLabel, taskStatusLabel, taskAssignedPersonLabel, buttonsBox);
                tasksBox.getChildren().add(taskBox);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement des tâches : " + e.getMessage());
        }
    }

    private void openAddTaskWindow(Stage primaryStage, VBox tasksBox) {
        Stage addTaskStage = new Stage();
        addTaskStage.setTitle("Ajouter une tâche");
        addTaskStage.initModality(Modality.APPLICATION_MODAL);
        addTaskStage.initOwner(primaryStage);

        VBox addTaskBox = new VBox(10);
        addTaskBox.setPadding(new Insets(20));
        addTaskBox.setAlignment(Pos.CENTER_LEFT);

        TextField taskNameField = new TextField();
        taskNameField.setPromptText("Nom de la tâche");

        TextArea taskDescriptionField = new TextArea();
        taskDescriptionField.setPromptText("Description");

        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();

        ComboBox<String> taskStateComboBox = new ComboBox<>();
        taskStateComboBox.getItems().addAll("Non commencée", "En cours", "Terminée");
        taskStateComboBox.setPromptText("État de la tâche");

        ComboBox<String> assignedPersonComboBox = new ComboBox<>();
        loadAllPersons(assignedPersonComboBox);

        Button saveButton = new Button("Enregistrer");
        saveButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        saveButton.setOnAction(e -> {
            String taskName = taskNameField.getText();
            String taskDescription = taskDescriptionField.getText();
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            String taskStatus = taskStateComboBox.getValue();
            String assignedPerson = assignedPersonComboBox.getValue();

            if (validateDates(startDate, endDate)) {
                addTaskToDatabase(taskName, taskDescription, startDate.toString(), endDate.toString(), taskStatus, assignedPerson);
                loadTasks(tasksBox, primaryStage);
                addTaskStage.close();
            } else {
                showError("Veuillez entrer des dates valides.");
            }
        });

        addTaskBox.getChildren().addAll(new Label("Nom :"), taskNameField,
                new Label("Description :"), taskDescriptionField,
                new Label("Date de début :"), startDatePicker,
                new Label("Date de fin :"), endDatePicker,
                new Label("État :"), taskStateComboBox,
                new Label("Assignée à :"), assignedPersonComboBox, saveButton);

        Scene addTaskScene = new Scene(addTaskBox, 400, 500);
        addTaskStage.setScene(addTaskScene);
        addTaskStage.show();
    }

    private void addTaskToDatabase(String name, String description, String startDate, String endDate, String state, String assignedPerson) {
        String sql = "INSERT INTO Tache_Tch (Tch_projet, Tch_nom, Tch_description, Tch_date_debut, Tch_date_fin, Tch_etat, Tch_affectation) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, projetNom);
            pstmt.setString(2, name);
            pstmt.setString(3, description);
            pstmt.setString(4, startDate);
            pstmt.setString(5, endDate);
            pstmt.setString(6, state);
            pstmt.setString(7, assignedPerson);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la tâche : " + e.getMessage());
        }
    }

    private void openEditTaskWindow(String taskId, String taskName, String taskDescription, String taskStartDate, String taskEndDate, String taskAssignedPerson, String taskStatus, VBox tasksBox, Stage primaryStage) {
        Stage editTaskStage = new Stage();
        editTaskStage.setTitle("Modifier une tâche");
        editTaskStage.initModality(Modality.APPLICATION_MODAL);
        editTaskStage.initOwner(primaryStage);

        VBox editTaskBox = new VBox(10);
        editTaskBox.setPadding(new Insets(20));
        editTaskBox.setAlignment(Pos.CENTER_LEFT);

        TextField taskNameField = new TextField(taskName);
        TextArea taskDescriptionField = new TextArea(taskDescription);
        DatePicker startDatePicker = new DatePicker(LocalDate.parse(taskStartDate));
        DatePicker endDatePicker = new DatePicker(LocalDate.parse(taskEndDate));
        ComboBox<String> taskStateComboBox = new ComboBox<>();
        taskStateComboBox.getItems().addAll("Non commencée", "En cours", "Terminée");
        taskStateComboBox.setValue(taskStatus);
        ComboBox<String> assignedPersonComboBox = new ComboBox<>();
        loadAllPersons(assignedPersonComboBox);
        assignedPersonComboBox.setValue(taskAssignedPerson);

        Button saveButton = new Button("Enregistrer");
        saveButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        saveButton.setOnAction(e -> {
            String newTaskName = taskNameField.getText();
            String newTaskDescription = taskDescriptionField.getText();
            LocalDate newStartDate = startDatePicker.getValue();
            LocalDate newEndDate = endDatePicker.getValue();
            String newTaskStatus = taskStateComboBox.getValue();
            String newAssignedPerson = assignedPersonComboBox.getValue();

            if (validateDates(newStartDate, newEndDate)) {
                updateTaskInDatabase(taskId, newTaskName, newTaskDescription, newStartDate.toString(), newEndDate.toString(), newTaskStatus, newAssignedPerson);
                loadTasks(tasksBox, primaryStage);
                editTaskStage.close();
            } else {
                showError("Veuillez entrer des dates valides.");
            }
        });

        editTaskBox.getChildren().addAll(new Label("Nom :"), taskNameField,
                new Label("Description :"), taskDescriptionField,
                new Label("Date de début :"), startDatePicker,
                new Label("Date de fin :"), endDatePicker,
                new Label("État :"), taskStateComboBox,
                new Label("Assignée à :"), assignedPersonComboBox, saveButton);

        Scene editTaskScene = new Scene(editTaskBox, 400, 500);
        editTaskStage.setScene(editTaskScene);
        editTaskStage.show();
    }

    private void updateTaskInDatabase(String taskId, String name, String description, String startDate, String endDate, String state, String assignedPerson) {
        String sql = "UPDATE Tache_Tch SET Tch_nom = ?, Tch_description = ?, Tch_date_debut = ?, Tch_date_fin = ?, Tch_etat = ?, Tch_affectation = ? WHERE Tch_id = ?";
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setString(3, startDate);
            pstmt.setString(4, endDate);
            pstmt.setString(5, state);
            pstmt.setString(6, assignedPerson);
            pstmt.setString(7, taskId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la tâche : " + e.getMessage());
        }
    }

    private void deleteTask(String taskId) {
        String sql = "DELETE FROM Tache_Tch WHERE Tch_id = ?";
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, taskId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la tâche : " + e.getMessage());
        }
    }

    private void loadAllPersons(ComboBox<String> comboBox) {
        String sql = "SELECT Utl_id FROM Utilisateur_Utl";
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                comboBox.getItems().add(rs.getString("Utl_id"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs assignables : " + e.getMessage());
        }
    }

    private boolean validateDates(LocalDate startDate, LocalDate endDate) {
        LocalDate projectStart = LocalDate.parse(projetDateDebut);
        LocalDate projectEnd = LocalDate.parse(projetDateFin);

        return (startDate != null && endDate != null &&
                !startDate.isBefore(projectStart) &&
                !endDate.isAfter(projectEnd));
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


        Label appTitle = new Label("GestionAPP");
        appTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: purple;");

        menuBar.getChildren().addAll(homeButton, appTitle);
        return menuBar;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
