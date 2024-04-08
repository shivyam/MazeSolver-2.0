package ca.mcmaster.se2aa4.mazerunner;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.util.Queue;
import java.util.LinkedList;

public class GraphAlgorithmSolver{


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
                System.out.print("Row: " + i + "   Column: "+ j + ":           " );
                for (Integer[] node: adjacentNodesList(i,j) ){
                    System.out.print("[" + node[0] + ", " + node[1] + "],  ");
                }
                adjacencyList.get(i).add(adjacentNodesList(i,j));
                System.out.println();
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
        String path= breadthFS();
        return path;
       
    }



    public String breadthFS(){
        Queue<Integer[]> queue = new LinkedList<>();
        queue.add(new Integer[] {maze.findEntryTile(),0});

        //Update parent node list: set previous node to indexes null
        ArrayList<String> nodeParent= new ArrayList<>(Arrays.asList(null, null, "null"));
        parentNodeList.get(maze.findEntryTile()).set(0, nodeParent);
        
        while (!queue.isEmpty()){
            //Update marked node list
            Integer[] currNodeCoords= queue.remove();
            Integer row= currNodeCoords[0];
            Integer column= currNodeCoords[1];

            //set to true, meaning it has been visited
            markedNodes.get(row).set(column, true);

            //add adjacent nodes to queue
            for(Integer[] adjacentNode: adjacencyList.get(row).get(column)){
                int adjRow = adjacentNode[0];
                int adjCol = adjacentNode[1];
                if(!markedNodes.get(adjRow).get(adjCol)){
                    queue.add(adjacentNode);
                    setParentNode(row, column, adjRow, adjCol);
                }
            }
            if(row== maze.findExitTile() && column== maze.getWidth()- 1){
                break;
            }
        }
        System.out.println("We made it");
        return buildPath();
    }





    public String buildPath(){
        int currRow= maze.findExitTile();
        int currColumn= maze.getWidth()-1;
        StringBuilder path = new StringBuilder();
        while(!parentNodeList.get(currRow).get(currColumn).get(2).equals("null")){

            System.out.println("Current Row: " + currRow + "   Current Column: " + currColumn);
            path.insert(0,parentNodeList.get(currRow).get(currColumn).get(2));
            currRow= Integer.parseInt(parentNodeList.get(currRow).get(currColumn).get(0));
            currColumn= Integer.parseInt(parentNodeList.get(currRow).get(currColumn).get(1));
        }
        return path.toString();
    }










    public void setParentNode(Integer row, Integer column,Integer adjRow, Integer adjCol){
        List<String> nodeParent = new ArrayList<>();
        nodeParent.add(String.valueOf(row));
        nodeParent.add(String.valueOf(column));
        nodeParent.add(moveToChild(row, column, adjRow, adjCol));
        nodeParent.add(dir.getDirection());
        parentNodeList.get(adjRow).set(adjCol, nodeParent);
    }

    public String moveToChild(Integer parentRow, Integer parentCol, Integer childRow, Integer childCol){
        String move= classifyMove(parentRow, parentCol, childRow, childCol);

        if (dir.getDirection().equals("east")){
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
        else if (dir.getDirection().equals("west")){
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
        else if (dir.getDirection().equals("north")){
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
        else if (dir.getDirection().equals("south")){
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

