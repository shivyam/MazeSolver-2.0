package ca.mcmaster.se2aa4.mazerunner;
import java.util.ArrayList;
import java.util.Arrays;

public class RightHandSolver implements MazeExploration{

    private Maze dummyMaze;


    public RightHandSolver(Maze maze){
        this.dummyMaze= maze;
    }


    //finds path in the form "FFFRRFFLLF"
    public String findCanonicalPath(){
        int endRow= dummyMaze.findExitTile();
        String path="";
        Direction dir= new Direction(dummyMaze);
        Position pos= new Position(dummyMaze);

        if(pos.canMoveForward()){
            pos.moveForward();
            path+="F";
        }
        while(pos.getCurrRow()!=endRow|| pos.getCurrColumn()!=(dummyMaze.getWidth()-1)){
            if(pos.canTurnRight()){
                pos.turnRight();
                path+="R";
                pos.moveForward();
                path+="F";
            }
            else if(pos.canMoveForward()){
                pos.moveForward();
                path+="F";
            }
            else if(pos.canTurnLeft()){
                pos.turnLeft();
                path+="L";
                pos.moveForward();
                path+="F";
            }
            else{
                pos.turnLeft();
                path+="L";
                pos.turnLeft();
                path+="L";
                pos.moveForward();
                path+="F";
            }
        }
        return path;
    }
    

    //uses findCanonicalPath method to find maze path, then outputs path in the form "3F2R2F2LF"
    public String findFactorizedPath(){
        String path = findCanonicalPath();
        String[] path_arr= path.split("");
        String strFactorizedPath="";
        if(path_arr.length==0){
            return "";
        }

        int repetition=1;
        String track= path_arr[0];
        
        for(int i=1;i<path_arr.length;i++){
            if (path_arr[i].equals(track)){
                //increases count each time current move is repeated
                repetition+=1;
            }
            else{
                if(repetition>1){
                    strFactorizedPath+= String.valueOf(repetition) + track;
                }
                else{
                    strFactorizedPath+= track;
                }
                //resets move count
                track= path_arr[i];
                repetition=1;
            }   
        }

        //if move is only repeated once, only the move is printed. repetition variable is ignored as "1F" is less factorized than "F"
        if(repetition>1){
            strFactorizedPath+= repetition + path_arr[path_arr.length - 1];
        }
        else{
            strFactorizedPath+= path_arr[path_arr.length - 1];
        }
        
        return strFactorizedPath;
    }

    
}