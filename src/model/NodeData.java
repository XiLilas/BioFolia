package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeData {
	private int id;
	private String name;
    private String parentName;
    private int childNodes;
    private int leaf_node;
    private int tolorgLink;
    private int extinct;
    private int confidence;
    private int phylesis;

    public NodeData(int id, String name, int childNodes, int leaf_node, int tolorgLink, int extinct, int confidence, int phylesis) {
    	this.id = id;
        this.name = name;
        //this.parentName = parentName;
        this.childNodes = childNodes;
        this.leaf_node = leaf_node;
        this.tolorgLink = tolorgLink;
        this.extinct = extinct;
        this.confidence = confidence;
        this.phylesis = phylesis;
    }
    
 // les getters et les setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(int childNodes) {
        this.childNodes = childNodes;
    }

    public int getTolorgLink() {
        return tolorgLink;
    }

    public void setTolorgLink(int tolorgLink) {
        this.tolorgLink = tolorgLink;
    }

    public int getExtinct() {
        return extinct;
    }

    public void setExtinct(int extinct) {
        this.extinct = extinct;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    public int getPhylesis() {
        return phylesis;
    }

    public void setPhylesis(int phylesis) {
        this.phylesis = phylesis;
    }
    
    public int getLeafNode() {
    	return leaf_node;
    }
    
    public void setLeafNode(int leaf_node) {
    	this.leaf_node = leaf_node;
    }
    
 // Méthode pour créer un tableau de NodeData à partir de données
    public static List<NodeData> createNodeDataListFromData(String filePath) {
        List<NodeData> nodeList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null ) {
                String[] rowData = parseCSVLine(line);
                if (rowData.length >= 8) {
                	//System.out.println("Line read from file: " + line); // Debugging statement
                	int id = Integer.parseInt(rowData[0]);
                    String name = rowData[1];
                    //String parentName = rowData[1];
                    int childNodes = Integer.parseInt(rowData[2]);
                    int leaf_node = Integer.parseInt(rowData[3]);
                    int tolorgLink = Integer.parseInt(rowData[4]);
                    int extinct = Integer.parseInt(rowData[5]);
                    int confidence = Integer.parseInt(rowData[6]);
                    int phylesis = Integer.parseInt(rowData[7]);

                    NodeData node = new NodeData(id, name, childNodes,leaf_node, tolorgLink, extinct, confidence, phylesis);
                    nodeList.add(node);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nodeList;
    }
    
 // Méthode pour analyser une ligne CSV en tenant compte des valeurs entre guillemets
    private static String[] parseCSVLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == ',' && !inQuotes) {
                values.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                if (c == '"') {
                    inQuotes = !inQuotes;
                }
                sb.append(c);
            }
        }

        values.add(sb.toString().trim());

        return values.toArray(new String[0]);
    }

	public void setId(int nodeId) {
		this.id = nodeId;
	}
	
    public int getId() {
        return this.id;
    }
    
}
