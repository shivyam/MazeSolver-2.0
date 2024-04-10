package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class GraphAlgorithmSolver extends MazeExploration{
    Maze maze;
    List<List<Node>> nodes= new ArrayList<>();
    Direction findMovement;


    public GraphAlgorithmSolver(Maze maze){
        this.maze= maze;
        this.findMovement= new Direction(maze);

        //initializes all nodes and stores them in a 2D arraylist
        for (int i = 0; i < maze.getHeight(); i++) {
            List<Node> node = new ArrayList<>();
            for (int j = 0; j < maze.getWidth(); j++) {
                node.add(new Node());
            }
            nodes.add(node);
        }
        adjacencyList();
    }

    //creates adjacency list for graph representation
    private void adjacencyList(){
        for(int i=0;i<maze.getHeight();i++){
            for(int j=0;j<maze.getWidth();j++){
                setAdjacentNodesList(i, j);
            }
        }
    }

    //helper method for creating adjacency list
    //adds all adjacent nodes to the specified nodes adjacent node list
    private void setAdjacentNodesList(int currRow, int currColumn){
        Node currNode= nodes.get(currRow).get(currColumn);
        if (findMovement.canCheckUp(currRow,currColumn)){
            currNode.addAdjacentNode(new Integer[] {currRow-1, currColumn});
        }
        if (findMovement.canCheckDown(currRow,currColumn)){
            currNode.addAdjacentNode(new Integer[] {currRow+1, currColumn});
        }
        if (findMovement.canCheckLeft(currRow,currColumn)){
            currNode.addAdjacentNode(new Integer[] {currRow, currColumn-1});
        }
        if (findMovement.canCheckRight(currRow,currColumn)){
            currNode.addAdjacentNode(new Integer[] {currRow, currColumn+1});
        }
    }

    public String solve(){
        Queue<Integer[]> queue = new LinkedList<>();
        //adds starting node to queue
        queue.add(new Integer[] {maze.findEntryTile(),0});

        while (!queue.isEmpty()){
            
            Integer[] currNodeCoords= queue.remove();
            Integer currRow= currNodeCoords[0];
            Integer currColumn= currNodeCoords[1];
            Node currNode= nodes.get(currRow).get(currColumn);

            //reached the exit node
            if(currRow== maze.findExitTile() && currColumn== maze.getWidth()- 1){
                break;
            }

            //set to true, meaning node has been visited
            currNode.setMarkedTrue();

            
            for(Integer[] adjacentNodeCoord: currNode.getAdjacentNodesList()){
                Integer adjRow = adjacentNodeCoord[0];
                Integer adjCol = adjacentNodeCoord[1];
                Node adjacentNode= nodes.get(adjRow).get(adjCol);
                if(!adjacentNode.getMarked()){
                    //add adjacent nodes to queue
                    queue.add(adjacentNodeCoord);

                    //sets current node attributes
                    adjacentNode.setParentDirection(currNode.getDirection());
                    adjacentNode.setNodeAttributes(currRow, currColumn, adjRow, adjCol);
                }
            }    
        }
        return buildPath();
    }

    //backtracks from ending node to generate the maze path
    private String buildPath(){
        StringBuilder path = new StringBuilder();

        Integer currRow= maze.findExitTile();
        Integer currColumn= maze.getWidth()-1; 
        Node currNode= nodes.get(currRow).get(currColumn);

        //the move the user needs to take to get from the parent node to its child node
        String move= currNode.getMove();

        //loops until node does not have a parent (aka starting node)
        while(!move.equals("null")){

            //adds moves to path
            path.insert(0,move);

            currRow= Integer.parseInt(currNode.getParentRow());
            currColumn= Integer.parseInt(currNode.getParentColumn());
            currNode= nodes.get(currRow).get(currColumn);
            move= currNode.getMove();
        }
        return path.toString();
    }
}

