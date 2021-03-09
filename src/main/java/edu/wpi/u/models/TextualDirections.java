package edu.wpi.u.models;

import edu.wpi.u.algorithms.Edge;
import edu.wpi.u.algorithms.Node;

import java.util.ArrayList;
import java.util.HashMap;

public class TextualDirections {

    public static void main(String[] args){

        Node A = new Node("A", 0, 0);
        Node B = new Node("B", 1, 0);
        Node C = new Node("C", 1, -1);
        Node D = new Node("D", 2, -1);
        Node E = new Node("E", 3, 0);
        Node F = new Node("F", 4, 0);
        Node G = new Node("G", 3, -1);
        Node H = new Node("H", 4, -1);

        ArrayList<Node> path = new ArrayList<>();
        path.add(A);
        path.add(B);
        path.add(C);
        path.add(D);
        path.add(E);
        path.add(F);
        path.add(G);
        path.add(H);

        System.out.println(getTextualDirections(path));
    }

    /**
     * takes in the path returned from the pathfinding function
     * and returns an array list of string each line being a textual
     * directions
     * @param path  the path found
     * @return      textual directions saved into an array list
     */
    public static ArrayList<String> getTextualDirections(ArrayList<Node> path){
        Node lastNode = path.get(0);
        Node curNode = path.get(0);

        ArrayList<String> returnMe = new ArrayList<>();

        for (int i = 1; i < path.size(); i++){
            Node nextNode = path.get(i);
            String direction = nodesToTextDirection(curNode,nextNode,lastNode);
            if(!direction.equals("")){
                returnMe.add(direction);
            }
            lastNode = curNode;
            curNode = nextNode;
        }
        return returnMe;
    }

    /**
     * takes in two nodes and returns a string that is a textual direction
     * that a user could understand
     * @param curNode
     * @param nextNode
     * @return
     */
    private static String nodesToTextDirection(Node curNode, Node nextNode, Node previousNode) {
        String direction = "";
        String curType = curNode.getNodeType();
        String nextType = nextNode.getNodeType();

        if(nextNode.getNodeType().equals("ELEV") || curNode.getNodeType().equals("ELEV")
        || nextNode.getNodeType().equals("STAI") || curNode.getNodeType().equals("STAI")) {
            //return textualSwitchFloors(curNode, nextNode);
            return "switch floor directions not working";
        }
        else if(previousNode != curNode) {
//            double angle = getAngle(curNode, nextNode);
//            double previousAngle = getAngle(previousNode, curNode);
//            double angleDifferance = angle - previousAngle;
//
//            return textualAngleDescription(curNode,nextNode, angleDifferance);
            return "turn at x angle";
        }
        //If at first node
        else return "Go to the starting location and walk " + calcDistance(curNode, nextNode) + "ft";
    }

/*    *//**
     * logic for choosing description when switching floors
     * @param //curNode
     * @param //nextNode
     * @return
     */
    private static String textualSwitchFloors(Node curNode, Node nextNode) {
        String direction = "";
        if (nextNode.getNodeType().equals("ELEV") && !curNode.getNodeType().equals("ELEV")) { //first option
            direction = "Take the elevator";
        }else if(nextNode.getNodeType().equals("STAI") && !curNode.getNodeType().equals("STAI")){
            direction = "Take the stairs";
        }else if(curNode.getNodeType().equals("ELEV") && !nextNode.getNodeType().equals("ELEV")){
            direction = "Get off at floor " + nextNode.getFloor();
        }else if(curNode.getNodeType().equals("STAI") && !nextNode.getNodeType().equals("STAI")){
            direction = "Stop at floor " + nextNode.getFloor();
        } else if (curNode.getNodeType().equals("ELEV") && nextNode.getNodeType().equals("ELEV")){
            //not used
        }else if (curNode.getNodeType().equals("STAI") && nextNode.getNodeType().equals("STAI")){
            //not used
        }
        return direction;
    }

    /**
     * logic for choosing description when turning
     * @return
     */
    public static String textualAngleDescription(double angleDifferance) {
        String ans;

        if (angleDifferance >= 22.5 && angleDifferance < 67.5) {
            //ans = "Take a slight right at ";
            ans = "Turn slightly right";
        } else if (angleDifferance >= 67.5 && angleDifferance < 112.5) {
            //ans = "Take a right turn at ";
            ans = "Turn right";
        } else if (angleDifferance >= 112.5 && angleDifferance < 157.5) {
            //ans = "Take a sharp right turn at ";
            ans = "Take a sharp right";
        } else if (angleDifferance >= 157.5 && angleDifferance < 202.5) {
            //ans = "Turn around at ";
            ans = "Turn around";
        } else if (angleDifferance >= 202.5 && angleDifferance < 247.5) {
            //ans = "Take a sharp left turn at ";
            ans = "Take a sharp left";
        } else if (angleDifferance >= 247.5 && angleDifferance < 292.5) {
            //ans = "Take a left turn at ";
            ans = "Turn left";;
        } else if (angleDifferance >= 292.5 && angleDifferance < 337.5) {
            //ans = "Take a slight left turn at ";
            ans = "Turn slightly left";
        } else {
            //ans = "Continue straight at ";
            ans = "Continue straight";
        }
        return ans;
    }

    public static String findTextualIconID(double angleDifferance) {
        String ans;

        if (angleDifferance >= 22.5 && angleDifferance < 67.5) {
            ans = "M15,5l-1.41,1.41L18.17,11H2V13h16.17l-4.59,4.59L15,19l7-7L15,5z";

        } else if (angleDifferance >= 67.5 && angleDifferance < 112.5) {
            ans = "M15,5l-1.41,1.41L18.17,11H2V13h16.17l-4.59,4.59L15,19l7-7L15,5z";
        } else if (angleDifferance >= 112.5 && angleDifferance < 157.5) {
            ans = "M15,5l-1.41,1.41L18.17,11H2V13h16.17l-4.59,4.59L15,19l7-7L15,5z";
        } else if (angleDifferance >= 157.5 && angleDifferance < 202.5) {
            ans = "M19,15l-1.41-1.41L13,18.17V2H11v16.17l-4.59-4.59L5,15l7,7L19,15z";
        } else if (angleDifferance >= 202.5 && angleDifferance < 247.5) {
            ans = "M9,19l1.41-1.41L5.83,13H22V11H5.83l4.59-4.59L9,5l-7,7L9,19z";
        } else if (angleDifferance >= 247.5 && angleDifferance < 292.5) {
            ans = "M9,19l1.41-1.41L5.83,13H22V11H5.83l4.59-4.59L9,5l-7,7L9,19z";
        } else if (angleDifferance >= 292.5 && angleDifferance < 337.5) {
            ans = "M5,15h2V8.41L18.59,20L20,18.59L8.41,7H15V5H5V15z";
        } else {
            ans = "M5,9l1.41,1.41L11,5.83V22H13V5.83l4.59,4.59L19,9l-7-7L5,9z";
        }
        return ans;
    }

    /**
     * calculates the angle if edge between the nodes assuming the current node is at the origin
     * @param start the start node
     * @param end   the end node
     * @return the edge
     */
    public static double getAngle(Node start, Node end){
        double dX = end.getCords()[0] - start.getCords()[0];
        double dY = end.getCords()[1] - start.getCords()[1];

        double angleInRadians = Math.atan2(dY,dX);
        double angleInDegrees = Math.toDegrees(angleInRadians);
//        if(angleInDegrees < 0){
//            angleInDegrees = 180 + Math.abs(angleInDegrees);
//        }
        return angleInDegrees;
    }


    /**
     * takes in two nodes and returns the distance between them in feet
     * @param start the start node
     * @param end   the end node
     * @return      distance between the given nodes
     */
    private static double calcDistance(Node start, Node end){
        double pixelPerFoot = 3;
        // calculate the rise and run
        double[][] nodeLocation = {start.getCords(), end.getCords()};
        double dx = Math.abs(nodeLocation[0][0] - nodeLocation[1][0]);
        double dy = Math.abs(nodeLocation[0][1] - nodeLocation[1][1]);
        // calculate the distance then
        // return the distance (weight)
        double pixelDistance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        double footDistance = pixelDistance / pixelPerFoot;
        double roundedDistanceBig = Math.round((footDistance));
        double roundedDistanceReal =roundedDistanceBig;
        return  (int)roundedDistanceReal;
    }


}
