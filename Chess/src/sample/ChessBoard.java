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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ChessBoard {
    private static final int ROWS = 8;
    private static final int COLUMNS = 8;

    static String[][] chessBoard = new String[ROWS][COLUMNS];
    public static HashMap<String, Button> buttonInPosition = new HashMap<>();
    public static HashMap<String, ImageView> imagesInPosition = new HashMap<>();

    public static Node createBoard(double side, double x, double y) throws FileNotFoundException {
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

        Image image = new Image(new FileInputStream("Chess/src/images/wood.png"));
        ImageView imageView = new ImageView(image);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitWidth(side);
        imageView.setFitHeight(side);
        // The black gradient border of the chessboard
       // Rectangle backDrop = createRectangle(side, x, y, lg1);
        board.getChildren().add(imageView);

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
                board.getChildren().add(tile);
                if(i==0) {
                    Text position = new Text(String.valueOf(j + 1));
                    position.setX(xShift + sizeOfEachTile * 0.05);
                    position.setY(yShift + sizeOfEachTile * 0.25);
                    position.setFont(new Font("Verdana", 25));
                    position.setFill(color == lightBG ? darkBG : lightBG);

                    board.getChildren().add(position);
                }
                if(j==7) {
                    Text position2 = new Text(String.valueOf(returnActivePosition(new byte[]{(byte) i, (byte) (j + 1)}).toCharArray()[0]));
                    position2.setX(xShift + sizeOfEachTile * 0.75);
                    position2.setY(yShift + sizeOfEachTile * 0.95);
                    position2.setFont(new Font("Verdana", 25));
                    position2.setFill(color == lightBG ? darkBG : lightBG);
                    board.getChildren().add(position2);
                }
            }
        }
        // Iterate through each highlighted rectangle in recArr and plonk it on last.
        board.getChildren().add(hLightB);
        return board;
    }

    public static Group placePieces(double side, double x, double y) throws FileNotFoundException {
        Group pieces = new Group();
        Group subpieces = new Group();
        double sizeOfEachTile = ((side * 0.95) / chessBoard.length);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String position = returnActivePosition(new byte[]{(byte) i, (byte) (j + 1)});
                System.out.println(position);
                final ChessPiece[] pieceAtPosition = {ChessPiece.getPiecePositions().get(position)};
                if (pieceAtPosition[0] == null) continue;
                // Give the given button the appropriate characteristics for the piece it is.

                pieceAtPosition[0].CurrentPosition = position;


                Button button = new Button();
                button.setMinWidth(sizeOfEachTile);
                button.setMinHeight(sizeOfEachTile);
                button.setBackground(Background.EMPTY);
             //   button.setText(pieceAtPosition.getSymbol() + "");

                button.setFont(new Font("Verdana", 30));

                if (pieceAtPosition[0].getSide().equalsIgnoreCase("white")) button.setTextFill(Paint.valueOf("WHITE"));
                button.setLayoutX(x + (side * 0.025) + (i * sizeOfEachTile));
                button.setLayoutY(x + (side * 0.025) + (j * sizeOfEachTile));
                // Handle Image Generation
                final Image[] image = {new Image(new FileInputStream("Chess/src/images/" + pieceAtPosition[0].getSide() + "_" + pieceAtPosition[0].getSymbol() + ".png"))};
                final ImageView[] imageView = {new ImageView(image[0])};
                //     if (pieceAtPosition instanceof Pawn) {

                //   }
                pieces.getChildren().add(button);
                buttonInPosition.put(position, button);
                System.out.println(sizeOfEachTile);
                double tempDouble = sizeOfEachTile;
                imageView[0].setFitWidth(tempDouble);
                imageView[0].setFitHeight(tempDouble);
                System.out.println(sizeOfEachTile);

                imageView[0].setX(x + (side * 0.025) + (i * sizeOfEachTile));
                imageView[0].setY(x + (side * 0.025) + (j * sizeOfEachTile));
                pieces.getChildren().add(imageView[0]);
                imagesInPosition.put(position, imageView[0]);
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
                        if (pieceAtPosition[0] instanceof Pawn) {
                            Pawn pawnTmp = (Pawn) pieceAtPosition[0];
                            moves = pawnTmp.getAcceptableMovements(pawnTmp.CurrentPosition);
                        } else if (pieceAtPosition[0] instanceof Knight) {
                            Knight knightTmp = (Knight) pieceAtPosition[0];
                            moves = knightTmp.getAcceptableMovements(knightTmp.CurrentPosition);
                        } else if (pieceAtPosition[0] instanceof Rook) {
                            Rook knightTmp = (Rook) pieceAtPosition[0];
                            moves = knightTmp.getAcceptableMovements(knightTmp.CurrentPosition);
                        } else if (pieceAtPosition[0] instanceof Bishop) {
                            Bishop knightTmp = (Bishop) pieceAtPosition[0];
                            moves = knightTmp.getAcceptableMovements(knightTmp.CurrentPosition);
                        } else if (pieceAtPosition[0] instanceof King) {
                            King knightTmp = (King) pieceAtPosition[0];
                            moves = knightTmp.getAcceptableMovements(knightTmp.CurrentPosition);
                        } else {
                            Queen knightTmp = (Queen) pieceAtPosition[0];
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
                                    System.out.println("Position:" + pieceAtPosition[0].CurrentPosition + "move" + move);
                                    // Invoke the static movePiece method of ChessPiece class.
                                    System.out.println(ChessPiece.movePiece(pieceAtPosition[0].CurrentPosition, move, pieceAtPosition[0]));
                                    System.out.println(pieces.getChildren().remove(buttonInPosition.get(move)));
                                    System.out.println(pieces.getChildren().remove(imagesInPosition.get(move)));

                                    buttonInPosition.remove(pieceAtPosition[0].CurrentPosition);

                                    imagesInPosition.remove(pieceAtPosition[0].CurrentPosition);
                                    imagesInPosition.remove(move);
                                    System.out.println(move.toCharArray()[1] + "here");

                                    if(pieceAtPosition[0] instanceof Pawn && (move.toCharArray()[1]=='1' || move.toCharArray()[1]=='8')) {
                                        pieces.getChildren().remove(imageView[0]);
                                        System.out.println("HERE");
                                        ChessPiece.promotePawn(move, pieceAtPosition[0].getSide());
                                        pieceAtPosition[0] = ChessPiece.getPiecePositions().get(move);
                                        try {
                                            imageView[0] = new ImageView(new Image(new FileInputStream("Chess/src/images/"+ pieceAtPosition[0].getSide()+"_Q.png")));
                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                        imageView[0].setX(xShift);
                                        imageView[0].setY(yShift);
                                        imageView[0].setFitHeight(sizeOfEachTile);
                                        imageView[0].setFitWidth(sizeOfEachTile);
                                        pieces.getChildren().add(imageView[0]);

                                    }
                                        pieceAtPosition[0].CurrentPosition = move;
                                        imageView[0].setX(xShift);
                                        imageView[0].setY(yShift);
                                        //   pieces.getChildren().add(image);
                                        button.setLayoutX(hLight.getLayoutX());
                                        button.setLayoutY(hLight.getLayoutY());
                                        imagesInPosition.put(pieceAtPosition[0].CurrentPosition, imageView[0]);
                                        buttonInPosition.put(pieceAtPosition[0].CurrentPosition, button);

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
