package com.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.server.game.Board;
import com.server.game.Point;
import com.server.game.StoneColor;

public class BoardTest 
{
    private Board board;

    // @Before
    // public void setUp() 
    // {
    //     board = new Board(19);
    // }

    // @Test
    // public void testInitializeBoard() 
    // {
    //     for (int i = 0; i < 19; i++) 
    //     {
    //         for (int j = 0; j < 19; j++) 
    //         {
    //             assertEquals(StoneColor.EMPTY, board.getStone(i, j).getColor());
    //         }
    //     }
    // }

    // @Test
    // public void testPlaceStone() 
    // {
    //     assertTrue(board.canPlaceStone(new Point(1, 1)));
    //     board.placeStone(new Point(1, 1), StoneColor.BLACK);
    //     assertEquals(StoneColor.BLACK, board.getStone(1, 1).getColor());
    //     assertFalse(board.canPlaceStone(new Point(1, 1)));
    // }
    
}
