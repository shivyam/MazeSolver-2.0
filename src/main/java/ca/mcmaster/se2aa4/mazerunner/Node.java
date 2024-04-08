package ca.mcmaster.se2aa4.mazerunner;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;


public class Node {

    Integer row;
    Integer col;
    String parentRow;
    String parentCol;
    Boolean marked= false;

    String direction;
    String move;

    String parentDir;

    List<Integer[]> adjacentNodes= new ArrayList<>();


    public void setParentNode(Integer row, Integer column,Integer adjRow, Integer adjCol){
       
        setParentRow(row);
        setParentColumn(column);
        setMove(moveToChild(row, column, adjRow, adjCol));
        setDirection(classifyMove(row, column, adjRow, adjCol));
        String dir= classifyMove(row, column, adjRow, adjCol);

        if (dir.equals("UP")){
            setMove("north");
        }
        else if (dir.equals("DOWN")){
            setMove("south");
        }
        else if (dir.equals("RIGHT")){
            setMove("east");
        }
        else{
            setMove("west");
        }
    }



    public String classifyMove(Integer parentRow, Integer parentCol, Integer childRow, Integer childCol){
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


    
    public String moveToChild(Integer parentRow, Integer parentCol, Integer childRow, Integer childCol){
        if (parentDir.equals("east")){
            if (move.equals("RIGHT")){
                return "F";
            }
            else if (move.equals("LEFT")){
                return "LLF";
            }
            else if(move.equals("UP")){
                return "LF";
            }
            else if(move.equals("DOWN")){
                return "RF";
            }
        }
        else if (parentDir.equals("west")){
            if (move.equals("RIGHT")){
                return "LLF";
            }
            else if (move.equals("LEFT")){
                return "F";
            }
            else if(move.equals("UP")){
                return "RF";
            }
            else if(move.equals("DOWN")){
                return "LF";
            }
        }
        else if (parentDir.equals("north")){
            if (move.equals("RIGHT")){
                return "RF";
            }
            else if (move.equals("LEFT")){
                return "LF";
            }
            else if(move.equals("UP")){
                return "F";
            }
            else if(move.equals("DOWN")){
                return "LLF";
            }
        }
        else if (parentDir.equals("south")){
            if (move.equals("RIGHT")){
                return "LF";
            }
            else if (move.equals("LEFT")){
                return "RF";
            }
            else if(move.equals("UP")){
                return "LLF";
            }
            else if(move.equals("DOWN")){
                return "F";
            }
        }
        return "";
    }




    public void addAdjacentNode(Integer[] coords){
        adjacentNodes.add(coords);
    }
    

    public List<Integer[]> getAdjacentNodesList(){
        return adjacentNodes;
    }

    public Integer getRow(){
        return row;
    }

    public Integer getColumn(){
        return col;
    }

    public void setParentRow(Integer parentRow){
        this.parentRow= String.valueOf(parentRow);
    }

    public String getParentRow(){
        return parentRow;
    }

    public void setParentColumn(Integer parentCol){
        this.parentCol= String.valueOf(parentCol);
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

    public void setMove(String move){
        this.move= move;
    }

    public String getMove(){
        return move;
    }
}
