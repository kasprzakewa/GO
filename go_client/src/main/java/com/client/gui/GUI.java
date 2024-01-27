package com.client.gui;

import java.io.IOException;

import com.client.GameClient;
import com.client.servercommuniaction.Client;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GUI {

    private final static int PVP = 0;
    private final static int BOT = 1;

    public GUI(Stage stage) throws IOException{
        
        Button pvpButton = new Button("PVP");
        Button botButton = new Button("BOT");
        StartScreen startScreen = new StartScreen(pvpButton, botButton);

        Client client = new Client("localhost", 6666);

        pvpButton.setOnMouseClicked(e -> {
            try{
                client.writeToServer(PVP);
                startGame(stage, client);
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        });
        
        botButton.setOnMouseClicked(e -> {
            try {
                client.writeToServer(BOT);
                startGame(stage, client);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        
        Scene scene = new Scene(startScreen, 640, 480);
        stage.setScene(scene);
        stage.setResizable(true);      
        stage.show();

    }

    private static void startGame(Stage stage, Client client) throws IOException {

        stage.hide();
        stage.setScene(new Scene(new WaitingScreen(), 640, 480));
        stage.show();

        Task<Void> task = new Task<>(){

            @Override
            protected Void call(){

                int gameBegin;
                try {
                    gameBegin = client.readFromServer();
                    int playerNumber = client.readFromServer();
                    if(gameBegin == 1){

                        GoBoard playerBoard = new GoBoard(9, playerNumber);
                        
                        Platform.runLater(() -> {
                            stage.hide();
                            stage.setScene(new Scene(playerBoard, 640, 480));
                            stage.show();
                        });

                        GameClient gameClient = new GameClient(client, playerBoard, playerNumber);
                        Thread gameClientThread = new Thread(gameClient);
                        gameClientThread.setDaemon(true);
                        gameClientThread.start();

                        
                    }   
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } 
                return null;  
            }
        };
        
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

        
    }
}
