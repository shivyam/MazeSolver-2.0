package ca.mcmaster.se2aa4.mazerunner;

public class Direction{

    public Maze dummyMaze;
    public int height;
    public int width;

    public String direction="east";

    public Direction(Maze maze){
        this.dummyMaze= maze;
        this.height= dummyMaze.getHeight();
        this.width= dummyMaze.getWidth();

    }

    public String getDirection(){
        return direction;
    }

    public void setDirection(String dir){
        this.direction=dir;
    }
    
    //returns updates row position if move is valid, else it returns -1
    public int moveNorth(int row){
        if(row-1>=0){
            row-=1;
            return row;
        }
        return -1;
        
    }

    //returns updates column position if move is valid, else it returns -1
    public int moveEast(int column){
        if(column+1<(width)){
            column+=1;
            return column;
        }
        return -1;
    }
    
    //returns updates row position if move is valid, else it returns -1
    public int moveSouth(int row){
        if(row+1<height){
            row+=1;
            return row;
        }
        return -1;
    }

    //returns updates column position if move is valid, else it returns -1
    public int moveWest(int column){
        if(column-1>=0){
            column-=1;
            return column;
        }
        return -1;
    }

}