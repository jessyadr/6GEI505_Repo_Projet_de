import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class page_accueil extends Application {

    private String nomUtilisateur;

    // Constructeur par défaut requis pour JavaFX
    public page_accueil() {
        this.nomUtilisateur = "Utilisateur";  // Vous pouvez définir une valeur par défaut ici.
    }

    // Constructeur pour recevoir le nom de l'utilisateur connecté
    public page_accueil(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("GestionAPP");

        // Barre de titre en haut avec barre de progression sur la même ligne
        HBox titleBar = new HBox(20);
        titleBar.setPadding(new Insets(10));
        titleBar.setAlignment(Pos.CENTER_LEFT);

        // Barre de progression
        HBox progressBar = new HBox();
        progressBar.setPadding(new Insets(10));
        progressBar.setStyle("-fx-background-color: #E0E0E0;");
        Region progress = new Region();
        progress.setStyle("-fx-background-color: #6A0DAD;");
        progress.setPrefSize(150, 10);
        progressBar.getChildren().add(progress);

        // Espacement flexible entre les éléments du titre
        Region spacerLeft = new Region();
        Region spacerRight = new Region();
        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        HBox.setHgrow(spacerRight, Priority.ALWAYS);

        // Titre de l'application
        Label appTitle = new Label("GestionAPP");
        appTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: purple;");

        // Affichage du nom de l'utilisateur connecté
        Label userLabel = new Label(nomUtilisateur);
        userLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #6A0DAD;");

        titleBar.getChildren().addAll(progressBar, spacerLeft, appTitle, spacerRight, userLabel);

        // Barre latérale de gauche
        VBox sideBar = new VBox(20);
        sideBar.setPadding(new Insets(20));
        sideBar.setStyle("-fx-background-color: #E0E0E0; -fx-pref-width: 200px;");
        sideBar.setAlignment(Pos.TOP_LEFT);

        // Boutons du menu latéral
        Button homeButton = new Button("Accueil");
        homeButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: purple; -fx-text-fill: #6A0DAD;");

        Button newUserButton = new Button("Nouvel utilisateur");
        newUserButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: purple; -fx-text-fill: #6A0DAD;");
        newUserButton.setOnAction(e -> {
            System.out.println("Bouton \"nouvel utilisateur\" cliqué");
            FormulaireNouvelUtilisateur formulaire = new FormulaireNouvelUtilisateur();
            formulaire.start(new Stage()); // Ouvre une nouvelle fenêtre pour créer un utilisateur
        });

        Button newProjectButton = new Button("Nouveau projet");
        newProjectButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: purple; -fx-text-fill: #6A0DAD;");
        newProjectButton.setOnAction(e -> {
            FormulaireNouveauProjet formulaireProjet = new FormulaireNouveauProjet();
            formulaireProjet.start(new Stage());
        });

        Button openButton = new Button("Ouvrir");
        openButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: purple; -fx-text-fill: #6A0DAD;");

        sideBar.getChildren().addAll(homeButton, newUserButton, newProjectButton, openButton);

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

        centralBox.getChildren().addAll(optionsCentralBox, templateLabel);

        // Assemblage du tout
        BorderPane root = new BorderPane();
        root.setTop(titleBar);
        root.setLeft(sideBar);
        root.setCenter(centralBox);

        // Créer la scène et l'afficher
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
