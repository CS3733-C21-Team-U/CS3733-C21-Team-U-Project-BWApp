package edu.wpi.u.models;

import edu.wpi.u.algorithms.Node;

import java.util.ArrayList;
import java.util.LinkedList;

public class PathHandling {

    public static String SVGPath = "";


    public void setSVGPath(LinkedList<Node> nodeList){
        System.out.println("PATHHANDLING GOT THE LIST");
        String path = "m 0, 0 l 1, 1 m ";
        path += nodeList.get(0).getXString() + ", " + nodeList.get(0).getYString();
        for(int i = 0; i<nodeList.size(); i++){
            double xdiff = nodeList.get(i).getCords()[0]-nodeList.get(i-1).getCords()[0];
            double ydiff = nodeList.get(i).getCords()[1]-nodeList.get(i-1).getCords()[1];
            path += " l " + xdiff + ", " + ydiff;
            System.out.println(path + " Path So far");
        }
        SVGPath = path;
        System.out.println("ENDING PATH: " + path);
    }

    public void clearSVGPath(){
        SVGPath = "";
    }





}
