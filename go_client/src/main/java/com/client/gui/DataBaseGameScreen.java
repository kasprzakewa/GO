package com.client.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.client.servercommuniaction.Client;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DataBaseGameScreen extends HBox {
    

    private VBox buttons;
    private GoBoard board;
    private Button nextButton;
    private Button previousButton;
    private Button backButton;
    private Client client;
    private BufferedReader dbReader;

    public DataBaseGameScreen(Client client) throws IOException{

        this.client = client;
        this.dbReader = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));

        this.board = new GoBoard(19);

        this.buttons = new VBox();
        buttons.setSpacing(30);
        this.nextButton = new Button("next");
        this.previousButton = new Button("previous");
        this.backButton = new Button("back");
        buttons.getChildren().addAll(nextButton, previousButton, backButton);
        getChildren().addAll(board, buttons);

        try {
            String boardString = dbReader.readLine();
            board.drawBoardDataBase(boardString);
        } catch (Exception e) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setContentText("Wrong game id");
            dialog.getDialogPane().getButtonTypes().add(javafx.scene.control.ButtonType.OK);
            Platform.runLater(() -> {
                dialog.showAndWait();
                new GoGUI((Stage)this.getScene().getWindow());
            });
        }
        

        nextButton.setOnAction(e -> {
            try {
                this.client.writeToServer("next");
                String boardString = client.readFromServer();
                board.drawBoardDataBase(boardString);
            } catch (IOException e1) {
                Dialog<String> dialog = new Dialog<>();
                dialog.setContentText("connection failed");
                dialog.getDialogPane().getButtonTypes().add(javafx.scene.control.ButtonType.OK);
                Platform.runLater(() -> {
                    dialog.showAndWait();
                    new GoGUI((Stage)this.getScene().getWindow());
                });
            }
        });

        previousButton.setOnAction(e -> {
            try {
                this.client.writeToServer("previous");
                String boardString = dbReader.readLine();
                board.drawBoard(boardString);
            } catch (IOException e1) {
                Dialog<String> dialog = new Dialog<>();
                dialog.setContentText("Wrong game id");
                dialog.getDialogPane().getButtonTypes().add(javafx.scene.control.ButtonType.OK);
                Platform.runLater(() -> {
                    dialog.showAndWait();
                    new GoGUI((Stage)this.getScene().getWindow());
                });
            }
        });

        backButton.setOnAction(e -> {
            try {
                this.client.writeToServer("end");
                Platform.runLater(() -> {
                    new GoGUI((Stage)this.getScene().getWindow());
                });
            } catch (IOException e1) {
                Dialog<String> dialog = new Dialog<>();
                dialog.setContentText("connection failed");
                dialog.getDialogPane().getButtonTypes().add(javafx.scene.control.ButtonType.OK);
                Platform.runLater(() -> {
                    dialog.showAndWait();
                    new GoGUI((Stage)this.getScene().getWindow());
                });
            }
        });
    }

    public GoBoard getBoard() {
        return board;
    }
}
