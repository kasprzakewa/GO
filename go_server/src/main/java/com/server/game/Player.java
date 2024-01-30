package com.server.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Player implements Opponent
{

    protected StoneColor color;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Board board;

    public void setColor(StoneColor color) {
        this.color = color;
    }

    public BufferedReader getInputStream() {
        return in;
    }

    public void setBufferedReader(BufferedReader in) {
        this.in = in;
    }

    public PrintWriter getPrintWriter() {
        return out;
    }

    public void setPrintWriter(PrintWriter out) {
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
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
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
    public void sendMessage(String message) throws IOException{
        out.println(message);
    }

    @Override
    public String receiveMessage() throws IOException{

        return in.readLine();
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

