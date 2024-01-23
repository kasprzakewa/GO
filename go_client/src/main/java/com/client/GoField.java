package com.client;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GoField extends Circle {
    
    private int x;
    private int y;

    public GoField(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.setFill(color);
        this.setCenterX(y);
        this.setCenterY(x);
        this.setRadius(20);
        this.setStroke(Color.BLACK);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
