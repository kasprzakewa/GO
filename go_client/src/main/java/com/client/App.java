package com.client;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import com.client.gui.GUI;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        try {
            new GUI(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch();
    }
}