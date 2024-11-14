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

public class page_projet extends Application {

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

        Button projectButton = new Button("projet"); // Garder la couleur par défaut (pas de style ajouté)

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

        // Utiliser deux Regions distinctes pour espacer correctement
        Region spacerLeft = new Region(); // Pour espacer entre les boutons de menu et le titre
        Region spacerRight = new Region(); // Pour espacer entre le titre et le profil utilisateur
        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        HBox.setHgrow(spacerRight, Priority.ALWAYS);

        Label userProfile = new Label("Nom utilisateur");
        userProfile.setStyle("-fx-border-color: purple; -fx-padding: 5;");

        // Ajouter les composants au menuBar
        menuBar.getChildren().addAll(fileButton, userButton, projectButton, taskButton, timesheetButton, resourcesButton, helpButton, spacerLeft, appTitle, spacerRight, userProfile);

        // Titre pour la liste des projets
        HBox projectTitleBox = new HBox(10);
        projectTitleBox.setStyle("-fx-background-color: #6A0DAD; -fx-padding: 12;-fx-pref-width: 800px;");
        projectTitleBox.setAlignment(Pos.CENTER_LEFT);

        ImageView menuIcon = new ImageView(new Image("file:images/menu.png")); // Icône de menu (remplacez par le bon chemin)
        menuIcon.setFitHeight(20);
        menuIcon.setFitWidth(20);

        Label projectTitleLabel = new Label("LISTE DES PROJETS");
        projectTitleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;-fx-pref-width: 800px;");

        projectTitleBox.getChildren().addAll(menuIcon, projectTitleLabel);

        // Barre de recherche à l'extrême droite
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_RIGHT);
        searchBox.setPadding(new Insets(10));

        ImageView searchIcon = new ImageView(new Image("file:images/recherche.jpg")); // Icône de recherche (remplacez par le bon chemin)
        searchIcon.setFitHeight(20);
        searchIcon.setFitWidth(20);

        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher");
        searchField.setStyle("-fx-border-color: lightgray;");

        searchBox.getChildren().addAll(searchIcon, searchField);
        HBox.setHgrow(searchBox, Priority.ALWAYS); // Permet à la barre de recherche de s'étendre à l'extrême droite

        // Conteneur pour le titre des projets et la barre de recherche
        BorderPane titleAndSearchPane = new BorderPane();
        titleAndSearchPane.setPadding(new Insets(10));
        titleAndSearchPane.setLeft(projectTitleBox);
        titleAndSearchPane.setRight(searchBox);

        // Liste des projets
        VBox projectsBox = new VBox(10);
        projectsBox.setPadding(new Insets(20));
        projectsBox.setAlignment(Pos.CENTER);

        Button project1Button = new Button("Projet 1");
        project1Button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray; -fx-pref-width: 800px;");

        Button project2Button = new Button("Projet 2");
        project2Button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray; -fx-pref-width: 800px;");

        Button project3Button = new Button("Projet 3");
        project3Button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray; -fx-pref-width: 800px;");

        Button project4Button = new Button("Projet 4");
        project4Button.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: gray; -fx-pref-width: 800px;");

        // Ajouter les projets à la liste
        projectsBox.getChildren().addAll(project1Button, project2Button, project3Button, project4Button);

        // Barre de progression en bas
        HBox progressBarBox = new HBox(10);
        progressBarBox.setPadding(new Insets(10));
        progressBarBox.setAlignment(Pos.BOTTOM_LEFT);

        Label progressLabel = new Label(" ");
        Region progressBar = new Region();
        progressBar.setStyle("-fx-background-color: #6A0DAD; -fx-pref-width: 300px; -fx-pref-height: 10px;");

        progressBarBox.getChildren().addAll(progressLabel, progressBar);

        // Ajouter le Logo DigiCraft en bas à droite
        ImageView logo = new ImageView(new Image("file:images/logo.png")); // Assurez-vous que l'image est disponible
        logo.setFitHeight(40);  // Redimensionner le logo pour qu'il soit plus petit
        logo.setFitWidth(40);
        StackPane.setAlignment(logo, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(logo, new Insets(10));

        // Bouton "page_accueil" pour naviguer vers la page d'accueil
        Button pageAccueilButton = new Button("page_accueil");
        pageAccueilButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white; -fx-font-size: 14px;");
        pageAccueilButton.setOnAction(e -> {
            // Créer une nouvelle instance de page_accueil et afficher l'interface
            page_accueil accueilPage = new page_accueil();
            try {
                accueilPage.start(primaryStage); // Appel de la méthode start pour remplacer la scène
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Ajouter les composants au conteneur principal
        VBox mainBox = new VBox(10);
        mainBox.setPadding(new Insets(10));
        mainBox.getChildren().addAll(titleAndSearchPane, projectsBox, progressBarBox);

        // Assembler tout dans un BorderPane
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(new ScrollPane(mainBox));
        root.setBottom(new HBox(pageAccueilButton)); // Bouton ajouté en bas à gauche
        BorderPane.setAlignment(pageAccueilButton, Pos.BOTTOM_LEFT);
        BorderPane.setMargin(pageAccueilButton, new Insets(10));

        // Ajouter le logo et le reste dans un StackPane
        StackPane stackPane = new StackPane(root, logo);
        Scene scene = new Scene(stackPane, 1200, 700); // Augmenter la taille de la fenêtre pour plus d'espace
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
