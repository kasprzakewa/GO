package com.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Test;

import com.server.game.Board;
import com.server.game.Point;
import com.server.game.Stone;
import com.server.game.StoneColor;

public class BoardTest 
{

    @Test
    public void testBoardSize() 
    {
        Board board = new Board(5, new ArrayList<>());
        assertEquals(5, board.getSize());
    }

    @Test
    public void testPlaceStone() 
    {
        Board board = new Board(5, new ArrayList<>());
        assertTrue(board.placeStone(new Point(0, 0), StoneColor.BLACK));
    }

    @Test
    public void testCannotPlaceStone() 
    {
        Board board = new Board(5, new ArrayList<>());
        board.placeStone(new Point(0, 0), StoneColor.BLACK);
        assertFalse(board.placeStone(new Point(0, 0), StoneColor.WHITE));
    }

    @Test
    public void testGetTerritory() 
    {
        Board board = new Board(5, new ArrayList<>());
        board.placeStone(new Point(0, 0), StoneColor.BLACK);
        assertEquals(board.getSize()*board.getSize() - 1, board.getTerritory(StoneColor.BLACK));
    }

    @Test
    public void testDeepCopy() 
    {
        Board board = new Board(5, new ArrayList<>());
        board.placeStone(new Point(0, 0), StoneColor.BLACK);
        Stone[][] copy = board.deepCopy(board);
        assertEquals(StoneColor.BLACK, copy[0][0].getColor());
    }

    @Test
    public void testSuicidalMove() 
    {
        Board board = new Board(5, new ArrayList<>());
        board.placeStone(new Point(0, 1), StoneColor.BLACK);
        board.placeStone(new Point(1, 0), StoneColor.BLACK);
        assertTrue(board.suicidalMove(new Point(0, 0), StoneColor.WHITE));
    }

    @Test
    public void testIsKo() 
    {
        ArrayList<String> history = new ArrayList<>();
        Board board = new Board(5, history);
        board.placeStone(new Point(0, 1), StoneColor.BLACK);
        history.add(board.toString());
        board.placeStone(new Point(1, 0), StoneColor.BLACK);
        history.add(board.toString());
        board.placeStone(new Point(1, 2), StoneColor.BLACK);
        history.add(board.toString());
        board.placeStone(new Point(2, 1), StoneColor.BLACK);
        history.add(board.toString());
        board.placeStone(new Point(0, 2), StoneColor.WHITE);
        history.add(board.toString());
        board.placeStone(new Point(2, 2), StoneColor.WHITE);
        history.add(board.toString());
        board.placeStone(new Point(1, 3), StoneColor.WHITE);
        history.add(board.toString());
        board.placeStone(new Point(1, 1), StoneColor.WHITE);
        history.add(board.toString());
        assertTrue(board.isKo(new Point(1, 2), StoneColor.BLACK));
    }

    @Test
    public void testSave() 
    {
        ArrayList<String> history = new ArrayList<>();
        Board board = new Board(5, history);
        board.placeStone(new Point(0, 0), StoneColor.BLACK);
        history.add(board.toString());
        assertEquals("B.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E.E", board.getHistory().get(0));
    }

    @Test
    public void testGetGroups() 
    {
        Board board = new Board(5, new ArrayList<>());
        board.placeStone(new Point(0, 0), StoneColor.BLACK);
        board.placeStone(new Point(0, 1), StoneColor.BLACK);
        Set<ArrayList<Stone>> groups = board.getGroups();
        assertEquals(2, groups.size());
        // assertEquals(2, groups.iterator().next().size());
    }

    @Test
    public void testToBoard() 
    {
        Board board = new Board(5, new ArrayList<>());
        board.placeStone(new Point(0, 0), StoneColor.BLACK);
        String boardString = board.toString();
        Stone[][] newBoard = board.toBoard(boardString);
        assertEquals(StoneColor.BLACK, newBoard[0][0].getColor());
    }

    @Test
    public void testToString() 
    {
        Board board = new Board(5, new ArrayList<>());
        board.placeStone(new Point(0, 0), StoneColor.BLACK);
        String boardString = board.toString();
        assertTrue(boardString.startsWith("B"));
    }
}
