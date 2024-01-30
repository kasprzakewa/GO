package com.client.gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GoField extends Rectangle {
    
    private Circle circle;
    private int row;
    private int col;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public GoField(Color color, int indexX, int indexY) {

        this.row = indexY;
        this.col = indexX;
        setWidth(50);
        setHeight(50);
        setFill(Color.TRANSPARENT);
        setX(indexX * 50);
        setY(indexY * 50);

        this.circle = new Circle(20);
        this.circle.setCenterX(getX());
        this.circle.setCenterY(getY());
        this.circle.setFill(color);
    }

    public Circle getCircle() {
        return circle;
    }

    public void setColor(Color color) {

        this.circle.setFill(color);
        if(color == Color.WHITE){
            this.circle.setStroke(Color.BLACK);
        }
    }
}
