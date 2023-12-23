package com.server.game;

public interface Opponent {
    

    boolean makeMove(Board board, Point point);

    void sendMessage(int message);

    int recieveMessage();
}
