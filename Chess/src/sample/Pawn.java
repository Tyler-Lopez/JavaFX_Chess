package sample;

public class Pawn extends ChessPiece {
    public Pawn(String side) {
        super(side);
        super.setName("Pawn");
        super.setSymbol('P');
    }
}
