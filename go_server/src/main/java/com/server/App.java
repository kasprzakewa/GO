package com.server;

import com.server.game.Game;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) 
    {
        Game game = new Game(19);
        game.play();
    }
}
