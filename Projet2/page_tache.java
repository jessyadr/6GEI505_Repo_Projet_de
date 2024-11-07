import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

        Button homeButton = new Button("accueil");
        homeButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        Button projectButton = new Button("projet");
        projectButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        Button taskButton = new Button("tache"); // Bouton actif, pas de style ajouté
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

        Label userProfile = new Label("Jessy Tremblay(15690)");

        menuBar.getChildren().addAll(fileButton, homeButton, projectButton, taskButton, timesheetButton, resourcesButton, helpButton, spacerLeft, appTitle, spacerRight, userProfile);

        // Menu Latéral pour Liste des Tâches (Style similaire à "page_projet")
        VBox sideMenu = new VBox(10);
        sideMenu.setPadding(new Insets(10));
        sideMenu.setStyle("-fx-background-color: #455a64; -fx-text-fill: white;");

        Label listTitle = new Label("Liste des tâches");
        listTitle.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        Button task1Button = new Button("tâche 1");
        task1Button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray;");

        Button task2Button = new Button("tâche 2");
        task2Button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray;");

        Button subTask1Button = new Button("sous tâche 1");
        subTask1Button.setStyle("-fx-background-color: #CFD8DC; -fx-border-color: lightgray;");

        Button subTask2Button = new Button("sous tâche 2");
        subTask2Button.setStyle("-fx-background-color: #CFD8DC; -fx-border-color: lightgray;");

        Button subTask3Button = new Button("sous tâche 3");
        subTask3Button.setStyle("-fx-background-color: #CFD8DC; -fx-border-color: lightgray;");

        Button task3Button = new Button("tâche 3");
        task3Button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray;");

        Button task4Button = new Button("tâche 4");
        task4Button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray;");

        // Ajouter les boutons des tâches dans le menu latéral
        sideMenu.getChildren().addAll(listTitle, task1Button, task2Button, subTask1Button, subTask2Button, subTask3Button, task3Button, task4Button);

        // Bouton "page_temps" pour naviguer vers l'interface "page_temps"
        Button pageTempsButton = new Button("page_temps");
        pageTempsButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        pageTempsButton.setOnAction(e -> {
            // Créer une nouvelle instance de page_temps et afficher l'interface
            page_temps tempsPage = new page_temps();
            try {
                tempsPage.start(primaryStage); // Appel de la méthode start pour remplacer la scène
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Ajouter un conteneur pour placer le bouton "page_temps" tout en bas
        VBox bottomMenu = new VBox(10);
        bottomMenu.setPadding(new Insets(10, 0, 10, 10));
        bottomMenu.setAlignment(Pos.BOTTOM_LEFT);
        bottomMenu.getChildren().add(pageTempsButton);

        // Ajouter le menu latéral et le bouton "page_temps"
        BorderPane leftPane = new BorderPane();
        leftPane.setTop(new ScrollPane(sideMenu));
        leftPane.setBottom(bottomMenu);

        // Zone Centrale pour les Détails des Tâches
        VBox centralBox = new VBox(10);
        centralBox.setPadding(new Insets(10));

        // Barre de recherche
        HBox searchBox = new HBox(5);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        ImageView searchIcon = new ImageView(new Image("file:images\\recherche.jpg")); // Icône de recherche
        searchIcon.setFitHeight(20);
        searchIcon.setFitWidth(20);

        TextField searchField = new TextField();
        searchField.setPromptText("rechercher");

        searchBox.getChildren().addAll(searchIcon, searchField);

        // Image représentant le tableau des Détails des Tâches
        ImageView taskDetailsImage = new ImageView(new Image("file:images\\tache.png")); // Assurez-vous que l'image est disponible
        taskDetailsImage.setFitHeight(700);  // Ajuster la hauteur de l'image
        taskDetailsImage.setFitWidth(800);   // Ajuster la largeur de l'image
        taskDetailsImage.setPreserveRatio(true);

        centralBox.getChildren().addAll(searchBox, taskDetailsImage);

        // Ajouter le Logo DigiCraft en bas à droite
        ImageView logo = new ImageView(new Image("file:images/logo.png")); // Assurez-vous que l'image est disponible
        logo.setFitHeight(40);  // Redimensionner le logo pour qu'il soit plus petit
        logo.setFitWidth(40);
        StackPane.setAlignment(logo, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(logo, new Insets(10));

        // Assembler le Tout dans une Disposition
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setLeft(leftPane);
        root.setCenter(centralBox);

        StackPane stackPane = new StackPane(root, logo);
        Scene scene = new Scene(stackPane, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
