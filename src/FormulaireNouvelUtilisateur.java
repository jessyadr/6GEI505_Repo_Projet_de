import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FormulaireNouvelUtilisateur extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Création d'un nouvel utilisateur");

        // Créer le conteneur principal pour le formulaire
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Ajouter les champs du formulaire
        Label userLabel = new Label("Identifiant :");
        TextField userTextField = new TextField();
        userTextField.setPromptText("Entrez l'identifiant du nouvel utilisateur");

        Label passwordLabel = new Label("Mot de passe :");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Entrez le mot de passe");

        Label nomLabel = new Label("Nom :");
        TextField nomTextField = new TextField();
        nomTextField.setPromptText("Entrez le nom du nouvel utilisateur");

        Label prenomLabel = new Label("Prénom :");
        TextField prenomTextField = new TextField();
        prenomTextField.setPromptText("Entrez le prénom du nouvel utilisateur");

        Label roleLabel = new Label("Rôle :");
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("administrateur", "gestionnaire de projet", "employe");
        roleComboBox.setPromptText("Sélectionner le rôle du nouvel utilisateur");

        // Label pour afficher le statut de la création
        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-text-fill: red;");

        Button createButton = new Button("Créer");
        createButton.setOnAction(e -> {
            String username = userTextField.getText();
            String password = passwordField.getText();
            String nom = nomTextField.getText();
            String prenom = prenomTextField.getText();
            String role = roleComboBox.getValue();

            // Appel à la méthode pour créer un utilisateur
            if (username.isEmpty() || password.isEmpty() || nom.isEmpty() || prenom.isEmpty() || role == null) {
                statusLabel.setText("Veuillez remplir tous les champs.");
            } else {
                boolean success = creerNouvelUtilisateur(username, password, nom, prenom, role);
                if (success) {
                    statusLabel.setText("Utilisateur créé avec succès !");
                    statusLabel.setStyle("-fx-text-fill: green;");  // Couleur verte pour le succès
                } else {
                    statusLabel.setText("Échec de la création de l'utilisateur.");
                    statusLabel.setStyle("-fx-text-fill: red;");  // Couleur rouge pour l'échec
                }
            }
        });

        // Ajouter les éléments au GridPane
        gridPane.add(userLabel, 0, 0);
        gridPane.add(userTextField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(nomLabel, 0, 2);
        gridPane.add(nomTextField, 1, 2);
        gridPane.add(prenomLabel, 0, 3);
        gridPane.add(prenomTextField, 1, 3);
        gridPane.add(roleLabel, 0, 4);
        gridPane.add(roleComboBox, 1, 4);
        gridPane.add(createButton, 1, 5);
        gridPane.add(statusLabel, 1, 6);

        // Créer la scène et l'afficher
        Scene scene = new Scene(gridPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode pour insérer un nouvel utilisateur dans la base de données
    private boolean creerNouvelUtilisateur(String username, String password, String nom, String prenom, String role) {
        String sql = "INSERT INTO Utilisateur_Utl (Utl_id, Utl_mdp, Utl_nom, Utl_prenom, Utl_role) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Définir les paramètres
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, nom);
            pstmt.setString(4, prenom);
            pstmt.setString(5, role);

            // Exécuter l'insertion
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;  // Si une ligne a été insérée, cela signifie un succès

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion dans la base de données : " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
