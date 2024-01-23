package com.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

import com.client.servercommuniaction.Client;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    final static int PVP = 0;
    final static int BOT = 1;

    final static int PLAYER1 = 1;
    final static int PLAYER2 = 2;

    private static boolean myTurn = false;
    private static boolean game = true;

    final static int PLAYER1_WON = 1;
    final static int PLAYER2_WON = 2;
    final static int DRAW = 3;

    private static Client client;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = new GoBoard(19);
        scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }

    /* 
    public static void main(String[] args) {
        //launch();

        


        try {
            
            client = new Client("localhost", 6666);
            client.writeToServer(PVP);
            int playerNumber = client.readFromServer();

            if(playerNumber == PLAYER1){
                System.out.println("black");
                client.readFromServer();

                myTurn = true;

            }
            else if (playerNumber == PLAYER2){
                System.out.println("white");
            }
            
            while(game){
                int moveCorrect;

                if(playerNumber == PLAYER1){
                    //do {
                    sendMove();
                       // moveCorrect = thisPlayer.recieveMessage();
                    //}while(moveCorrect == 0);
                    
                    recieveServerResponse();
                }
                else if (playerNumber == PLAYER2){
                    
                    recieveServerResponse();
                    //do {
                    sendMove();
                        //moveCorrect = thisPlayer.recieveMessage();
                    //}while(moveCorrect == 0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMove() throws IOException {
    }

    private static void recieveServerResponse() throws IOException {

        int status = client.readFromServer();
        System.out.println("status: " + status);
        if (status == PLAYER1_WON){
            game = false;
        }
        else if (status == PLAYER2_WON){
            game = false;
        }
        else if (status == DRAW){
            game = false;
            System.out.println("Draw :O");

        }
        else {
            recieveMove();
            myTurn = true;
        }
    }

    private static void recieveMove() throws IOException{
        int row = client.readFromServer();
        int col = client.readFromServer();
    }
    */
}