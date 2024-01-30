package com.server;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.server.game.Board;
import com.server.game.ServerGame;
import com.server.game.Player;
import com.server.game.StoneColor;
import com.server.game.bot.Bot;
import com.server.game.database.DataBaseManager;

import jakarta.persistence.Persistence;

public class ClientServer implements Runnable{

    private List<Player> waitingPlayers;
    private Socket socket;
    private Object waitingListMutex;
    private Board board;
    private jakarta.persistence.EntityManagerFactory emf;
    private jakarta.persistence.EntityManager em;

    final static int GAME_FOUND = 1;

    final static int PVP = 0;
    final static int BOT = 1;

    final static int PLAYER1 = 1;
    final static int PLAYER2 = 2;


    public ClientServer(Socket socket, List<Player> waitingPlayers, Object waitingListMutex){

        this.waitingPlayers = waitingPlayers;
        this.socket = socket;
        this.waitingListMutex = waitingListMutex;
        this.emf = Persistence.createEntityManagerFactory("default");
        this.em = emf.createEntityManager();
    }

    @Override
    public void run() {
        try {

            Player player = new Player(StoneColor.EMPTY, socket, board);
            //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            //while(true){
                String mode;
                mode = player.receiveMessage();
                System.out.println("mode: " + mode);
                
                if("db".equals(mode)){

                    player.receiveMessage();
                    int id = Integer.parseInt(player.receiveMessage());
                    System.out.println("id: " + id);
                    DataBaseManager db = new DataBaseManager(player, id, em);
                    Thread dbThread = new Thread(db);
                    dbThread.setDaemon(true);
                    dbThread.start();
                }
                if ("pvp".equals(mode)){
                    synchronized (waitingListMutex){
                        waitingPlayers.add(player);
                    }
                    
                }

                if ("bot".equals(mode)){

                    player.sendMessage("true");
                    player.sendMessage("1");
                    player.setColor(StoneColor.BLACK);
                    Bot bot = new Bot(StoneColor.WHITE, board);
                    ServerGame game = new ServerGame(19, player, bot, em);
                    Thread gameThread = new Thread(game);
                    gameThread.setDaemon(true);
                    gameThread.start();
                    System.out.println("starting game");
                    
                }
                int queueSize;
                Player player1 = null;
                Player player2 = null;
                synchronized (waitingListMutex){
                    queueSize = waitingPlayers.size();
                    System.out.println("queue size: " + queueSize);
                    if (queueSize == 2){
                        System.out.println("found 2 players");
                        player1 = waitingPlayers.get(0);
                        player2 = waitingPlayers.get(1);
                        waitingPlayers.clear();
                    }
                }
                if (queueSize==2 && player1!=null && player2!=null){

                    System.out.println("sending player info");
                    player1.sendMessage("true");
                    player1.sendMessage("1");
                    player1.setColor(StoneColor.BLACK);
                    player2.sendMessage("true");
                    player2.sendMessage("2");
                    player2.setColor(StoneColor.WHITE);

                    ServerGame game = new ServerGame(19, player1, player2,em);
                    Thread gameThread = new Thread(game);
                    gameThread.setDaemon(true);
                    gameThread.start();
                    System.out.println("starting game");
                }
            //}
            em.close();
            emf.close();
        }
        catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }
    
}
