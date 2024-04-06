package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;
import java.io.IOException;

public class Main {

    private static final Logger logger = LogManager.getLogger();
    

    public static void main(String[] args) {
        ArrayList<ArrayList<String>> userMaze = new ArrayList<ArrayList<String>>();
        Configuration config= Configuration.load(args);
        
        String inputFilePath= config.inputFilePath();
        String userPath= config.userPath();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
            String line;
            
            while ((line = reader.readLine()) != null) {
                ArrayList<String> row = new ArrayList<String>();

                for (int idx = 0; idx < line.length(); idx++) {
                    if (line.charAt(idx) == '#') {
                        row.add("WALL ");
                    } else if (line.charAt(idx) == ' ') {
                        row.add("PASS ");
                    }  
                }
                userMaze.add(row);
            }
            
                Maze mazeTest= new Maze(userMaze);
                RightHandSolver path= new RightHandSolver(mazeTest);
                PathValidator check= new PathValidator(mazeTest, userPath);
                if((userPath.equals(""))){
                    logger.info(path.findFactorizedPath());
                }
                else{
                    logger.info(check.checkPath());
                }
        } 
        catch (IOException e) { 
            logger.error("Error occured while reading the file: " + e.getMessage());
        }
       
       

       
        
        
        
        
    }
}