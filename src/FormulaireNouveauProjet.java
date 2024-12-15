import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FormulaireNouveauProjet extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Création d'un nouveau projet");

        // Conteneur principal
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Champs du formulaire
        Label nomLabel = new Label("Nom du projet :");
        TextField nomTextField = new TextField();
        nomTextField.setPromptText("Entrez le nom du projet");

        Label descriptionLabel = new Label("Description :");
        TextField descriptionTextField = new TextField();
        descriptionTextField.setPromptText("Entrez la description du projet");

        Label dateDebutLabel = new Label("Date de début :");
        DatePicker dateDebutPicker = new DatePicker();

        Label dateFinLabel = new Label("Date de fin :");
        DatePicker dateFinPicker = new DatePicker();

        Button createButton = new Button("Créer");
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red;");

        createButton.setOnAction(e -> {
            String nom = nomTextField.getText();
            String description = descriptionTextField.getText();
            String dateDebut = dateDebutPicker.getValue() != null ? dateDebutPicker.getValue().toString() : "";
            String dateFin = dateFinPicker.getValue() != null ? dateFinPicker.getValue().toString() : "";

            if (nom.isEmpty() || description.isEmpty() || dateDebut.isEmpty() || dateFin.isEmpty()) {
                messageLabel.setText("Veuillez remplir tous les champs.");
            } else if (dateDebut.compareTo(dateFin) > 0) {
                messageLabel.setText("La date de début ne peut pas être après la date de fin.");
            } else {
                boolean success = creerNouveauProjet(nom, description, dateDebut, dateFin);
                if (success) {
                    messageLabel.setStyle("-fx-text-fill: green;");
                    messageLabel.setText("Projet créé avec succès !");
                } else {
                    messageLabel.setStyle("-fx-text-fill: red;");
                    messageLabel.setText("Échec de la création du projet.");
                }
            }
        });

        // Ajouter les éléments au GridPane
        gridPane.add(nomLabel, 0, 0);
        gridPane.add(nomTextField, 1, 0);
        gridPane.add(descriptionLabel, 0, 1);
        gridPane.add(descriptionTextField, 1, 1);
        gridPane.add(dateDebutLabel, 0, 2);
        gridPane.add(dateDebutPicker, 1, 2);
        gridPane.add(dateFinLabel, 0, 3);
        gridPane.add(dateFinPicker, 1, 3);
        gridPane.add(createButton, 1, 4);
        gridPane.add(messageLabel, 1, 5);

        Scene scene = new Scene(gridPane, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean creerNouveauProjet(String nom, String description, String dateDebut, String dateFin) {
        String sql = "INSERT INTO Projet_Pro (Pro_nom, Pro_description, Pro_date_debut, Pro_date_fin) VALUES (?, ?, ?, ?)";
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nom);
            pstmt.setString(2, description);
            pstmt.setString(3, dateDebut);
            pstmt.setString(4, dateFin);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion dans la base de données : " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
