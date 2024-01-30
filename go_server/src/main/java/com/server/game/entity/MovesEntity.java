package com.server.game.entity;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Moves", schema = "GO")
public class MovesEntity 
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private int ID;

    @ManyToOne
    @JoinColumn(name = "GameID", referencedColumnName = "ID")
    private GameEntity gameID;

    @Basic
    @Column(name = "Move")
    private String move;

    @Basic
    @Column(name = "Date")
    private Timestamp date;

    @Basic
    @Column(name = "Player")
    private Boolean player;

    public void save(String board, int game, EntityManager em, Boolean player)
    {
        String stringQuery = "INSERT INTO Moves (GameID, Move, Player) VALUES (:game, :board, :player)";
    
        Query query = em.createNativeQuery(stringQuery, MovesEntity.class);
        query.setParameter("game", game);
        query.setParameter("board", board);
        query.setParameter("player", player);
    
        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();
    }

    public List<String> getGame(int gameID, EntityManager em)
    {
        String stringQuery = "SELECT Move FROM Moves WHERE GameID = :gameID ORDER BY Date";
        Query query = em.createNativeQuery(stringQuery, String.class); // Change MovesEntity.class to String.class
        query.setParameter("gameID", gameID);
    
        return (List<String>) query.getResultList(); // Add a typecast here
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public void setGameID(int gameID, EntityManager em) {
        this.gameID = em.find(GameEntity.class, gameID);
    }
    

    public void setID(int iD) {
        ID = iD;
    }

    public Timestamp getDate() {
        return date;
    }

    public GameEntity getGameID() {
        return gameID;
    }

    public int getID() {
        return ID;
    }

    public String getMove() {
        return move;
    }
    
}

