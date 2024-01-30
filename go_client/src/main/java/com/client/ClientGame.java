package com.client;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Semaphore;

import com.client.gui.EndGameDialog;
import com.client.gui.GameScreen;
import com.client.gui.GoBoard;
import com.client.gui.GoField;
import com.client.servercommuniaction.Client;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

public class ClientGame implements Runnable {

    private final static int PLAYER1 = 1;
    private final static int PLAYER2 = 2;

    private static boolean myTurn = false;
    private static boolean game = true;

    private String gameStatus = "continue";

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
                    
                    String isMoveCorrect;
                    
                    Platform.runLater(() -> {
                        gameScreen.getTurnLabel().setText("Your turn");
                    });
                    
                    do{

                        myTurn = true;
                        semaphore.acquire();

                        isMoveCorrect = client.readFromServer();
                        System.out.println("Is move correct: " + isMoveCorrect);

                    }while(isMoveCorrect.equals("incorrect_move"));

                    gameStatus = client.readFromServer();

                    Platform.runLater(() -> {
                        gameScreen.getTurnLabel().setText("Opponent's turn");
                    });

                    myTurn = false;
                    //BLACK MOVES HERE
                    if(!gameStatus.equals("continue")){
                        break;
                    }
                    String board = receiveBoardInfo();
                    playerBoard.drawBoard(board);
                    //RECEIVING BLACK MOVE
                    
                    
                    //WHITES MOVE HERE
                    gameStatus = client.readFromServer();
                    if(!gameStatus.equals("continue")){
                        break;
                    }
                    board = receiveBoardInfo();
                    playerBoard.drawBoard(board);
                }
                else if (playerNumber == PLAYER2){
                    
                    //RECEIVING BLACK MOVE
                    gameStatus = client.readFromServer();
                    if(!gameStatus.equals("continue")){
                        break;
                    }
                    String board = receiveBoardInfo();
                    playerBoard.drawBoard(board);
                    
                    //WHITES MOVE HERE
                    String isMoveCorrect;

                    Platform.runLater(() -> {
                        gameScreen.getTurnLabel().setText("Your turn");
                    });

                    do{
                        myTurn = true;

                        semaphore.acquire();
                        isMoveCorrect = client.readFromServer();
                        System.out.println("Is move correct: " + isMoveCorrect);
                        
                    }while(isMoveCorrect.equals("incorrect_move"));

                    gameStatus = client.readFromServer();

                    myTurn = false;

                    Platform.runLater(() -> {
                        gameScreen.getTurnLabel().setText("Opponent's turn");
                    });

                    //RECIVING WHITE MOVE HERE
                    if(!gameStatus.equals("continue")){
                        break;
                    }
                    board = receiveBoardInfo();
                    playerBoard.drawBoard(board);
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

    private String receiveBoardInfo() throws IOException, NumberFormatException {

        String blackPoints = client.readFromServer();
        String whitePoints = client.readFromServer();
        String blackTerritory = client.readFromServer();
        String whiteTerritory = client.readFromServer();

        Platform.runLater(()->{
            
            gameScreen.getPointsLabel().setText("Points:\nblack-> " + blackPoints + "\nwhite-> " + whitePoints);
            gameScreen.getTerritoryLabel().setText("Territory:\nblack-> " + blackTerritory + "\nwhite-> " + whiteTerritory);
        });

        String boardInfo = client.readFromServer();
        return boardInfo;
    }

    public String getGameStatus() {

        return gameStatus;
    }

    public void setFieldButtons() {

        for(GoField field : playerBoard.getFields()){

            field.getCircle().setOnMouseClicked(e -> {

                if(myTurn){

                    try {

                        System.out.println("Clicked");
                        client.writeToServer(Integer.toString(field.getCol())+" "+Integer.toString(field.getRow()));
                        semaphore.release();
                        myTurn = false;

                    } catch (IOException e1) {

                        Dialog<String> dialog = new Dialog<>();
                        dialog.setContentText("Server connection failed");
                        Platform.runLater(() -> {
                            dialog.showAndWait();
                            new GoGUI((Stage)gameScreen.getScene().getWindow());
                        });
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
                    client.writeToServer("passed");
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
                    client.writeToServer("resigned");
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

    public void endGame(String gameStatus){

        Platform.runLater(() -> {

            EndGameDialog popup = new EndGameDialog();
            
            if(gameStatus.equals("player1_won")){
                popup.setMessage("Black won!");
                
                
            }
            else if(gameStatus.equals("player2_won")){
                popup.setMessage("White won!");
            }
            else if(gameStatus.equals("draw")){
                popup.setMessage("Draw!");
            }
            else if(gameStatus.equals("server_error")){
                popup.setMessage("Server error!!!");
            }
            else{
                popup.setMessage("Unknown error!!!");
            }

            Optional<ButtonType> result = popup.showAndWait();
            if(result.isPresent() && result.get() == popup.getBackToMenu()){
                Platform.runLater(() -> {
                    new GoGUI((Stage)gameScreen.getScene().getWindow());
                });
            }
        });
    }
}