package com.server;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;

import com.server.game.Game;
import com.server.game.Player;
import com.server.game.StoneColor;

public class GameTest 
{
    private Game game;

    @Before
    public void setUp() throws IOException 
    {
        Socket mockSocket1 = mock(Socket.class);
        Socket mockSocket2 = mock(Socket.class);

        game = new Game(19, new Player(StoneColor.BLACK, mockSocket1), new Player(StoneColor.WHITE, mockSocket1));

    }

    @Test
    public void testGameInitialization() 
    {
        assertNotNull(game.getBoard());
        assertNotNull(game.getWhitePlayer());
        assertNotNull(game.getBlackPlayer());
    }
}
