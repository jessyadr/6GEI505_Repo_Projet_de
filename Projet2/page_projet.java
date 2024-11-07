import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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

        Button homeButton = new Button("accueil");
        homeButton.setStyle("-fx-background-color: #6A0DAD; -fx-text-fill: white;");

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

        Label userProfile = new Label("Alpha Tremblay (15690)");

        // Ajouter les composants au menuBar
        menuBar.getChildren().addAll(fileButton, homeButton, projectButton, taskButton, timesheetButton, resourcesButton, helpButton, spacerLeft, appTitle, spacerRight, userProfile);

        // Menu Latéral pour Liste des Projets
        VBox sideMenu = new VBox(10);
        sideMenu.setPadding(new Insets(10));
        sideMenu.setStyle("-fx-background-color: #455a64; -fx-text-fill: white;");

        Label listTitle = new Label("Liste des projets");
        listTitle.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        Button project1 = new Button("Projet 1");
        Button subProject1 = new Button("sous projet 1");
        subProject1.setStyle("-fx-background-color: #cfd8dc;");
        Button subProject2 = new Button("sous projet 2");
        subProject2.setStyle("-fx-background-color: #cfd8dc;");
        Button subProject3 = new Button("sous projet 3");
        subProject3.setStyle("-fx-background-color: #cfd8dc;");
        Button project2 = new Button("Projet 2");
        Button project3 = new Button("Projet 3");
        Button project4 = new Button("Projet 4");

        sideMenu.getChildren().addAll(listTitle, project1, subProject1, subProject2, subProject3, project2, project3, project4);

        // Zone Centrale (Diagramme de type Gantt simplifié)
        VBox ganttChart = new VBox(10);
        ganttChart.setPadding(new Insets(10));

        Label searchLabel = new Label("Rechercher");
        TextField searchField = new TextField();
        HBox searchBox = new HBox(5, searchLabel, searchField);
        searchBox.setAlignment(Pos.CENTER_RIGHT);

        // Image représentant le diagramme de Gantt
        ImageView ganttImage = new ImageView(new Image("file:images\\diagramme.png")); // Remplacez par le chemin correct
        ganttImage.setFitHeight(400);  // Ajuster la hauteur de l'image
        ganttImage.setFitWidth(600);   // Ajuster la largeur de l'image
        ganttImage.setPreserveRatio(true);

        ganttChart.getChildren().addAll(searchBox, ganttImage);

        // Graphique (Chart) sur le côté droit
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Projets");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Progression (%)");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Progression des Projets");
        barChart.setLegendVisible(false);
        barChart.setPrefWidth(300);  // Réduire la largeur du graphique pour le rendre plus petit

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        XYChart.Data<String, Number> data1 = new XYChart.Data<>("Projet 1", 30);
        XYChart.Data<String, Number> data2 = new XYChart.Data<>("Projet 2", 50);
        XYChart.Data<String, Number> data3 = new XYChart.Data<>("Projet 3", 70);

        // Appliquer la couleur violette aux barres
        data1.nodeProperty().addListener((obs, oldNode, newNode) -> newNode.setStyle("-fx-bar-fill: #6A0DAD;"));
        data2.nodeProperty().addListener((obs, oldNode, newNode) -> newNode.setStyle("-fx-bar-fill: #6A0DAD;"));
        data3.nodeProperty().addListener((obs, oldNode, newNode) -> newNode.setStyle("-fx-bar-fill: #6A0DAD;"));

        dataSeries.getData().addAll(data1, data2, data3);
        barChart.getData().add(dataSeries);

        // Ajouter le Logo DigiCraft en bas à droite
        ImageView logo = new ImageView(new Image("file:images/logo.png")); // Assurez-vous que l'image est disponible
        logo.setFitHeight(20);  // Redimensionner le logo pour qu'il soit plus petit
        logo.setFitWidth(15);
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

        // Ajouter le bouton "page_accueil" en bas à gauche
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setLeft(new ScrollPane(sideMenu));
        root.setCenter(ganttChart);
        root.setRight(barChart);
        root.setBottom(new HBox(pageAccueilButton)); // Bouton ajouté en bas à gauche
        BorderPane.setAlignment(pageAccueilButton, Pos.BOTTOM_LEFT);
        BorderPane.setMargin(pageAccueilButton, new Insets(10));

        // Assembler tout dans un StackPane
        StackPane stackPane = new StackPane(root, logo);
        Scene scene = new Scene(stackPane, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
