package control;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
/*import javafx.scene.control.MenuItem;*/
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.NodeData;
import view.TreeVisualizer;


public class Controller {
    @FXML
    private AnchorPane treePane;

    @FXML
    private TextField searchField;

    @FXML
    private ImageView iconView;

    @FXML
    private MenuButton daltonismeMenu;

    @FXML
    private HBox hbox;

    @FXML
    private Label search;
	
	@FXML
	private void zoomIn() {
	    Zoom.zoomIn();
	}

	@FXML
	private void zoomOut() {
	    Zoom.zoomOut();
	}
	
	@FXML
	private void resetTree() {
	    Zoom.resetTree();
	}

	//Méthode recherche
	@FXML
	private void search() {
		String searchText = searchField.getText();
		search.setText("");
		boolean found = false;
		//System.out.println(" \n SF:" +searchText);
		for (NodeData node : TreeVisualizer.nodeList) {
			if (node.getName().equals(searchText)) {
				String data = "Node Name: " + node.getName() + "\n";
				String parentName = TreeVisualizer.findParentNameById(TreeVisualizer.nodeList, TreeVisualizer.nodeParentMap, node.getId());
				if (parentName != null) {
	                data += "Parent: " + parentName + ", \n";
	            }
	            data += "Child Nodes: " + node.getChildNodes() + ", \n";
	            data += "Leaf Node: " + node.getLeafNode() + ", \n";
	            data += "Tolorg Link: " + node.getTolorgLink() + ", \n";
	            data += "Extinct: " + node.getExtinct() + ", \n";
	            data += "Confidence: " + node.getConfidence() + ", \n";
	            data += "Phylesis: " + node.getPhylesis() + ", \n";
	            search.setText(data);
	            found = true;
	            break;
			}
		}
		if (!found) {
	        search.setText("Espèce n'existe pas.");
	    }
	}
	
    
    @FXML
    private void applyRedGreenFilter() {
    	applyFilter(Color.YELLOW, Color.BLUE);
    }
    
    @FXML
    private void applyBlueYellowFilter() {
    	applyFilter(Color.RED, Color.LIGHTPINK);
    }
    
    @FXML
    private void applyTotalGrayScaleFilter() {
    	applyFilter(Color.LIGHTGRAY, Color.GREY);
    }
    
    @FXML
    private void clearFilters() {
        resetFilterToNode(treePane);
    }

    private void applyFilter(Color color1, Color color2) {
    	applyFilterToNode(treePane, color1, color2);
    }

    private void applyFilterToNode(Node node, Color color1, Color color2) {
    	if (node instanceof Pane) {
    		Pane pane = (Pane) node;
    		pane.setStyle("-fx-background-color: linear-gradient(to bottom, " + colorToHex(color1) + ", " + colorToHex(color2) + ");");
    	}
    	if (node instanceof Parent) {
    		Parent parent = (Parent) node;
    		for (Node child : parent.getChildrenUnmodifiable()) {
    			applyFilterToNode(child, color1, color2);
    		}
    	}
    }
    
    private void resetFilterToNode(Node node) {
    	if (node instanceof Pane) {
    		Pane pane = (Pane) node;
    		pane.setStyle("-fx-background-color: linear-gradient(to bottom, rgba(0, 255, 0, 0.4), rgba(0, 0, 255, 0.4));");
    	}
    	if (node instanceof Parent) {
    		Parent parent = (Parent) node;
    		for (Node child : parent.getChildrenUnmodifiable()) {
    			resetFilterToNode(child);
    		}
    	}
    }
    
    private String colorToHex(Color color) {
    	return "#" + color.toString().substring(2, 8);
    }
    
    //Mettre en place image l'oeuil
    public void initialize() {
    	Image icon = new Image("file:image/eye_ic.png");
    	//System.out.print(icon);
    	ImageView iconView = new ImageView(icon);
    	iconView.setFitWidth(60); 
    	iconView.setFitHeight(32); 
    	iconView.setOpacity(1.0); 
    	hbox.getChildren().add(iconView);
//      daltonismeMenu.setStyle("-fx-font-size: 14px;");
//      daltonismeMenu.setStyle("-fx-text-fill: black;");
    }


}
