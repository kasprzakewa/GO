package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.server.servercore.ServerThread;

public class Server {
    
    public static void main(String[] args){

        //Jakiś rozdzielacz tego co trzeba robić o w tym miejscu powstanie
        //i będzie przekazywany do każdego threada

        try(ServerSocket serverSocket = new ServerSocket(6666)){

            System.out.println("Server is listening on port 2137");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                new ServerThread(socket).start();
            }
        }
        catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }
}
