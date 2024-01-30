package com.server;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.server.game.database.entity.MovesEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class MovesEntityTest {
    private MovesEntity movesEntity;
    private EntityManager em;
    private Query query;

    @Before
    public void setUp() 
    {
        movesEntity = new MovesEntity();
        em = Mockito.mock(EntityManager.class);
        EntityTransaction et = Mockito.mock(EntityTransaction.class);
        when(em.getTransaction()).thenReturn(et);
        query = Mockito.mock(Query.class);
    }

    @Test
    public void testGetGame() 
    {
        int gameID = 1;
        List<String> expectedMoves = Arrays.asList("move1", "move2");
        when(em.createNativeQuery(Mockito.anyString(), Mockito.eq(String.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedMoves);
        List<String> actualMoves = movesEntity.getGame(gameID, em);
        assertEquals(expectedMoves, actualMoves);
    }
}
