package com.github.mateuszpach.Asunkoban.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static Parent introRoot;
    private static Parent gameRoot;
    private static Parent winRoot;
    private static Parent exitRoot;
    private static Stage stage;

    public enum SceneType {INTRO, GAME, WIN, EXIT}

    public static void start(Stage stage) throws IOException {
        SceneManager.stage = stage;

        introRoot = FXMLLoader.load(SceneManager.class.getResource("/view/intro.fxml"));
        gameRoot = FXMLLoader.load(SceneManager.class.getResource("/view/game.fxml"));
        winRoot = FXMLLoader.load(SceneManager.class.getResource("/view/win.fxml"));
        exitRoot = FXMLLoader.load(SceneManager.class.getResource("/view/exit.fxml"));
        Scene scene = new Scene(introRoot, 1200, 800);

        GameController.registerKeyHandlers(scene);

        stage.setTitle("Asunkoban");
        stage.setScene(scene);
        stage.setMinWidth(1100);
        stage.setMinHeight(800);
        stage.getIcons().add(new Image("/img/asuna124.png"));
        stage.show();
    }

    public static void changeScene(SceneType sceneType) {
        switch (sceneType) {
            case INTRO:
                stage.getScene().setRoot(introRoot);
                break;
            case GAME:
                GameController.initNewGame();
                stage.getScene().setRoot(gameRoot);
                break;
            case WIN:
                stage.getScene().setRoot(winRoot);
                break;
            case EXIT:
                stage.getScene().setRoot(exitRoot);
        }
    }
}
