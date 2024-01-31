package com.client.gui;

import java.io.IOException;

import com.client.GoGUI;
import com.client.servercommuniaction.Client;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

public class DataBaseCom implements Runnable{

    private Client client;
    private DataBaseGameScreen dataBaseGameScreen;

    public DataBaseCom(DataBaseGameScreen dataBaseGameScreen, Client client) {
        
        this.dataBaseGameScreen = dataBaseGameScreen;
        this.client = client;
    }

    @Override
    public void run() {
        
        boolean db = true;
        while(db){
            try {
                String board= client.readFromServer();
                Platform.runLater(() -> {
                    dataBaseGameScreen.getBoard().drawBoard(board);
                });
            } catch (IOException e) {

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setContentText("Server connection failed");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                Platform.runLater(() -> {
                    dialog.showAndWait();
                    new GoGUI((Stage)dataBaseGameScreen.getScene().getWindow());
                });
            }
        }
    }
    
}
