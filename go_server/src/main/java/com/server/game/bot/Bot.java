package com.server.game.bot;

import com.server.game.Board;
import com.server.game.Opponent;
import com.server.game.Point;
import com.server.game.StoneColor;

public class Bot implements Opponent{

    private StoneColor color;

    public Bot(StoneColor color){
        this.color = color;
    }
    

    public StoneColor getColor() {
        return color;
    }

    public void setColor(StoneColor color) {
        this.color = color;
    }

    @Override
    public boolean makeMove(Board board, Point position) {

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendMessage'");
    }

    @Override
    public int recieveMessage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recieveMessage'");
    }
    
}
