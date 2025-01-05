package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.TreeVisualizer;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class HomePageController {

    private Stage primaryStage;
    public static boolean boolStart = false;
    public static boolean boolArchaea = false;
    public static boolean boolEubacteria = false;
    public static boolean boolEukaryotes = false;
    public static boolean boolViruses = false;
    
    @FXML
    private Pane pane;
    
    @FXML
    private ImageView bkg;

    // Méthode pour définir la référence de la fenêtre principale
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    public static void setBoolStart(boolean value) {
        boolStart = value;
    }
    
    public static void setBoolArchaea(boolean value) {
        boolArchaea = value;
    }

    public static void setBoolEubacteria(boolean value) {
        boolEubacteria = value;
    }
    
    public static void setBoolEukaryotes(boolean value) {
        boolEukaryotes = value;
    }
    
    public static void setBoolViruses(boolean value) {
        boolViruses = value;
    }
    
    @FXML
    private void handleStartButton(ActionEvent event) {
        setBoolStart(true);
        
        TreeVisualizer treeVisualizer = new TreeVisualizer();
        treeVisualizer.start(new Stage());
        
        // fenêtre actuelle 
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleArchaeaButton(ActionEvent event) {
    	setBoolArchaea(true);
    	TreeVisualizer treeVisualizer = new TreeVisualizer();
        treeVisualizer.start(new Stage());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleEubacteriaButton(ActionEvent event) {
    	setBoolEubacteria(true);
    	TreeVisualizer treeVisualizer = new TreeVisualizer();
        treeVisualizer.start(new Stage());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleEukaryotesButton(ActionEvent event) {
    	setBoolEukaryotes(true);
    	TreeVisualizer treeVisualizer = new TreeVisualizer();
        treeVisualizer.start(new Stage());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleVirusesButton(ActionEvent event) {
    	setBoolViruses(true);
    	TreeVisualizer treeVisualizer = new TreeVisualizer();
        treeVisualizer.start(new Stage());
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
//    public void initialize() {
//    	Image icon = new Image("file:image/bkg.png");
//    	bkg = new ImageView(icon);
//    	bkg.setFitWidth(800); // Largeur
//    	bkg.setFitHeight(600); // Hauteur
//    	bkg.setOpacity(0.45); // Opacité complète
//    	
//    	pane.getChildren().add(bkg);
//    }
    
}


