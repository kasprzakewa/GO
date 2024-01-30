package com.client.gui;

import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;

public class DataBaseScreen extends StackPane {
    
    private TextField gameId;

    public DataBaseScreen(){

        gameId = new TextField();

        gameId.setOnKeyTyped(e -> {
            try{
                if(e.getCode()==KeyCode.ENTER){
                    int id = Integer.parseInt(gameId.getText());
                }
            }
            catch(NumberFormatException ex){
                
                Dialog<String> dialog = new Dialog<>();
                dialog.setContentText("Wrong game id");
                dialog.showAndWait();
            }
            
        });
    }
}
