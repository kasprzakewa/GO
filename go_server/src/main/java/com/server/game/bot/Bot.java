package com.server.game.bot;

import com.server.game.Board;
import com.server.game.Opponent;
import com.server.game.Point;
import com.server.game.StoneColor;

public class Bot implements Opponent{

    private StoneColor color;
    private Board board;

    public Bot(StoneColor color, Board board){
        this.color = color;
        this.board = board;
    }
    

    public StoneColor getColor() {
        return color;
    }

    public void setColor(StoneColor color) {
        this.color = color;
    }

    @Override
    public boolean makeMove(Point position) {

        return this.board.placeStone(position, color);
    }

    @Override
    public void sendMessage(int message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendMessage'");
    }

    @Override
    public int receiveMessage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'recieveMessage'");
    }


    @Override
    public void setBoard(Board board) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setBoard'");
    }


    @Override
    public int getPoints() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPoints'");
    }


    @Override
    public int getTerritory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTerritory'");
    }

    
    
}
