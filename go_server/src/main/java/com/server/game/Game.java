package com.server.game;

import java.util.Scanner;

public class Game 
{
    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private boolean placed;

    public Game(int size)
    {
        board = new Board(size);
        whitePlayer = new Player(StoneColor.WHITE);
        blackPlayer = new Player(StoneColor.BLACK);
    } 

    public void play() 
    {
        Scanner scanner = new Scanner(System.in);

        boolean play = true;

        while (play) 
        {
            System.out.println("Current Board:");
            board.displayBoard();

            placed = false;
            while(!placed)
            {
                System.out.println("Black's turn (enter coordinates separated by space): ");
                int blackX = scanner.nextInt();
                int blackY = scanner.nextInt();
                if (blackPlayer.makeMove(board, new Point(blackX-1, blackY-1)))
                    placed = true;
            }
            

            System.out.println("Current Board:");
            board.displayBoard();

            placed = false;
            while(!placed)
            {
                System.out.println("White's turn (enter coordinates separated by space): ");
                int whiteX = scanner.nextInt();
                int whiteY = scanner.nextInt();
                if (whitePlayer.makeMove(board, new Point(whiteX-1, whiteY-1)))
                    placed = true;
            }
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
