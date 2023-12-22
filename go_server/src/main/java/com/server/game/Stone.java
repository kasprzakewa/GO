package com.server.game;

import java.util.ArrayList;

public class Stone 
{
    private StoneColor color;
    private ArrayList<Stone> neighbours;
    private Point position;

    public Stone(StoneColor color, Point position, Stone neighbour1, Stone neighbour2,
                 Stone neighbour3, Stone neighbour4)
    {
        this.color = color;
        this.neighbours = new ArrayList<>();
        neighbours.add(neighbour1);
        neighbours.add(neighbour2);
        neighbours.add(neighbour3);
        neighbours.add(neighbour4);
        this.position = position;
    }

    public Stone(StoneColor color, Point position){
        
        this.color = color;
        this.position = position;
    }

    public void addNeighbour(Stone neighbour){
        
        this.neighbours.add(neighbour);
    }

    public void setColor(StoneColor color) 
    {
        this.color = color;
    }

    public void setPosition(Point position) 
    {
        this.position = position;
    }

    public StoneColor getColor() 
    {
        return this.color;
    }

    public Point getPosition() 
    {
        return this.position;
    }

    public ArrayList<Stone> getNeighbours()
    {
        return this.neighbours;
    }

    //sprawdzanie czy mamy pustego sąsiada
    public boolean emptyNeighbour()
    {

        for (Stone neighbour : this.neighbours)
        {
            if (neighbour.getColor() == StoneColor.EMPTY)
                return true;
        }

        return false;
    }
    
}

