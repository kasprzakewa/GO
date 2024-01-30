package com.server.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.server.game.database.entity.MovesEntity;

import jakarta.persistence.EntityManager;

public class Board 
{
    private int size;
    private Stone[][] stones;
    private ArrayList<String> history;
    private HashMap<StoneColor, Integer> points;
    private MovesEntity me;
    private EntityManager em;
    private int gameID;

    public Board(int size, ArrayList<String> history, int gameID, EntityManager em)
    {
        this.size = size;
        this.stones = new Stone[size][size];
        this.history = history;
        this.points = new HashMap<StoneColor, Integer>();
        this.points.put(StoneColor.WHITE, 0);
        this.points.put(StoneColor.BLACK, 0);
        this.gameID = gameID;
        this.em = em;
        this.me = new MovesEntity();


        //inicjalizacja planszy pustymi kamieniami
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                this.stones[i][j] = new Stone(StoneColor.EMPTY, new Point(i, j), this);
            }
        }
    }

    public Board(int size, ArrayList<String> history)
    {
        this.size = size;
        this.stones = new Stone[size][size];
        this.history = history;
        this.points = new HashMap<StoneColor, Integer>();
        this.points.put(StoneColor.WHITE, 0);
        this.points.put(StoneColor.BLACK, 0);

        //inicjalizacja planszy pustymi kamieniami
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                this.stones[i][j] = new Stone(StoneColor.EMPTY, new Point(i, j), this);
            }
        }

    }

    //stawianie kamieni i usuwanie martwych
    public boolean placeStone(Point position, StoneColor color)
    {
        if (canPlaceStone(position, color))
        {
            setStone(position, color);

            for (int i = 0; i < size; i++)
            {
                for (int j = 0; j < size; j++)
                {
                    this.stones[i][j].remove(color);
                }
            }

            return true;
        }

        return false;
    }

    public boolean canPlaceStone(Point position, StoneColor color)
    {
        if (getStone(position, 0, 0).getColor() == StoneColor.EMPTY)
        {
            if (! suicidalMove(position, color))
            {
                if (! isKo(position, color))
                {
                    return true;
                }
            }
        }

        return false;     
    }

    public boolean suicidalMove(Point position, StoneColor color)
    {
        Board temporary = new Board(size, history);

        temporary.setStones(deepCopy(temporary));
        temporary.getStone(position, 0, 0).setColor(color);

        ArrayList<Stone> neighbors = getStone(position, 0, 0).getNeighbors();
        String strNeighbors = neighborsToString(neighbors);

        if (temporary.getStone(position, 0, 0).isCaptured())
        {
            for (int i = 0; i < size; i++)
            {
                for (int j = 0; j < size; j++)
                {
                    temporary.stones[i][j].remove(color);
                }
            }

            String curNeighbors = neighborsToString(temporary.getStone(position, 0, 0).getNeighbors());

            if (strNeighbors.equals(curNeighbors))
            {
                return true;
            }
            else
            {
                return false;
            }

        }

        return false;
    }

    public boolean isKo(Point position, StoneColor color) 
    {
        if (history.size() > 1) 
        {
            Board temporary = new Board(size, history);

            temporary.setStones(deepCopy(temporary));
            temporary.getStone(position, 0, 0).setColor(color);
    
            if (temporary.getStone(position, 0, 0).isCaptured()) 
            {
                for (int i = 0; i < size; i++) 
                {
                    for (int j = 0; j < size; j++) 
                    {
                        temporary.stones[i][j].remove(color);
                    }
                }
            }
    
            String current = temporary.toString();
            String previous = history.get(history.size() - 2);
    
            if (current.equals(previous)) 
            {
                return true;
            }
        }
        return false;
    }

    public void save(boolean player) 
    {
        history.add(toString());
        me.save(toString(), gameID, em, player);
    }

    public Set<ArrayList<Stone>> getGroups()
    {
        Set<ArrayList<Stone>> groups = new HashSet<ArrayList<Stone>>();
        boolean[][] visited = new boolean[size][size];

        for (int i = 0; i < size; i++)
        {
            Arrays.fill(visited[i], false);
        }

        for(int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                ArrayList<Stone> group = stones[i][j].getGroup();

                if (!visited[i][j])
                {
                    groups.add(group);

                    for (Stone stone : group)
                    {
                        visited[stone.getPosition().getX()][stone.getPosition().getY()] = true;
                    }
                }
            }
        }

        return groups;
    }

    public int getTerritory(StoneColor color)
    {
        int territory = 0;
        Set<ArrayList<Stone>> groups = getGroups();

        for (ArrayList<Stone> group : groups)
        {
            if (group.get(0).getColor() == StoneColor.EMPTY)
            {
                if(group.get(0).isSurroundedBySpecificColor(color) != StoneColor.EMPTY)
                territory += group.size();
            }
        }

        return territory;
    }

    public Stone getStone(Point position, int a, int b)
    {
        return stones[position.getX() + a][position.getY() + b];
    }

    public int getSize()
    {
        return this.size;
    }

    public void setStone(Point position, StoneColor color)
    {
        this.stones[position.getX()][position.getY()].setColor(color);
    }

    public void setStones(Stone[][] stones)
    {
        this.stones = stones;
    }

    public Stone[][] getStones()
    {
        return this.stones;
    }

    public Stone[][] deepCopy(Board temporary)
    {
        Stone[][] copy = new Stone[size][size];
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                copy[i][j] = new Stone(this.stones[i][j].getColor(), new Point(i, j), temporary);
            }
        }
        return copy;
    }

    public Stone[][] toBoard(String stringBoard)
    {
        Stone[][] toBoard = new Stone[size][size];

        String[] splitted = stringBoard.split("\\.");
        System.out.println(Arrays.toString(splitted));
        System.out.println(toColor("B"));
        int counter = 0;
        double length = Math.sqrt(splitted.length);

        for (int i = 0; i < length; i++)
        {
            for (int j = 0; j < length; j++)
            {
                toBoard[i][j] = new Stone(toColor(splitted[counter]), new Point(i, j), this);
            }
        }

        return toBoard;
    }

    @Override
    public String toString()
    {
        StringBuilder board = new StringBuilder();
    
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                board.append(convertColor(this.stones[i][j].getColor()));
            }
        }

        board.deleteCharAt(board.length() - 1);
    
        return board.toString();
    } 

    public String neighborsToString(ArrayList<Stone> neighbors)
    {
        StringBuilder neighborsString = new StringBuilder();

        for (int i = 0; i < neighbors.size(); i++)
        {
            neighborsString.append(neighbors.get(i).getColor());
        }

        return neighborsString.toString();
    }

    public String convertColor(StoneColor color)
    {
        String convColor = null;

        switch (color)
        {
            case WHITE: 
                convColor = "W.";
                break;
            case BLACK:
                convColor = "B.";
                break;
            case EMPTY:
                convColor = "E.";
                break;
            default:
                break;
        }

        return convColor;
    }

    public StoneColor toColor(String str)
    {
        StoneColor color = null;

        switch (str)
        {
            case "W":
                color = StoneColor.WHITE;
                break;
            case "B":
                color = StoneColor.BLACK;
                break;
            case "E":
                color = StoneColor.EMPTY;
                break;
            default:
                break;
        }

        return color;
    }

    public Board getBoard()
    {
        return this;
    }

    public HashMap<StoneColor, Integer> getPoints()
    {
        return this.points;
    }

    public ArrayList<String> getHistory() 
    {
        return history;
    }

}
