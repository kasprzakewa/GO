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
                this.getChildren().add(field.getCircle());
                if (i < size - 1 && j < size -1){
                    field.setStroke(Color.BLACK);
                }
                fields[i][j] = field;
            }
        }

        for (int i =0; i < fields.length; i++){
            for (int j = 0; j < fields[i].length; j++){
                fieldsList.add(fields[j][i]);
            }
        }
    }

    public GoField[][] getFieldBoard() {
        
        return this.fields;
    }

    public List<GoField> getFields() {
        return this.fieldsList;
    }

    public void drawBoard(String board){
            
        String[] fields = board.split("\\.");
        int length = (int) Math.sqrt(fields.length);
        int counter = 0;

        for (int i = 0; i < length; i++){
            for (int j = 0; j < length; j++){
                if (fields[counter].equals("E")){
                    this.fields[i][j].setColor(Color.TRANSPARENT);
                    this.fields[i][j].getCircle().setStroke(Color.TRANSPARENT);
                }
                if(fields[counter].equals("B")){
                    this.fields[i][j].setColor(Color.BLACK);

                }
                if(fields[counter].equals("W")){
                    this.fields[i][j].setColor(Color.WHITE);
                    this.fields[i][j].getCircle().setStroke(Color.BLACK);
                }
                counter++;
            }
        }


    }

    public void drawBoardDataBase(String board)
    {
        String[] fields = board.split("\\.");

                for(int i = 0; i < fields.length; i++){
            if(fields[i].equals("E")){
                this.fieldsList.get(i).setColor(Color.TRANSPARENT);
                this.fieldsList.get(i).getCircle().setStroke(Color.TRANSPARENT);
            }
            if(fields[i].equals("B")){
                this.fieldsList.get(i).setColor(Color.BLACK);
            }
            if(fields[i].equals("W")){
                this.fieldsList.get(i).setColor(Color.WHITE);
            }
        }

    }

}
