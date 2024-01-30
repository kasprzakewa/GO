package com.server.game;

import java.io.IOException;
import java.net.Socket;

public class WhitePlayer extends Player
{

    public WhitePlayer(Socket socket, Board board) throws IOException 
    {
        super(socket, board);
        this.color = StoneColor.WHITE;
    }
    
}
