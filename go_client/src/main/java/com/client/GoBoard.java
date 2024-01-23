package com.client;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GoBoard extends GridPane {
    
    private int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public GoBoard(int size) {

        this.size = size;
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                GoField field = new GoField(i, j, Color.TRANSPARENT);

                field.setOnMouseClicked(e -> {
                    if(field.getFill() == Color.TRANSPARENT){
                        field.setFill(Color.BLACK);
                    } else {
                        field.setFill(Color.TRANSPARENT);
                    }
                });

                this.add(field, i, j);
                this.autosize();
                this.setHgap(10);
                this.setVgap(10);
            }
        }
    }
}
