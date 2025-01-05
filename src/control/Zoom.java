package control;


import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;

import view.TreeVisualizer;


public class Zoom {
	
    public static final int DEFAULT_MAX_ALLOWED_DEPTH = 1000;

    private static Pane treePane;
    private static TreeVisualizer treeVisualizer;
    private final Map<Integer, List<Integer>> nodeParentMap;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;
    public static double zoomFactor = 1.0;
    public static int currentZoomLevel = 2;
    private static int maxAllowedDepth = 1000;
    private static int minAllowedDepth = 0;  

    public Zoom() {
        this(null, null, new HashMap<>());
    }
 

    /**
    public Zoom(Pane treePane, TreeVisualizer treeVisualizer, Map<Integer, List<Integer>> nodeParentMap) {
        this.treePane = treePane;
        this.treeVisualizer = treeVisualizer;
        this.maxAllowedDepth = nodeParentMap.size();
     // Ajoutez les gestionnaires d'événements pour le drag-and-drop
        treePane.setOnMousePressed(this::handleMousePressed);
        treePane.setOnMouseDragged(this::handleMouseDragged);
    }*/
    
    public Zoom(Pane treePane, TreeVisualizer treeVisualizer, Map<Integer, List<Integer>> nodeParentMap) {
        this.treePane = treePane;
        this.treeVisualizer = treeVisualizer;
		this.nodeParentMap = nodeParentMap;
        this.maxAllowedDepth = nodeParentMap.size();
        this.minAllowedDepth = 0; 
        treePane.setOnMousePressed(this::handleMousePressed);
        treePane.setOnMouseDragged(this::handleMouseDragged);
    }
    

    public HBox createZoomButtons() {
        Button zoomInButton = new Button("Zoom In");
        Button zoomOutButton = new Button("Zoom Out");

        zoomInButton.setOnAction(event -> {
            zoomIn();
            treeVisualizer.updateZoom(treePane);
        });

        zoomOutButton.setOnAction(event -> {
            zoomOut();
            treeVisualizer.updateZoom(treePane);
        });
        
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(zoomInButton, zoomOutButton);
        buttonBox.setAlignment(Pos.CENTER);
        return buttonBox;
    }


    

    public static void zoomIn() {
        try {
			if (currentZoomLevel < maxAllowedDepth) {
			    zoomFactor += 1.5;
			    zoom();
			    currentZoomLevel++;
			    treeVisualizer.updateZoom(treePane);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }


    public static void zoomOut() {
        try {
			if (currentZoomLevel - 2 > minAllowedDepth) {
			    zoomFactor -= 1.5;
			    zoom();
			    currentZoomLevel--;
			    treeVisualizer.updateZoom(treePane);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

    }

    private static void zoom() {
        treePane.setScaleX(zoomFactor);
        treePane.setScaleY(zoomFactor);
    }
    
 // Méthode pour gérer l'événement de clic de souris
    private void handleMousePressed(MouseEvent event) {
        orgSceneX = event.getSceneX();
        orgSceneY = event.getSceneY();
        orgTranslateX = treePane.getTranslateX();
        orgTranslateY = treePane.getTranslateY();
    }

    // Méthode pour gérer l'événement de glissement de souris
    private void handleMouseDragged(MouseEvent event) {
        double offsetX = event.getSceneX() - orgSceneX;
        double offsetY = event.getSceneY() - orgSceneY;
        double newTranslateX = orgTranslateX + offsetX;
        double newTranslateY = orgTranslateY + offsetY;

        treePane.setTranslateX(newTranslateX);
        treePane.setTranslateY(newTranslateY);
    }
    
    
    //Réinitialiser
	public static void resetTree() {
	    Zoom.currentZoomLevel = 2;
	    Zoom.zoomFactor = 1.0;
	    zoom();

	    treePane.getChildren().clear();
	    TreeVisualizer treeVisualizer = new TreeVisualizer();
	    treeVisualizer.loadNodeNames();
	    treeVisualizer.loadNodeParentMap();
	    treeVisualizer.calculateNodeCoordinates(treePane);

	    // Recentrez le pane de l'arbre dans le conteneur parent
	    treePane.setTranslateX(0);
	    treePane.setTranslateY(0);
	}
	
	/**
	public static void movePaneLeft(double distance) {
	    treePane.setTranslateX(treePane.getTranslateX() + distance);
	}

	public static void movePaneRight(double distance) {
	    treePane.setTranslateX(treePane.getTranslateX() - distance);
	}
	
	public static void movePaneDown(double distance) {
	    treePane.setTranslateY(treePane.getTranslateY() - distance);
	}
	
	public static void movePaneUp(double distance) {
	    treePane.setTranslateY(treePane.getTranslateY() + distance);
	}*/
	public static void movePane(double distance, double distance2) {
		treePane.setTranslateX(treePane.getTranslateX() + distance);
		treePane.setTranslateY(treePane.getTranslateY() + distance2);
	}


}

