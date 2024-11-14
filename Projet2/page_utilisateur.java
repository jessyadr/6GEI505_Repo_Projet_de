import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class page_utilisateur extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GestionAPP");

        // Barre de Menu en Haut
        HBox menuBar = new HBox(10);
        menuBar.setPadding(new Insets(10));
        menuBar.setStyle("-fx-background-color: #b0bec5;");  // Couleur de fond grise
        menuBar.setAlignment(Pos.CENTER_LEFT);

        Button fileButton = new Button("Fichier");
        fileButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        Button userButton = new Button("utilisateur"); // Bouton actif, pas de style ajouté
        
        Button projectButton = new Button("projet");
        projectButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        Button taskButton = new Button("tache");
        taskButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        Button timesheetButton = new Button("Feuille de temps");
        timesheetButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        Button resourcesButton = new Button("Ressources");
        resourcesButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        Button helpButton = new Button("Aide");
        helpButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        Label appTitle = new Label("GestionAPP");
        appTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: purple;");

        Region spacerLeft = new Region(); // Pour espacer entre les boutons de menu et le titre
        Region spacerRight = new Region(); // Pour espacer entre le titre et le profil utilisateur
        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        HBox.setHgrow(spacerRight, Priority.ALWAYS);

        Label userProfile = new Label("Nom utilisateur");
        userProfile.setStyle("-fx-border-color: purple; -fx-padding: 5;");

        menuBar.getChildren().addAll(fileButton, userButton, projectButton, taskButton, timesheetButton, resourcesButton, helpButton, spacerLeft, appTitle, spacerRight, userProfile);

        // Titre "Liste des utilisateurs"
        HBox userTitleBox = new HBox(10);
        userTitleBox.setStyle("-fx-background-color: #6A0DAD; -fx-padding: 12;");
        userTitleBox.setAlignment(Pos.CENTER_LEFT);

        ImageView menuIcon = new ImageView(new Image("file:images/menu.png")); // Icône de menu (remplacez par le bon chemin)
        menuIcon.setFitHeight(20);
        menuIcon.setFitWidth(20);

        Label userTitleLabel = new Label("Liste des utilisateurs");
        userTitleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;-fx-pref-width: 600px");

        userTitleBox.getChildren().addAll(menuIcon, userTitleLabel);

        // Liste des utilisateurs
        VBox usersBox = new VBox(10);
        usersBox.setPadding(new Insets(20));
        usersBox.setAlignment(Pos.TOP_LEFT);

        Button user1Button = new Button("Utilisateur 1");
        user1Button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray; -fx-pref-width: 600px;");

        Button user2Button = new Button("Utilisateur 2");
        user2Button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray; -fx-pref-width: 600px;");

        Button user3Button = new Button("Utilisateur 3");
        user3Button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray; -fx-pref-width: 600px;");

        Button user4Button = new Button("Utilisateur 4");
        user4Button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray; -fx-pref-width: 600px;");

        Button user5Button = new Button("Utilisateur 5");
        user5Button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray; -fx-pref-width: 600px;");

        // Ajouter les utilisateurs à la liste
        usersBox.getChildren().addAll(user1Button, user2Button, user3Button, user4Button, user5Button);

        // Barre de recherche à l'extrême droite
        HBox searchBox = new HBox(10);
        searchBox.setPadding(new Insets(10));
        searchBox.setAlignment(Pos.CENTER_RIGHT);

        ImageView searchIcon = new ImageView(new Image("file:images/recherche.jpg")); // Icône de recherche
        searchIcon.setFitHeight(20);
        searchIcon.setFitWidth(20);

        TextField searchField = new TextField();
        searchField.setPromptText("rechercher");
        searchField.setStyle("-fx-border-color: lightgray;");

        searchBox.getChildren().addAll(searchIcon, searchField);

        // Créer un conteneur pour le titre et la barre de recherche (sur la même ligne)
        HBox titleAndSearchBox = new HBox(10);
        titleAndSearchBox.setAlignment(Pos.CENTER_LEFT);
        titleAndSearchBox.setPadding(new Insets(10, 10, 10, 20));
        HBox.setHgrow(searchBox, Priority.ALWAYS);
        titleAndSearchBox.getChildren().addAll(userTitleBox, searchBox);

        // Ajouter le Logo DigiCraft en bas à droite
        ImageView logo = new ImageView(new Image("file:images/logo.png")); // Assurez-vous que l'image est disponible
        logo.setFitHeight(40);
        logo.setFitWidth(40);

        // Bouton "page_temps" pour naviguer vers l'interface "page_temps"
        Button pageTempsButton = new Button("page_temps");
        pageTempsButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        pageTempsButton.setOnAction(e -> {
            page_temps tempsPage = new page_temps();
            try {
                tempsPage.start(primaryStage); // Appel de la méthode start pour remplacer la scène
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox bottomLeftBox = new VBox(10, pageTempsButton);
        bottomLeftBox.setPadding(new Insets(10));
        bottomLeftBox.setAlignment(Pos.BOTTOM_LEFT);

        // Conteneur principal
        VBox mainContent = new VBox(10);
        mainContent.getChildren().addAll(titleAndSearchBox, usersBox);
        mainContent.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(mainContent);

        StackPane stackPane = new StackPane(root, logo, bottomLeftBox);
        StackPane.setAlignment(logo, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(logo, new Insets(10));

        StackPane.setAlignment(bottomLeftBox, Pos.BOTTOM_LEFT);
        StackPane.setMargin(bottomLeftBox, new Insets(10));

        // Créer la scène et l'afficher
        Scene scene = new Scene(stackPane, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
