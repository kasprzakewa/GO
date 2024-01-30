package com.server.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class Player implements Opponent
{

    protected StoneColor color;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Board board;

    public void setColor(StoneColor color) {
        this.color = color;
    }

    public DataInputStream getInputStream() {
        return in;
    }

    public void setInputStream(DataInputStream in) {
        this.in = in;
    }

    public DataOutputStream getOutputStream() {
        return out;
    }

    public void setOutputStream(DataOutputStream out) {
        this.out = out;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player(Socket socket, Board board) throws IOException
    {
        this.color = color;
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        this.board = board;
        
    }

    public StoneColor getColor() 
    {
        return color;
    }

    public boolean makeMove(Point position)
    {
        return this.board.placeStone(position, color);
    }

    @Override
    public void sendMessage(int message) throws IOException{
        out.writeInt(message);
    }

    @Override
    public int receiveMessage() throws IOException{

        return in.readInt();
    }

    public int getTerritory()
    {
        return this.board.getTerritory(color);

    }

    public int getPoints()
    {
        return this.board.getPoints().get(color);
    }

}

