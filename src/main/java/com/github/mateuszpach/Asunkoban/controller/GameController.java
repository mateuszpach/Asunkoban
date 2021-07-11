package com.github.mateuszpach.Asunkoban.controller;

import com.github.mateuszpach.Asunkoban.model.Game;
import com.github.mateuszpach.Asunkoban.model.Level;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class GameController {

    private static GameController gameController;

    public BorderPane root;
    public GridPane gridPane;
    public StackPane gridPaneBox;
    private ArrayList<ArrayList<Pane>> squares;
    private Level level;

    public static void registerKeyHandlers(Scene gameScene) {
        gameScene.setOnKeyPressed(e -> {
            Game.Direction direction = null;
            switch (e.getCode()) {
                case UP:
                    direction = Game.Direction.LEFT;
                    break;
                case RIGHT:
                    direction = Game.Direction.DOWN;
                    break;
                case DOWN:
                    direction = Game.Direction.RIGHT;
                    break;
                case LEFT:
                    direction = Game.Direction.UP;
                    break;
                case ESCAPE:
                    SceneManager.changeScene(SceneManager.SceneType.EXIT);
                    break;
            }
            if (direction != null) {
                ArrayList<Game.Update> updates = Game.move(direction);
                for (Game.Update update : updates) {
                    ObservableList<String> style = gameController.squares.get(update.prev.x).get(update.prev.y).
                            getChildren().get(0).getStyleClass();
                    gameController.squares.get(update.curr.x).get(update.curr.y).
                            getChildren().get(0).getStyleClass().addAll(style);
                    gameController.squares.get(update.prev.x).get(update.prev.y).
                            getChildren().get(0).getStyleClass().clear();
                }
                if (Game.is_finished()) {
                    SceneManager.changeScene(SceneManager.SceneType.WIN);
                }
            }
        });
    }

    public void drawBoard() {
        level = Game.getLevel();

        NumberBinding binding = Bindings.min(root.widthProperty(), root.heightProperty());
        gridPaneBox.minHeightProperty().bind(binding);
        gridPaneBox.maxHeightProperty().bind(binding);
        gridPane.minWidthProperty().bind(gridPane.heightProperty());
        gridPane.maxWidthProperty().bind(gridPane.heightProperty());

        gridPane.getChildren().clear();
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        for (int i = 0; i < level.board.size(); i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100d / level.boardSize);
            gridPane.getColumnConstraints().add(columnConstraints);
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100d / level.boardSize);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        squares = new ArrayList<>();

        int rowI = 0;
        int colI;
        for (ArrayList<Level.Field> row : level.board) {
            colI = 0;
            squares.add(new ArrayList<>());
            for (Level.Field field : row) {
                Pane square = new AnchorPane();
                Pane squareIn = new AnchorPane();
                AnchorPane.setTopAnchor(squareIn, 0.0);
                AnchorPane.setRightAnchor(squareIn, 0.0);
                AnchorPane.setBottomAnchor(squareIn, 0.0);
                AnchorPane.setLeftAnchor(squareIn, 0.0);
                square.getChildren().add(squareIn);

                squares.get(rowI).add(square);


                square.getStyleClass().add("square");

                if (field.type == Level.Field.FieldType.WALL) {
                    square.getStyleClass().add("wall");
                }

                if (field.type == Level.Field.FieldType.TARGET) {
                    square.getStyleClass().add("roll-gray");
                }

                if (field.hasBox) {
                    squareIn.getStyleClass().add("roll");
                }

                square.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);

                gridPane.add(square, colI, rowI);
                colI++;
            }
            rowI++;
        }

        squares.get(level.playerPos.x).get(level.playerPos.y).getChildren().get(0).getStyleClass().add("asuna");
    }

    public void initialize() {
        gameController = this;
    }

    public static void initNewGame() {
        gameController.drawBoard();
    }

}