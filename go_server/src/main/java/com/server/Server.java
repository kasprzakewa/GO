package com.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.server.game.Player;
import com.server.game.StoneColor;
import com.server.game.bot.Bot;

public class Server {


    final static int PVP = 0;
    final static int BOT = 1;
    final static int PLAYER1 = 1;
    final static int PLAYER2 = 2;
    
    public static void main(String[] args){

        ArrayList<Player> waitingPlayers = new ArrayList<>();

        try(ServerSocket serverSocket = new ServerSocket(6666)){

            System.out.println("Server is listening on port 2137");

            while (true) {

                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                try {

                    Player player = new Player(StoneColor.EMPTY, socket);
                    //DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    int mode;

                    mode = player.recieveMessage();

                    if (mode == PVP){

                        waitingPlayers.add(player);
                        System.out.println("poszlo");
                        
                    }

                    if (mode == BOT){

                        player.setColor(StoneColor.BLACK);
                        Bot bot = new Bot(StoneColor.WHITE);
                        GameEngine engine = new GameEngine(player, bot);
                        engine.initGame(19);
                        Thread engineThread = new Thread(engine);
                        engineThread.setDaemon(true);
                        engineThread.start();
                        
                    }

                    if (waitingPlayers.size()==2){
                        System.out.println("sending player info");
                        waitingPlayers.get(0).sendMessage(PLAYER1);
                        waitingPlayers.get(0).setColor(StoneColor.BLACK);
                        waitingPlayers.get(1).sendMessage(PLAYER2);
                        waitingPlayers.get(1).setColor(StoneColor.WHITE);
                        
                        System.out.println("starting game");
                        GameEngine engine = new GameEngine(waitingPlayers.get(0), waitingPlayers.get(1));
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
