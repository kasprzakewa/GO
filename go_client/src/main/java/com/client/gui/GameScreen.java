package com.client.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameScreen extends HBox {

    private GoBoard board;
    private Button passButton;
    private Button resignButton;
    private int playerNumber;
    private Label playerLabel;
    private Label pointsLabel;
    private Label territoryLabel;

    public GameScreen(GoBoard board, int playerNumber) {

        this.board = board;
        this.playerNumber = playerNumber;
        getChildren().add(board);
        setMargin(board, new Insets(30, 30, 30, 30));
        setSpacing(30);
        VBox buttonBox = new VBox();
        this.playerLabel = new Label("Player: " + (this.playerNumber==1?"Black":"White"));
        this.pointsLabel = new Label("Points: 0");
        this.territoryLabel = new Label("Territory: 0");
        this.playerLabel.setPrefSize(100, 50);
        this.pointsLabel.setPrefSize(100, 50);
        this.territoryLabel.setPrefSize(100, 50);
        this.playerLabel.setStyle("-fx-font-size: 24px");
        this.pointsLabel.setStyle("-fx-font-size: 24px");
        this.territoryLabel.setStyle("-fx-font-size: 24px");
        buttonBox.getChildren().addAll(playerLabel, pointsLabel, territoryLabel);
        buttonBox.setSpacing(30);
        buttonBox.setPadding(new Insets(30, 30, 30, 30));
        this.passButton = new Button("Pass");
        this.resignButton = new Button("Resign");
        passButton.setPrefSize(100, 50);
        resignButton.setPrefSize(100, 50);
        buttonBox.getChildren().addAll(passButton, resignButton);
        getChildren().add(buttonBox);
    }

    public GoBoard getBoard() {
        return board;
    }

    public void setBoard(GoBoard board) {
        this.board = board;
    }

    public Button getPassButton() {
        return passButton;
    }

    public void setPassButton(Button passButton) {
        this.passButton = passButton;
    }

    public Button getResignButton() {
        return resignButton;
    }

    public void setResignButton(Button resignButton) {
        this.resignButton = resignButton;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
        this.playerLabel.setText("Player: " + (this.playerNumber==1?"Black":"White"));
    }

    public Label getPlayerLabel() {
        return playerLabel;
    }

    public void setPlayerLabel(Label playerLabel) {
        this.playerLabel = playerLabel;
    }

    public Label getPointsLabel() {
        return pointsLabel;
    }

    public void setPointsLabel(Label pointsLabel) {
        this.pointsLabel = pointsLabel;
    }

    public Label getTerritoryLabel() {
        return territoryLabel;
    }

    public void setTerritoryLabel(Label territoryLabel) {
        this.territoryLabel = territoryLabel;
    }
}
