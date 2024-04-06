package ca.mcmaster.se2aa4.mazerunner;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GraphAlgorithmSolver{


    Maze maze;
    List<List<Boolean>> markedNodes= new ArrayList<>();
    List<List<List<String>>> prevNode= new ArrayList<>();


    public String solve(Maze maze){


        this.maze= maze;




        for(int i=0;i<maze.getHeight();i++){
            List<Boolean> markedLine = new ArrayList<>(Collections.nCopies(maze.getWidth(), false));
            markedNodes.add(markedLine);
        }


        for (int i = 0; i < maze.getHeight(); i++) {
            List<List<String>> prevNodeLine = new ArrayList<>();
            for (int j = 0; j < maze.getWidth(); j++) {
                prevNodeLine.add(new ArrayList<>());
            }
            prevNode.add(prevNodeLine);
        }


        return breadthFS();
       
    }


    public String breadthFS(){
    
        //set start previous node to null
        ArrayList<String> initialNodeDir= new ArrayList<>();
        initialNodeDir.add(null);
        initialNodeDir.add("null");


        Integer x= 0;
        Integer y = maze.findEntryTile();


        prevNode.get(y).set(0, initialNodeDir);



        while (true){
            ArrayList<String> nodeDir= new ArrayList<>();
            nodeDir.add(null);
            nodeDir.add("null");
            prevNode.get(y).set(x, nodeDir);


            if(x== maze.getWidth() && y== maze.findExitTile()){
                break;
            }
           break;
        }
        return "";
    }




   
}

