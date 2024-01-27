package com.server.game;

import java.io.IOException;
import java.util.ArrayList;

public class GameServer 
{
    private Board board;
    private Opponent whitePlayer;
    private Opponent blackPlayer;
    private ArrayList<String> history;

    final static int PLAYER1_WON = 1;
    final static int PLAYER2_WON = 2;
    final static int DRAW = 3;
    final static int CONTINUE = 4;

    final static int BLACK = 1;
    final static int WHITE = 2;
    final static int TRANSPARENT = 0;

    final static int CORRECT_MOVE = 0;
    final static int INCORRECT_MOVE = 1;

    

    public GameServer(int size, Opponent player1, Opponent player2) throws IOException
    {
        history = new ArrayList<>();
        
        board = new Board(size, history);
        whitePlayer = player2;
        blackPlayer = player1;

        whitePlayer.setBoard(board);
        blackPlayer.setBoard(board);
    } 

    public void play() 
    {

        //Scanner scanner = new Scanner(System.in);
        try {

            blackPlayer.sendMessage(1);
            boolean play = true;

            while (play) 
            {   
                int blackX = 0;
                int blackY = 0;
                int whiteX = 0;
                int whiteY = 0;
                
                

                boolean placed;

                do
                {
                    System.out.println("black to move");
                    blackX = blackPlayer.recieveMessage();
                    
                    blackY = blackPlayer.recieveMessage();

                    System.out.println("black move" + blackX + ", " + blackY);

                    if (blackPlayer.makeMove(new Point(blackX, blackY))){

                        placed = true;

                        System.out.println("black move correct");

                        blackPlayer.sendMessage(CORRECT_MOVE);

                        System.out.println("update sent");
                    }
                    else{

                        System.out.println("black move incorrect");

                        blackPlayer.sendMessage(INCORRECT_MOVE);

                        System.out.println("update sent");

                        placed = false;
                    }
                }while(!placed);
                board.save();

                sendUpdates();

                placed = false;
                

                do
                {
                    System.out.println("white to move");
                    whiteX = whitePlayer.recieveMessage();
                    
                    whiteY = whitePlayer.recieveMessage();

                    System.out.println("white move" + whiteX + ", " + whiteY);

                    if (whitePlayer.makeMove(new Point(whiteX, whiteY))){

                        System.out.println("white move correct");
                        placed = true;

                        whitePlayer.sendMessage(CORRECT_MOVE);

                    }
                    else{
                        System.out.println("white move incorrect");

                        whitePlayer.sendMessage(INCORRECT_MOVE);

                        System.out.println("update sent");

                        placed = false;
                    }
                    
                }while(!placed);
                sendUpdates();
                System.out.println("end of turn");

                board.save();

            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //scanner.close();
    }

    public Board getBoard() 
    {
        return board;
    }

    public void setBoard(Board board) 
    {
        this.board = board;
    }

    public Opponent getWhitePlayer() 
    {
        return whitePlayer;
    }

    public void setWhitePlayer(Player whitePlayer) 
    {
        this.whitePlayer = whitePlayer;
    }

    public Opponent getBlackPlayer() 
    {
        return blackPlayer;
    }

    public void setBlackPlayer(Player blackPlayer) 
    {
        this.blackPlayer = blackPlayer;
    }

    public void sendUpdates(){

        for(int i = 0; i < board.getSize(); i++)
        {
            for(int j = 0; j < board.getSize(); j++)
            {
                StoneColor color = board.getStone(new Point(i, j), 0, 0).getColor();
                if(color == StoneColor.BLACK)
                {   
                    blackPlayer.sendMessage(i);
                    blackPlayer.sendMessage(j);
                    blackPlayer.sendMessage(BLACK);
                    whitePlayer.sendMessage(i);
                    whitePlayer.sendMessage(j);
                    whitePlayer.sendMessage(BLACK);
                    System.out.println("black sent, x: " + i + ", y: " + j);
                }
                else if(color == StoneColor.WHITE)
                {
                    blackPlayer.sendMessage(i);
                    blackPlayer.sendMessage(j);
                    blackPlayer.sendMessage(WHITE);
                    whitePlayer.sendMessage(i);
                    whitePlayer.sendMessage(j);
                    whitePlayer.sendMessage(WHITE);
                    System.out.println("white sent, x: " + i + ", y: " + j);
                }
                else
                {
                    blackPlayer.sendMessage(i);
                    blackPlayer.sendMessage(j);
                    blackPlayer.sendMessage(TRANSPARENT);
                    whitePlayer.sendMessage(i);
                    whitePlayer.sendMessage(j);
                    whitePlayer.sendMessage(TRANSPARENT);
                }
            }
            
        }
        blackPlayer.sendMessage(-100);
        whitePlayer.sendMessage(-100);
    }
}
