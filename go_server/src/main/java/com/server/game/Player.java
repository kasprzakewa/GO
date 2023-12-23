package com.server.game;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Player implements Opponent
{

    private StoneColor color;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

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

    public Player(StoneColor color, Socket socket) throws IOException
    {
        this.color = color;
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        
    }

    public StoneColor getColor() 
    {
        return color;
    }

    public boolean makeMove(Board board, Point position)
    {
        board.placeStone(position, this.color);
        boolean placed = false;

        if (board.getStone(position.getX(), position.getY()).getColor() == this.color)
        {
            placed = true;

            for (int i = 0; i < board.getSize(); i++)
            {
                for (int j = 0; j < board.getSize(); j++)
                {
                    board.removeGroup(board.getStone(i, j), this.color);
                }
            }
        }

        return placed;
    }

    @Override
    public void sendMessage(int message) {
        try {
            out.writeInt(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int recieveMessage() {
        try {
            return in.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}

