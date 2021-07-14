package sample;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.FileNotFoundException;

public class ViewManager {
    private StackPane mainPane; // A Pane is a container class.
    private Scene mainScene;
    private Stage mainStage;

    private static final double HEIGHT = 1000;
    private static final double WIDTH = 1000;
    int count = 0;

    public ViewManager() throws FileNotFoundException {
        mainPane = new StackPane();
        Stop[] stopsLight = new Stop[]{new Stop(0, Color.web("#5d472c")), new Stop(1, Color.web("#4c3e2c"))};
        LinearGradient lightBG = new LinearGradient(1, 1, 1, 0, true, CycleMethod.NO_CYCLE, stopsLight);
        mainPane.setBackground(new Background(new BackgroundFill(lightBG, CornerRadii.EMPTY, Insets.EMPTY)));
        mainPane.setPrefSize(HEIGHT, WIDTH);
        mainStage = new Stage();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);

        mainStage.setScene(mainScene);

        // Initialize the board with pieces in all positions.
        Node chessboard = ChessBoard.createBoard(HEIGHT, 0 ,0);
        mainPane.getChildren().add(chessboard);
        mainPane.getChildren().add(ChessBoard.placePieces(HEIGHT,0,0));
     //   createButtons();
     //   createBoard();
    }

    public Stage getMainStage() {
    return mainStage;
    }
    private void createBoard() {
        int initialX = 200;
        int initialY = 200;
        int[][] chessBoard = new int[8][8];
        double boardSize = 300;
        for(int i = 0; i < chessBoard.length; i ++) {
            for(int j = 0; j < chessBoard[i].length; j++) {
                Rectangle rect = new Rectangle();
                double tileSize = boardSize/chessBoard.length;
                rect.setHeight(tileSize);
                rect.setWidth(tileSize);
                Paint a = Paint.valueOf("white");
                if(i%2==0) {
                    if(j%2==0) rect.setFill(a);
                } else if(j%2!=0) rect.setFill(a);
                rect.setLayoutX(initialX + (j * tileSize));
                rect.setLayoutY(initialY + (i * tileSize));
                mainPane.getChildren().add(rect);
                rect.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        rect.setFill(Paint.valueOf("grey"));
                    }
                });
                int finalJ = j;
                int finalI = i;
                rect.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if (finalI % 2 == 0) {
                            if (finalJ % 2 == 0) rect.setFill(a);
                            else rect.setFill(Paint.valueOf("BLACK"));
                        } else {
                            if (finalJ % 2 == 0) rect.setFill(Paint.valueOf("BLACK"));
                            else rect.setFill(a);
                        }
                    }
                });

            }
        }

    }
    private void createButtons() {
        Button button = new Button();
        button.setMinHeight(100);
        button.setMinWidth(200);
        button.setLayoutX(10);
        mainPane.getChildren().add(button);

        button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode pressed = keyEvent.getCode();
                double tmp;

                switch(pressed) {
                    case S:
                        tmp = button.getLayoutY()+5;
                        button.setLayoutY(tmp);
                        break;
                    case W:
                        tmp = button.getLayoutY()-5;
                        button.setLayoutY(tmp);
                        break;

                    case A:
                        tmp = button.getLayoutX()-5;
                        button.setLayoutX(tmp);
                        break;
                    case D:
                        tmp = button.getLayoutX()+5;
                        button.setLayoutX(tmp);
                        break;

                }
            }
        });

        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // TODO THIS WILL BE CALLED
                count++;
                System.out.println(count);
            }
        });
    }
}

