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
    Integer parentRow;
    Integer parentCol;
    Boolean marked= false;

    List<Integer[]> adjacentNodes= new ArrayList<>();


    public void addAdjacentNode(Integer[] coords){
        adjacentNodes.add(coords);
    }
    
}
