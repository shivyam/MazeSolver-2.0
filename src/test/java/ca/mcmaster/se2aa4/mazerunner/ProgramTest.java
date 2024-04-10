package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProgramTest {

    Maze testMaze;
    
    @BeforeEach
    public void testMaze() throws Exception{
        this.testMaze= new Maze("./examples/small.maz.txt");
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
}
