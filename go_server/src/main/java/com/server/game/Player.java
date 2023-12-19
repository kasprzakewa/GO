package com.server.game;

public class Player 
{
    private StoneColor color;

    public Player(StoneColor color)
    {
        this.color = color;
        
    }

    public StoneColor getColor() 
    {
        return color;
    }

    public void makeMove(Board board, Point position)
    {
        board.placeStone(position, this.color);

        for (int i = 0; i < board.getSize(); i++)
        {
            for (int j = 0; j < board.getSize(); j++)
            {
                board.removeGroup(board.getStone(i, j));
            }
        }
        
    }
}

