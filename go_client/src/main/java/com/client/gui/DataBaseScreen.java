package com.client.gui;

import com.client.servercommuniaction.Client;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class DataBaseScreen extends HBox {
    
    private TextField gameId;

    public DataBaseScreen(Client client){

        Label label = new Label("Enter game id: ");
        gameId = new TextField();

        gameId.setOnKeyPressed(e -> {
            try{
                if(e.getCode()==KeyCode.ENTER){
                    System.out.println("enter");
                    Task<Void> task = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            int id = Integer.parseInt(gameId.getText());
                            client.writeToServer(Integer.toString(id));
                            DataBaseGameScreen gameScreen = new DataBaseGameScreen(client);
                            Platform.runLater(() -> {
                                getChildren().clear();
                                getChildren().add(gameScreen);
                            });
                            return null;
                        }
                    };
                    Thread thread = new Thread(task);
                    thread.setDaemon(true);
                    thread.start();
                }
            }
            catch(NumberFormatException ex){
                
                Dialog<String> dialog = new Dialog<>();
                dialog.setContentText("Wrong game id");
                dialog.getDialogPane().getButtonTypes().add(javafx.scene.control.ButtonType.OK);
                Platform.runLater(() -> {
                    dialog.showAndWait();
                    new GoGUI((Stage)this.getScene().getWindow());
                });
            }
        });
        getChildren().addAll(label, gameId);
        setAlignment(javafx.geometry.Pos.CENTER);
    }
}
