package sample;

import java.util.Dictionary;
import java.util.Hashtable;

public class ChessPiece {
    // Global Variables
    public String CurrentPosition;
    private String Side;
    private String Name;
    private char Symbol;
    private static Dictionary<String, ChessPiece> piecePositions = new Hashtable<>();

    // Constructors
    public ChessPiece(String side) {
        this.Side = side;
    }

    // Getter and Setter Methods
    public void setSide(String side) {
        this.Side = side;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setSymbol(char symbol) {
        this.Symbol = symbol;
    }
    public char getSymbol() {
        return Symbol;
    }

    public String getName() {
        return Name;
    }

    public String getSide() {
        return Side;
    }

    public static Dictionary<String, ChessPiece> getPiecePositions() {
        return piecePositions;
    }

    // Initialize the board with pieces assigned to each side in appropriate positinos
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
        for (byte i = 0; i < 8; i++) {
            char letter = (char) ('A' + (char) i);
            piecePositions.put(letter + "7", new Pawn("black"));
            piecePositions.put(letter + "2", new Pawn("white"));
        }
    }

    public static boolean movePiece(String current, String newPosition, ChessPiece piece) {
        piecePositions.remove(current);

        boolean toReturn = false;
        if(piecePositions.get(newPosition) != null) {
            System.out.println(piecePositions.get(newPosition).getName()+"HAS BEEN TAKEN");
            piecePositions.remove(newPosition);
            toReturn = true;

        }
        piecePositions.put(newPosition, piece);
        return toReturn;
    }

    protected static String charArrToString(char[] arr) {
        return arr[0] + "" + arr[1];
    }

    protected static char[] stringToCharArr(String s) {
        return s.toCharArray();
    }

    protected static boolean isValidMove(char[] newPosition, String side) {
        if (newPosition[0] < 'A' || newPosition[0] > 'H' || newPosition[1] < '1' || newPosition[1] > '8') {
            return false;
        }
        if (piecePositions.get(newPosition[0] + "" + newPosition[1]) != null) {
            ChessPiece pieceOnLocation = piecePositions.get(newPosition[0] + "" + newPosition[1]);
            return pieceOnLocation.getSide().equals(side) ? false : true;
        }
        return true;
    }

    protected static boolean isEnemyPieceHere(char[] newPosition, String side) {
        ChessPiece pieceOnLocation = piecePositions.get(newPosition[0] + "" + newPosition[1]);
        if (pieceOnLocation == null) return false;
        return pieceOnLocation.getSide().equals(side) ? false : true;
    }

    protected static boolean isAnyPieceHere(char[] newPosition) {
        ChessPiece pieceOnLocation = piecePositions.get(newPosition[0] + "" + newPosition[1]);
        if (pieceOnLocation == null) return false;
        return true;
    }
}
