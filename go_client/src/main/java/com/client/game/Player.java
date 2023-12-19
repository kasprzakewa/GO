package com.client.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Player 
{
    private StoneColor color;

    public Player(StoneColor color) throws IOException
    {
        this.color = color;

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
                    board.removeGroup(board.getStone(i, j));
                }
            }
        }

        return placed;
    }
}

