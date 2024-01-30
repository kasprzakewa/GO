package com.client.gui;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar.ButtonData;

public class EndGameDialog extends Dialog<ButtonType>{

    private ButtonType backToMenu = new ButtonType("back to menu", ButtonData.FINISH);
    private ButtonType checkTheGame = new ButtonType("check the game", ButtonData.NEXT_FORWARD);
    private String message;

    public EndGameDialog(){
        super();
        this.setTitle("Game over");
        getDialogPane().getButtonTypes().add(backToMenu);
        getDialogPane().getButtonTypes().add(checkTheGame);
    }
    
    public ButtonType getBackToMenu(){
        return backToMenu;
    }

    public ButtonType getCheckTheGame(){
        return checkTheGame;
    }

    public void setBackToMenu(ButtonType backToMenu){
        this.backToMenu = backToMenu;
    }

    public void setCheckTheGame(ButtonType checkTheGame){
        this.checkTheGame = checkTheGame;
    }

    public void setMessage(String message){
        this.message = message;
        getDialogPane().setContentText(message);
    }
}
