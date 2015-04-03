package pt.up.fe.ia.a4_2;

public class Board {

    public enum PieceType {
        Player,
        Floor,
        Wall,
        Box,
        IceBox,
        Exit
    }

    public enum Direction {
        Up,
        Down,
        Left,
        Right
    }

    private int heuristicG = 0;

    private PieceType[][] boardRepresentation;

    public Board(PieceType[][] boardRep) {
        boardRepresentation = boardRep;
    }

    public boolean movePlayer(Direction d) {
        if (false /* TODO: Move */) {
            heuristicG++;

            return true;
        }

        return false;
    }

    public int getHeuristicG() {
        return heuristicG;
    }

    public int getHeuristicH() {
        //  TODO: Calculate

        return -1;
    }

    public int getHeuristicF() {
        return getHeuristicG() + getHeuristicH();
    }
}
