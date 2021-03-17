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
            ans = "m 17.504931,13.5 h -2 V 8.729 c 0,0 -5.26151,3.159241 -5.8610013,3.919316 -0.599491,0.760075 -0.248012,7.197851 -0.248012,7.197851 l -2.327561,0.128978 c 0,0 -0.429684,-7.849447 0.865531,-8.86976" +
                    " C 9.2290997,10.085074 13.985931,7.5 13.985931,7.5 H 9.5049307 v -2 c 5.9865073,-0.027708 5.2562133,-0.038205 8.0000003,0 z";
        } else if (angleDifferance >= 67.5 && angleDifferance < 112.5) {
            ans = "m 12.297327,18.31355 -1.41,-1.41 4.580001,-4.59 c 0,0 -6.0289366,-0.463446 -6.6284276,0.296629 -0.599491,0.760075 -0.495897,6.644878 -0.495897,6.644878 l -2.04154,0.01457 c 0,0 -0.429684,-7.315542 0.865531," +
                    "-8.335855 1.295212,-1.0203107 8.3003336,-0.620222 8.3003336,-0.620222 l -4.590001,-4.5899997 1.42,-1.41 7.000001,6.9999997 z";
        } else if (angleDifferance >= 112.5 && angleDifferance < 157.5) {
            ans = "m 8.9751299,17.977601 v -2 h 4.7709991 c 0,0 -4.9120491,-5.692969 -5.5642591,-5.699202 C 7.5296599,10.272166 7.0024569,18 7.0024569,18 h -2 c 0,0 0.897537,-10.4032628 2.726839," +
                    "-10.2945647 1.829302,0.1086982 7.2458331,6.7531667 7.2458331,6.7531667 V 9.9776019 h 2 c 0.02771,5.9865071 0.0382,5.2562121 0,7.9999991 z";
        } else if (angleDifferance >= 157.5 && angleDifferance < 202.5) {
            ans = "M19,15l-1.41-1.41L13,18.17V2H11v16.17l-4.59-4.59L5,15l7,7L19,15z";
        } else if (angleDifferance >= 202.5 && angleDifferance < 247.5) {
            ans = "m 13.027327,17.977601 v -2 H 8.2563277 c 0,0 4.9120493,-5.692969 5.5642593,-5.699202 C 14.472797,10.272166 15,18 15,18 h 2 C 17,18 16.102463,7.5967372 14.273161,7.7054353 12.443859,7.8141335" +
                    " 7.0273277,14.458602 7.0273277,14.458602 V 9.9776019 h -2 c -0.02771,5.9865071 -0.0382,5.2562121 0,7.9999991 z";
        } else if (angleDifferance >= 247.5 && angleDifferance < 292.5) {
            ans = "M 9,19 10.41,17.59 5.83,13 c 0,0 6.028936,-0.463446 6.628427,0.296629 0.599491,0.760075 0.495897,6.644878 0.495897,6.644878 l 2.04154,0.01457 " +
                    "c 0,0 0.429684,-7.315542 -0.865531,-8.335855 C 12.835121,10.599911 5.83,11 5.83,11 L 10.42,6.41 9,5 2,12 Z";
        } else if (angleDifferance >= 292.5 && angleDifferance < 337.5) {
            ans = "M 7,13.5 H 9 V 8.729 c 0,0 5.26151,3.159241 5.861001,3.919316 0.599491,0.760075 0.248012,7.197851 0.248012,7.197851 l 2.327561,0.128978 c 0,0 0.429684,-7.849447" +
                    " -0.865531,-8.86976 C 15.275831,10.085074 10.519,7.5 10.519,7.5 H 15 v -2 C 9.0134925,5.4722924 9.7437868,5.4617951 7,5.5 Z";
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
