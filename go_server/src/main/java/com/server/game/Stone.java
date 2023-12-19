package com.server.game;

public class Stone 
{
    private StoneColor color;
    private Point position;

    public Stone(StoneColor color, Point position)
    {
        this.color = color;
        this.position = position;
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
    
}

