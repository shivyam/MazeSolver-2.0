package ca.mcmaster.se2aa4.mazerunner;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.util.Queue;
import java.util.LinkedList;

public class GraphAlgorithmSolver extends MazeExploration{


    Maze maze;
    List<List<List<Integer[]>>> adjacencyList= new ArrayList<>();
    List<List<Boolean>> markedNodes= new ArrayList<>();
    List<List<List<String>>> parentNodeList= new ArrayList<>();
    Direction dir;



    public GraphAlgorithmSolver(Maze maze){
        this.maze= maze;
        this.dir= new Direction(maze);

        adjacencyList();
        //initialize markedNodes array
        for(int i=0;i<maze.getHeight();i++){
            List<Boolean> markedLine = new ArrayList<>(Collections.nCopies(maze.getWidth(), false));
            markedNodes.add(markedLine);
        }

        //intialize parent node array, contains parent node + steps to take from parent node to child node
        for (int i = 0; i < maze.getHeight(); i++) {
            List<List<String>> prevNodeLine = new ArrayList<>();
            for (int j = 0; j < maze.getWidth(); j++) {
                prevNodeLine.add(new ArrayList<>());
            }
            parentNodeList.add(prevNodeLine);
        }
    }


    public void adjacencyList(){
        for(int i=0;i<maze.getHeight();i++){
            adjacencyList.add(new ArrayList<List<Integer[]>>());
            for(int j=0;j<maze.getWidth();j++){
                adjacencyList.get(i).add(adjacentNodesList(i,j));
            }
        }
    }

    public List<Integer[]> adjacentNodesList(int currRow, int currColumn){
        List<Integer[]> adjacentNodes= new ArrayList<>();

        if (canCheckUp(currRow,currColumn)){
            adjacentNodes.add(new Integer[] {currRow-1, currColumn});
        }
        if (canCheckDown(currRow,currColumn)){
            adjacentNodes.add(new Integer[] {currRow+1, currColumn});
        }
        if (canCheckLeft(currRow,currColumn)){
            adjacentNodes.add(new Integer[] {currRow, currColumn-1});
        }
        if (canCheckRight(currRow,currColumn)){
            adjacentNodes.add(new Integer[] {currRow, currColumn+1});
        }
        return adjacentNodes;
    }


    public String solve(){
        Queue<Integer[]> queue = new LinkedList<>();
        queue.add(new Integer[] {maze.findEntryTile(),0});

        //Update parent node list: set previous node to indexes null
        ArrayList<String> nodeParent= new ArrayList<>(Arrays.asList(null, null, "null", "east"));
        parentNodeList.get(maze.findEntryTile()).set(0, nodeParent);
        
        while (!queue.isEmpty()){
            //Update marked node list
            Integer[] currNodeCoords= queue.remove();
            Integer row= currNodeCoords[0];
            Integer column= currNodeCoords[1];

            //set to true, meaning it has been visited
            markedNodes.get(row).set(column, true);
            System.out.println("Current Row: " + row + "   Current Column: "+ column);

            //add adjacent nodes to queue
            for(Integer[] adjacentNode: adjacencyList.get(row).get(column)){
                int adjRow = adjacentNode[0];
                int adjCol = adjacentNode[1];
                if(!markedNodes.get(adjRow).get(adjCol)){
                    System.out.println("Adjacent Row: " + adjRow + "   Adjacent Column: "+ adjCol);
                    queue.add(adjacentNode);
                    setParentNode(row, column, adjRow, adjCol);
                }
                System.out.println("--------------------------------");
            }
            if(row== maze.findExitTile() && column== maze.getWidth()- 1){
                break;
            }
        }
        return buildPath();
    }



    public String buildPath(){
        Integer currRow= maze.findExitTile();
        Integer currColumn= maze.getWidth()-1;
        StringBuilder path = new StringBuilder();
   
        while(!parentNodeList.get(currRow).get(currColumn).get(2).equals("null")){
            path.insert(0,parentNodeList.get(currRow).get(currColumn).get(2));

            Integer row= Integer.parseInt(parentNodeList.get(currRow).get(currColumn).get(0));
            currColumn= Integer.parseInt(parentNodeList.get(currRow).get(currColumn).get(1));
            currRow= row;     
        }
        return path.toString();
    }


    public void setParentNode(Integer row, Integer column,Integer adjRow, Integer adjCol){
        List<String> nodeParent = new ArrayList<>();
        nodeParent.add(String.valueOf(row));
        nodeParent.add(String.valueOf(column));
        String move= moveToChild(row, column, adjRow, adjCol);
        nodeParent.add(move);
        String dir= classifyMove(row, column, adjRow, adjCol);

        if (dir.equals("UP")){
            nodeParent.add("north");
        }
        else if (dir.equals("DOWN")){
            nodeParent.add("south");
        }
        else if (dir.equals("RIGHT")){
            nodeParent.add("east");
        }
        else{
            nodeParent.add("west");
        }
        parentNodeList.get(adjRow).set(adjCol, nodeParent);
    }


    public String moveToChild(Integer parentRow, Integer parentCol, Integer childRow, Integer childCol){
        String move= classifyMove(parentRow, parentCol, childRow, childCol);
        String direction= parentNodeList.get(parentRow).get(parentCol).get(3);

        if (direction.equals("east")){
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
        else if (direction.equals("west")){
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
        else if (direction.equals("north")){
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
        else if (direction.equals("south")){
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

    public boolean canCheckUp(int currRow, int currColumn){
        if (currRow-1>=0 && maze.getTile(currRow-1,currColumn)==1){
            return true;
        }
        return false;
    }
    
    public boolean canCheckDown(int currRow, int currColumn){
        if (currRow+1<maze.getHeight() && maze.getTile(currRow+1,currColumn)==1){
            return true;
        }
        return false;
    }

    public boolean canCheckLeft(int currRow, int currColumn){
        if (currColumn-1>=0 && maze.getTile(currRow,currColumn-1)==1){
            return true;
        }
        return false;
    }
    
    public boolean canCheckRight(int currRow, int currColumn){
        if (currColumn+1<maze.getWidth() && maze.getTile(currRow,currColumn+1)==1){
            return true;
        }
        return false;
    }

}

