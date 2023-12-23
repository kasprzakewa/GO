package com.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.Console;
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
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        //launch();

        client = new Client("localhost", 6666);
        board = new Board(19);


        try {
            client.writeToServer(PVP);
            int playerNumber = client.readFromServer();

            if(playerNumber == PLAYER1){
                System.out.println("black");
                thisPlayer = StoneColor.BLACK;
                otherPlayer = StoneColor.WHITE;
                client.readFromServer();

                myTurn = true;

            }
            else if (playerNumber == PLAYER2){
                System.out.println("white");
                thisPlayer = StoneColor.WHITE;
                otherPlayer = StoneColor.BLACK;
            }

            console = System.console();
            
            while(game){
                int moveCorrect;
                board.displayBoard();

                if(playerNumber == PLAYER1){
                    //do {
                    sendMove();
                    board.displayBoard();
                       // moveCorrect = thisPlayer.recieveMessage();
                    //}while(moveCorrect == 0);
                    
                    recieveServerResponse();
                }
                else if (playerNumber == PLAYER2){
                    
                    recieveServerResponse();
                    board.displayBoard();
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
        int rows = Integer.parseInt(console.readLine());
        int cols = Integer.parseInt(console.readLine());
        client.writeToServer(rows);
        client.writeToServer(cols);
        thisPlayer.makeMove(board, new Point(rows-1, cols-1));
    }

    private static void recieveServerResponse() throws IOException {

        int status = client.readFromServer();
        System.out.println("status: " + status);
        if (status == PLAYER1_WON){
            game = false;
            if(thisPlayer.getColor() == StoneColor.BLACK){
                System.out.println("I won!!!");
            }
            else if (thisPlayer.getColor() == StoneColor.WHITE){
                System.out.println("Another player won :(");
                recieveMove();
            }
        }
        else if (status == PLAYER2_WON){
            game = false;
            if(thisPlayer.getColor() == StoneColor.WHITE){
                System.out.println("I won!!!");
            }
            else if(thisPlayer.getColor() == StoneColor.BLACK){
                System.out.println("Another player won :(");
                recieveMove();
            }
        }
        else if (status == DRAW){
            game = false;
            System.out.println("Draw :O");

            if(thisPlayer.getColor() == StoneColor.WHITE){
                recieveMove();
            }
        }
        else {
            recieveMove();
            myTurn = true;
        }
    }

    private static void recieveMove() throws IOException{
        int row = client.readFromServer();
        int col = client.readFromServer();
        otherPlayer.makeMove(board, new Point(row, col));
    }

}