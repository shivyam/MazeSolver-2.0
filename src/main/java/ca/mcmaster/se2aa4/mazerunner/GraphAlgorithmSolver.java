package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class GraphAlgorithmSolver extends MazeExploration{
    Maze maze;
    List<List<Node>> nodes= new ArrayList<>();
    Direction checkMovement;

    public GraphAlgorithmSolver(Maze maze){
        this.maze= maze;
        this.checkMovement= new Direction(maze);
        //create all nodes
        for (int i = 0; i < maze.getHeight(); i++) {
            List<Node> node = new ArrayList<>();
            for (int j = 0; j < maze.getWidth(); j++) {
                node.add(new Node());
            }
            nodes.add(node);
        }
        adjacencyList();
    }


    private void adjacencyList(){
        for(int i=0;i<maze.getHeight();i++){
            for(int j=0;j<maze.getWidth();j++){
                setAdjacentNodesList(i, j);
            }
        }
    }

    private void setAdjacentNodesList(int currRow, int currColumn){
        Node currNode= nodes.get(currRow).get(currColumn);
        if (checkMovement.canCheckUp(currRow,currColumn)){
            currNode.addAdjacentNode(new Integer[] {currRow-1, currColumn});
        }
        if (checkMovement.canCheckDown(currRow,currColumn)){
            currNode.addAdjacentNode(new Integer[] {currRow+1, currColumn});
        }
        if (checkMovement.canCheckLeft(currRow,currColumn)){
            currNode.addAdjacentNode(new Integer[] {currRow, currColumn-1});
        }
        if (checkMovement.canCheckRight(currRow,currColumn)){
            currNode.addAdjacentNode(new Integer[] {currRow, currColumn+1});
        }
    }

    public String solve(){
        Queue<Integer[]> queue = new LinkedList<>();
        queue.add(new Integer[] {maze.findEntryTile(),0});

        while (!queue.isEmpty()){
            //Update marked node list
            Integer[] currNodeCoords= queue.remove();
            Integer currRow= currNodeCoords[0];
            Integer currColumn= currNodeCoords[1];
            Node currNode= nodes.get(currRow).get(currColumn);

            if(currRow== maze.findExitTile() && currColumn== maze.getWidth()- 1){
                break;
            }

            //set to true, meaning it has been visited
            currNode.setMarkedTrue();

            //add adjacent nodes to queue
            for(Integer[] adjacentNodeCoord: currNode.getAdjacentNodesList()){
                Integer adjRow = adjacentNodeCoord[0];
                Integer adjCol = adjacentNodeCoord[1];
                Node adjacentNode= nodes.get(adjRow).get(adjCol);
                if(!adjacentNode.getMarked()){
                    queue.add(adjacentNodeCoord);
                    adjacentNode.setParentDirection(currNode.getDirection());
                    adjacentNode.setNodeVariables(currRow, currColumn, adjRow, adjCol);
                }
            }    
        }
        return buildPath();
    }


    private String buildPath(){
        StringBuilder path = new StringBuilder();

        Integer currRow= maze.findExitTile();
        Integer currColumn= maze.getWidth()-1; 
        Node currNode= nodes.get(currRow).get(currColumn);
        String move= currNode.getMove();

        while(!move.equals("null")){
            path.insert(0,move);

            Integer tempRow= Integer.parseInt(currNode.getParentRow());
            currColumn= Integer.parseInt(currNode.getParentColumn());
            currRow= tempRow;
            currNode= nodes.get(currRow).get(currColumn);
            move= currNode.getMove();
        }
        return path.toString();
    }
}

