package com.server.servercore;

import java.io.IOException;
import java.net.Socket;

import com.server.game.Game;

public class PlayerGameEngine implements GameEngine, Runnable {

    private Game game;

    private Socket player1;
    private Socket player2;


    public PlayerGameEngine(Socket player1, Socket player2){

        this.player1=player1;
        this.player2=player2;
    }

    @Override
    public void initGame(int size){
        
        try {

            this.game = new Game(size, player1, player2);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void run() {
                
        game.play();
    }
    
}
