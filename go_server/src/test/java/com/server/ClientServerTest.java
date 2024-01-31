package com.server;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.server.game.Player;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientServerTest 
{

    private Socket socket;
    private List<Player> waitingPlayers;
    private Object waitingListMutex;
    private ClientServer clientServer;
    private InputStream inputStream;
    private OutputStream outputStream;


    @Before
    public void setUp() throws IOException 
    {
        socket = mock(Socket.class);
        waitingPlayers = new ArrayList<>();
        waitingListMutex = new Object();
        // clientServer = new ClientServer(socket, waitingPlayers, waitingListMutex);
        inputStream = mock(InputStream.class);
        outputStream = mock(OutputStream.class);
        when(socket.getInputStream()).thenReturn(inputStream);
        when(socket.getOutputStream()).thenReturn(outputStream);
    }

    @Test
    public void testGetMode() 
    {
        clientServer.setMode(ClientServer.PVP);
        assertEquals(ClientServer.PVP, clientServer.getMode());
    }

    @Test
    public void testGetWaitingPlayers() 
    {
        Player player = mock(Player.class);
        waitingPlayers.add(player);
        assertEquals(waitingPlayers, clientServer.getWaitingPlayers());
    }

    @Test
    public void testSetWaitingPlayers() 
    {
        List<Player> newWaitingPlayers = new ArrayList<>();
        Player player = mock(Player.class);
        newWaitingPlayers.add(player);
        clientServer.setWaitingPlayers(newWaitingPlayers);
        assertEquals(newWaitingPlayers, clientServer.getWaitingPlayers());
    }

    @Test
    public void testRun() throws IOException 
    {
        Player player = Mockito.mock(Player.class);
        when(player.receiveMessage()).thenReturn(ClientServer.PVP);
        waitingPlayers.add(player);
        clientServer.run();

    }
}
