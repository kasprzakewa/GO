package com.client.gui;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar.ButtonData;

public class EndGameDialog extends Dialog<ButtonType>{

    private ButtonType backToMenu = new ButtonType("back to menu", ButtonData.FINISH);

    public EndGameDialog(){
        super();
        this.setTitle("Game over");
        getDialogPane().getButtonTypes().add(backToMenu);
    }
    
    public ButtonType getBackToMenu(){
        return backToMenu;
    }

    public void setBackToMenu(ButtonType backToMenu){
        this.backToMenu = backToMenu;
    }

    public void setMessage(String message){
        getDialogPane().setContentText(message);
    }
}
