package com.client;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import com.client.gui.GoBoard;
import com.client.gui.GoField;
import com.client.servercommuniaction.Client;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GameClient implements Runnable {

    private final static int PLAYER1 = 1;
    private final static int PLAYER2 = 2;

    private static boolean myTurn = false;
    private static boolean game = true;

    private final static int PLAYER1_WON = 1;
    private final static int PLAYER2_WON = 2;
    private final static int DRAW = 3;
    private final static int CONTINUE = 4;

    private final static int BLACK = 1;
    private final static int WHITE = 2;

    private final static int CORRECT_MOVE = 0;
    private final static int INCORRECT_MOVE = 1;

    private int SERVER_ERROR = -10;

    private int gameStatus = CONTINUE;

    private Client client;
    private GoBoard playerBoard;
    private Semaphore semaphore = new Semaphore(0);
    private int playerNumber;
    private Button resignButton;
    private Button passButton;
    private Label pointLabel;
    private Label territoryLabel;

    public GameClient(Client client, GoBoard playerBoard, int playerNumber, Button resignButton, Button passButton) {

        this.client = client;
        this.playerBoard = playerBoard;
        this.playerNumber = playerNumber;
        this.resignButton = resignButton;
        this.passButton = passButton;

        System.out.println("Player number: " + playerNumber);

        setButtons();

    }

    @Override
    public void run(){

        try {

            if(playerNumber == PLAYER1){
                client.readFromServer();
                myTurn = true;
            }
            
            while(game){
                if(playerNumber == PLAYER1){

                    int isMoveCorrect;
                    

                    do{

                        myTurn = true;
                        semaphore.acquire();

                        isMoveCorrect = client.readFromServer();
                        gameStatus = client.readFromServer();
                        System.out.println("Is move correct: " + isMoveCorrect);

                    }while(isMoveCorrect == INCORRECT_MOVE);

                    myTurn = false;
                    //BLACK MOVES HERE
                    if(gameStatus != CONTINUE){
                        break;
                    }
                    int[][] board = recieveBoardInfo();
                    drawBoard(board);
                    //RECIEVING BLACK MOVE
                    
                    
                    //WHITES MOVE HERE
                    gameStatus = client.readFromServer();
                    if(gameStatus != CONTINUE){
                        break;
                    }
                    board = recieveBoardInfo();
                    drawBoard(board);
                }
                else if (playerNumber == PLAYER2){
                    
                    //RECIEVING BLACK MOVE
                    gameStatus = client.readFromServer();
                    if(gameStatus != CONTINUE){
                        break;
                    }
                    int[][] board = recieveBoardInfo();
                    drawBoard(board);

                    

                    //WHITES MOVE HERE
                    int isMoveCorrect;

                    do{

                        myTurn = true;
                        semaphore.acquire();
                        isMoveCorrect = client.readFromServer();
                        gameStatus = client.readFromServer();
                        System.out.println("Is move correct: " + isMoveCorrect);
                        
                    }while(isMoveCorrect == INCORRECT_MOVE);

                    myTurn = false;

                    //RECIVING WHITE MOVE HERE
                    if(gameStatus != CONTINUE){
                        break;
                    }
                    board = recieveBoardInfo();
                    drawBoard(board);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int[][] recieveBoardInfo() throws IOException {

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

                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                
            });
        }
    }

    public void setButtons(){

        setFieldButtons();

        passButton.setOnMouseClicked(e -> {

            if(myTurn){

                try {

                    System.out.println("Clicked");
                    client.writeToServer(-1);
                    client.writeToServer(-1);
                    semaphore.release();
                    myTurn = false;

                } catch (IOException e1) {

                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        resignButton.setOnMouseClicked(e -> {

            if(myTurn){

                try {

                    System.out.println("Clicked");
                    client.writeToServer(-2);
                    client.writeToServer(-2);
                    semaphore.release();
                    myTurn = false;

                } catch (IOException e1) {

                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
    }

    public void setPointLabel(Label pointLabel) {
        this.pointLabel = pointLabel;
    }

    public void setTerritoryLabel(Label territoryLabel) {
        this.territoryLabel = territoryLabel;
    }

    public void setResignButton(Button resignButton) {
        this.resignButton = resignButton;
    }

    public void setPassButton(Button passButton) {
        this.passButton = passButton;
    }
}