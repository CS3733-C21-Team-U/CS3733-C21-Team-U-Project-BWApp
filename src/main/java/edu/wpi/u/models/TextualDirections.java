package edu.wpi.u.models;

import edu.wpi.u.algorithms.Node;

import java.util.ArrayList;

public class TextualDirections {

    public static void main(String[] args){

        Node A = new Node("A", 0, 0);
        Node B = new Node("B", 1, 0);
        Node C = new Node("C", 2, 0);
        Node D = new Node("D", 2, 1);
        Node E = new Node("E", 1, 30);
        Node F = new Node("F", 0, 1);

        ArrayList<Node> path = new ArrayList<>();
        path.add(A);
        path.add(B);
        path.add(C);
        path.add(D);
        path.add(E);
        path.add(F);

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
        Node lastNode = null;
        Node curNode = path.get(0);

        ArrayList<String> returnMe = new ArrayList<>();

        for (int i = 1; i < path.size(); i++){
            Node nextNode = path.get(i);
            returnMe.add(nodesToTextDirection(curNode,nextNode,lastNode));
            lastNode = curNode;
            curNode = nextNode;
        }
        return returnMe;
    }

    /**
     * takes in two nodes and returns a string that is a textual direction
     * that a user could understand
     * @param start
     * @param end
     * @return
     */
    private static String nodesToTextDirection(Node start, Node end, Node previous){
        String direction = "";
        double angle = getAngle(start,end);
        if(previous != null){
            double previousAngle = getAngle(previous,start);
            double angleDifferance = Math.abs(previousAngle - angle);
            if(angleDifferance >= 45 && angleDifferance < 135){
                direction = "Turn left and continue strait for ";
            }else if(angleDifferance >= 135 && angleDifferance < 225){
                direction = "Turn around continue strait for ";
            }else if(angleDifferance >= 225 && angleDifferance < 315){
                direction = "Turn Right and continue strait for ";
            }else{
                direction = "Continue strait for ";
            }
            direction = direction + calcDistance(start,end) + "ft";
        }else{
            direction = "Go to the starting location and walk " + calcDistance(start,end) + "ft";
        }

        return direction;
    }

    /**
     * calculates the angle if edge between the nodes assuming the current node is at the origin
     * @param start the start node
     * @param end   the end node
     * @return the edge
     */
    private static double getAngle(Node start, Node end){
        double dX = end.getCords()[0] - start.getCords()[0];
        double dY = end.getCords()[1] - start.getCords()[1];

        double angleInRadians = Math.atan2(dY,dX);
        double angleInDegrees = Math.toDegrees(angleInRadians);
        if(angleInDegrees < 0){
            angleInDegrees = 180 + Math.abs(angleInDegrees);
        }
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
        double roundedDistanceBig = Math.round((footDistance * 100));
        double roundedDistanceReal =roundedDistanceBig/100;
        return  roundedDistanceReal;
    }


}
