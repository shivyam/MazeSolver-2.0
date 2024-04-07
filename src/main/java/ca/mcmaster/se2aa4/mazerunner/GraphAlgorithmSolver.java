package ca.mcmaster.se2aa4.mazerunner;


import java.util.ArrayList;
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



    public String solve(){
        String path= breadthFS();
        return path;
       
    }



    public String breadthFS(){
        Queue<Integer[]> queue = new LinkedList<>();

        Integer column= 0;
        Integer row = maze.findEntryTile();

        
        queue.add(new Integer[] {row,column});

     
        ArrayList<String> initialNodeParent= new ArrayList<>();

        //Update parent node list: set previous node to null, set move to null

        //parent node row
        initialNodeParent.add(null);
        //parent node column
        initialNodeParent.add(null);
        //how to get from parent node to child node (e.g. "LF")
        initialNodeParent.add("null");
        parentNodeList.get(row).set(column, initialNodeParent);

        //Update marked node list
        Integer[] currNodeCoords= queue.remove();
        row= currNodeCoords[0];
        column= currNodeCoords[1];

        System.out.println("Current Row: " + row + "   Current Column: " + column);

        //set to true, meaning it has been visited
        markedNodes.get(row).set(column, true);

        //add adjacent nodes to queue
        for(Integer[] adjacentNode: adjacencyList.get(row).get(column)){
            queue.add(adjacentNode);
            ArrayList<String> nodeParent= new ArrayList<>();
            nodeParent.add(String.valueOf(row));
            nodeParent.add(String.valueOf(column));
            System.out.println("Parent node: " + "[" + row + ", " + column + "]");
            System.out.println("Move: "+ moveToChild(row, column, adjacentNode[0], adjacentNode[1]));
            nodeParent.add(moveToChild(row, column, adjacentNode[0], adjacentNode[1]));
            parentNodeList.get(adjacentNode[0]).set(adjacentNode[1], nodeParent);
        }

        while (!queue.isEmpty()){
            System.out.println(queue.size());
            currNodeCoords= queue.remove();
            row= currNodeCoords[0];
            column= currNodeCoords[1];

            //System.out.println("Current Row: " + row + "   Current Column: " + column);
            //set to true, meaning it has been visited
            markedNodes.get(row).set(column, true);

            //add adjacent nodes to queue
            for(Integer[] adjacentNode: adjacencyList.get(row).get(column)){
                if(!markedNodes.get(adjacentNode[0]).get(adjacentNode[1])){
                    queue.add(adjacentNode);
                    ArrayList<String> nodeParent= new ArrayList<>();
                    nodeParent.add(String.valueOf(row));
                    nodeParent.add(String.valueOf(column));
                    nodeParent.add(moveToChild(row, column, adjacentNode[0], adjacentNode[1]));
                    parentNodeList.get(adjacentNode[0]).set(adjacentNode[1], nodeParent);
                }

            }


            if(row== maze.findExitTile() && column== maze.getWidth()-1 ){
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
        else{
            return "LEFT";
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
}

