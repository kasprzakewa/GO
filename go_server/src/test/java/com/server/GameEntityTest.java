package com.server;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.server.game.entity.GameEntity;

import java.sql.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class GameEntityTest 
{

    private GameEntity gameEntity;
    private EntityManager em;
    private Query query;

    @Before
    public void setUp() 
    {
            query = Mockito.mock(Query.class);
        gameEntity = new GameEntity();
        em = Mockito.mock(EntityManager.class);
    }

    @Test
    public void testGetDate() 
    {
        Date date = new Date(System.currentTimeMillis());
        gameEntity.setDate(date);
        assertEquals(date, gameEntity.getDate());
    }

    @Test
    public void testGetID() 
    {
        int id = 1;
        gameEntity.setID(id);
        assertEquals(id, gameEntity.getID());
    }

    @Test
    public void testSetGameID() 
    {
        when(em.createNativeQuery(Mockito.anyString(), Mockito.eq(Integer.class))).thenReturn(query);
        when(query.getSingleResult()).thenReturn(1);
        int id = gameEntity.setGameID(em);
        assertEquals(1, id);
    }

}
