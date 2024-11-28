import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FormulaireNouveauProjet extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Création d'un nouveau projet");

        // Créer le conteneur principal pour le formulaire
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Ajouter les champs du formulaire
        Label nomLabel = new Label("Nom du projet :");
        TextField nomTextField = new TextField();
        nomTextField.setPromptText("Entrez le nom du projet");

        Label descriptionLabel = new Label("Description :");
        TextField descriptionTextField = new TextField();
        descriptionTextField.setPromptText("Entrez la description du projet");

        Button createButton = new Button("Créer");
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red;");  // Pour rendre les messages d'erreur visibles

        createButton.setOnAction(e -> {
            String nom = nomTextField.getText();
            String description = descriptionTextField.getText();

            if (nom.isEmpty() || description.isEmpty()) {
                messageLabel.setText("Veuillez remplir tous les champs.");
            } else {
                boolean success = creerNouveauProjet(nom, description);
                if (success) {
                    messageLabel.setStyle("-fx-text-fill: green;");  // Si succès, message en vert
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
        gridPane.add(createButton, 1, 2);
        gridPane.add(messageLabel, 1, 3);

        // Créer la scène et l'afficher
        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode pour insérer un nouveau projet dans la base de données
    private boolean creerNouveauProjet(String nom, String description) {
        String sql = "INSERT INTO Projet_Pro (Pro_nom, Pro_description) VALUES (?, ?)";

        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Définir les paramètres
            pstmt.setString(1, nom);
            pstmt.setString(2, description);

            // Exécuter l'insertion
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion dans la base de données : " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
