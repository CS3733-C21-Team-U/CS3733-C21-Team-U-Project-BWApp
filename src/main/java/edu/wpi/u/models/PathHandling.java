package edu.wpi.u.models;

import edu.wpi.u.App;
import edu.wpi.u.algorithms.Node;

import java.util.LinkedList;

public class PathHandling {

    public static String SVGPathString = "";


    public void setSVGPath(LinkedList<Node> nodeList){
        System.out.println("PATHHANDLING GOT THE LIST");
        String path = "m 0, 0 l 1, 1 m ";
        String backPath = "";
        path += nodeList.get(0).getXString() + ", " + nodeList.get(0).getYString();
        for(int i = 1; i<nodeList.size(); i++){
            double xdiff = nodeList.get(i).getCords()[0]-nodeList.get(i-1).getCords()[0];
            double ydiff = nodeList.get(i).getCords()[1]-nodeList.get(i-1).getCords()[1];
            path += " l " + xdiff + ", " + ydiff;
            backPath = " l " + -xdiff + ", " + -ydiff + backPath;
            System.out.println(path + " Path So far");
        }
        SVGPathString = path + backPath;
        System.out.println("ENDING PATH: " + SVGPathString);
        App.pathFindingPath.setContent(SVGPathString);
        App.pathFindingPath2.setContent(SVGPathString);
    }

    public void clearSVGPath(){
        SVGPathString = "";
    }





}
