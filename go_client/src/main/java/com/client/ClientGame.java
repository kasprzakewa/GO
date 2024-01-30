package com.client;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Semaphore;

import com.client.gui.EndGameDialog;
import com.client.gui.GameScreen;
import com.client.gui.GoBoard;
import com.client.gui.GoField;
import com.client.gui.GoGUI;
import com.client.servercommuniaction.Client;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ClientGame implements Runnable {

    private final static int PLAYER1 = 1;
    private final static int PLAYER2 = 2;

    private static boolean myTurn = false;
    private static boolean game = true;

    private final static int PLAYER1_WON = 1;
    private final static int PLAYER2_WON = 2;
    private final static int DRAW = 3;
    private final static int CONTINUE = 4;
    private final static int INCORRECT_MOVE = 1;
    private final static int SERVER_ERROR = -10;

    private int gameStatus = CONTINUE;

    private Client client;
    private Semaphore semaphore = new Semaphore(0);
    private int playerNumber;
    private GameScreen gameScreen;
    private GoBoard playerBoard;
    
    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public ClientGame(GameScreen gameScreen, Client client, int playerNumber) throws IOException {

        this.gameScreen = gameScreen;
        this.client = client;
        this.playerNumber = playerNumber;
        this.playerBoard = gameScreen.getBoard();
        setButtons();
    }

    @Override
    public void run(){

        try {

            if(playerNumber == PLAYER1){

                client.readFromServer();
                myTurn = true;

                Platform.runLater(() -> {
                    gameScreen.getTurnLabel().setText("Your turn");
                });
            }
            else if(playerNumber == PLAYER2){

                Platform.runLater(() -> {
                    gameScreen.getTurnLabel().setText("Opponent's turn");
                });
            }
            
            while(game){
                
                if(playerNumber == PLAYER1){
                    
                    int isMoveCorrect;
                    
                    Platform.runLater(() -> {
                        gameScreen.getTurnLabel().setText("Your turn");
                    });
                    
                    do{

                        myTurn = true;
                        semaphore.acquire();

                        isMoveCorrect = client.readFromServer();
                        System.out.println("Is move correct: " + isMoveCorrect);

                    }while(isMoveCorrect == INCORRECT_MOVE);

                    gameStatus = client.readFromServer();

                    Platform.runLater(() -> {
                        gameScreen.getTurnLabel().setText("Opponent's turn");
                    });

                    myTurn = false;
                    //BLACK MOVES HERE
                    if(gameStatus != CONTINUE){
                        break;
                    }
                    int[][] board = receiveBoardInfo();
                    drawBoard(board);
                    //RECEIVING BLACK MOVE
                    
                    
                    //WHITES MOVE HERE
                    gameStatus = client.readFromServer();
                    if(gameStatus != CONTINUE){
                        break;
                    }
                    board = receiveBoardInfo();
                    drawBoard(board);
                }
                else if (playerNumber == PLAYER2){
                    
                    //RECEIVING BLACK MOVE
                    gameStatus = client.readFromServer();
                    if(gameStatus != CONTINUE){
                        break;
                    }
                    int[][] board = receiveBoardInfo();
                    drawBoard(board);
                    
                    //WHITES MOVE HERE
                    int isMoveCorrect;

                    Platform.runLater(() -> {
                        gameScreen.getTurnLabel().setText("Your turn");
                    });

                    do{
                        myTurn = true;

                        semaphore.acquire();
                        isMoveCorrect = client.readFromServer();
                        System.out.println("Is move correct: " + isMoveCorrect);
                        
                    }while(isMoveCorrect == INCORRECT_MOVE);

                    gameStatus = client.readFromServer();

                    myTurn = false;

                    Platform.runLater(() -> {
                        gameScreen.getTurnLabel().setText("Opponent's turn");
                    });

                    //RECIVING WHITE MOVE HERE
                    if(gameStatus != CONTINUE){
                        break;
                    }
                    board = receiveBoardInfo();
                    drawBoard(board);
                }
            }

            endGame(gameStatus);
        } catch (IOException | InterruptedException e) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setContentText("Server connection failed");
            Platform.runLater(() -> {
                dialog.showAndWait();
                Platform.exit();
            });
            return;
        }
    }

    private int[][] receiveBoardInfo() throws IOException {

        int blackPoints = client.readFromServer();
        int whitePoints = client.readFromServer();
        int blackTerritory = client.readFromServer();
        int whiteTerritory = client.readFromServer();

        Platform.runLater(()->{
            
            gameScreen.getPointsLabel().setText("Points:\nblack-> " + blackPoints + "\nwhite-> " + whitePoints);
            gameScreen.getTerritoryLabel().setText("Territory:\nblack-> " + blackTerritory + "\nwhite-> " + whiteTerritory);
        });

        int[][] boardInfo = new int[playerBoard.getSize()][playerBoard.getSize()];

        int message = client.readFromServer();
        while(message != -100){
            int row = message;
            int col = client.readFromServer();
            int color = client.readFromServer();
            boardInfo[row][col] = color;
            message = client.readFromServer();
        }
        return boardInfo;
    }

    public int getGameStatus() {

        return gameStatus;
    }

    public void drawBoard(int[][] board) throws IOException{

        Platform.runLater(() -> {

            for (int i = 0; i < playerBoard.getSize(); i++) {
                for (int j = 0; j < playerBoard.getSize(); j++) {
                    playerBoard.getFieldBoard()[i][j].setColor(board[i][j]);
                }
            }
        });
    }

    public void setFieldButtons() {

        for(GoField field : playerBoard.getFields()){

            field.getCircle().setOnMouseClicked(e -> {

                if(myTurn){

                    try {

                        System.out.println("Clicked");
                        client.writeToServer(field.getCol());
                        client.writeToServer(field.getRow());
                        semaphore.release();
                        myTurn = false;

                    } catch (IOException e1) {

                        Dialog<String> dialog = new Dialog<>();
                        dialog.setContentText("Server connection failed");
                        Platform.runLater(() -> {
                            dialog.showAndWait();
                            Platform.exit();
                        });
                        return;
                    }
                }
                
            });
        }
    }

    public void setButtons(){

        setFieldButtons();

        gameScreen.getPassButton().setOnMouseClicked(e -> {

            if(myTurn){

                try {

                    System.out.println("Clicked");
                    client.writeToServer(-1);
                    client.writeToServer(-1);
                    semaphore.release();
                    myTurn = false;

                } catch (IOException e1) {

                    Dialog<String> dialog = new Dialog<>();
                    dialog.setContentText("Server connection failed");
                    Platform.runLater(() -> {
                        dialog.showAndWait();
                        Platform.exit();
                    });
                    return;
                }
            }
        });

        gameScreen.getResignButton().setOnMouseClicked(e -> {

            if(myTurn){

                try {

                    System.out.println("Clicked");
                    client.writeToServer(-2);
                    client.writeToServer(-2);
                    semaphore.release();
                    myTurn = false;

                } catch (IOException e1) {

                    Dialog<String> dialog = new Dialog<>();
                    dialog.setContentText("Server connection failed");
                    Platform.runLater(() -> {
                        dialog.showAndWait();
                        Platform.exit();
                    });
                    return;
                }
            }
        });
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void endGame(int gameStatus){

        Platform.runLater(() -> {

            EndGameDialog popup = new EndGameDialog();
            
            if(gameStatus == PLAYER1_WON){
                popup.setMessage("Black won!");
                
                
            }
            else if(gameStatus == PLAYER2_WON){
                popup.setMessage("White won!");
            }
            else if(gameStatus == DRAW){
                popup.setMessage("Draw!");
            }
            else if(gameStatus == SERVER_ERROR){
                popup.setMessage("Server error!!!");
            }
            else{
                popup.setMessage("Unknown error!!!");
            }

            Optional<ButtonType> result = popup.showAndWait();
            if(result.isPresent() && result.get() == popup.getBackToMenu()){
                Platform.runLater(() -> {
                    new GoGUI((Stage)playerBoard.getScene().getWindow());
                });
            }
            else if(result.isPresent() && result.get() == popup.getCheckTheGame()){
                Platform.exit();
            }
        });
    }
}