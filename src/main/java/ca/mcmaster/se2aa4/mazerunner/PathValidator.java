package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.Arrays;
public class PathValidator{

    public Maze dummyMaze;
    public String userPath;
    Direction dir;

    public PathValidator(Maze maze, String userPath){
        this.dummyMaze= maze;
        this.userPath= userPath;
        this.dir= new Direction(dummyMaze);
    }

    //Converts and returns user input path in canonical form, and strips all whitespace fro inputted path
    public ArrayList<String> convertUserPath(String userPath){
        //strips whitespace from user input path
        String path = userPath.replaceAll("\\s", "");
        String[] path_arr= path.split("");
        ArrayList<String> canonicalPath= new ArrayList<>();
        int repetition=1;

        for(int i=0;i<path_arr.length;i++){
            if(!(path_arr[i].equals("F")) && !(path_arr[i].equals("L")) &&!(path_arr[i].equals("R"))){
                repetition= Integer.parseInt(path_arr[i]);
                continue;
            }

            else{
                for(int j=0;j<repetition;j++){
                    canonicalPath.add(path_arr[i]);
                }
                repetition=1;
            }
        }
        return canonicalPath;
    }


    //outputs string message verifying if user input path is correct or not 
    public String checkPath(){
        //if no path is inputted
        if (userPath.equals("")){
            return "";
        }
        ArrayList<String> path= convertUserPath(userPath);
       
        int currColumn=0;
        int currRow= dummyMaze.findEntryTile();
        int endRow = dummyMaze.findExitTile();

        int width= dummyMaze.getWidth();
        int height = dummyMaze.getHeight();

        int index=0;
        while(currColumn<=(width-1) && currColumn>=0 && currRow>=0 && currRow<=(height-1) && index<path.size()){

                //if current tile is a wall, this automatically means that the path is incorrect
                //terminates path check immediately
                if(dummyMaze.getTile(currRow,currColumn).equals("WALL ")){
                    return "incorrect path";
                }

                if(path.get(index).equals("F")){
                    //moves user forward based on the direction they are currently facing
                    //adjusts current position in the maze accordingly
                    if(dir.getDirection().equals("east")){
                        currColumn+=1;
                    }
                    else if(dir.getDirection().equals("south")){
                        currRow+=1;
                    }
                    else if(dir.getDirection().equals("west")){
                        currColumn-=1;
                    }
                    else{
                        currRow-=1;
                    }
                }
                
                //turns user left
                else if((path.get(index).equals("L"))){
                    turnLeft();
                }

                //turns user right
                else if((path.get(index).equals("R"))){
                    turnRight();
                }
            index+=1;
        }
        
        //checks if final position is the maze's exit tile
        if(currRow==(endRow) && currColumn==(width-1) && index==path.size()){
                return "correct path";
        }
        else{
            return "incorrect path";
        }
    }


    //turns user right by changing the direction they are facing based on their current direction
    public void turnRight(){
        String direction= dir.getDirection();
        if(direction.equals("east")){
            dir.setDirection("south");
        }
        else if(direction.equals("south")){
            dir.setDirection("west");
        }
        else if(direction.equals("west")){
            dir.setDirection("north");
        }
        else{
            dir.setDirection("east");
        }
    }


    //turns user left by changing the direction they are facing based on their current direction
    public void turnLeft(){
        String direction= dir.getDirection();
        if(direction.equals("east")){
            dir.setDirection("north");
        }
        else if(direction.equals("south")){
            dir.setDirection("east");
        }
        else if(direction.equals("west")){
            dir.setDirection("south");
        }
        else{
            dir.setDirection("west");
        }
    }


        
    
    
}