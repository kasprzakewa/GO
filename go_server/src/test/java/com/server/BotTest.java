package com.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.server.game.Board;
import com.server.game.Point;
import com.server.game.StoneColor;
import com.server.game.bot.Bot;

public class BotTest 
{

    @Test
    public void testMakeMove() 
    {
        ArrayList<String> history = new ArrayList<>();
        Board board = new Board(5, history);
        Bot bot = new Bot(StoneColor.BLACK, board);
        Point position = new Point(0, 0);
        assertTrue(bot.makeMove(position));
    }

    @Test
    public void testReceiveMessage() 
    {
        ArrayList<String> history = new ArrayList<>();
        Board board = new Board(5, history);
        Bot bot = new Bot(StoneColor.BLACK, board);
        String message = bot.receiveMessage();
        int x = Integer.parseInt(message.split(" ")[0]);
        int y = Integer.parseInt(message.split(" ")[1]);
        assertTrue(x >= 0 && x < board.getSize());
        assertTrue(y >= 0 && y < board.getSize());
    }

    @Test
    public void testGetPoints() 
    {
        ArrayList<String> history = new ArrayList<>();
        Board board = new Board(5, history);
        Bot bot = new Bot(StoneColor.BLACK, board);
        int points = bot.getPoints();
        assertEquals(0, points);
    }

    @Test
    public void testGetTerritory() 
    {
        ArrayList<String> history = new ArrayList<>();
        Board board = new Board(5, history);
        Bot bot = new Bot(StoneColor.BLACK, board);
        int territory = bot.getTerritory();
        assertEquals(0, territory);
        bot.makeMove(new Point(0, 0));
        territory = bot.getTerritory();
        assertEquals(24, territory);
    }
}
