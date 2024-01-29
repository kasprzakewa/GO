package com.client;

import com.client.gui.GoBoard;
import com.client.servercommuniaction.Client;

import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;

public class ClientGameBuilder {
    
    ClientGame client;

    public ClientGameBuilder() {
        client = new ClientGame();
    }

    public ClientGameBuilder setClient(ClientGame client) {
        this.client = client;
        return this;
    }

    public ClientGame build() {
        return client;
    }

    public ClientGameBuilder setPlayerBoard(GoBoard playerBoard) {
        client.setPlayerBoard(playerBoard);
        return this;
    }

    public ClientGameBuilder setPlayerNumber(int playerNumber) {
        client.setPlayerNumber(playerNumber);
        return this;
    }

    public ClientGameBuilder setResignButton(Button resignButton) {
        client.setResignButton(resignButton);
        return this;
    }

    public ClientGameBuilder setPassButton(Button passButton) {
        client.setPassButton(passButton);
        return this;
    }

    public ClientGameBuilder setTurnLabel(Label turnLabel) {
        client.setTurnLabel(turnLabel);
        return this;
    }

    public ClientGameBuilder setPointsLabel(Label pointLabel) {
        client.setPointLabel(pointLabel);
        return this;
    }

    public ClientGameBuilder setTerritoryLabel(Label territoryLabel) {
        client.setTerritoryLabel(territoryLabel);
        return this;
    }

    public ClientGameBuilder setClient(Client client){
        this.client.setClient(client);
        return this;
    }

    public ClientGameBuilder setPopup(Dialog<String> popup){
        this.client.setPopup(popup);
        return this;
    }
}
