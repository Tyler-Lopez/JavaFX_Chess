package sample;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ChessBoard {
    private static final int ROWS = 8;
    private static final int COLUMNS = 8;

    static String[][] chessBoard = new String[ROWS][COLUMNS];
    public static HashMap<String, Button> buttonInPosition = new HashMap<>();

    public static Node createBoard(double side, double x, double y) {
        // Create a group to which all board elements will be added as children of.
        Group board = new Group();
        // Initialize an array which will be used to put all highlighted squares, finally made a child of board at the end of the method.
        Group hLightB = new Group();

        Stop[] stops = new Stop[]{new Stop(0, Color.web("#4B4B4B")), new Stop(1, Color.web("#1B1B1B"))};
        LinearGradient lg1 = new LinearGradient(1, 1, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        // Color of light pieces
        Stop[] stopsLight = new Stop[]{new Stop(0, Color.web("#f0d9b7")), new Stop(1, Color.web("#Bea884"))};
        LinearGradient lightBG = new LinearGradient(1, 1, 1, 0, true, CycleMethod.NO_CYCLE, stopsLight);
        // Color of dark pieces
        Stop[] stopsDark = new Stop[]{new Stop(0, Color.web("#b48866")), new Stop(1, Color.web("#977052"))};
        LinearGradient darkBG = new LinearGradient(1, 1, 1, 0, true, CycleMethod.NO_CYCLE, stopsDark);

        // The black gradient border of the chessboard
        Rectangle backDrop = createRectangle(side, x, y, lg1);
        board.getChildren().add(backDrop);

        double sizeOfEachTile = ((side * 0.95) / chessBoard.length);

        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                Paint color = null;
                if (i % 2 == 0) {
                    if (j % 2 == 0) color = lightBG;
                    else color = darkBG;
                } else {
                    if (j % 2 != 0) color = lightBG;
                    else color = darkBG;
                }
                double xShift = x + (side * 0.025) + (i * sizeOfEachTile);
                double yShift = x + (side * 0.025) + (j * sizeOfEachTile);
                Rectangle tile = createRectangle(sizeOfEachTile, xShift, yShift, color);
                Text position = new Text(returnActivePosition(new byte[]{(byte) i, (byte) (j + 1)}));
                position.setLayoutX(xShift + sizeOfEachTile * 0.05);
                position.setLayoutY(yShift + sizeOfEachTile * 0.95);
                position.setFont(new Font("Verdana", 15));
                position.setFill(color == lightBG ? darkBG : lightBG);
                board.getChildren().add(tile);
                board.getChildren().add(position);
            }
        }
        // Iterate through each highlighted rectangle in recArr and plonk it on last.
        board.getChildren().add(hLightB);
        return board;
    }

    public static Group placePieces(double side, double x, double y) {
        Group pieces = new Group();
        Group subpieces = new Group();
        double sizeOfEachTile = ((side * 0.95) / chessBoard.length);

        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[0].length; j++) {
                String position = returnActivePosition(new byte[]{(byte) i, (byte) (j + 1)});
                System.out.println(position);
                ChessPiece pieceAtPosition = ChessPiece.getPiecePositions().get(position);
                if (pieceAtPosition == null) continue;
                // Give the given button the appropriate characteristics for the piece it is.

                pieceAtPosition.CurrentPosition = position;


                Button button = new Button();
                button.setMinWidth(sizeOfEachTile);
                button.setMinHeight(sizeOfEachTile);
                button.setBackground(Background.EMPTY);
                button.setText(pieceAtPosition.getSymbol() + "");
                button.setFont(new Font("Verdana", 20));
                if (pieceAtPosition.getSide().equalsIgnoreCase("white")) button.setTextFill(Paint.valueOf("WHITE"));
                button.setLayoutX(x + (side * 0.025) + (i * sizeOfEachTile));
                button.setLayoutY(x + (side * 0.025) + (j * sizeOfEachTile));
                pieces.getChildren().add(button);
                buttonInPosition.put(position, button);
                button.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        button.setBackground(new Background(new BackgroundFill(Color.hsb(270, 1.0, 1.0, 0.2), CornerRadii.EMPTY, Insets.EMPTY)));
                    }
                });
                button.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        button.setBackground(Background.EMPTY);
                    }
                });
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        ArrayList<String> moves = new ArrayList<>();

                        subpieces.getChildren().clear();
                        Paint c = Color.rgb(255, 236, 57, 0.3);
                        if (pieceAtPosition instanceof Pawn) {
                            Pawn pawnTmp = (Pawn) pieceAtPosition;
                            moves = pawnTmp.getAcceptableMovements(pawnTmp.CurrentPosition);
                        } else if (pieceAtPosition instanceof Knight) {
                            Knight knightTmp = (Knight) pieceAtPosition;
                            moves = knightTmp.getAcceptableMovements(knightTmp.CurrentPosition);
                        } else if (pieceAtPosition instanceof Rook) {
                            Rook knightTmp = (Rook) pieceAtPosition;
                            moves = knightTmp.getAcceptableMovements(knightTmp.CurrentPosition);
                        } else if (pieceAtPosition instanceof Bishop) {
                            Bishop knightTmp = (Bishop) pieceAtPosition;
                            moves = knightTmp.getAcceptableMovements(knightTmp.CurrentPosition);
                        } else if (pieceAtPosition instanceof King) {
                            King knightTmp = (King) pieceAtPosition;
                            moves = knightTmp.getAcceptableMovements(knightTmp.CurrentPosition);
                        } else {
                            Queen knightTmp = (Queen) pieceAtPosition;
                            moves = knightTmp.getAcceptableMovements(knightTmp.CurrentPosition);
                        }
                        ArrayList<String> finalMoves = moves;
                        for (String move : finalMoves) {
                            char[] tmp = ChessPiece.stringToCharArr(move);
                            double xShift = x + (side * 0.025) + ((tmp[0] - 'A') * sizeOfEachTile);
                            double yShift = x + (side * 0.025) + ((tmp[1] - '1') * sizeOfEachTile);
                            Button hLight = new Button();
                            hLight.setMinHeight(sizeOfEachTile);
                            hLight.setMinWidth(sizeOfEachTile);
                            //   hLight.setPrefHeight(sizeOfEachTile);
                            hLight.setLayoutX(xShift);
                            hLight.setLayoutY(yShift);

                            hLight.setBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY)));
                            hLight.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    // DEBUG TEXT
                                    System.out.println("Position:" + pieceAtPosition.CurrentPosition + "move" + move);
                                    // Invoke the static movePiece method of ChessPiece class.
                                    System.out.println(ChessPiece.movePiece(pieceAtPosition.CurrentPosition, move, pieceAtPosition));
                                    System.out.println("JUST AFTER REMOVER");
                              //      pieces.getChildren().remove(buttonInPosition.get(pieceAtPosition.CurrentPosition));
                                   System.out.println(pieces.getChildren().remove(buttonInPosition.get(move)));
                                    buttonInPosition.remove(pieceAtPosition.CurrentPosition);
                                    pieceAtPosition.CurrentPosition = move;
                                    button.setLayoutX(hLight.getLayoutX());
                                    button.setLayoutY(hLight.getLayoutY());
                                    buttonInPosition.put(pieceAtPosition.CurrentPosition, button);

                                    subpieces.getChildren().clear();
                                    System.out.println(move);

                                   // System.out.println(pieces.getChildren().remove(buttonInPosition.get(move)));
                                }
                            });
                            subpieces.getChildren().add(hLight);

                        }
                        //      subpieces.getChildren().add(createRectangle(sizeOfEachTile, x + (side * 0.025) + (finalI * sizeOfEachTile),x + (side * 0.025) + (finalJ * sizeOfEachTile), c));

                    }
                });


            }
        }
        pieces.getChildren().add(subpieces);
        return pieces;
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
