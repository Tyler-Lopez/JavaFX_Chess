package sample;

import java.util.ArrayList;

public class Pawn extends ChessPiece {

    public Pawn(String side) {
        super(side);
        super.setName("Pawn");
        super.setSymbol('P');
    }
    // Provided a String return a HashSet<String> of other acceptable positions
    public ArrayList<String> getAcceptableMovements(String current) {
        ArrayList<String> toReturn = new ArrayList<>();
        char[] activePosition = current.toCharArray();
        if(getSide().equalsIgnoreCase("white")) {
            // Is the pawn in the initial position?
            if(activePosition[1] == '2' && !isAnyPieceHere(new char[] {activePosition[0], (char)(activePosition[1] + 2)}) && !isAnyPieceHere(new char[] {activePosition[0], (char)(activePosition[1] + 1)})) {
                if(isValidMove(new char[] {activePosition[0], (char)(activePosition[1] + 2)},getSide())) toReturn.add(activePosition[0] + "" + (char)(activePosition[1] + 2));
            }
            if(!isAnyPieceHere(new char[] {activePosition[0], (char)(activePosition[1] + 1)})) {
                if(isValidMove(new char[] {activePosition[0], (char)(activePosition[1] + 1)},getSide())) toReturn.add(activePosition[0] + "" + (char)(activePosition[1] + 1));
            }

        } else {
            // Is the pawn in the initial position?
            if(activePosition[1] == '7' && !isAnyPieceHere(new char[] {activePosition[0], (char)(activePosition[1] - 2)}) && !isAnyPieceHere(new char[] {activePosition[0], (char)(activePosition[1] - 1)}) ) {
                if(isValidMove(new char[] {activePosition[0], (char)(activePosition[1] - 2)},getSide())) toReturn.add(activePosition[0] + "" + (char)(activePosition[1] - 2));
            }
            if(!isAnyPieceHere(new char[] {activePosition[0], (char)(activePosition[1] - 1)})) {
                if(isValidMove(new char[] {activePosition[0], (char)(activePosition[1] - 1)},getSide())) toReturn.add(activePosition[0] + "" + (char)(activePosition[1] - 1));
            }
        }
        // THIS DOES NOT YET COVER THE PAWN BEING ABLE TO MOVE DIAGONALLY!!!!




        // Create a HashSet<String> which acceptable movements will be added to.
        // Convert the provided String to a char[]
        return toReturn;
    }
}
