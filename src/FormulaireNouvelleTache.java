import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;
import java.util.*;

public class FormulaireNouvelleTache extends Application {

    private String projetNom;
    private String dateDebutProjet;
    private String dateFinProjet;

    // Constructeur pour recevoir le nom du projet et ses dates
    public FormulaireNouvelleTache(String projetNom, String dateDebutProjet, String dateFinProjet) {
        this.projetNom = projetNom;
        this.dateDebutProjet = dateDebutProjet;
        this.dateFinProjet = dateFinProjet;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Création d'une Nouvelle Tâche");

        // Conteneur principal
        VBox mainContainer = new VBox(10);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setAlignment(Pos.CENTER);

        // Titre du formulaire
        Label titleLabel = new Label("Créer une Nouvelle Tâche");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Champ pour le nom de la tâche
        TextField taskNameField = new TextField();
        taskNameField.setPromptText("Nom de la tâche");

        // Champ pour la description de la tâche
        TextArea taskDescriptionArea = new TextArea();
        taskDescriptionArea.setPromptText("Description de la tâche");
        taskDescriptionArea.setPrefRowCount(3);

        // Champ pour le temps estimé
        TextField estimatedTimeField = new TextField();
        estimatedTimeField.setPromptText("Temps estimé (en heures)");

        // Liste déroulante pour choisir l'utilisateur
        ComboBox<String> userComboBox = new ComboBox<>();
        userComboBox.setPromptText("Attribuer à un utilisateur");

        // Récupérer la liste des utilisateurs existants de la base de données
        List<String> utilisateurs = getUtilisateurs();
        userComboBox.getItems().addAll(utilisateurs);

        // Champs pour les dates de début et de fin
        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Date de début");

        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setPromptText("Date de fin");

        // Bouton de création
        Button createTaskButton = new Button("Créer Tâche");
        createTaskButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        createTaskButton.setOnAction(e -> {
            String taskName = taskNameField.getText();
            String taskDescription = taskDescriptionArea.getText();
            String estimatedTime = estimatedTimeField.getText();
            String selectedUser = userComboBox.getValue();
            String startDate = startDatePicker.getValue() != null ? startDatePicker.getValue().toString() : "";
            String endDate = endDatePicker.getValue() != null ? endDatePicker.getValue().toString() : "";

            if (taskName.isEmpty() || estimatedTime.isEmpty() || selectedUser == null || startDate.isEmpty() || endDate.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis !");
            } else {
                if (isDateValid(startDate, endDate)) {
                    try {
                        int estimatedHours = Integer.parseInt(estimatedTime);
                        // Insérer la nouvelle tâche dans la base de données
                        ajouterTache(taskName, taskDescription, estimatedHours, selectedUser, startDate, endDate);
                        showAlert(Alert.AlertType.INFORMATION, "Succès", "La tâche a été créée avec succès !");
                        primaryStage.close(); // Fermer la fenêtre après la création
                    } catch (NumberFormatException ex) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Le temps estimé doit être un nombre valide !");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Les dates de la tâche ne sont pas valides par rapport aux dates du projet.");
                }
            }
        });

        mainContainer.getChildren().addAll(titleLabel, taskNameField, taskDescriptionArea, estimatedTimeField, userComboBox, startDatePicker, endDatePicker, createTaskButton);

        // Créer la scène et l'afficher
        Scene scene = new Scene(mainContainer, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode pour récupérer la liste des utilisateurs existants
    private List<String> getUtilisateurs() {
        List<String> utilisateurs = new ArrayList<>();
        try (Connection conn = SQLiteConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT Utl_nom FROM Utilisateur_Utl")) {

            while (rs.next()) {
                utilisateurs.add(rs.getString("Utl_nom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    // Méthode pour ajouter une tâche dans la base de données
    private void ajouterTache(String nom, String description, int temps, String utilisateur, String startDate, String endDate) {
        String sql = "INSERT INTO Tache_Tch (Tch_projet, Tch_nom, Tch_description, Tch_temps, Tch_affectation, Tch_date_debut, Tch_date_fin) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, projetNom);
            pstmt.setString(2, nom);
            pstmt.setString(3, description);
            pstmt.setInt(4, temps);
            pstmt.setString(5, utilisateur);
            pstmt.setString(6, startDate);
            pstmt.setString(7, endDate);

            pstmt.executeUpdate();
            System.out.println("Tâche ajoutée avec succès.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour vérifier si les dates de la tâche sont valides
    private boolean isDateValid(String startDate, String endDate) {
        return startDate.compareTo(dateDebutProjet) >= 0 && endDate.compareTo(dateFinProjet) <= 0;
    }

    // Méthode pour afficher des alertes
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
