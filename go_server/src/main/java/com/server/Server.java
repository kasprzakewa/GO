package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.server.game.Player;

public class Server {
    
    public static void main(String[] args){

        Object mutexObject = new Object();

        ArrayList<Player> waitingPlayers = new ArrayList<>();

        try(ServerSocket serverSocket = new ServerSocket(6666)){

            System.out.println("Server is listening on port 2137");

            while (true) {

                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                ClientServer clientServer = new ClientServer(socket, waitingPlayers, mutexObject);
                Thread clientThread = new Thread(clientServer);
                clientThread.setDaemon(true);
                clientThread.start();
            }
        }
        catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }
}
