package com.server.game.bot;

import com.server.game.Board;
import com.server.game.Opponent;
import com.server.game.Point;
import com.server.game.StoneColor;

public class Bot implements Opponent{

    private StoneColor color;
    private Board board;
    private int[] move = new int[2];
    private int[] lastMove;
    private boolean isXSent = false;

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
        
        return;
    }

    @Override
    public int receiveMessage() {

        if(!isXSent){
            move = planMove();
        }
        isXSent = !isXSent;
        return isXSent ? move[0] : move[1];
    }


    @Override
    public void setBoard(Board board) {
        
        this.board = board;
    }


    @Override
    public int getPoints() {
        
        return this.board.getPoints().get(color);
    }


    @Override
    public int getTerritory() {
        
        return this.board.getTerritory(color);
    }

    private int[] planMove(){
        if(move == null){

            int moveX = (int)(Math.random() * board.getSize());
            int moveY = (int)(Math.random() * board.getSize());
            move = new int[]{moveX, moveY};
        }
        else{
            lastMove = move;
            
        }
        return move;
    }

}
