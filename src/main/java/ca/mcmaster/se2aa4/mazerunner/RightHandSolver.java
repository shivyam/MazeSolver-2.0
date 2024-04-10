package ca.mcmaster.se2aa4.mazerunner;

public class RightHandSolver implements MazeExploration{

    private Maze dummyMaze;


    public RightHandSolver(Maze maze){
        this.dummyMaze= maze;
    }


    //finds path in the form "FFFRRFFLLF", stores it in path object
    public Path solve(){
        int endRow= dummyMaze.findExitTile();
        String path="";
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
        Path finalPath= new Path(path);
        return finalPath;
    }
}