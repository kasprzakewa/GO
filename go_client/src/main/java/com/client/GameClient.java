package com.client;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import com.client.gui.GoBoard;
import com.client.gui.GoField;
import com.client.servercommuniaction.Client;

import javafx.application.Platform;

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

    private int SURRENDER_WHITE = 0;
    private int SURRENDER_BLACK = 0;

    private int gameStatus = CONTINUE;


    private Client client;
    private GoBoard playerBoard;
    private Semaphore semaphore = new Semaphore(0);
    private int playerNumber;

    public GameClient(Client client, GoBoard playerBoard, int playerNumber) {

        this.client = client;
        this.playerBoard = playerBoard;
        this.playerNumber = playerNumber;

        System.out.println("Player number: " + playerNumber);

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

    public void play(){

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
                        System.out.println("Is move correct: " + isMoveCorrect);

                    }while(isMoveCorrect == INCORRECT_MOVE);

                    myTurn = false;
                    //BLACK MOVES HERE
                    
                    int[][] board = recieveBoardInfo();
                    drawBoard(board);
                    //RECIEVING BLACK MOVE
                    
                    
                    //WHITES MOVE HERE
                    board = recieveBoardInfo();
                    drawBoard(board);
                }
                else if (playerNumber == PLAYER2){
                    
                    //RECIEVING BLACK MOVE
                    int[][] board = recieveBoardInfo();
                    drawBoard(board);

                    

                    //WHITES MOVE HERE
                    int isMoveCorrect;

                    do{

                        myTurn = true;
                        semaphore.acquire();
                        isMoveCorrect = client.readFromServer();
                        System.out.println("Is move correct: " + isMoveCorrect);
                        
                    }while(isMoveCorrect == INCORRECT_MOVE);

                    myTurn = false;

                    //RECIVING WHITE MOVE HERE
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

    @Override
    public void run() {
        play();
    }

    private void handleGameStatus(int status){
            
            if(status == PLAYER1_WON){
                System.out.println("Player 1 won");
                game = false;
            }
            else if(status == PLAYER2_WON){
                System.out.println("Player 2 won");
                game = false;
            }
            else if(status == DRAW){
                System.out.println("Draw");
                game = false;
            }
    }

    private void surrender(int color){
        if(color == BLACK){
            SURRENDER_BLACK = 1;
        }
        else if(color == WHITE){
            SURRENDER_WHITE = 1;
        }

        if(SURRENDER_BLACK == 1 && SURRENDER_WHITE == 1){
            game = false;
        }
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
}