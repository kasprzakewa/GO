package com.server.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Board 
{
    private int size;
    private Stone[][] board;
    private ArrayList<Board> history;
    private ArrayList<Stone> ko;

    public Board(int size)
    {
        this.size = size;
        this.board = new Stone[size][size];
        this.history = new ArrayList<>();
        this.ko = new ArrayList<>();
        initializeBoard();
    }

    public void initializeBoard()
    {
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                this.board[i][j] = new Stone(StoneColor.EMPTY, new Point(i, j));
            }
        }
        setNeighbours();
    }

    private void setNeighbours(){

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(j>=1){
                    this.board[i][j].addNeighbour(this.board[i][j-1]);
                }
                if(j<size-1){
                    this.board[i][j].addNeighbour(this.board[i][j+1]);
                }
                if(i>=1){
                    this.board[i][j].addNeighbour(this.board[i-1][j]);
                }
                if(i<size-1){
                    this.board[i][j].addNeighbour(this.board[i+1][j]);
                }
            }
        }
    }

    public boolean canPlaceStone(Point position)
    {
        return (this.board[position.getX()][position.getY()].getColor() == StoneColor.EMPTY);
    }

    public void placeStone(Point position, StoneColor color)
    {
        if (canPlaceStone(position))                                                                                                    
        {
            this.board[position.getX()][position.getY()].setColor(color);
        }
    }

    public void removeStone(Stone stone) 
    {
        int x = stone.getPosition().getX();
        int y = stone.getPosition().getY();
        this.board[x][y].setColor(StoneColor.EMPTY);
    }

    // tworzenie grupy kamieni
    public ArrayList<Stone> getStoneGroup(Stone stone)
    {
        ArrayList<Stone> group = new ArrayList<>();
        Set<Stone> visited = new HashSet<>();
        Queue<Stone> queue = new LinkedList<>();

        queue.add(stone);

        while (!queue.isEmpty())
        {
            Stone current = queue.poll();

            if (!visited.contains(current) && current.getColor() == stone.getColor())
            {
                group.add(current);
                visited.add(current);
                queue.addAll(stone.getNeighbours());
            }
        }

        return group;
    }

    // sprawdzanie czy grupa, do której należy kamień jest otoczona
    public boolean isGroupCaptured(ArrayList<Stone> group)
    {
        for (Stone stone : group)
        {
            if (stone.emptyNeighbour())
                return false;

        }

        return true;
    }

    public void removeGroup(Stone stone, StoneColor color)
    {
        if (stone.getColor() != color)
        {
            ArrayList<Stone> group = getStoneGroup(stone);
    
            if (isGroupCaptured(group)) 
            {
                for (Stone elem : group) 
                {
                    removeStone(elem);
                }
            }
        }
    }

    public Stone getStone(int x, int y)
    {
        return board[x][y];
    }

    public void save()
    {
        Board temp = new Board(size);
        temp.setStones(board);

        history.add(temp);
    }
    
    public void displayBoard() 
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

    public void setStones(Stone[][] board)
    {
        this.board = board;
    }

    public void setStone(int x, int y, StoneColor color)
    {
        this.board[x][y] = new Stone(color, new Point(x, y));
    }

    public StoneColor getColor(int x, int y)
    {
        return this.board[x][y].getColor();
    }

    public ArrayList<Stone> getKo()
    {
        return this.ko;
    }
}
