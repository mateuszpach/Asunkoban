package com.github.mateuszpach.Asunkoban;

import com.github.mateuszpach.Asunkoban.controller.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SceneManager.start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}