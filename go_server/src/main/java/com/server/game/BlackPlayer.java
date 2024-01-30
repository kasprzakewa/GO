package com.server.game;

import java.io.IOException;
import java.net.Socket;

public class BlackPlayer extends Player
{

    public BlackPlayer(Socket socket, Board board) throws IOException 
    {
        super(socket, board);
        this.color = StoneColor.BLACK;
    }
    
}
