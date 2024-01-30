package com.server.game.bot;

import java.util.ArrayList;
import java.util.List;

import com.server.game.Board;
import com.server.game.Opponent;
import com.server.game.Point;
import com.server.game.StoneColor;

public class Bot implements Opponent{

    private StoneColor color;
    private Board board;
    private int[] move;

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
    public void sendMessage(String message) {
        
        return;
    }

    @Override
    public String receiveMessage() {

        move = planMove();
        return Integer.toString(move[0])+ " "+ Integer.toString(move[1]);
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

        List<int[]> triedMoves;

        do{
            triedMoves = new ArrayList<>();
            if(move == null){

                int moveX = (int)(Math.random() * board.getSize());
                int moveY = (int)(Math.random() * board.getSize());
                move = new int[]{moveX, moveY};
            }
            else{
                int direction = (int)Math.floor(Math.random() * 4);
                int diagonal =(int)Math.random();
                if(diagonal == 1){
                    if (direction == 0){
                    move[0]=move[0]-1;
                    move[1]=move[1]-1;
                    }
                    else if(direction == 1){
                        move[0]=move[0]-1;
                        move[1]=move[1]+1;
                    }
                    else if(direction == 2){
                        move[0]=move[0]+1;
                        move[1]=move[1]+1;
                    }
                    else if(direction == 3){
                        move[0]=move[0]+1;
                        move[1]=move[1]-1;
                    }
                    else{
                        move[0] = (int)(Math.random() * board.getSize());
                        move[1] = (int)(Math.random() * board.getSize());
                    }
                }
                else{

                    if (direction == 0){
                        move[1]=move[1]-1;
                    }
                    else if(direction == 1){
                        move[1]=move[1]+1;
                    }
                    else if(direction == 2){
                        move[0]=move[0]+1;
                    }
                    else if(direction == 3){
                        move[0]=move[0]-1;
                    }
                    else{
                        move[0] = (int)(Math.random() * board.getSize());
                        move[1] = (int)(Math.random() * board.getSize());
                    }
                }
                
            }
            boolean contains = false;
            for(int[] oldMove: triedMoves){
                if(oldMove[0] == move[0] && oldMove[1]==move[1]){
                    contains = true;
                }
            }
            if(!contains){
                triedMoves.add(move);
            }
        }while(move[0]<0 || move[1]<0 || move[0] >= 19 || move[1] >=19 || !board.canPlaceStone(new Point(move[0], move[1]), color));
        if(triedMoves.size()==board.getSize()){
            return new int[]{-2,-2};
        }
        System.out.println("bot move x: " + move[0] + ", y: " + move[1]);
        return move;
    }

}
