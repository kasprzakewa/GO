package com.client;

import static org.junit.Assert.*;

import org.junit.Test;

import com.client.gui.GoField;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TestGoField {

    @Test
    public void testRow() {
        GoField field = new GoField(Color.TRANSPARENT, 1, 2);
        assertEquals(2, field.getRow());
        field.setRow(3);
        assertEquals(3, field.getRow());
    }

    @Test
    public void testCol() {
        GoField field = new GoField(Color.TRANSPARENT, 1, 2);
        assertEquals(1, field.getCol());
        field.setCol(3);
        assertEquals(3, field.getCol());
    }

    @Test
    public void testCircle() {
        GoField field = new GoField(Color.TRANSPARENT, 1, 2);
        assertTrue(field.getCircle() instanceof Circle);
    }

    @Test
    public void testColor() {
        GoField field = new GoField(Color.TRANSPARENT, 1, 2);
        assertEquals(Color.TRANSPARENT, field.getColor());
        field.setColor(Color.WHITE);
        assertEquals(Color.WHITE, field.getColor());
        assertEquals(Color.BLACK, field.getCircle().getStroke());
    }
}
