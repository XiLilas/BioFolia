package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.NodeData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import control.HomePageController;
import control.Zoom;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;


public class TreeVisualizer extends Application {

    private static final String NODES_FILE_PATH = "data/treeoflife_nodes.csv";
    private static final String LINKS_FILE_PATH = "data/treeoflife_links.csv";

    private Map<Integer, String> nodeNames = new HashMap<>();
    public static Map<Integer, List<Integer>> nodeParentMap = new HashMap<>();
    private Map<Integer, Double> nodeXCoordinates = new HashMap<>();
    private Map<Integer, Double> nodeYCoordinates = new HashMap<>();
    public static List<NodeData> nodeList = new ArrayList<>();
    
    public static final int width = 800;
    public static final int height = 600;
    
    private int maxDepth = 2;
    
    @Override
    public void start(Stage primaryStage) {
    	homePage(primaryStage);
        if (HomePageController.boolStart) {
        	loadNodeNames();
	        loadNodeParentMap();
	        //System.out.print("parent : " + nodeParentMap +"\n");
	        Pane pane = new Pane();
	        calculateNodeCoordinates(pane);
	    	try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("tree.fxml"));
	            SplitPane root = loader.load();
	            Zoom zoom = new Zoom(pane, this, nodeParentMap);
	            Pane fxmlTreePane = (Pane) loader.getNamespace().get("treePane");
	            //VBox vbox = new VBox(10);
	            //root.getChildren().addAll(pane, zoom.createZoomButtons());
	            //vbox.getChildren().addAll(pane);
	            fxmlTreePane.getChildren().addAll(pane);
	            Scene scene = new Scene(root, width, height);
	            primaryStage.setScene(scene);
	            primaryStage.setTitle("Tree Visualizer");
	            primaryStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    	HomePageController.setBoolStart(false);
        } else if (HomePageController.boolArchaea) {
            loadAndShowTree(primaryStage, -550, 300);
            HomePageController.setBoolArchaea(false);
        } else if (HomePageController.boolEubacteria) {
            loadAndShowTree(primaryStage, 250, -350);
            HomePageController.setBoolEubacteria(false);
        } else if (HomePageController.boolEukaryotes) {
            loadAndShowTree(primaryStage, 250, 350);
            HomePageController.setBoolEukaryotes(false);
        } else if (HomePageController.boolViruses) {
            loadAndShowTree(primaryStage, -550, -350);
            HomePageController.setBoolViruses(false);
        }
        /**
    	if(HomePageController.boolStart) {
	        // Load node names and parent-child relationships
	        loadNodeNames();
	        loadNodeParentMap();
	        System.out.print("parent : " + nodeParentMap +"\n");
	        
	        Pane pane = new Pane();
	        
	        calculateNodeCoordinates(pane);
	        
	    	// Charger le fichier FXML
	    	try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("tree.fxml"));
	            SplitPane root = loader.load();
	           
	            Zoom zoom = new Zoom(pane, this, nodeParentMap);
	            
	            Pane fxmlTreePane = (Pane) loader.getNamespace().get("treePane");
	            
	            //VBox vbox = new VBox(10);
	            //root.getChildren().addAll(pane, zoom.createZoomButtons());
	            //vbox.getChildren().addAll(pane);
	            
	            // Add the tree pane and zoom controls to the FXML tree pane
	            fxmlTreePane.getChildren().addAll(pane);
	
	            // Show the scene
	            Scene scene = new Scene(root, width, height);
	            primaryStage.setScene(scene);
	            primaryStage.setTitle("Tree Visualizer");
	            primaryStage.show();
	
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    	HomePageController.setBoolStart(false);
	    	
    	}else if(HomePageController.boolArchaea) {
    		loadNodeNames();
	        loadNodeParentMap();
	        System.out.print("parent : " + nodeParentMap +"\n");
	        
	        Pane pane = new Pane();
	    	try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("tree.fxml"));
	            SplitPane root = loader.load();
	            
	            Zoom zoom = new Zoom(pane, this, nodeParentMap);
	        	Zoom.zoomIn();
	        	Zoom.movePaneRight(550);
	        	Zoom.movePaneUp(300);
	            calculateNodeCoordinates(pane);
	            
	            Pane fxmlTreePane = (Pane) loader.getNamespace().get("treePane");

	            fxmlTreePane.getChildren().addAll(pane);

	            // Show the scene
	            Scene scene = new Scene(root, width, height);
	            primaryStage.setScene(scene);
	            primaryStage.setTitle("Tree Visualizer");
	            primaryStage.show();
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    	HomePageController.setBoolArchaea(false);
    	}else if(HomePageController.boolEubacteria) {
    		
    		loadNodeNames();
	        loadNodeParentMap();
	        System.out.print("parent : " + nodeParentMap +"\n");
	        Pane pane = new Pane();
	    	try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("tree.fxml"));
	            SplitPane root = loader.load();
	            Zoom zoom = new Zoom(pane, this, nodeParentMap);
	        	Zoom.zoomIn();
	        	Zoom.movePaneLeft(250);
	        	Zoom.movePaneDown(350);
	            calculateNodeCoordinates(pane);
	            Pane fxmlTreePane = (Pane) loader.getNamespace().get("treePane");
	            fxmlTreePane.getChildren().addAll(pane);
	            Scene scene = new Scene(root, width, height);
	            primaryStage.setScene(scene);
	            primaryStage.setTitle("Tree Visualizer");
	            primaryStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    	HomePageController.setBoolEubacteria(false);
    		
    	}else if(HomePageController.boolEukaryotes) {
    		
    		loadNodeNames();
	        loadNodeParentMap();
	        System.out.print("parent : " + nodeParentMap +"\n");
	        Pane pane = new Pane();
	    	try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("tree.fxml"));
	            SplitPane root = loader.load();
	            Zoom zoom = new Zoom(pane, this, nodeParentMap);
	        	Zoom.zoomIn();
	        	Zoom.movePaneLeft(250);
	        	Zoom.movePaneUp(350);
	            calculateNodeCoordinates(pane);
	            Pane fxmlTreePane = (Pane) loader.getNamespace().get("treePane");
	            fxmlTreePane.getChildren().addAll(pane);
	            // Show the scene
	            Scene scene = new Scene(root, width, height);
	            primaryStage.setScene(scene);
	            primaryStage.setTitle("Tree Visualizer");
	            primaryStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    	HomePageController.setBoolEukaryotes(false);
    		
    	}else if(HomePageController.boolViruses) {
    		
    		loadNodeNames();
	        loadNodeParentMap();
	        System.out.print("parent : " + nodeParentMap +"\n");
	        Pane pane = new Pane();
	    	try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("tree.fxml"));
	            SplitPane root = loader.load();
	            Zoom zoom = new Zoom(pane, this, nodeParentMap);
	        	Zoom.zoomIn();
	        	Zoom.movePaneRight(550);
	        	Zoom.movePaneDown(350);
	            calculateNodeCoordinates(pane);
	            Pane fxmlTreePane = (Pane) loader.getNamespace().get("treePane");
	            fxmlTreePane.getChildren().addAll(pane);
	            // Show the scene
	            Scene scene = new Scene(root, width, height);
	            primaryStage.setScene(scene);
	            primaryStage.setTitle("Tree Visualizer");
	            primaryStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    	HomePageController.setBoolViruses(false);	
    	}*/
    }
    
    private void loadAndShowTree(Stage primaryStage, double moveX, double moveY) {
        loadNodeNames();
        loadNodeParentMap();
        System.out.print("parent : " + nodeParentMap + "\n");
        Pane pane = new Pane();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tree.fxml"));
        try {
            SplitPane root = loader.load();
            Zoom zoom = new Zoom(pane, this, nodeParentMap);
            Zoom.zoomIn();
            Zoom.movePane(moveX, moveY);
            calculateNodeCoordinates(pane);
            Pane fxmlTreePane = (Pane) loader.getNamespace().get("treePane");
            fxmlTreePane.getChildren().addAll(pane);
            Scene scene = new Scene(root, width, height);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Tree Visualizer");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public Map<Integer, String> getNodeNames() {
        return nodeNames;
    }

    public Map<Integer, Double> getNodeXCoordinates() {
        return nodeXCoordinates;
    }

    public Map<Integer, Double> getNodeYCoordinates() {
        return nodeYCoordinates;
    }
    
 // Méthode pour afficher la page home_page.fxml
    private void homePage(Stage primaryStage) {
        try {
        	FXMLLoader home = new FXMLLoader(getClass().getResource("home_page.fxml"));
        	Parent homeRoot = home.load();
            
            HomePageController homeController = home.getController();
            
            homeController.setPrimaryStage(primaryStage);

            Scene homeScene = new Scene(homeRoot);

            primaryStage.setScene(homeScene);
            primaryStage.setTitle("Home Page");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    //On met (id, name) de chaque noeud dans nodeNames (de plus charger les données dans nodeList)
    public void loadNodeNames() {
        try {
            BufferedReader nodesReader = new BufferedReader(new FileReader(NODES_FILE_PATH));
            nodesReader.readLine();
            String line;
            while ((line = nodesReader.readLine()) != null) {
            	//System.out.print("line:"+ line + "\n");
                String[] parts = line.split(",");
                
                if (parts.length >= 2) {
                	//System.out.print("parts:"+ parts + "\n");
                    int nodeId = Integer.parseInt(parts[0].trim());
                    //System.out.print("nodeID : " + nodeId + "\n");
                    String nodeName = parts[1].trim();
                    nodeNames.put(nodeId, nodeName);
                }
            }
            nodesReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        nodeList = NodeData.createNodeDataListFromData(NODES_FILE_PATH);
        //System.out.print("\n" + "nodeList : " + nodeList + "\n");
    }
    
    //Dans nodeParentMap les éléments suit le format (childId, parentIf) .
    public void loadNodeParentMap() {
        try {
            BufferedReader linksReader = new BufferedReader(new FileReader(LINKS_FILE_PATH));
            String line;
            boolean isFirstLine = true; 
            while ((line = linksReader.readLine()) != null) {
                if (isFirstLine) { 
                    isFirstLine = false; 
                    continue; 
                }
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    int parentId = Integer.parseInt(parts[0].trim());
                    int childId = Integer.parseInt(parts[1].trim());
                    /**if(parentId == 1) {
                    	System.out.print("parent" + parentId +", enfant" + childId + "\n");
                    	nodeParentMap.put(parentId, childId);
                    }*/

                    nodeParentMap.computeIfAbsent(parentId, k -> new ArrayList<>());

                    nodeParentMap.get(parentId).add(childId);
                }
            }
            linksReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }


    
 // Espacement horizontal entre les nœuds
    //private static final double HORIZONTAL_SPACING = 100.0;

    // Espacement vertical entre les nœuds
    //private static final double VERTICAL_SPACING = 100.0;

    
    //Initialiser la position du 1er node et calculer la positions de leur enfants.
    public void calculateNodeCoordinates(Pane pane) {
    	int oldestParentId = findOldestParentId();

        double centerX = width/2;
        double centerY = height/2;
        
        calculateNodeCoordinatesRecursive(oldestParentId, centerX, centerY, Math.PI, 0, pane);
    }
    
    
    
    private void calculateNodeCoordinatesRecursive(int parentId, double parentX, double parentY, double angleOffset, int depth, Pane pane) {
        nodeXCoordinates.put(parentId, parentX);
        nodeYCoordinates.put(parentId, parentY);

        List<Integer> children = nodeParentMap.get(parentId);

        if (children != null && !children.isEmpty()) {
            int numChildren = children.size();
            double maxRadius = 150.0; 
            double radius;

            if (depth == 0) {
                radius = maxRadius;
            } else if (depth == 1) {
                radius = maxRadius /2.0;
            } else {
                radius = maxRadius / (2.0 * (depth - 1));
            }
            
            double angleStep;
            if (depth == 0) {
                angleStep = 2 * Math.PI / numChildren;
            }else {
                angleStep = Math.PI / (2 * (numChildren - 1));
            }

            for (int i = 0; i < numChildren; i++) {
                double angle = angleOffset - (Math.PI / 4) + (i * angleStep);
                double childX = parentX + radius * Math.cos(angle);
                double childY = parentY + radius * Math.sin(angle);

                calculateNodeCoordinatesRecursive(children.get(i), childX, childY, angle, depth + 1, pane);

                Line line = new Line(parentX, parentY, childX, childY);
                double opacity = calculateOpacity(depth);
                double lineWidth = 1.5 / Zoom.zoomFactor;
                line.setStrokeWidth(lineWidth);
                line.setStroke(Color.BLACK.deriveColor(0, 1, 1, opacity));
                pane.getChildren().add(line);
            }
            
            // trois générations
            if (depth <= maxDepth && depth >= maxDepth-2) {
            	double fontSize = 15/ Zoom.zoomFactor; 
	            Label parentLabel = new Label(nodeNames.get(parentId));
	            parentLabel.setLayoutX(parentX);
	            parentLabel.setLayoutY(parentY);
	            System.out.print("id" + parentId+" ");
	            NodeData node = getNodeDataById(parentId);
	            System.out.print("node" + node + " ");
	            Tooltip tooltip = new Tooltip();
	            if(node != null) {
		            String tooltipText = "Node Name: " + node.getName() + "\n";
		            String parentName = findParentNameById(nodeList, nodeParentMap, node.getId());
		            if (parentName != null) {
		                tooltipText += "Parent: " + parentName + ", \n";
		            }
		            tooltipText += "Child Nodes: " + node.getChildNodes() + ", \n";
		            tooltipText += "Leaf Node: " + node.getLeafNode() + ", \n";
		            tooltipText += "Tolorg Link: " + node.getTolorgLink() + ", \n";
		            tooltipText += "Extinct: " + node.getExtinct() + ", \n";
		            tooltipText += "Confidence: " + node.getConfidence() + ", \n";
		            tooltipText += "Phylesis: " + node.getPhylesis() + ", \n";
		            
		            tooltip.setText(tooltipText); 
	            }else {
	            	tooltip.setText("null");
	            }

	            parentLabel.setOnMouseClicked(event -> {
	            	//tooltip.setPrefWidth(100 / Zoom.zoomFactor);
	                //tooltip.setPrefHeight(100 / Zoom.zoomFactor);
	                tooltip.show(parentLabel, event.getScreenX(), event.getScreenY()+10);
	            });
	            parentLabel.setOnMouseExited(event -> {
	                tooltip.hide();
	            });
	          
	            tooltip.setFont(Font.font(12.0) );
	            parentLabel.setFont(Font.font(fontSize)); // Set font size
	            double opacity = calculateOpacity(depth) + 0.5 ;
	            parentLabel.setTextFill(Color.BLACK.deriveColor(0, 1, 1, opacity)); 
	            pane.getChildren().add(parentLabel);
            }
        }
    }
    
    public NodeData getNodeDataById(int nodeId) {
        for (NodeData node : nodeList) {
            if (node.getId() == nodeId) {
                return node;
            }
        }
        return null;
    }

    //Obtenir id et puis le nom de son parent par son node_id si il a un parent.
    public static String findParentNameById(List<NodeData> nodeList, Map<Integer, List<Integer>> nodeParentMap, int nodeId) {
    	for (Map.Entry<Integer, List<Integer>> entry : nodeParentMap.entrySet()) {
            List<Integer> children = entry.getValue();
            if (children != null && children.contains(nodeId)) {
                int parentId = entry.getKey();
                for (NodeData node : nodeList) {
                    if (node.getId() == parentId) {
                        return node.getName();
                    }
                }
            }
        }
        return null;
    }


    
    private double calculateOpacity(int depth) {
        // Calculate opacity based on proximity to the main generations
        double proximity = Math.abs(depth - (maxDepth-2)); 
        double maxProximity = 3; 
        double opacity = 0.5 - 0.3 * (proximity / maxProximity); 
        return Math.max(0, opacity);
    }
    
    private int findOldestParentId() {
        int oldestParentId = Integer.MAX_VALUE;
        for (int parentId : nodeParentMap.keySet()) {
            oldestParentId = Math.min(oldestParentId, parentId);
        }
        return oldestParentId;
    }
    
    public void updateZoom(Pane pane) {
		pane.getChildren().clear();
		maxDepth = Zoom.currentZoomLevel;
        calculateNodeCoordinates(pane);
    }

    
    public static void main(String[] args) {
        launch(args);
    }

}

