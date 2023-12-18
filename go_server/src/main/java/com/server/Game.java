package com.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class Game 
{
    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;

    public Game(int size)
    {
        board = new Board(size);
        whitePlayer = new Player(StoneColor.WHITE);
        blackPlayer = new Player(StoneColor.BLACK);
    } 

    void play() 
    {
        Scanner scanner = new Scanner(System.in);

        while (true) 
        {
            System.out.println("Current Board:");
            board.displayBoard();

            System.out.println("Black's turn (enter coordinates separated by space): ");
            int blackX = scanner.nextInt();
            int blackY = scanner.nextInt();
            blackPlayer.makeMove(board, new Point(blackX, blackY));

            System.out.println("Current Board:");
            board.displayBoard();

            System.out.println("White's turn (enter coordinates separated by space): ");
            int whiteX = scanner.nextInt();
            int whiteY = scanner.nextInt();
            whitePlayer.makeMove(board, new Point(whiteX, whiteY));
        }
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
