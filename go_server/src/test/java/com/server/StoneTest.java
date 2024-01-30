package com.server;

import com.server.game.*;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;


public class StoneTest 
{
    private Stone stone;
    private Board board;
    private Point point;

    @Test
    public void testGetColor() 
    {
        point = new Point(0, 0);
        board = new Board(5, new ArrayList<>());
        stone = new Stone(StoneColor.BLACK, point, board);
        assertEquals(StoneColor.BLACK, stone.getColor());
    }

    @Test
    public void testSetColor() 
    {
        point = new Point(0, 0);
        board = new Board(5, new ArrayList<>());
        stone = new Stone(StoneColor.BLACK, point, board);
        stone.setColor(StoneColor.WHITE);
        assertEquals(StoneColor.WHITE, stone.getColor());
    }

    @Test
    public void testGetPosition() 
    {
        point = new Point(0, 0);
        board = new Board(5, new ArrayList<>());
        stone = new Stone(StoneColor.BLACK, point, board);
        assertEquals(point, stone.getPosition());
    }

    @Test
    public void testGetGroup() 
    {
        point = new Point(0, 0);
        board = new Board(5, new ArrayList<>());
        stone = new Stone(StoneColor.BLACK, point, board);
        ArrayList<Stone> group = stone.getGroup();
        assertEquals(1, group.size());
        assertEquals(stone, group.get(0));
    }

    @Test
    public void testIsCaptured() 
    {
        point = new Point(0, 0);
        board = new Board(5, new ArrayList<>());
        stone = new Stone(StoneColor.BLACK, point, board);
        assertFalse(stone.isCaptured());
    }

    @Test
    public void testSingleBreaths()
    {        
        point = new Point(0, 0);
        board = new Board(5, new ArrayList<>());
        stone = new Stone(StoneColor.BLACK, point, board);
        assertEquals(2, stone.singleBreaths());
    }

    @Test
    public void testRemove() 
    {
        point = new Point(0, 0);
        board = new Board(5, new ArrayList<>());
        stone = new Stone(StoneColor.BLACK, point, board);
        Stone stone1 = new Stone(StoneColor.WHITE, new Point(1, 0), board);
        board.setStone(stone1.getPosition(), stone1.getColor());
        Stone stone2 = new Stone(StoneColor.WHITE, new Point(0, 1), board);
        board.setStone(stone2.getPosition(), stone2.getColor());
        stone.remove(StoneColor.BLACK);
        assertEquals(StoneColor.BLACK, stone.getColor());
        stone.remove(StoneColor.WHITE);
        assertEquals(StoneColor.EMPTY, stone.getColor());
    }
}
