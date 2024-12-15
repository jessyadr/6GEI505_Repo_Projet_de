import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FormulaireNouveauProjet extends Application {

    private String nomUtilisateur; // Nom de l'utilisateur connecté

    // Constructeur par défaut
    public FormulaireNouveauProjet() {
        this.nomUtilisateur = "Utilisateur inconnu"; // Valeur par défaut
    }

    // Constructeur avec un argument
    public FormulaireNouveauProjet(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Nouveau Projet - GestionAPP");

        // Configuration de l'interface utilisateur
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER_LEFT);

        // Champ pour le nom du projet
        TextField projectNameField = new TextField();
        projectNameField.setPromptText("Nom du projet");

        // Champ pour la description
        TextField projectDescriptionField = new TextField();
        projectDescriptionField.setPromptText("Description");

        // DatePicker pour la date de début et de fin
        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();

        // Menu déroulant pour choisir un responsable de projet
        ComboBox<String> responsibleComboBox = new ComboBox<>();
        responsibleComboBox.setPromptText("Sélectionner un responsable (Admin ou Gestionnaire)");
        loadEligiblePersons(responsibleComboBox);

        // Bouton Enregistrer
        Button saveButton = new Button("Créer Projet");
        saveButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        saveButton.setOnAction(e -> {
            String projectName = projectNameField.getText();
            String projectDescription = projectDescriptionField.getText();
            String startDate = startDatePicker.getValue() != null ? startDatePicker.getValue().toString() : null;
            String endDate = endDatePicker.getValue() != null ? endDatePicker.getValue().toString() : null;
            String responsible = responsibleComboBox.getValue();

            if (projectName.isEmpty() || projectDescription.isEmpty() || startDate == null || endDate == null || responsible == null) {
                showError("Tous les champs doivent être remplis.");
                return;
            }

            // Vérification que la date de début précède la date de fin
            if (startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
                showError("La date de début doit précéder la date de fin.");
                return;
            }

            // Ajouter le projet à la base de données
            addProjectToDatabase(projectName, projectDescription, startDate, endDate, responsible);
            primaryStage.close();
        });

        // Ajouter les composants à l'interface
        root.getChildren().addAll(
                new Label("Nom du Projet :"), projectNameField,
                new Label("Description :"), projectDescriptionField,
                new Label("Date de Début :"), startDatePicker,
                new Label("Date de Fin :"), endDatePicker,
                new Label("Responsable :"), responsibleComboBox,
                saveButton
        );

        // Configuration de la scène
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Charger les utilisateurs éligibles (administrateurs et gestionnaires de projet)
     * dans le menu déroulant.
     */
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

    /**
     * Ajouter un nouveau projet à la base de données.
     */
    private void addProjectToDatabase(String name, String description, String startDate, String endDate, String responsible) {
        String sql = "INSERT INTO Projet_Pro (Pro_nom, Pro_description, Pro_date_debut, Pro_date_fin, Pro_personne_assignee) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setString(3, startDate);
            pstmt.setString(4, endDate);
            pstmt.setString(5, responsible);
            pstmt.executeUpdate();

            System.out.println("Projet ajouté avec succès : " + name);

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du projet : " + e.getMessage());
        }
    }

    /**
     * Afficher un message d'erreur dans une boîte de dialogue.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
