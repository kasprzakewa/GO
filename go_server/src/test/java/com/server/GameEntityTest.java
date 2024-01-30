package com.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.mockito.Mockito;

import com.server.game.database.entity.GameEntity;

import jakarta.persistence.*;

public class GameEntityTest {

    // @Test
    // public void testSetGame() {
    //     EntityManager em = Mockito.mock(EntityManager.class);
    //     EntityTransaction et = Mockito.mock(EntityTransaction.class);
    //     Query query = Mockito.mock(Query.class);
    //     Mockito.when(em.getTransaction()).thenReturn(et);
    //     Mockito.when(em.createNativeQuery(Mockito.anyString(), Mockito.eq(GameEntity.class))).thenReturn(query);
    //     Mockito.when(query.executeUpdate()).thenReturn(1);
    //     Mockito.when(query.getSingleResult()).thenReturn(1);
    //     GameEntity game = new GameEntity();
    //     assertEquals(1, game.setGame(em));
    // }

    // @Test
    // public void testSetWinner() {
    //     EntityManager em = Mockito.mock(EntityManager.class);
    //     EntityTransaction et = Mockito.mock(EntityTransaction.class);
    //     Query query = Mockito.mock(Query.class);
    //     Mockito.when(em.getTransaction()).thenReturn(et);
    //     Mockito.when(em.createNativeQuery(Mockito.anyString(), Mockito.eq(GameEntity.class))).thenReturn(query);
    //     Mockito.when(query.executeUpdate()).thenReturn(1);
    //     GameEntity game = new GameEntity();
    //     game.setWinner(true, 1, em);
    //     assertTrue(game.getWinner());
    // }

    // Add more tests as needed...
}