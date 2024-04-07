package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Maze{

    private static final Logger logger = LogManager.getLogger();

    private List<List<Integer>> maze= new ArrayList<>();

    
    public Maze(String filePath) throws Exception {
        logger.debug("Reading the maze from file " + filePath);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            List<Integer> row = new ArrayList<>();
            for (int idx = 0; idx < line.length(); idx++) {
                if (line.charAt(idx) == '#') {
                    row.add(0);
                } else if (line.charAt(idx) == ' ') {
                    row.add(1);
                }
            }
            maze.add(row);
        }
        reader.close();
    }

    public List<List<Integer>> getMaze(){
        return maze;
    }


    //to aid with finding maze path + check user path
    public int getWidth(){
        if (maze.size()>0){
            return maze.get(0).size();
        }
        return -1;
    }


    //to aid with finding maze path + check user path
    public int getHeight(){
        if (maze.size()>0){
            return maze.size();
        }
        return -1;
    }


    //returns if current tile is a wall or open
    public Integer getTile(int rowIndex, int columnIndex){
        ArrayList<Integer> row= new ArrayList<>(maze.get(rowIndex));
        return row.get(columnIndex);
    }

    
    //searches last column in maze to find ending position
    public int findExitTile(){
        for (int i=0;i<maze.size();i++){
            ArrayList<Integer> row = new ArrayList<Integer>(maze.get(i));

            if (row.get(row.size()-1)==1){
                return i;
            }
        }
        return 0;
    }


    //searches first column in maze to find starting position
    public int findEntryTile(){
        for (int i=0;i<maze.size();i++){
            ArrayList<Integer> row = new ArrayList<Integer>(maze.get(i));

            if (row.get(0)==1){
                return i;
            }
        }
        return 0;
    }

    //to help visualize the maze when debugging
    public void printMaze(){
        for (int i=0; i<maze.size(); i++) {
            ArrayList<Integer> row = new ArrayList<Integer>(maze.get(i));
            for (int j=0; j<row.size(); j++) {
                System.out.print(row.get(j));
            }
            System.out.println("");
        }
       
    }
}


