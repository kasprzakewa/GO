package com.server;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Player 
{
    private StoneColor color;
    private Socket socket;

    public Player(StoneColor color)
    {
        this.color = color;
        
    }

    public void setSocket(String ip, int port){
        try{

            this.socket = new Socket(ip, port);
        }
        catch(UnknownHostException e1){
            
        }
        catch(IOException e2){

        }
        
    }

    public StoneColor getColor() 
    {
        return color;
    }

    public void makeMove(Board board, Point position)
    {
        board.placeStone(position, this.color);
    }
}

