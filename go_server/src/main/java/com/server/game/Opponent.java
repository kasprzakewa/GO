package com.server.game;

import java.io.IOException;

public interface Opponent {
    

    boolean makeMove(Point point);

    void sendMessage(String message) throws IOException;

    String receiveMessage() throws IOException;

    void setBoard(Board board);

    int getPoints();

    int getTerritory();

    StoneColor getColor();
}
