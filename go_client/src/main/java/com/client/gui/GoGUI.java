package com.client.gui;

import java.io.IOException;

import com.client.ClientGame;
import com.client.servercommuniaction.Client;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

public class GoGUI {

    public GoGUI(Stage stage) {
        
        Button pvpButton = new Button("PVP");
        pvpButton.setPrefSize(200, 200);
        pvpButton.setStyle("-fx-font-size: 30px");

        Button botButton = new Button("BOT");
        botButton.setPrefSize(200, 200);
        botButton.setStyle("-fx-font-size: 30px");

        Button dataBaseButton = new Button("Previous\n Games");
        dataBaseButton.setPrefSize(200, 200);
        dataBaseButton.setStyle("-fx-font-size: 30px");
        
        StartScreen startScreen = new StartScreen(pvpButton, botButton, dataBaseButton);

        Client client;
        try {
            
            client = new Client("localhost", 6666);

            pvpButton.setOnMouseClicked(e -> {
                try{
                    client.writeToServer("pvp");
                    System.out.println("message sent: pvp");
                    startGame(stage, client);
                }
                catch(IOException ex){
                    Dialog<String> dialog = new Dialog<>();
                    dialog.setContentText("Server connection failed");
                    dialog.showAndWait();
                    new GoGUI(stage);
                }
            });
            
            botButton.setOnMouseClicked(e -> {
                try {
                    client.writeToServer("bot");
                    startGame(stage, client);
                } catch (IOException e1) {
                    Dialog<String> dialog = new Dialog<>();
                    dialog.setContentText("Server connection failed");
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                    dialog.showAndWait();
                    new GoGUI(stage);

                }
            });

            dataBaseButton.setOnMouseClicked(e -> {
                try {
                    client.writeToServer("db");
                    checkDataBase(stage, client);
                } catch (IOException e1) {
                    Dialog<String> dialog = new Dialog<>();
                    dialog.setContentText("Server connection failed");
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                    dialog.showAndWait();
                    new GoGUI(stage);
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
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.showAndWait();
            new GoGUI(stage);
        }
    }

    private static void startGame(Stage stage, Client client) throws IOException {

        StartScreen startScreen = (StartScreen)stage.getScene().getRoot();
        stage.hide();
        stage.setScene(new Scene(new WaitingScreen(), 640, 480));
        stage.show();

        System.out.println("Waiting for the second player");

        Task<Void> task = new Task<>(){

            @Override
            protected Void call(){

                String gameBegin;

                try {

                    gameBegin = client.readFromServer();
                    int playerNumber = Integer.parseInt(client.readFromServer());

                    System.out.println("Player number: " + playerNumber);

                    if("true".equals(gameBegin)){

                        System.out.println("Game begin");

                        GoBoard playerBoard = new GoBoard(19);
                        GameScreen gameScreen = new GameScreen(playerBoard, playerNumber, startScreen, stage);
                        
                        Platform.runLater(() -> {

                            stage.hide();
                            stage.setScene(new Scene(gameScreen, 640, 480));
                            stage.show();
                        });

                        ClientGame gameClient = new ClientGame(gameScreen, client, playerNumber);
                        Thread thread = new Thread(gameClient);
                        thread.setDaemon(true);
                        thread.start();
                        
                    }   
                } catch (IOException e) {
                    Platform.runLater(() -> {
                        Dialog<String> dialog = new Dialog<>();
                        dialog.setContentText("Server connection failed");
                        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                        dialog.showAndWait();
                        Platform.exit();
                    });
                } 
                return null;  
            }
        };
        
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void checkDataBase(Stage stage, Client client){
        
        stage.hide();
        stage.setScene(new Scene(new DataBaseScreen(client)));
        stage.setMaximized(true);
        stage.show();
    }
}
