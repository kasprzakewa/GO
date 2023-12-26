package com.server;

import java.io.IOException;
import java.net.Socket;

import com.server.game.Game;
import com.server.game.Opponent;

public class GameEngine implements Runnable {

    private Game game;

    private Opponent player1;
    private Opponent player2;


    public GameEngine(Opponent player1, Opponent player2){

        this.player1=player1;
        this.player2=player2;
    }

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
