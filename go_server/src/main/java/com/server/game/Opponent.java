package com.server.game;

public interface Opponent {
    

    boolean makeMove(Point point);

    void sendMessage(int message);

    int receiveMessage();

    void setBoard(Board board);

    int getPoints();

    int getTerritory();

    StoneColor getColor();
}
