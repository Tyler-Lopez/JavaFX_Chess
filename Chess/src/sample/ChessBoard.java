package sample;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ChessBoard {
    private static final int ROWS = 8;
    private static final int COLUMNS = 8;

    static String[][] chessBoard = new String[ROWS][COLUMNS];

    public static Node createBoard(double side, double x, double y) {

        Group board = new Group();

        Rectangle backDrop = createRectangle(side, x, y, Paint.valueOf("BLACK"));
        Rectangle foreGround = createRectangle(side * 0.95, x + (side * 0.025) , y + (side * 0.025) , Paint.valueOf("WHITE"));

        board.getChildren().add(backDrop);
        board.getChildren().add(foreGround);

        double sizeOfEachTile = ((side * 0.95) / chessBoard.length);

        for(int i = 0; i < chessBoard.length; i++) {
            for(int j = 0; j < chessBoard[i].length; j++) {
                Paint color = null;
                if(i % 2 == 0) {
                    if(j % 2 == 0) color = Paint.valueOf("WHITE");
                    else color = Paint.valueOf("BLACK");
                } else {
                    if(j % 2 != 0) color = Paint.valueOf("WHITE");
                    else color = Paint.valueOf("BLACK");
                }
                Rectangle tile = createRectangle(sizeOfEachTile, x + (side * 0.025) + (i * sizeOfEachTile), x + (side * 0.025) + (j * sizeOfEachTile), color);
                board.getChildren().add(tile);
                if(i == 0 && j == 0) {
                    Text t = new Text(10, 50, "Test");
                    board.getChildren().add(t);

                }
            }
        }




        return board;
    }

    public static Rectangle createRectangle(double side, double x, double y, Paint color) {
        Rectangle backDrop = new Rectangle();
        backDrop.setHeight(side);
        backDrop.setWidth(side);
        backDrop.setLayoutX(x);
        backDrop.setLayoutY(y);
        backDrop.setFill(color);
        return backDrop;
    }

}
