package com.server.game;

import java.io.IOException;
import java.util.ArrayList;

import com.server.game.database.entity.GameEntity;

import jakarta.persistence.EntityManager;
public class ServerGame implements Runnable
{
    private Board board;
    private Opponent whitePlayer;
    private Opponent blackPlayer;
    private ArrayList<String> history;
    private EntityManager em;
    private int gameID;
    private GameEntity ge;

    private static final int PLAYER1_WON = 1;
    private static final int PLAYER2_WON = 2;
    private static final int DRAW = 3;


    private static int passed = 0; 

    public ServerGame(int size, Opponent player1, Opponent player2, EntityManager em) throws IOException
    {
        history = new ArrayList<>();
        ge = new GameEntity();
        gameID = ge.setGame(em);
        this.em = em;
        
        board = new Board(size, history, gameID, em);
        whitePlayer = player2;
        blackPlayer = player1;

        whitePlayer.setBoard(board);
        blackPlayer.setBoard(board);
    } 

    @Override
    public void run() 
    {

        //Scanner scanner = new Scanner(System.in);
        try {

            blackPlayer.sendMessage("1");
            boolean play = true;

            while (play) 
            {   
                String blackMessage;
                String whiteMessage;
                
                

                boolean placed;

                do
                {
                    System.out.println("black to move");

                    blackMessage = blackPlayer.receiveMessage();

                    if("resigned".equals(blackMessage))
                    {
                        System.out.println("black surrendered");

                        blackPlayer.sendMessage("correct_move");

                        placed = true;
                        play = false;
                        passed=0;
                    }
                    else if("passed".equals(blackMessage))
                    {
                        System.out.println("black passed");

                        blackPlayer.sendMessage("correct_move");
                        placed = true;
                        passed++;
                    }
                    else if (blackMessage == null){

                        System.out.println("server connection error");

                        blackPlayer.sendMessage("correct_move");
                        placed = true;
                        play = false;
                        passed=0;
                    }
                    else{

                        int blackX = Integer.parseInt(blackMessage.split(" ")[0]);
                        int blackY = Integer.parseInt(blackMessage.split(" ")[1]);
                        if (blackPlayer.makeMove(new Point(blackX, blackY))){

                            placed = true;
                            passed = 0;
    
                            System.out.println("black move correct");
    
                            blackPlayer.sendMessage("correct_move");
    
                            System.out.println("update sent");
                        }
                        else{
    
                            System.out.println("black move incorrect");
    
                            blackPlayer.sendMessage("incorrect_move");
                            passed = 0;
    
                            System.out.println("update sent");
    
                            placed = false;
                        }
                    } 
                }while(!placed);

                sendGameStatus(blackMessage, StoneColor.BLACK);
                board.save(true);

                if(!play)
                {
                    break;
                }

                sendUpdates();

                placed = false;

                if (passed == 2)
                {
                    break;
                }
                
                do
                {
                    System.out.println("white to move");

                    whiteMessage = whitePlayer.receiveMessage();

                    if("resigned".equals(whiteMessage))
                    {
                        System.out.println("white surrendered");

                        whitePlayer.sendMessage("correct_move");

                        placed = true;
                        play = false;
                        passed=0;
                    }
                    else if("passed".equals(whiteMessage))
                    {
                        System.out.println("white passed");

                        whitePlayer.sendMessage("correct_move");
                        placed = true;
                        passed++;
                    }
                    else if (whiteMessage == null){

                        System.out.println("server connection error");

                        whitePlayer.sendMessage("correct_move");
                        placed = true;
                        play = false;
                        passed=0;
                    }
                    else{

                        int whiteX = Integer.parseInt(whiteMessage.split(" ")[0]);
                        int whiteY = Integer.parseInt(whiteMessage.split(" ")[1]);
                        if (whitePlayer.makeMove(new Point(whiteX, whiteY))){

                            placed = true;
                            passed = 0;
    
                            System.out.println("white move correct");
    
                            whitePlayer.sendMessage("correct_move");
    
                            System.out.println("update sent");
                        }
                        else{
    
                            System.out.println("white move incorrect");
    
                            whitePlayer.sendMessage("incorrect_move");
                            passed = 0;
    
                            System.out.println("update sent");
    
                            placed = false;
                        }
                    } 
                }while(!placed);

                sendGameStatus(whiteMessage, StoneColor.WHITE);
                board.save(false);

                if(!play)
                {
                    break;
                }

                sendUpdates();

                System.out.println("end of turn");

                if(passed == 2)
                {
                    break;
                }
                

            }
            em.close();
        } catch (IOException e) {
            System.out.println("Server ERROR: lost connection to client");
        }
        //scanner.close();
    }

    public void sendGameStatus(String message, StoneColor color) throws IOException{

        if(passed == 2){

            int winningPlayer = calculateResult();

            if(winningPlayer == PLAYER1_WON){

                blackPlayer.sendMessage("player1_won");
                whitePlayer.sendMessage("player1_won");
            }
            else if(winningPlayer == PLAYER2_WON){

                blackPlayer.sendMessage("player2_won");
                whitePlayer.sendMessage("player2_won");
            }
            else{
                blackPlayer.sendMessage("draw");
                whitePlayer.sendMessage("draw");
            }
        }
        else if(message.equals("resigned")){
            if(color == StoneColor.BLACK){
                blackPlayer.sendMessage("player2_won");
                whitePlayer.sendMessage("player2_won");
            }
            else{
                blackPlayer.sendMessage("player1_won");
                whitePlayer.sendMessage("player1_won");
            }
        }
        else{
            blackPlayer.sendMessage("continue");
            whitePlayer.sendMessage("continue");
        }
    }

    public Board getBoard() 
    {
        return board;
    }

    public void setBoard(Board board) 
    {
        this.board = board;
    }

    public Opponent getWhitePlayer() 
    {
        return whitePlayer;
    }

    public void setWhitePlayer(Player whitePlayer) 
    {
        this.whitePlayer = whitePlayer;
    }

    public Opponent getBlackPlayer() 
    {
        return blackPlayer;
    }

    public void setBlackPlayer(Player blackPlayer) 
    {
        this.blackPlayer = blackPlayer;
    }

    public void sendUpdates() throws IOException{

        blackPlayer.sendMessage(Integer.toString(blackPlayer.getPoints()));
        whitePlayer.sendMessage(Integer.toString(blackPlayer.getPoints()));

        blackPlayer.sendMessage(Integer.toString(whitePlayer.getPoints()));
        whitePlayer.sendMessage(Integer.toString(whitePlayer.getPoints()));

        blackPlayer.sendMessage(Integer.toString(blackPlayer.getTerritory()));
        whitePlayer.sendMessage(Integer.toString(blackPlayer.getTerritory()));

        blackPlayer.sendMessage(Integer.toString(whitePlayer.getTerritory()));
        whitePlayer.sendMessage(Integer.toString(whitePlayer.getTerritory()));

        blackPlayer.sendMessage(board.toString());
        whitePlayer.sendMessage(board.toString());
    }

    public int calculateResult(){

        int blackPoints = blackPlayer.getPoints();
        int whitePoints = whitePlayer.getPoints();

        int blackTerritory = blackPlayer.getTerritory();
        int whiteTerritory = whitePlayer.getTerritory();

        int blackTotal = blackPoints + blackTerritory;
        int whiteTotal = whitePoints + whiteTerritory;

        if(blackTotal > whiteTotal){
            return PLAYER1_WON;
        }
        else if(whiteTotal > blackTotal){
            return PLAYER2_WON;
        }
        else{
            return DRAW;
        }
    }
}
