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

        // Créer le conteneur principal
        VBox mainContainer = new VBox(10);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(20));

        // Image de profil (avatar)
        ImageView profileImage = new ImageView(new Image("file:images\\avatar.png")); // ajout de l'image de l'avatar pour la connexion
        profileImage.setFitHeight(80);
        profileImage.setFitWidth(80);

        // Texte d'en-tête
        Label headerLabel = new Label("Se connecter à votre compte");
        headerLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Créer un GridPane pour les champs de connexion
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

        // Ajout des champs au GridPane
        gridPane.add(userTextField, 0, 0, 2, 1);
        gridPane.add(passwordField, 0, 1, 2, 1);

        // Options supplémentaires (Se souvenir de moi et Mot de passe oublié)
        CheckBox rememberMeCheckBox = new CheckBox("Se souvenir de moi");
        Hyperlink forgotPasswordLink = new Hyperlink("Mot de passe oublié?");
        HBox optionsBox = new HBox(10, rememberMeCheckBox, forgotPasswordLink);
        optionsBox.setAlignment(Pos.CENTER);

        // Label pour afficher les erreurs de connexion
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        // Bouton de connexion
        Button loginButton = new Button("Connexion");
        loginButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white; -fx-font-size: 14px;");
        loginButton.setOnAction(e -> {
            String username = userTextField.getText();
            String password = passwordField.getText();
            if (authenticateUser(username, password)) {
                page_accueil accueilPage = new page_accueil(username);
                try {
                    accueilPage.start(primaryStage); // Appel de la méthode start pour remplacer la scène
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                errorLabel.setText("Identifiant ou mot de passe incorrect");
            }
        });

        // Autre texte
        Label otherLabel = new Label("ou se connecter avec");
        otherLabel.setStyle("-fx-font-size: 10px;");

        // Boutons de connexion avec autres comptes
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

        // Assemblage du conteneur principal
        mainContainer.getChildren().addAll(profileImage, headerLabel, gridPane, optionsBox, loginButton, errorLabel, otherLabel, socialMediaBox);

        // Créer la scène et l'afficher
        Scene scene = new Scene(mainContainer, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode pour vérifier les identifiants de l'utilisateur
    private boolean authenticateUser(String username, String password) {
        Connection conn = SQLiteConnection.connect();
        if (conn == null) {
            System.out.println("Erreur: Impossible d'etablir la connexion à la base de données.");
            return false;
        }
        String sql = "SELECT * FROM Utilisateur_Utl WHERE Utl_id = ? AND Utl_mdp = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Retourne true si un utilisateur correspondant est trouvé
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
