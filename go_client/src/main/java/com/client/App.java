package com.client;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import com.client.gui.GoGUI;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        new GoGUI(stage);
    }

    public static void main(String[] args){
        launch();
    }
}