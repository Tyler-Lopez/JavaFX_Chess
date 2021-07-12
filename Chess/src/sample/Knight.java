package sample;

import java.util.HashSet;

public class Knight extends ChessPiece {
    public Knight(String side) {
        super.setSide(side);
        super.setName("Knight");
        super.setSymbol('N');
    }

    public HashSet<String> getAcceptableMovements(String current) {
        HashSet<String> toReturn = new HashSet<>();
        byte[] position = stringToByteArr(current);

        return toReturn;
    }


}
