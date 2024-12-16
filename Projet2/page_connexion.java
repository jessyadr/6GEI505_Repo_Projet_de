import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class page_connexion extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GestionAPP");

        // Conteneur principal
        VBox mainContainer = new VBox(10);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(20));

        // Image de profil (avatar)
        ImageView profileImage = new ImageView(new Image("file:images\\avatar.png"));
        profileImage.setFitHeight(80);
        profileImage.setFitWidth(80);

        // Texte d'en-tête
        Label headerLabel = new Label("Se connecter à votre compte");
        headerLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Conteneur pour les champs
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Champ Identifiant
        TextField userTextField = new TextField();
        userTextField.setPromptText("Identifiant de l'employé");

        // Champ Mot de passe
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");

        // Ajouter les champs au GridPane
        gridPane.add(userTextField, 0, 0, 2, 1);
        gridPane.add(passwordField, 0, 1, 2, 1);

        // Options supplémentaires
        CheckBox rememberMeCheckBox = new CheckBox("Se souvenir de moi");
        Hyperlink forgotPasswordLink = new Hyperlink("Mot de passe oublié?");
        HBox optionsBox = new HBox(10, rememberMeCheckBox, forgotPasswordLink);
        optionsBox.setAlignment(Pos.CENTER);

        // Label pour afficher les erreurs
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        // Bouton de connexion
        Button loginButton = new Button("Connexion");
        loginButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white; -fx-font-size: 14px;");

        // Gestion des événements
        userTextField.setOnAction(e -> {
            if (passwordField.getText().isEmpty()) {
                errorLabel.setText("Veuillez entrer votre mot de passe");
                passwordField.requestFocus(); // Déplace le focus vers le champ du mot de passe
            } else {
                loginButton.fire(); // Simule un clic sur le bouton de connexion
            }
        });

        passwordField.setOnAction(e -> {
            if (userTextField.getText().isEmpty()) {
                errorLabel.setText("Veuillez entrer un identifiant");
                userTextField.requestFocus(); // Déplace le focus vers le champ d'identifiant
            } else if (passwordField.getText().isEmpty()) {
                errorLabel.setText("Veuillez entrer votre mot de passe");
                passwordField.requestFocus();
            } else {
                loginButton.fire(); // Simule un clic sur le bouton de connexion
            }
        });

        loginButton.setOnAction(e -> {
            String username = userTextField.getText();
            String password = passwordField.getText();
            if (username.isEmpty()) {
                errorLabel.setText("Veuillez entrer un identifiant");
                userTextField.requestFocus();
            } else if (password.isEmpty()) {
                errorLabel.setText("Veuillez entrer votre mot de passe");
                passwordField.requestFocus();
            } else if (authenticateUser(username, password)) {
                page_accueil accueilPage = new page_accueil(username);
                try {
                    accueilPage.start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                errorLabel.setText("Identifiant ou mot de passe incorrect");
            }
        });

        // Texte supplémentaire
        Label otherLabel = new Label("ou se connecter avec");
        otherLabel.setStyle("-fx-font-size: 10px;");

        // Connexions avec d'autres comptes
        HBox socialMediaBox = new HBox(10);
        socialMediaBox.setAlignment(Pos.CENTER);

        ImageView appleIcon = new ImageView(new Image("file:images\\a.jpg"));
        ImageView googleIcon = new ImageView(new Image("file:images\\g.jpg"));
        ImageView facebookIcon = new ImageView(new Image("file:images\\f.jpg"));

        appleIcon.setFitHeight(30);
        appleIcon.setFitWidth(30);
        googleIcon.setFitHeight(30);
        googleIcon.setFitWidth(30);
        facebookIcon.setFitHeight(30);
        facebookIcon.setFitWidth(30);

        socialMediaBox.getChildren().addAll(appleIcon, googleIcon, facebookIcon);

        // Assemblage
        mainContainer.getChildren().addAll(profileImage, headerLabel, gridPane, optionsBox, loginButton, errorLabel, otherLabel, socialMediaBox);

        // Affichage
        Scene scene = new Scene(mainContainer, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean authenticateUser(String username, String password) {
        Connection conn = SQLiteConnection.connect();
        if (conn == null) {
            System.out.println("Erreur: Impossible d'établir la connexion à la base de données.");
            return false;
        }
        String sql = "SELECT * FROM Utilisateur_Utl WHERE Utl_id = ? AND Utl_mdp = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
