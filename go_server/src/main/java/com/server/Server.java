package com.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.server.servercore.PlayerGameEngine;

public class Server {


    final static int PVP = 0;
    final static int BOT = 1;
    final static int PLAYER1 = 1;
    final static int PLAYER2 = 2;
    
    public static void main(String[] args){

        ArrayList<Socket> waitingPlayers = new ArrayList<>();

        try(ServerSocket serverSocket = new ServerSocket(6666)){

            System.out.println("Server is listening on port 2137");

            while (true) {

                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                try {

                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    int mode;

                    mode = in.readInt();

                    if (mode == PVP){

                        waitingPlayers.add(socket);
                        System.out.println("poszlo");
                        
                    }

                    if (mode == BOT){
                        //further implementation
                    }

                    if (waitingPlayers.size()==2){
                        System.out.println("sending player info");
                        new DataOutputStream(waitingPlayers.get(0).getOutputStream()).writeInt(PLAYER1);
                        new DataOutputStream(waitingPlayers.get(1).getOutputStream()).writeInt(PLAYER2);
                        
                        System.out.println("starting game");
                        PlayerGameEngine engine = new PlayerGameEngine(waitingPlayers.get(0), waitingPlayers.get(1));
                        engine.initGame(19);
                        Thread engineThread = new Thread(engine);
                        engineThread.setDaemon(true);
                        engineThread.start();

                        waitingPlayers.clear();
                    }
                    
                }
                catch (IOException e) {
                    System.out.println("Server exception: " + e.getMessage());
                }
            }
        }
        catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }
}
