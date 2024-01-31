package com.client;

import org.junit.Test;

import com.client.gui.GoBoard;
import com.client.gui.GoField;

import javafx.scene.paint.Color;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestGoBoard {

    @Test
    public void testGetSize() {
        GoBoard board = new GoBoard(5);
        assertEquals(5, board.getSize());
    }

    @Test
    public void testSetSize() {
        GoBoard board = new GoBoard(5);
        board.setSize(10);
        assertEquals(10, board.getSize());
    }

    @Test
    public void testGetFieldBoard() {
        GoBoard board = new GoBoard(5);
        GoField[][] fields = board.getFieldBoard();
        assertEquals(5, fields.length);
        assertEquals(5, fields[0].length);
    }

    @Test
    public void testGetFields() {
        GoBoard board = new GoBoard(5);
        List<GoField> fields = board.getFields();
        assertEquals(25, fields.size());
    }

    @Test
    public void testDrawBoard() {
        GoBoard board = new GoBoard(5);
        board.drawBoard("E.E.B.W.E.E.B.W.E.E.B.W.E.E.B.W.E.E.B.W.E");
        List<GoField> fields = board.getFields();
        assertEquals(Color.TRANSPARENT, fields.get(0).getColor());
        assertEquals(Color.TRANSPARENT, fields.get(1).getColor());
        assertEquals(Color.BLACK, fields.get(2).getColor());
        assertEquals(Color.WHITE, fields.get(3).getColor());
        assertEquals(Color.TRANSPARENT, fields.get(4).getColor());
    }
}
