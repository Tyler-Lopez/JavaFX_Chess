package sample;

import java.util.Dictionary;
import java.util.Hashtable;

public class ChessPiece {
    private String side;
    private String name;
    private char symbol;

    public ChessPiece() {

    }
    public ChessPiece(String side) {

    }
    public void setSide(String side) {
        this.side = side;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
    public char getSymbol() {
        return symbol;
    }
    public String getName() {
        return name;
    }
    public String getSide() {
     return side;
    }

    private static Dictionary<String, ChessPiece> piecePositions = new Hashtable<>();

    public static Dictionary<String, ChessPiece> getPiecePositions() {
        return piecePositions;
    }

    public static void setBoard() {
        piecePositions.put("A8", new Rook("black"));
        piecePositions.put("B8", new Knight("black"));
        piecePositions.put("C8", new Bishop("black"));
        piecePositions.put("D8", new Queen("black"));
        piecePositions.put("E8", new King("black"));
        piecePositions.put("F8", new Bishop("black"));
        piecePositions.put("G8", new Knight("black"));
        piecePositions.put("H8", new Rook("black"));
        piecePositions.put("A1", new Rook("white"));
        piecePositions.put("B1", new Knight("white"));
        piecePositions.put("C1", new Bishop("white"));
        piecePositions.put("D1", new Queen("white"));
        piecePositions.put("E1", new King("white"));
        piecePositions.put("F1", new Bishop("white"));
        piecePositions.put("G1", new Knight("white"));
        piecePositions.put("H1", new Rook("white"));
        for(byte i = 0; i < 8; i++) {
            char letter = (char)('A' + (char)i);
            piecePositions.put(letter + "7", new Pawn("black"));
            piecePositions.put(letter + "2", new Pawn("whites"));
        }
    }
    protected static String byteArrToString(byte[] arr) {
        char letter = (char)('A' + (char)arr[0]);
        return letter + "" + arr[1];
    }
    protected static byte[] stringToByteArr(String s) {
        char[] cArr = s.toCharArray();
        return new byte[] { Byte.valueOf(String.valueOf(cArr[0])), Byte.valueOf(String.valueOf(cArr[1])) };
    }
    protected static boolean isValidMove(byte[] newPosition, String side) {
        if(newPosition[0] < 0 || newPosition[0] > 7 || newPosition[1] < 0 || newPosition[1] > 7) {
            return false;
        }
        if(piecePositions.get(byteArrToString(newPosition)) != null) {
            ChessPiece pieceOnLocation = piecePositions.get(byteArrToString(newPosition));
            return pieceOnLocation.getSide() == side ? false : true;
        }
        return true;
    }
}
