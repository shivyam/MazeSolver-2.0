package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> convertUserPath(String userPath){
        //strips whitespace from user input path
        String path = userPath.replaceAll("\\s", "");
        String[] path_arr= path.split("");
        List<String> expanded = new ArrayList<String>();

        for (int i = 0; i < path_arr.length; i++) {
            if (!Character.isDigit(path_arr[i].charAt(0))) {
                expanded.add(path_arr[i]);
            } else {
                int count = 0;
                int digit = 0;
                do {
                    count *= (int) Math.pow(10, digit++);
                    count += Character.getNumericValue(path.charAt(i++));
                } while (Character.isDigit(path_arr[i].charAt(0)));

                for(int j=0;j<count;j++){
                    expanded.add(path_arr[i]);
                }
            }
        }
        return expanded;
    }

    //outputs string message verifying if user input path is correct or not 
    public String checkPath(){
        //if no path is inputted
        if (userPath.equals("")){
            return "";
        }
        if (checkLeftToRight()){
            return "correct path";
        }
        else if(checkRightToLeft()){
            return "correct path";
        }
        else{
            return "incorrect path";
        }
    }


    private boolean checkLeftToRight(){
        int currColumn=0;
        int currRow= dummyMaze.findEntryTile();
        int endRow = dummyMaze.findExitTile();
        return traverseMaze(currColumn, currRow, endRow);
    }

    private boolean checkRightToLeft(){
        int currColumn= dummyMaze.getWidth();
        int currRow= dummyMaze.findExitTile();
        int endRow = dummyMaze.findEntryTile();
        dir.setDirection("west");
        return traverseMaze(currColumn, currRow, endRow);

    }

    private boolean traverseMaze(int currColumn, int currRow, int endRow){
        List<String> path= convertUserPath(userPath);
        int width= dummyMaze.getWidth();
        int height = dummyMaze.getHeight();
        int index=0;
        while(currColumn<=(width-1) && currColumn>=0 && currRow>=0 && currRow<=(height-1) && index<path.size()){

                //if current tile is a wall, this automatically means that the path is incorrect
                //terminates path check immediately
                if(dummyMaze.getTile(currRow,currColumn)==0){
                    return false;
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
                return true;
        }
        else{
            return false;
        }
    }


    //turns user right by changing the direction they are facing based on their current direction
    private void turnRight() {
        String direction = dir.getDirection();
        switch (direction) {
            case "east":
                dir.setDirection("south");
                break;
            case "south":
                dir.setDirection("west");
                break;
            case "west":
                dir.setDirection("north");
                break;
            default:
                dir.setDirection("east");
                break;
        }
    }
    

    //turns user left by changing the direction they are facing based on their current direction
    private void turnLeft() {
        String direction = dir.getDirection();
        switch (direction) {
            case "east":
                dir.setDirection("north");
                break;
            case "south":
                dir.setDirection("east");
                break;
            case "west":
                dir.setDirection("south");
                break;
            default:
                dir.setDirection("west");
                break;
        }
    }
}