package com.server.game.database;

import java.io.IOException;

import com.server.game.Player;
import com.server.game.database.entity.MovesEntity;
import jakarta.persistence.EntityManager;

public class DataBaseManager implements Runnable{

    private Player player;
    private int id;
    private MovesEntity me;
    private EntityManager em;
    private int increment = 0;

    public DataBaseManager(Player player, int id, EntityManager em){
        
        this.player = player;
        this.id = id;
        this.me = new MovesEntity();
        this.em = em;  
        
    }

    @Override
    public void run() {
        

        try {
            player.sendMessage(me.getGame(id, em).get(increment));
            while(true){
                String command = player.receiveMessage();
                if("next".equals(command)){
                    increment++;
                    if(increment<me.getGame(id, em).size()){
                        player.sendMessage(me.getGame(id, em).get(increment));
                    }
                    else{
                        player.sendMessage("end");
                    }
                }
                else if("previous".equals(command)){
                    if(increment>0){
                        increment--;
                    }
                    
                    player.sendMessage(me.getGame(id, em).get(increment));
                }
                else if("end".equals(command)){
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error sending board to client");
        }
    }
    
}
