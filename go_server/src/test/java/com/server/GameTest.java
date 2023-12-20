package com.server;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;

import com.server.game.Game;

public class GameTest 
{
    private Game game;
    private Socket mockSocket1;
    private Socket mockSocket2;

    @Before
    public void setUp() throws IOException 
    {
        mockSocket1 = mock(Socket.class);
        mockSocket2 = mock(Socket.class);

        game = new Game(19, mockSocket1, mockSocket2);
    }

    @Test
    public void testGameInitialization() 
    {
        assertNotNull(game.getBoard());
        assertNotNull(game.getWhitePlayer());
        assertNotNull(game.getBlackPlayer());
    }
}
