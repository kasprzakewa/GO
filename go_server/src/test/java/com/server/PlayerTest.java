package com.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;

import com.server.game.Board;
import com.server.game.Player;
import com.server.game.Point;
import com.server.game.StoneColor;

public class PlayerTest 
{
    private Player player;

    @Before
    public void setUp() throws IOException 
    {
        Socket mockSocket = mock(Socket.class);

        player = new Player(StoneColor.BLACK, mockSocket);

    }

    @Test
    public void testMakeMove() 
    {
        Board board = new Board(19);

        assertTrue(player.makeMove(board, new Point(1, 1)));
        assertEquals(StoneColor.BLACK, board.getStone(1, 1).getColor());
    }
    
}
