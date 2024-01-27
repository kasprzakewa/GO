package com.server.game;

public interface Opponent {
    

    boolean makeMove(Point point);

    void sendMessage(int message);

    int recieveMessage();

    void setBoard(Board board);
}
