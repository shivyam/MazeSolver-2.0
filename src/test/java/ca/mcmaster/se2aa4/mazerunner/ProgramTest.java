package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramTest {

    private Maze testMaze;
    private Position testPosition;
    
    @BeforeEach
    public void testMaze() throws Exception{
        this.testMaze= new Maze("./examples/small.maz.txt");
        this.testPosition = new Position(testMaze);
    }

    //Maze Exploration interface test
    public void testFindFactorizedPath(){
        GraphAlgorithmSolver test= new GraphAlgorithmSolver(testMaze);
        assertEquals("F L F R 2F L 6F R 4F R 2F L 2F R 2F L F", test.findFactorizedPath());
    }

    //Direction class tests
    @Test
    public void testMoveNorth() {
        Direction direction = new Direction(testMaze);
        int initialRow = 2;
        int newRow = direction.moveNorth(initialRow);
        assertEquals(1, newRow); 
    }

    @Test
    public void testMoveEast() {
        Direction direction = new Direction(testMaze);
        int initialColumn = 2;
        int newColumn = direction.moveEast(initialColumn);
        assertEquals(3, newColumn); 
    }

    @Test
    public void testMoveSouth() {
        Direction direction = new Direction(testMaze);
        int initialRow = 2;
        int newRow = direction.moveSouth(initialRow);
        assertEquals(3, newRow);
    } 


    @Test
    public void testMoveWest() {
        Direction direction = new Direction(testMaze);
        int initialColumn = 2;
        int newColumn = direction.moveWest(initialColumn);
        assertEquals(1, newColumn); 
    }


    @Test
    public void testCanCheckUp() {
        Direction direction = new Direction(testMaze);
        assertEquals(false, direction.canCheckUp(0, 2)); 
        assertEquals(true, direction.canCheckUp(8, 1));
    }

    @Test
    public void testCanCheckDown() {
        Direction direction = new Direction(testMaze);
        assertEquals(true,direction.canCheckDown(0, 7));
        assertEquals(false, direction.canCheckDown(4, 2)); 
    }

    
    @Test
    public void testCanCheckLeft() {
        Direction direction = new Direction(testMaze);
        assertEquals(true, direction.canCheckLeft(7, 2)); 
        assertEquals(false, direction.canCheckLeft(1, 1));
    }

    @Test
    public void testCanCheckRight() {
        Direction direction = new Direction(testMaze);
        assertEquals(true,direction.canCheckRight(8, 0)); 
        assertEquals(false, direction.canCheckRight(0, 0)); 
    }



    //Maze class tests

    @Test
    public void testGetWidth() {
        assertEquals(11, testMaze.getWidth()); 
    }

    @Test
    public void testGetHeight() {
        assertEquals(11, testMaze.getHeight()); 
    }

    @Test
    public void testGetTile() {
        assertEquals(0, testMaze.getTile(0, 0)); 
        assertEquals(1, testMaze.getTile(8, 0)); 
    }

    @Test
    public void testFindEntryTile() {
        assertEquals(8, testMaze.findEntryTile()); 
    }

    @Test
    public void testFindExitTile() {
        assertEquals(5, testMaze.findExitTile()); 
    }


    //Position class tests

    @Test
    public void testCanTurnRight() {
        assertEquals(false,testPosition.canTurnRight()); 
    }

    @Test
    public void testCanTurnLeft() {
        assertEquals(false, testPosition.canTurnLeft()); 
    }
    
    @Test
    public void testCanMoveForward() {
        assertEquals(true, testPosition.canMoveForward()); 
    }

    @Test
    public void testTurnRight() {
        String initialDirection = testPosition.dir.getDirection();
        testPosition.turnRight();
        assertNotEquals(initialDirection, testPosition.dir.getDirection()); 
    }

    @Test
    public void testTurnLeft() {
        String initialDirection = testPosition.dir.getDirection();
        testPosition.turnLeft();
        assertNotEquals(initialDirection, testPosition.dir.getDirection()); 
    }

    @Test
    public void testMoveForward() {
        int initialRow = testPosition.getCurrRow();
        int initialColumn = testPosition.getCurrColumn();
        testPosition.moveForward();
        assertEquals(initialRow, testPosition.getCurrRow()); 
        assertNotEquals(initialColumn, testPosition.getCurrColumn()); 
    }


    //Path Validator class tests
    @Test
    public void testPathValidator(){
        PathValidator check= new PathValidator(testMaze,"F L F R 2F L 6F R 4F R 2F L 2F R 2F L F");
        assertEquals("correct path", check.checkPath());
    }

    //Node class tests

    @Test
    public void testGetDirection(){
        Node node= new Node();
        assertEquals("east", node.getDirection());
    }

    public void testSetDirection(){
        Node node= new Node();
        node.setDirection("south");
        assertEquals("south", node.getDirection());
    }

}
