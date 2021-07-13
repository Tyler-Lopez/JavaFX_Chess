package sample;

import java.util.ArrayList;

public class King extends ChessPiece {
    private byte[][] prospectiveMoves = new byte[][]{{0,1},{1,0},{-1,0},{0,-1},{1,1},{-1,-1},{-1,1},{1,-1}};

    public King(String side) {
        super(side);
        super.setName("King");
        super.setSymbol('K');
    }
    // Provided a String return a HashSet<String> of other acceptable positions
    public ArrayList<String> getAcceptableMovements(String current) {
        // Create a HashSet<String> which acceptable movements will be added to.
        ArrayList<String> toReturn = new ArrayList<>();
        // Convert the provided String to a char[]
        char[] activePosition = current.toCharArray();

        for(byte i = 0; i < prospectiveMoves.length; i++) {
            char[] tmp = new char[] {0,0};
            tmp[0] = (char)(prospectiveMoves[i][0] + activePosition[0]);
            tmp[1] = (char)(prospectiveMoves[i][1] + activePosition[1]);
            if (isValidMove(tmp, getSide())) {
                toReturn.add(tmp[0]+""+tmp[1]);
            }
        }
        return toReturn;
    }
}
