package ca.mcmaster.se2aa4.mazerunner;
import java.util.ArrayList;
import java.util.List;


public class Node {
    private String parentRow=null;
    private String parentCol=null;
    private Boolean marked= false;
    private String direction= "east";
    private String move="null";
    private String movement;
    private String parentDir;
    private List<Integer[]> adjacentNodes= new ArrayList<>();


    //updates Node attributes
    public void setNodeAttributes(Integer row, Integer column,Integer adjRow, Integer adjCol){
        this.parentRow= String.valueOf(row);
        this.parentCol= String.valueOf(column);
        this.movement= tileRelativeToParent(row, column, adjRow, adjCol);
        this.move= moveToChild(row, column, adjRow, adjCol);
        this.direction= findNewDirection();
    }

    //finds the direction the user ends up facing based on where the child node is relative to its parent node
    private String findNewDirection(){
        switch (movement) {
            case "UP":
                return "north";
            case "DOWN":
                return "south";
            case "RIGHT":
                return "east";
            default:
                return "west";
        }
    }

    //identifies where the child node is relative to its parent node
    private String tileRelativeToParent(Integer parentRow, Integer parentCol, Integer childRow, Integer childCol){
        if (parentRow+1==childRow){
            return "DOWN";
        }
        else if(parentRow-1==childRow){
            return "UP";
        }
        else if(parentCol+1== childCol){
            return "RIGHT";
        }
        else if(parentCol-1== childCol){
            return "LEFT";
        }
        else{
            return "null";
        }
    }
    
    //finds the move required to go from the parent node to the child node
    //considers direction player ends up facing on the parent node
    private String moveToChild(Integer parentRow, Integer parentCol, Integer childRow, Integer childCol){
        switch (parentDir) {
            case "east":
                switch (movement) {
                    case "RIGHT":
                        return "F";
                    case "LEFT":
                        return "LLF";
                    case "UP":
                        return "LF";
                    case "DOWN":
                        return "RF";
                }
                break;
    
            case "west":
                switch (movement) {
                    case "RIGHT":
                        return "LLF";
                    case "LEFT":
                        return "F";
                    case "UP":
                        return "RF";
                    case "DOWN":
                        return "LF";
                }
                break;
    
            case "north":
                switch (movement) {
                    case "RIGHT":
                        return "RF";
                    case "LEFT":
                        return "LF";
                    case "UP":
                        return "F";
                    case "DOWN":
                        return "LLF";
                }
                break;
    
            case "south":
                switch (movement) {
                    case "RIGHT":
                        return "LF";
                    case "LEFT":
                        return "RF";
                    case "UP":
                        return "LLF";
                    case "DOWN":
                        return "F";
                }
                break;
            }
            return "";
    }

    //updates current nodes adjacent nodes
    public void addAdjacentNode(Integer[] coords){
        adjacentNodes.add(coords);
    }

    //getter and setter methods
    public List<Integer[]> getAdjacentNodesList(){
        return adjacentNodes;
    }

    public String getParentRow(){
        return parentRow;
    }

    public String getParentColumn(){
        return parentCol;
    }

    public boolean getMarked(){
        return marked;
    }

    public void setMarkedTrue(){
        marked= true;
    }

    public void setDirection(String direction){
        this.direction= direction;
    }
    
    public String getDirection(){
        return direction;
    }
    
    public void setParentDirection(String parentDirection){
        this.parentDir= parentDirection;
    }
    
    public String getParentDirection(){
        return direction;
    }

    public String getMove(){
        return move;
    }
}
