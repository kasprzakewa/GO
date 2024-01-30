package com.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.Socket;

import org.junit.Test;

import com.server.game.BlackPlayer;
import com.server.game.Board;
import com.server.game.Player;
import com.server.game.Point;
import com.server.game.StoneColor;
import org.mockito.Mockito;
import java.io.*;

public class PlayerTest 
{

    @Test
    public void testMakeMove() throws IOException 
    {
        Socket socket = Mockito.mock(Socket.class);
        Mockito.when(socket.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
        Mockito.when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        Board board = Mockito.mock(Board.class);
        Mockito.when(board.placeStone(Mockito.any(), Mockito.any())).thenReturn(true);
        Player player = new BlackPlayer(socket, board);
        assertTrue(player.makeMove(new Point(0, 0)));
    }

    @Test
    public void testSendMessage() throws IOException 
    {
        Socket socket = Mockito.mock(Socket.class);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Mockito.when(socket.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
        Mockito.when(socket.getOutputStream()).thenReturn(baos);
        Board board = Mockito.mock(Board.class);
        Player player = new BlackPlayer(socket, board);
        player.sendMessage("123");
        assertEquals("123", new DataInputStream(new ByteArrayInputStream(baos.toByteArray())).readInt());
    }
}
