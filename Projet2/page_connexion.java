import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

        // Bouton de connexion
        Button loginButton = new Button("Connexion");
        loginButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white; -fx-font-size: 14px;");
        loginButton.setOnAction(e -> {
            String username = userTextField.getText();
            page_accueil accueilPage = new page_accueil(username);
            try {
                accueilPage.start(primaryStage); // Appel de la méthode start pour remplacer la scène
            } catch (Exception ex) {
                ex.printStackTrace();
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
        mainContainer.getChildren().addAll(profileImage, headerLabel, gridPane, optionsBox, loginButton, otherLabel, socialMediaBox);

        // Créer la scène et l'afficher
        Scene scene = new Scene(mainContainer, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
