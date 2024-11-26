import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class page_accueil extends Application {

    private String nomUtilisateur;

    public page_accueil(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GestionAPP");

        // Barre de titre
        HBox titleBar = new HBox(20);
        titleBar.setPadding(new Insets(10));
        titleBar.setAlignment(Pos.CENTER_LEFT);

        Label appTitle = new Label("GestionAPP");
        appTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: purple;");

        // Bouton de nom d'utilisateur (à l'extrême droite)
        Button userButton = new Button(nomUtilisateur);
        userButton.setStyle("-fx-border-color: purple; -fx-padding: 5; -fx-text-fill: #6A0DAD;");

        titleBar.getChildren().addAll(appTitle, userButton);
        HBox.setHgrow(appTitle, Priority.ALWAYS);

        // Barre latérale de gauche
        VBox sideBar = new VBox(20);
        sideBar.setPadding(new Insets(20));
        sideBar.setStyle("-fx-background-color: #E0E0E0; -fx-pref-width: 200px;");
        sideBar.setAlignment(Pos.TOP_LEFT);

        Button homeButton = new Button("Accueil");
        homeButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: purple; -fx-text-fill: #6A0DAD;");

        Button newUserButton = new Button("Nouvel utilisateur");
        newUserButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        newUserButton.setOnAction(e -> openNewUserDialog());

        Button newProjectButton = new Button("Nouveau projet");
        newProjectButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: purple; -fx-text-fill: #6A0DAD;");

        sideBar.getChildren().addAll(homeButton, newUserButton, newProjectButton);

        // Zone centrale
        VBox centralBox = new VBox(20);
        centralBox.setPadding(new Insets(20));
        centralBox.setAlignment(Pos.TOP_LEFT);
        
        // Conteneur principal
        BorderPane root = new BorderPane();
        root.setTop(titleBar);
        root.setLeft(sideBar);
        root.setCenter(centralBox);

        // Créer la scène et l'afficher
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode pour ouvrir la fenêtre de création de nouvel utilisateur
    private void openNewUserDialog() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Créer un nouvel utilisateur");

        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(20));
        dialogVBox.setAlignment(Pos.CENTER);

        Label headerLabel = new Label("Créer un nouvel utilisateur");
        headerLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Nom d'utilisateur");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");

        Button createButton = new Button("Créer");
        createButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        createButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (!username.isEmpty() && !password.isEmpty()) {
                SQLiteConnection.connectAndAddUser(username, password);
                dialog.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez remplir tous les champs", ButtonType.OK);
                alert.showAndWait();
            }
        });

        dialogVBox.getChildren().addAll(headerLabel, usernameField, passwordField, createButton);

        Scene dialogScene = new Scene(dialogVBox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
