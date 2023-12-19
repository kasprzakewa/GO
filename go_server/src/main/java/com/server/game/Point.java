package com.server.game;

public class Point 
{
    private int x;
    private int y;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }  
    
    int getX()
    {
        return this.x;
    }

    int getY()
    {
        return this.y;
    }
}
