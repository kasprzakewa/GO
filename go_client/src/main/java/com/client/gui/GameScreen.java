package com.client.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameScreen extends HBox {

    private GoBoard board;
    private Button passButton;
    private Button resignButton;
    private int playerNumber;
    private Label playerLabel;
    private Label pointsLabel;
    private Label territoryLabel;
    private Label turnLabel;
    private EndGameDialog popup;
    private StartScreen startScreen;
    private Stage stage;
    private Button pvpButton;
    private Button botButton;
    private Button dataBaseButton;

    public Label getTurnLabel() {
        return turnLabel;
    }

    public void setTurnLabel(Label turnLabel) {
        this.turnLabel = turnLabel;
    }

    public GameScreen(GoBoard board, int playerNumber, StartScreen startScreen, Stage stage) {

        this.board = board;
        this.playerNumber = playerNumber;
        this.startScreen = startScreen;
        this.stage = stage;
        getChildren().add(board);
        setMargin(board, new Insets(30, 0, 30, 50));
        setSpacing(30);

        VBox buttonBox = new VBox();

        this.playerLabel = new Label("Player: " + (this.playerNumber==1?"Black":"White"));
        this.pointsLabel = new Label("Points: 0");
        this.turnLabel = new Label();
        this.territoryLabel = new Label("Territory: 0");

        this.turnLabel.setPrefSize(200, 50);
        this.playerLabel.setPrefSize(200, 50);
        this.pointsLabel.setPrefSize(200, 200);
        this.territoryLabel.setPrefSize(200, 200);

        this.playerLabel.setStyle("-fx-font-size: 15px");
        this.pointsLabel.setStyle("-fx-font-size: 15px");
        this.territoryLabel.setStyle("-fx-font-size: 15px");
        this.turnLabel.setStyle("-fx-font-size: 15px");

        buttonBox.getChildren().addAll(playerLabel, pointsLabel, territoryLabel, turnLabel);
        buttonBox.setSpacing(30);

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

    public EndGameDialog getPopup() {
        return popup;
    }

    public void setPopup(EndGameDialog popup) {
        this.popup = popup;
    }
    
    public void goBackToMenu(){
        
        stage.hide();
        stage.setScene(startScreen.getScene());
        stage.setMaximized(true);
        stage.show();
    }

    public void setStartScreen(StartScreen startScreen) {
        this.startScreen = startScreen;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
