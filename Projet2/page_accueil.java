import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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

        // Bouton "Déconnexion" pour naviguer vers l'interface "page_connexion"
        Button pageConnexionButton = new Button("Déconnexion");
        pageConnexionButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");
        pageConnexionButton.setOnAction(e -> {
            page_connexion connexionPage = new page_connexion();
            try {
                connexionPage.start(primaryStage); // Appel de la méthode start pour remplacer la scène
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Affichage du nom de l'utilisateur connecté
        Label userLabel = new Label(nomUtilisateur);
        userLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #6A0DAD;");

        // Ajouter les éléments à la barre de titre
        titleBar.getChildren().addAll(progressBar, spacerLeft, appTitle, spacerRight, pageConnexionButton, userLabel);

        // Barre latérale de gauche
        VBox sideBar = new VBox(20);
        sideBar.setPadding(new Insets(20));
        sideBar.setStyle("-fx-background-color: #E0E0E0; -fx-pref-width: 200px;");
        sideBar.setAlignment(Pos.TOP_LEFT);

        // Boutons du menu latéral
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

        Button openButton = new Button("Ouvrir Projet");
        openButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: purple; -fx-text-fill: #6A0DAD;");
        openButton.setOnAction(e -> {
            page_projet ProjetPage = new page_projet(nomUtilisateur);
            try {
                ProjetPage.start(primaryStage); // Appel de la méthode start pour remplacer la scène
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        sideBar.getChildren().addAll(newUserButton, newProjectButton, openButton);

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

        // Zone centrale (message de bienvenue)
        VBox welcomeBox = new VBox(20);
        welcomeBox.setAlignment(Pos.CENTER);

        Label welcomeLabel1 = new Label("BIENVENUE");
        Label welcomeLabel2 = new Label("SUR");
        Label welcomeLabel3 = new Label("GESTIONAPP");

        welcomeLabel1.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: #6A0DAD;");
        welcomeLabel2.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: #6A0DAD;");
        welcomeLabel3.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: #6A0DAD;");

        welcomeBox.getChildren().addAll(welcomeLabel1, welcomeLabel2, welcomeLabel3);

        StackPane centralPane = new StackPane(welcomeBox);
        centralPane.setAlignment(Pos.CENTER);

        // Assemblage du tout
        BorderPane root = new BorderPane();
        root.setTop(titleBar);
        root.setLeft(sideBar);
        root.setCenter(centralPane);

        // Créer la scène et l'afficher
        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
