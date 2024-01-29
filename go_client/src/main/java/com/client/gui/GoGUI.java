package com.client.gui;

import java.io.IOException;

import com.client.ClientGame;
import com.client.ClientGameBuilder;
import com.client.servercommuniaction.Client;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

public class GoGUI {

    private final static int PVP = 0;
    private final static int BOT = 1;

    public GoGUI(Stage stage) {
        
        Button pvpButton = new Button("PVP");
        pvpButton.setPrefSize(200, 200);
        pvpButton.setStyle("-fx-font-size: 30px");

        Button botButton = new Button("BOT");
        botButton.setPrefSize(200, 200);
        botButton.setStyle("-fx-font-size: 30px");
        
        StartScreen startScreen = new StartScreen(pvpButton, botButton);

        Client client;
        try {
            
            client = new Client("localhost", 6666);

            pvpButton.setOnMouseClicked(e -> {
                try{
                    client.writeToServer(PVP);
                    startGame(stage, client);
                }
                catch(IOException ex){
    
                    Dialog<String> dialog = new Dialog<>();
                    dialog.setContentText("Server connection failed");
                    dialog.showAndWait();
                    Platform.exit();
                }
            });
            
            botButton.setOnMouseClicked(e -> {
                try {
                    client.writeToServer(BOT);
                    startGame(stage, client);
                } catch (IOException e1) {
                    Dialog<String> dialog = new Dialog<>();
                    dialog.setContentText("Server connection failed");
                    dialog.showAndWait();
                    Platform.exit();
                }
            });
            
            Scene scene = new Scene(startScreen, 640, 480);
            stage.setScene(scene);
            stage.setTitle("Go");
            stage.setMaximized(true);
            stage.setResizable(true);      
            stage.show();


        } catch (IOException e) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setContentText("Server connection failed");
            dialog.showAndWait();
            Platform.exit();
        }
    }

    private static void startGame(Stage stage, Client client) throws IOException {

        StartScreen startScreen = (StartScreen)stage.getScene().getRoot();
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

                        GoBoard playerBoard = new GoBoard(19);
                        GameScreen gameScreen = new GameScreen(playerBoard, playerNumber, startScreen, stage);
                        
                        Platform.runLater(() -> {

                            stage.hide();
                            stage.setScene(new Scene(gameScreen, 640, 480));
                            stage.show();
                        });

                        ClientGame gameClient = new ClientGameBuilder()
                                .setClient(client)
                                .setPlayerBoard(playerBoard)
                                .setPlayerNumber(playerNumber)
                                .setPassButton(gameScreen.getPassButton())
                                .setResignButton(gameScreen.getResignButton())
                                .setTurnLabel(gameScreen.getTurnLabel())
                                .setPointsLabel(gameScreen.getPointsLabel())
                                .setTerritoryLabel(gameScreen.getTerritoryLabel())
                                .setPopup(gameScreen.getPopup())
                                .build();
                        gameClient.setButtons();
                        Thread gameClientThread = new Thread(gameClient);
                        gameClientThread.setDaemon(true);
                        gameClientThread.start();

                        
                    }   
                } catch (IOException e) {
                    Dialog<String> dialog = new Dialog<>();
                    dialog.setContentText("Server connection failed");
                    dialog.showAndWait();
                    Platform.exit();
                } 
                return null;  
            }
        };
        
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

        
    }
}
