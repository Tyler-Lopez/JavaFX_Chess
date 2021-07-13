package sample;

import java.util.ArrayList;

public class Rook extends ChessPiece {
    public Rook(String side) {
        super(side);
        super.setName("Rook");
        super.setSymbol('R');
    }

    // Provided a String return a HashSet<String> of other acceptable positions
    public ArrayList<String> getAcceptableMovements(String current) {
        // Create a HashSet<String> which acceptable movements will be added to.
        ArrayList<String> toReturn = new ArrayList<>();
        // Convert the provided String to a char[]
        char[] activePosition = current.toCharArray();
        // There are four directions the Rook may possible travel. Iterate through each of them.
        byte[][] rookMoves = new byte[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (byte i = 0; i < 4; i++) {
            // The most the rook could possible travel is 7 squares.
            for (byte j = 1; j < 8; j++) {
                char[] tmp = new char[]{0, 0};
                tmp[0] = (char) ((rookMoves[i][0] * j) + activePosition[0]);
                tmp[1] = (char) ((rookMoves[i][1] * j)  + activePosition[1]);

                if (isValidMove(tmp, getSide())) {
                    toReturn.add(tmp[0] + "" + tmp[1]);
                    if(isEnemyPieceHere(tmp, getSide())) break;
                } else break;
            }
        }
        return toReturn;
    }
}
