package com.client;

import com.client.gui.GoBoard;
import com.client.servercommuniaction.Client;

import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;

public class GameClientBuilder {
    
    GameClient client;

    public GameClientBuilder() {
        client = new GameClient();
    }

    public GameClientBuilder setClient(GameClient client) {
        this.client = client;
        return this;
    }

    public GameClient build() {
        return client;
    }

    public GameClientBuilder setPlayerBoard(GoBoard playerBoard) {
        client.setPlayerBoard(playerBoard);
        return this;
    }

    public GameClientBuilder setPlayerNumber(int playerNumber) {
        client.setPlayerNumber(playerNumber);
        return this;
    }

    public GameClientBuilder setResignButton(Button resignButton) {
        client.setResignButton(resignButton);
        return this;
    }

    public GameClientBuilder setPassButton(Button passButton) {
        client.setPassButton(passButton);
        return this;
    }

    public GameClientBuilder setTurnLabel(Label turnLabel) {
        client.setTurnLabel(turnLabel);
        return this;
    }

    public GameClientBuilder setPointsLabel(Label pointLabel) {
        client.setPointLabel(pointLabel);
        return this;
    }

    public GameClientBuilder setTerritoryLabel(Label territoryLabel) {
        client.setTerritoryLabel(territoryLabel);
        return this;
    }

    public GameClientBuilder setClient(Client client){
        this.client.setClient(client);
        return this;
    }

    public GameClientBuilder setPopup(Dialog<String> popup){
        this.client.setPopup(popup);
        return this;
    }
}
