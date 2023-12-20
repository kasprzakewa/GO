package com.server;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.server.game.Point;

public class PointTest 
{
    private Point point;

    @Before
    public void setUp() 
    {
        point = new Point(3, 5);
    }

    @Test
    public void testGetX() 
    {
        assertEquals(3, point.getX());
    }

    @Test
    public void testGetY() 
    {
        assertEquals(5, point.getY());
    }
    
}
