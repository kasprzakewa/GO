package com.server.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Game 
{
    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;

    final static int PLAYER1_WON = 1;
    final static int PLAYER2_WON = 2;
    final static int DRAW = 3;
    final static int CONTINUE = 4;

    private DataInputStream blackPlayerInput;
    private DataInputStream whitePlayerInput;
    private DataOutputStream blackPlayerOutput;
    private DataOutputStream whitePlayerOutput;

    

    public Game(int size, Socket player1, Socket player2) throws IOException
    {
        board = new Board(size);
        whitePlayer = new Player(StoneColor.WHITE);
        blackPlayer = new Player(StoneColor.BLACK);
        blackPlayerInput = new DataInputStream(player1.getInputStream());
        blackPlayerOutput = new DataOutputStream(player1.getOutputStream());
        whitePlayerInput = new DataInputStream(player2.getInputStream());
        whitePlayerOutput = new DataOutputStream(player2.getOutputStream());
    } 

    public void play() 
    {
        System.out.println("starting game");
        boolean placed;

        Scanner scanner = new Scanner(System.in);
        try {

            blackPlayerOutput.writeInt(1);
            boolean play = true;

            while (play) 
            {   
                int blackX;
                int blackY;
                int whiteX;
                int whiteY;
                System.out.println("black to move");
                placed = false;
                //while(!placed)
                //{
                    //System.out.println("Black's turn (enter coordinates separated by space): ");
                    //blackPlayer.sendMessage("Black's turn (enter coordinates separated by space): ");
                    //int blackX = scanner.nextInt();
                    //int blackY = scanner.nextInt();
                    //String message = (String)blackPlayer.recieveMessage();
                    //String[] coordinates = message.split(" ");
                    //int blackX = Integer.parseInt(coordinates[0]);
                    //int blackY = Integer.parseInt(coordinates[1]);
                    blackX = blackPlayerInput.readInt();
                    blackY = blackPlayerInput.readInt();
                    System.out.println("black move" + blackX + ", " + blackY);
                    if (blackPlayer.makeMove(board, new Point(blackX-1, blackY-1))){
                        placed = true;
                        //blackPlayer.sendMessage(1);
                    }
                    else{
                        //blackPlayer.sendMessage(0);
                    }
                //}
                System.out.println("sending end of turn signal");
                whitePlayerOutput.writeInt(CONTINUE);
                whitePlayerOutput.writeInt(blackX);
                whitePlayerOutput.writeInt(blackY);


                placed = false;
                System.out.println("white to move");
                //while(!placed)
                //{
                    //System.out.println("White's turn (enter coordinates separated by space): ");
                    //whitePlayer.sendMessage("Black's turn (enter coordinates separated by space): ");
                    //int whiteX = scanner.nextInt();
                    //int whiteY = scanner.nextInt();
                    //String message = (String)whitePlayer.recieveMessage();
                    //String[] coordinates = message.split(" ");
                    //int whiteX = Integer.parseInt(coordinates[0]);
                    //int whiteY = Integer.parseInt(coordinates[1]);
                    whiteX = whitePlayerInput.readInt();
                    whiteY = whitePlayerInput.readInt();
                    System.out.println("white move" + whiteX + ", " + whiteY);
                    if (whitePlayer.makeMove(board, new Point(whiteX-1, whiteY-1))){
                        placed = true;
                        //whitePlayer.sendMessage(1);
                    }
                    else{
                        //whitePlayer.sendMessage(0);
                    }     
                //}
                System.out.println("sending end of turn information");
                blackPlayerOutput.writeInt(CONTINUE);
                blackPlayerOutput.writeInt(whiteX);
                blackPlayerOutput.writeInt(whiteY);

            }
        } catch (NumberFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        scanner.close();
    }

    public Board getBoard() 
    {
        return board;
    }

    public void setBoard(Board board) 
    {
        this.board = board;
    }

    public Player getWhitePlayer() 
    {
        return whitePlayer;
    }

    public void setWhitePlayer(Player whitePlayer) 
    {
        this.whitePlayer = whitePlayer;
    }

    public Player getBlackPlayer() 
    {
        return blackPlayer;
    }

    public void setBlackPlayer(Player blackPlayer) 
    {
        this.blackPlayer = blackPlayer;
    }
}
