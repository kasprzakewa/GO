package com.client.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class StartScreen extends HBox {
    
    private Button pvpButton;
    private Button botButton;

    public StartScreen(Button pvpButton, Button botButton) {

        this.pvpButton = pvpButton;
        this.botButton = botButton;

        setStyle("-fx-background-color: #f5f5dc;");
        setSpacing(30);
        setAlignment(Pos.CENTER);

        getChildren().addAll(pvpButton, botButton);
    }

    public Button getPvpButton() {
        return pvpButton;
    }

    public Button getBotButton() {
        return botButton;
    }

    public void setPvpButton(Button pvpButton) {
        this.pvpButton = pvpButton;
    }

    public void setBotButton(Button botButton) {
        this.botButton = botButton;
    }


}
