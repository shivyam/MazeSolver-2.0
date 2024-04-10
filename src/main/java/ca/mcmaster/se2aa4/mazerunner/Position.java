package ca.mcmaster.se2aa4.mazerunner;

public class Position{

    private static Maze dummyMaze;
    private static int currRow;
    private static int currColumn;
    private static Direction dir;

    public Position(Maze maze){
        Position.dummyMaze= maze;
        Position.dir= new Direction(dummyMaze);
        Position.currRow= dummyMaze.findEntryTile();
        Position.currColumn=0;
    }

    public int getCurrRow(){
        return currRow;
    }

    public int getCurrColumn(){
        return currColumn;
    }

    public static Direction getDirectionObj(){
        return dir;
    }

    //checks if the tile to the right of current position is not a wall
    public boolean canTurnRight() {
        String direction = dir.getDirection();
    
        switch (direction) {
            case "east":
                return !(dummyMaze.getTile(currRow + 1, currColumn)==0);
            case "south":
                return !(dummyMaze.getTile(currRow, currColumn - 1)==0);
            case "west":
                return !(dummyMaze.getTile(currRow - 1, currColumn)==0);
            default:
                return !(dummyMaze.getTile(currRow, currColumn + 1)==0);
        }
    }
    

    //checks if the tile to the left of current position is not a wall
    public boolean canTurnLeft() {
        String direction = dir.getDirection();
    
        switch (direction) {
            case "east":
                return !(dummyMaze.getTile(currRow - 1, currColumn)==0);
            case "south":
                return !(dummyMaze.getTile(currRow, currColumn + 1)==0);
            case "west":
                return !(dummyMaze.getTile(currRow + 1, currColumn)==0);
            default:
                return !(dummyMaze.getTile(currRow, currColumn - 1)==0);
        }
    }
    

    //turns user right by changing the direction they are facing based on their current direction
    public void turnRight() {
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
    public void turnLeft() {
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
    

    //checks if the tile ahead of current position is not a wall
    public boolean canMoveForward() {
        String direction = dir.getDirection();
    
        switch (direction) {
            case "north":
                return (currRow - 1 >= 0 && !(dummyMaze.getTile(currRow - 1, currColumn)==0));
            case "east":
                return (currColumn + 1 < dummyMaze.getWidth() && !(dummyMaze.getTile(currRow, currColumn + 1)==0));
            case "south":
                return (currRow + 1 <dummyMaze.getHeight() && !(dummyMaze.getTile(currRow + 1, currColumn)==0));
            case "west":
                return (currColumn - 1 >= 0 && !(dummyMaze.getTile(currRow, currColumn - 1)==0));
            default:
                return false;
        }
    }
    

    //moves user forward by updated their column or row value
    public void moveForward() {
        String direction = dir.getDirection();
    
        switch (direction) {
            case "north":
                currRow = dir.moveNorth(currRow);
                break;
            case "east":
                currColumn = dir.moveEast(currColumn);
                break;
            case "south":
                currRow = dir.moveSouth(currRow);
                break;
            case "west":
                currColumn = dir.moveWest(currColumn);
                break;
        }
    }

    
}