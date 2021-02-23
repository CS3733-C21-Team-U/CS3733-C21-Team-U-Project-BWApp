package edu.wpi.u.models;

import edu.wpi.u.algorithms.Node;

import java.util.ArrayList;
import java.util.LinkedList;

public class PathHandling {

    public static String SVGPath = "m 0, 0 l 1, 1 m 1468.0, 694.0 l 20.0, 0.0 l 16.0, 0.0 l 31.0, 0.0 l 0.0, 20.0 l 0.0, 18.0 l 0.0, 17.0 l 53.0, 180.0 l -156.0, 22.0 l -118.0, 78.0 l -106.0, -31.0 l -136.0, -4.0 l -68.0, -39.0 l -131.0, 13.0 l -126.0, 31.0 l -218.0, 206.0 l -23.0, 2.0 l -2.0, -32.0";


    public void setSVGPath(LinkedList<Node> nodeList){
        System.out.println("PATHHANDLING GOT THE LIST");
        String path = "m 0, 0 l 1, 1 m ";
        path += nodeList.get(0).getXString() + ", " + nodeList.get(0).getYString();
        for(int i = 1; i<nodeList.size(); i++){
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
