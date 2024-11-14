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

public class page_temps extends Application {

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

        Button homeButton = new Button("utilisateur");
        homeButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        Button projectButton = new Button("projet");
        projectButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        Button taskButton = new Button("tache");
        taskButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        Button timesheetButton = new Button("Feuille de temps"); // Bouton actif, pas de style ajouté
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

        Label userProfile = new Label("Alpha Gagnon (15690)");

        menuBar.getChildren().addAll(fileButton, homeButton, projectButton, taskButton, timesheetButton, resourcesButton, helpButton, spacerLeft, appTitle, spacerRight, userProfile);

        // Menu Latéral pour Liste des Jours
        VBox sideMenu = new VBox(10);
        sideMenu.setPadding(new Insets(10));
        sideMenu.setStyle("-fx-background-color: #455a64; -fx-text-fill: white;");

        Label listTitle = new Label("Semaine 13");
        listTitle.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        Button mondayButton = new Button("Lundi");
        mondayButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray;");

        Button tuesdayButton = new Button("Mardi");
        tuesdayButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray;");

        Button wednesdayButton = new Button("Mercredi");
        wednesdayButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray;");

        Button thursdayButton = new Button("Jeudi");
        thursdayButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray;");

        Button fridayButton = new Button("Vendredi");
        fridayButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray;");

        Button saturdayButton = new Button("Samedi");
        saturdayButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray;");

        Button sundayButton = new Button("Dimanche");
        sundayButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray;");

        sideMenu.getChildren().addAll(listTitle, mondayButton, tuesdayButton, wednesdayButton, thursdayButton, fridayButton, saturdayButton, sundayButton);

        // Bouton "page_connexion" pour naviguer vers l'interface "page_connexion"
        Button pageConnexionButton = new Button("page_connexion");
        pageConnexionButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        pageConnexionButton.setOnAction(e -> {
            // Créer une nouvelle instance de page_connexion et afficher l'interface
            page_connexion connexionPage = new page_connexion();
            try {
                connexionPage.start(primaryStage); // Appel de la méthode start pour remplacer la scène
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Ajouter un conteneur pour placer le bouton "page_connexion" en bas à gauche
        VBox bottomMenu = new VBox(10);
        bottomMenu.setPadding(new Insets(10, 0, 10, 10));
        bottomMenu.setAlignment(Pos.BOTTOM_LEFT);
        bottomMenu.getChildren().add(pageConnexionButton);

        // Ajouter le menu latéral et le bouton "page_connexion"
        BorderPane leftPane = new BorderPane();
        leftPane.setTop(new ScrollPane(sideMenu));
        leftPane.setBottom(bottomMenu);

        // Zone Centrale pour les Détails des Tâches de la Semaine
        VBox centralBox = new VBox(10);
        centralBox.setPadding(new Insets(10));

        // Barre de recherche
        HBox searchBox = new HBox(5);
        searchBox.setAlignment(Pos.CENTER_RIGHT);

        ImageView searchIcon = new ImageView(new Image("file:images\\recherche.jpg")); // Icône de recherche
        searchIcon.setFitHeight(20);
        searchIcon.setFitWidth(20);

        TextField searchField = new TextField();
        searchField.setPromptText("rechercher");

        searchBox.getChildren().addAll(searchIcon, searchField);

        // Tableau des Tâches de la Semaine - Remplacé par une Image
        ImageView tasksImage = new ImageView(new Image("file:images\\temps.png")); // Image représentant le tableau des tâches
        tasksImage.setFitHeight(700);
        tasksImage.setFitWidth(800);
        tasksImage.setPreserveRatio(true);

        centralBox.getChildren().addAll(searchBox, tasksImage);

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
