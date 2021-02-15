package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Graph;
import edu.wpi.u.algorithms.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EdgeController {
    @FXML
    public TextField edgeId;

    @FXML
    public TextField endNode;

    @FXML
    public TextField startNode;

    @FXML
    public void addEdge(ActionEvent actionEvent) {
        Graph graph = App.graph;
        Edge myEdge = new Edge(edgeId.getText(), graph.getNodeById(startNode.getText()), graph.getNodeById(endNode.getText()));
        graph.addEdge(myEdge);
        }

    @FXML
    //check while database
    public void deleteEdge(ActionEvent actionEvent) {
        Graph graph =App.graph;
        Node start = graph.getNodeById(startNode.getText());
        Node end = graph.getNodeById(endNode.getText());
        Edge edge = graph.getEdgeByNodes(start,end);
        graph.removeEdge(edge);
    }

    @FXML
    //check while database
    public void editEdge(ActionEvent actionEvent) {
        Graph graph =App.graph;
        Node start = graph.getNodeById(startNode.getText());
        Node end = graph.getNodeById(endNode.getText());
        graph.updateEdge(edgeId.getText(),start,end);
    }
}
