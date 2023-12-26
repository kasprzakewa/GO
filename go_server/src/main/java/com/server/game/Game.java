package com.server.game;

import java.io.IOException;

public class Game 
{
    private Board board;
    private Opponent whitePlayer;
    private Opponent blackPlayer;

    final static int PLAYER1_WON = 1;
    final static int PLAYER2_WON = 2;
    final static int DRAW = 3;
    final static int CONTINUE = 4;

    

    public Game(int size, Opponent player1, Opponent player2) throws IOException
    {
        board = new Board(size);
        whitePlayer = player2;
        blackPlayer = player1;
    } 

    public void play() 
    {
        System.out.println("starting game");
        boolean placed;

        //Scanner scanner = new Scanner(System.in);
        try {

            blackPlayer.sendMessage(1);
            boolean play = true;

            while (play) 
            {   
                int blackX = -1;
                int blackY = -1;
                int whiteX = -1;
                int whiteY = -1;
                System.out.println("black to move");
                placed = false;
                while(!placed)
                {
                    //System.out.println("Black's turn (enter coordinates separated by space): ");
                    //blackPlayer.sendMessage("Black's turn (enter coordinates separated by space): ");
                    //int blackX = scanner.nextInt();
                    //int blackY = scanner.nextInt();
                    //String message = (String)blackPlayer.recieveMessage();
                    //String[] coordinates = message.split(" ");
                    //int blackX = Integer.parseInt(coordinates[0]);
                    //int blackY = Integer.parseInt(coordinates[1]);
                    blackX = blackPlayer.recieveMessage();
                    blackY = blackPlayer.recieveMessage();
                    blackX -= 1;
                    blackY -= 1;
                    System.out.println("black move" + blackX + ", " + blackY);
                    if (blackPlayer.makeMove(board, new Point(blackX, blackY))){
                        placed = true;
                        blackPlayer.sendMessage(1);
                    }
                    else{
                        blackPlayer.sendMessage(0);
                    }
                }
                System.out.println("sending end of turn signal");
                whitePlayer.sendMessage(CONTINUE);
                whitePlayer.sendMessage(blackX);
                whitePlayer.sendMessage(blackY);


                placed = false;
                System.out.println("white to move");
                while(!placed)
                {
                    //System.out.println("White's turn (enter coordinates separated by space): ");
                    //whitePlayer.sendMessage("Black's turn (enter coordinates separated by space): ");
                    //int whiteX = scanner.nextInt();
                    //int whiteY = scanner.nextInt();
                    //String message = (String)whitePlayer.recieveMessage();
                    //String[] coordinates = message.split(" ");
                    //int whiteX = Integer.parseInt(coordinates[0]);
                    //int whiteY = Integer.parseInt(coordinates[1]);
                    whiteX = whitePlayer.recieveMessage();
                    whiteY = whitePlayer.recieveMessage();
                    whiteX -= 1;
                    whiteY -= 1;
                    System.out.println("white move" + whiteX + ", " + whiteY);
                    if (whitePlayer.makeMove(board, new Point(whiteX, whiteY))){
                        placed = true;
                        whitePlayer.sendMessage(1);
                    }
                    else{
                        whitePlayer.sendMessage(0);
                    }     
                }
                System.out.println("sending end of turn information");
                blackPlayer.sendMessage(CONTINUE);
                blackPlayer.sendMessage(whiteX);
                blackPlayer.sendMessage(whiteY);

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
}
