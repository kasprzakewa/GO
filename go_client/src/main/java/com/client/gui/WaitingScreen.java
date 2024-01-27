package com.client.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class WaitingScreen extends Pane{

    public WaitingScreen() {
        
        Label waitingLabel = new Label();
        waitingLabel.setText("Waiting for opponent...");

        setStyle("-fx-background-color: #f5f5dc;");

        getChildren().add(waitingLabel);     
    }
    
}
