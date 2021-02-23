package edu.wpi.u.controllers;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class PathOverlayController {

    private ArrayList<Line> loadedLines = new ArrayList<Line>();

//    public void loadMapPath(ArrayList<Node> nodeList){
//        double anchorHeight = App.pane.getHeight();
//        for(int i = 0; i < nodeList.size(); i++){
//            Line line = new Line();
//            double N1y = nodeList.get(i).getCords()[1];
//            double N2y = nodeList.get(i+1).getCords()[1];
//            double N1x = nodeList.get(i).getCords()[0];
//            double N2x = nodeList.get(i+1).getCords()[0];
//            double uneditedY = N1y + (N2y-N1y)/2;
//            double uneditedX = N1x + (N2x-N1x)/2;
//            double ScaledY = (uneditedY/1966)*anchorHeight;//this 1966 number needs to be changed for the regular floor maps.
//            double ScaledX= (uneditedX/1966)*anchorHeight;//this 1966 number needs to be changed for the regular floor maps.
//            //line1.set
//            loadedLines.add(line);
//            App.pane.getChildren().add(line);
//        }
//    }
}
