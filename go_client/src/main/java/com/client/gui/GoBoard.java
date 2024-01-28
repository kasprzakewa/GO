package com.client.gui; 

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GoBoard extends Pane {
    
    private int size;
    private GoField[][] fields;
    private List<GoField> fieldsList;


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public GoBoard(int size) {

        
        this.fieldsList = new ArrayList<>();
        this.size = size;
        this.fields = new GoField[this.size][this.size];

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){

                GoField field = new GoField(Color.TRANSPARENT, i, j);
                this.getChildren().add(field);
                this.fieldsList.add(field);
                this.getChildren().add(field.getCircle());
                if (i < size - 1 && j < size -1){
                    field.setStroke(Color.BLACK);
                }
                fields[i][j] = field;
            }
        }
    }

    public GoField[][] getFieldBoard() {
        
        return this.fields;
    }

    public List<GoField> getFields() {
        return this.fieldsList;
    }

}
