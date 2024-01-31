package com.client;

import com.client.servercommuniaction.Client;


import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TestClient {

    @Test
    public void testWriteToServer() throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(socket.getOutputStream()).thenReturn(outputStream);
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
        Client client = new Client();

        client.setSocket(socket);
        client.writeToServer("Test message");

        verify(socket, times(1)).getOutputStream();
        assertEquals("Test message\n", outputStream.toString());
    }

    @Test
    public void testReadFromServer() throws IOException {
        Socket socket = mock(Socket.class);
        String testMessage = "Test message";
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream(testMessage.getBytes()));
        when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        Client client = new Client();
        client.setSocket(socket);
        String response = client.readFromServer();

        verify(socket, times(1)).getInputStream();
        assertEquals(testMessage, response);
    }
}


