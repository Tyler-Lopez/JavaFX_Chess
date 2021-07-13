package sample;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashSet;

public class ChessBoard {
    private static final int ROWS = 8;
    private static final int COLUMNS = 8;

    static String[][] chessBoard = new String[ROWS][COLUMNS];

    public static Node createBoard(double side, double x, double y) {
        // Create a group to which all board elements will be added as children of.
        Group board = new Group();
        // Initialize an array which will be used to put all highlighted squares, finally made a child of board at the end of the method.
        Group hLightB = new Group();

        Stop[] stops = new Stop[] { new Stop(0, Color.web("#4B4B4B")), new Stop(1, Color.web("#1B1B1B"))};
        LinearGradient lg1 = new LinearGradient(1, 1, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        Rectangle backDrop = createRectangle(side, x, y, lg1);
        Rectangle foreGround = createRectangle(side * 0.95, x + (side * 0.025), y + (side * 0.025), Paint.valueOf("WHITE"));

        board.getChildren().add(backDrop);
        board.getChildren().add(foreGround);

        double sizeOfEachTile = ((side * 0.95) / chessBoard.length);

        // Initialize the board with pieces in all positions.
        ChessPiece.setBoard();

        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                Paint color = null;
                if (i % 2 == 0) {
                    if (j % 2 == 0) color = Paint.valueOf("WHITE");
                    else color = Paint.valueOf("grey");
                } else {
                    if (j % 2 != 0) color = Paint.valueOf("WHITE");
                    else color = Paint.valueOf("grey");
                }
                Rectangle tile = createRectangle(sizeOfEachTile, x + (side * 0.025) + (i * sizeOfEachTile), x + (side * 0.025) + (j * sizeOfEachTile), color);
                board.getChildren().add(tile);
                // Active position as a String a char[]
                String position = returnActivePosition(new byte[]{(byte) i, (byte) (j + 1)});
                char[] charArr = ChessPiece.stringToCharArr(position);
                ChessPiece pieceAtPosition = ChessPiece.getPiecePositions().get(position);
                // 
                Text text = new Text();
                if (pieceAtPosition != null) {
                    // If the piece is a knight, create a Pink square on all legal moves.
                    if (pieceAtPosition instanceof Knight) {
                        // Cast the piece to a Knight for the sake of keeping track easier
                        Knight knight = (Knight) pieceAtPosition;
                        Button button = new Button();
                        button.setMinWidth(sizeOfEachTile);
                        button.setMinHeight(sizeOfEachTile);
                        button.setLayoutX(x + (side * 0.025) + (i * sizeOfEachTile));
                        button.setLayoutY(x + (side * 0.025) + (j * sizeOfEachTile));
                        board.getChildren().add(button);
                        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                hLightB.getChildren().clear();
                                ArrayList<String> moves = new ArrayList<>();
                                moves = knight.getAcceptableMovements(position);
                                for (String move : moves) {

                                    Paint c = Color.hsb(270, 1.0, 1.0, 0.3);

                                    char[] tmp = ChessPiece.stringToCharArr(move);
                                    double xShift = x + (side * 0.025) + ((tmp[0] - 'A') * sizeOfEachTile);
                                    double yShift = x + (side * 0.025) + ((tmp[1] - '1') * sizeOfEachTile);


                                    Rectangle hLight = createRectangle(sizeOfEachTile, xShift, yShift, c);
                                    hLight.setLayoutX(xShift);
                                    hLight.setLayoutY(yShift);
                                    hLightB.getChildren().add(hLight);
                                }
                            }
                        });

                        // Iterate through each of the returned moves.


                    }
                    // " " + position + " " + charArr[0] + ":" + charArr[1] debug info
                    text.setText("" + pieceAtPosition.getSymbol());
                    text.setFont(new Font("Verdana", 40));
                    text.setX(x + (side * 0.025) + (i * sizeOfEachTile));
                    text.setY(x + (side * 0.025) + (j * sizeOfEachTile) + sizeOfEachTile / 2);
                }



                //   Image w_r_1 = new Image("sample/black_rook.png", sizeOfEachTile, sizeOfEachTile, true, true);
                // ImageView ie = new ImageView(w_r_1);
                // ie.setLayoutX(x + (side * 0.025) + (i * sizeOfEachTile));
                // ie.setLayoutY(x + (side * 0.025) + (j * sizeOfEachTile));

                if (text != null) board.getChildren().add(text);
            }
        }
        // Iterate through each highlighted rectangle in recArr and plonk it on last.
        board.getChildren().add(hLightB);
        return board;
    }

    private static String returnActivePosition(byte[] arr) {
        char letter = (char) ('A' + (char) arr[0]);
        return letter + "" + arr[1];
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
