package com.server;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.server.game.Player;
import com.server.game.StoneColor;
import com.server.game.bot.Bot;

public class ClientServer implements Runnable{

    private List<Player> waitingPlayers;
    private Socket socket;
    private Object mutexObject;

    final static int GAMEFOUND = 1;

    final static int PVP = 0;
    final static int BOT = 1;

    final static int PLAYER1 = 1;
    final static int PLAYER2 = 2;


    public ClientServer(Socket socket, List<Player> waitingPlayers, Object mutexObject){

        this.waitingPlayers = waitingPlayers;
        this.socket = socket;
        this.mutexObject = mutexObject;
    }

    @Override
    public void run() {
        try {

            Player player = new Player(StoneColor.EMPTY, socket);
            //DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            int mode;
            mode = player.recieveMessage();

            if (mode == PVP){
                synchronized (mutexObject){
                    waitingPlayers.add(player);
                }
                
                System.out.println("poszlo");
                
            }

            if (mode == BOT){

                player.sendMessage(GAMEFOUND);
                player.sendMessage(PLAYER1);
                player.setColor(StoneColor.BLACK);
                Bot bot = new Bot(StoneColor.WHITE);
                GameEngine engine = new GameEngine(player, bot);
                engine.initGame(19);
                Thread engineThread = new Thread(engine);
                engineThread.setDaemon(true);
                engineThread.start();
                
            }
            int queueSize;
            Player player1 = null;
            Player player2 = null;
            synchronized (mutexObject){
                queueSize = waitingPlayers.size();
                System.out.println("queue size: " + queueSize);
                if (queueSize == 2){
                    player1 = waitingPlayers.get(0);
                    player2 = waitingPlayers.get(1);
                    waitingPlayers.clear();
                }
            }
            if (queueSize==2 && player1!=null && player2!=null){

                System.out.println("sending player info");
                player1.sendMessage(GAMEFOUND);
                player1.sendMessage(PLAYER1);
                player1.setColor(StoneColor.BLACK);
                player2.sendMessage(GAMEFOUND);
                player2.sendMessage(PLAYER2);
                player2.setColor(StoneColor.WHITE);
                
                System.out.println("starting game");
                GameEngine engine = new GameEngine(player1, player2);
                engine.initGame(9);
                Thread engineThread = new Thread(engine);
                engineThread.setDaemon(true);
                engineThread.start();
            }
        }
        catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }
    
}
