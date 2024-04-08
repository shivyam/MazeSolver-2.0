package ca.mcmaster.se2aa4.mazerunner;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.util.Queue;
import java.util.LinkedList;

public class Redo{


    Maze maze;
    Direction dir;


    List<List<Node>> nodes= new ArrayList<>();


    public Redo(Maze maze){
        this.maze= maze;
        this.dir= new Direction(maze);

        
    }
}

