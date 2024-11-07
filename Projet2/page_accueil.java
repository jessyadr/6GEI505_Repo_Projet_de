import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class page_accueil extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GestionAPP");

        // Barre de titre en haut avec barre de progression sur la même ligne (progression à gauche, titre à droite)
        HBox titleBar = new HBox(20);
        titleBar.setPadding(new Insets(10));
        titleBar.setAlignment(Pos.CENTER_LEFT);

        // Barre de progression
        HBox progressBar = new HBox();
        progressBar.setPadding(new Insets(10));
        progressBar.setStyle("-fx-background-color: #E0E0E0;");
        Region progress = new Region();
        progress.setStyle("-fx-background-color: #6A0DAD; -fx-pref-width: 150px; -fx-pref-height: 10px;");
        progressBar.getChildren().add(progress);

        Region spacer = new Region(); // Espacement flexible entre la barre de progression et le titre
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Titre de l'application
        Label appTitle = new Label("GestionAPP");
        appTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: purple;");

        titleBar.getChildren().addAll(progressBar, spacer, appTitle);

        // Barre latérale de gauche
        VBox sideBar = new VBox(20);
        sideBar.setPadding(new Insets(20));
        sideBar.setStyle("-fx-background-color: #E0E0E0; -fx-pref-width: 200px;");
        sideBar.setAlignment(Pos.TOP_LEFT);

        // Boutons du menu latéral
        Button homeButton = new Button("Accueil");
        homeButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: purple; -fx-text-fill: #6A0DAD;");

        Button newProjectButton = new Button("Nouveau projet");
        newProjectButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: purple; -fx-text-fill: #6A0DAD;");

        Button openButton = new Button("Ouvrir");
        openButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

        sideBar.getChildren().addAll(homeButton, newProjectButton, openButton);

        // Lignes séparatrices et autres options
        sideBar.getChildren().add(new Label("______________________"));
        Hyperlink saveLink = new Hyperlink("Enregistrer");
        Hyperlink saveAsLink = new Hyperlink("Enregistrer sous");
        Hyperlink printLink = new Hyperlink("Imprimer");
        Hyperlink shareLink = new Hyperlink("Partager");
        Hyperlink closeLink = new Hyperlink("Fermer");

        VBox optionsBox = new VBox(10, saveLink, saveAsLink, printLink, shareLink, closeLink);
        optionsBox.setAlignment(Pos.TOP_LEFT);
        sideBar.getChildren().add(optionsBox);

        // Zone de recherche
        HBox searchBox = new HBox(10);
        searchBox.setPadding(new Insets(10));
        searchBox.setAlignment(Pos.CENTER_LEFT);

        ImageView searchIcon = new ImageView(new Image("file:images\\recherche.jpg")); // Icône de recherche
        searchIcon.setFitHeight(20);
        searchIcon.setFitWidth(20);

        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher");

        searchBox.getChildren().addAll(searchIcon, searchField);

        // Zone centrale (choix de récents, PC, parcourir)
        VBox centralBox = new VBox(20);
        centralBox.setPadding(new Insets(20));

        Button recentButton = new Button("Récents");
        recentButton.setStyle("-fx-background-color: #CFD8DC;");

        Button pcButton = new Button("Ce PC");
        pcButton.setStyle("-fx-background-color: #CFD8DC;");

        Button browseButton = new Button("Parcourir");
        browseButton.setStyle("-fx-background-color: #CFD8DC;");

        VBox optionsCentralBox = new VBox(15, recentButton, pcButton, browseButton);
        optionsCentralBox.setAlignment(Pos.TOP_LEFT);

        Label templateLabel = new Label("Template projet");
        templateLabel.setStyle("-fx-border-color: lightgrey; -fx-padding: 5;");

        centralBox.getChildren().addAll(searchBox, optionsCentralBox, templateLabel);

        // Logo DigiCraft en bas à droite
        ImageView logo = new ImageView(new Image("file:images\\logo.png")); // Assurez-vous que l'image est disponible
        logo.setFitHeight(60);
        logo.setFitWidth(60);

        // Bouton "Page Tâche" en bas à gauche
        Button pageTacheButton = new Button("Page Tâche");
        pageTacheButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        pageTacheButton.setOnAction(e -> {
            page_tache tachePage = new page_tache();
            try {
                tachePage.start(primaryStage); // Appel de la méthode start pour remplacer la scène
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox bottomLeftBox = new VBox(10, pageTacheButton);
        bottomLeftBox.setPadding(new Insets(10));
        bottomLeftBox.setAlignment(Pos.BOTTOM_LEFT);

        // Assemblage du tout
        BorderPane root = new BorderPane();
        root.setTop(titleBar);
        root.setLeft(sideBar);
        root.setCenter(centralBox);

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
