package sample;

import java.util.ArrayList;
import java.util.HashSet;

public class Knight extends ChessPiece {
    // [0] = x movement, [1] = y movement
    private byte[][] prospectiveMoves = new byte[][]{{1,2},{-1,2},{2,1},{2,-1},{1,-2},{-1,-2},{-2,1},{-2,-1}};

    public Knight(String side) {
        super.setSide(side);
        super.setName("Knight");
        super.setSymbol('N');
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
