package com.server.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Stone
{
    private ArrayList<Stone> group;
    private int breaths;
    private StoneColor color;
    private Point position;
    private Board board;

    public Stone(StoneColor color, Point position, Board board)
    {
        this.color = color;
        this.breaths = 0;
        this.position = position;
        this.board = board;
        this.group = new ArrayList<>();

        group.add(this);
    }

    public void remove(StoneColor color)
    {
        if (this.color != color && this.color != StoneColor.EMPTY)
        {
            if (isCaptured())
            {
                group();
    
                for (Stone stone : group)
                {
                    stone.setColor(StoneColor.EMPTY);
                    this.board.getPoints().put(color, this.board.getPoints().get(color) + 1);
                }
            }
        }
    }

    public int singleBreaths()
    {
        int singleBreaths = 0;

        for (Stone stone : getNeighbors())
        {
            if (stone.getColor() == StoneColor.EMPTY)
            {
                singleBreaths ++;
            }
        }

        return singleBreaths;
    }

    //ustawiamy wartość oddechów, jeżeli będzie ona 0, to usuwamy grupę kamieni
    public void breaths()
    {
        breaths = 0;
        group();

        for (Stone stone : group)
        {
            breaths += stone.singleBreaths();
        }
    }

    public boolean isCaptured()
    {
        breaths();

        if (breaths <= 0)
            return true;
        else
            return false;
    }

    public void group()
    {
        ArrayList<Stone> tempGroup = new ArrayList<>();
        Set<Stone> visited = new HashSet<>();
        Queue<Stone> queue = new LinkedList<>();

        queue.add(this);

        while (!queue.isEmpty())
        {
            Stone current = queue.poll();

            if (!visited.contains(current) && current.getColor() == this.color)
            {
                tempGroup.add(current);
                visited.add(current);
                queue.addAll(current.getNeighbors());
            }
        }

        group = tempGroup;
    }

    public StoneColor isSurroundedBySpecificColor(StoneColor color)
    {
        ArrayList<StoneColor> colors = new ArrayList<>();
        StoneColor neighborColor = StoneColor.EMPTY;

        for (Stone stone : getGroup())
        {
            ArrayList<Stone> neighbors = stone.getNeighbors();

            for (Stone neighbor : neighbors)
            {
                neighborColor = neighbor.getColor();
                if (neighborColor != StoneColor.EMPTY && !colors.contains(neighborColor))
                    colors.add(neighborColor);
            }
        }

       if (colors.size() == 1 && colors.get(0).equals(color))
            return color;
        else return StoneColor.EMPTY;
    }
    

    public ArrayList<Stone> getNeighbors()
    {   
        ArrayList<Stone> neighbors = new ArrayList<Stone>();

        if (position.getX() > 0)
            neighbors.add(this.board.getStone(position, -1, 0));

        if (position.getX() < this.board.getSize()-1)
            neighbors.add(this.board.getStone(position, 1, 0));

        if (position.getY() > 0)
            neighbors.add(this.board.getStone(position, 0, -1));

        if (position.getY() < this.board.getSize()-1)
            neighbors.add(this.board.getStone(position, 0, 1));

        return neighbors;
    }

    public StoneColor getColor()
    {
        return this.color;
    }

    public void setColor(StoneColor color)
    {
        this.color = color;
    }

    public Point getPosition()
    {
        return this.position;
    }
    
    public ArrayList<Stone> getGroup() 
    {
        group();
        return group;
    }
}
