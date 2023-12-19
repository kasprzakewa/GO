package com.server.game;

public class Board 
{
    private int size;
    private Stone[][] board;

    public Board(int size)
    {
        this.size = size;
        this.board = new Stone[size][size];
        initializeBoard();
    }

    void initializeBoard()
    {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                this.board[i][j] = new Stone(StoneColor.EMPTY, new Point(i, j));
            }
        }
    }

    boolean canPlaceStone(Point position)
    {
        return this.board[position.getX()][position.getY()].getColor() == StoneColor.EMPTY;
    }

    void placeStone(Point position, StoneColor color)
    {
        if (canPlaceStone(position))
        {
            this.board[position.getX()][position.getY()].setColor(color);
        }
    }

    void removeStone(Point position)
    {
        this.board[position.getX()][position.getY()].setColor(StoneColor.EMPTY);
    } 

    void displayBoard() 
    {
        for (int i = 0; i < this.size; i++) 
        {
            for (int j = 0; j < this.size; j++) 
            {
                switch (this.board[i][j].getColor()) 
                {
                    case WHITE:
                        printColoredText("W ", ConsoleColor.WHITE);
                        break;
                    case BLACK:
                        printColoredText("B ", ConsoleColor.BLUE);
                        break;
                    case EMPTY:
                        printColoredText("E ", ConsoleColor.GREEN);
                        break;
                    default:
                        break;
                }
            }
            System.out.println();
        }
    }

    enum ConsoleColor 
    {
        RESET("\u001B[0m"),
        WHITE("\u001B[37m"),
        BLUE("\u001B[34m"),
        GREEN("\u001B[32m");
    
        private final String code;
    
        ConsoleColor(String code) 
        {
            this.code = code;
        }
    
        @Override
        public String toString() 
        {
            return code;
        }
    }
    
    void printColoredText(String text, ConsoleColor color) 
    {
        System.out.print(color + text + ConsoleColor.RESET);
    }

    public int getSize() 
    {
        return size;
    }
    
    public Stone[][] getStones() 
    {
        return this.board;
    }

    public StoneColor getColor(int x, int y)
    {
        return this.board[x][y].getColor();
    }
}
