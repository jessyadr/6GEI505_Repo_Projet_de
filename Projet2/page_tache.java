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

public class page_tache extends Application {

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

        Button userButton = new Button("utilisateur");
        userButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        Button projectButton = new Button("projet");
        projectButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        Button taskButton = new Button("tache"); // Garder le bouton actif sans style supplémentaire

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

        // Titre "Liste des tâches"
        HBox taskTitleBox = new HBox(10);
        taskTitleBox.setStyle("-fx-background-color: #6A0DAD; -fx-padding: 12;");
        taskTitleBox.setAlignment(Pos.CENTER_LEFT);

        ImageView menuIcon = new ImageView(new Image("file:images/menu.png")); // Icône de menu (remplacez par le bon chemin)
        menuIcon.setFitHeight(20);
        menuIcon.setFitWidth(20);

        Label taskTitleLabel = new Label("Liste des tâches");
        taskTitleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;-fx-pref-width: 600px");

        taskTitleBox.getChildren().addAll(menuIcon, taskTitleLabel);

        // Liste des tâches
        VBox tasksBox = new VBox(10);
        tasksBox.setPadding(new Insets(20));
        tasksBox.setAlignment(Pos.TOP_LEFT);

        Button task1Button = new Button("tâche 1");
        task1Button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray; -fx-pref-width: 600px;");

        Button task2Button = new Button("tâche 2");
        task2Button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray; -fx-pref-width: 600px;");

        Button task3Button = new Button("tâche 3");
        task3Button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray; -fx-pref-width: 600px;");

        Button task4Button = new Button("tâche 4");
        task4Button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray; -fx-pref-width: 600px;");

        // Ajouter les tâches à la liste
        tasksBox.getChildren().addAll(task1Button, task2Button, task3Button, task4Button);

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
        titleAndSearchBox.getChildren().addAll(taskTitleBox, searchBox);

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
        mainContent.getChildren().addAll(titleAndSearchBox, tasksBox);
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
