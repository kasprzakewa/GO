package com.server.servercore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {

    Socket socket;
    
    public ServerThread(Socket socket){

        this.socket = socket;
    }

    @Override
    public void run() {
        
        try {
            InputStream input = socket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter out = new PrintWriter(output, true);

            in.close();
            out.close();
            socket.close();
        }
        catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }
}
