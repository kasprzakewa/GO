package com.server.game.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "Games", schema = "GO")
public class GameEntity 
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private int ID;

    @Basic
    @Column(name = "Winner")
    private Boolean winner;

    @Basic
    @Column(name = "Date")
    private Date date;

    @OneToMany(mappedBy = "gameID", cascade = CascadeType.ALL)
    List<MovesEntity> moves = new ArrayList<MovesEntity>();

    public int setGame(EntityManager em)
    {
        var stringQuery = "INSERT INTO Games (Winner) VALUES (null)";
        var query = em.createNativeQuery(stringQuery, GameEntity.class);

        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();

        return setGameID(em);
    }

    public int setGameID(EntityManager em)
    {
        var stringQuery = "SELECT ID FROM Games ORDER BY ID DESC LIMIT 1";
        var query = em.createNativeQuery(stringQuery, Integer.class);
        int a = (Integer) query.getSingleResult();
        return a;
    }

    public Date getDate() {
        return date;
    }

    public int getID() {
        return ID;
    }

    public Boolean getWinner(){
        return winner;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public void setID(int iD) {
        ID = iD;
    }
    
    public void setWinner(Boolean winner, int gameID, EntityManager em) 
    {
        var stringQuery = "UPDATE Games SET Winner = " + winner + " WHERE ID = " + gameID;
        var query = em.createNativeQuery(stringQuery, GameEntity.class);

        em.getTransaction().begin();
        query.executeUpdate();
        em.getTransaction().commit();
    }

}
