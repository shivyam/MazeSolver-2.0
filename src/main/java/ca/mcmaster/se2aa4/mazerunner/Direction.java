package ca.mcmaster.se2aa4.mazerunner;

public class Direction{

    public Maze maze;
    public int height;
    public int width;

    public String direction="east";

    public Direction(Maze maze){
        this.maze= maze;
        this.height= maze.getHeight();
        this.width= maze.getWidth();

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

    //checks if there is an open tile above current tile
    public boolean canCheckUp(int currRow, int currColumn){
        if (currRow-1>=0 && maze.getTile(currRow-1,currColumn)==1){
            return true;
        }
        return false;
    }
    
    //checks if there is an open tile below current tile
    public boolean canCheckDown(int currRow, int currColumn){
        if (currRow+1<maze.getHeight() && maze.getTile(currRow+1,currColumn)==1){
            return true;
        }
        return false;
    }

    //checks if there is an open tile to the left of the current tile
    public boolean canCheckLeft(int currRow, int currColumn){
        if (currColumn-1>=0 && maze.getTile(currRow,currColumn-1)==1){
            return true;
        }
        return false;
    }

    //checks if there is an open tile to the right of the current tile
    public boolean canCheckRight(int currRow, int currColumn){
        if (currColumn+1<maze.getWidth() && maze.getTile(currRow,currColumn+1)==1){
            return true;
        }
        return false;
    }

}