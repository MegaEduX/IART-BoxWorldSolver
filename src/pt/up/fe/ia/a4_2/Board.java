package pt.up.fe.ia.a4_2;

public class Board {

    private class PieceNotFoundException extends Exception {
        public PieceNotFoundException() {
            super();
        }

        public PieceNotFoundException(String message) {
            super(message);
        }

        public PieceNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }

        public PieceNotFoundException(Throwable cause) {
            super(cause);
        }
    }

    public enum PieceType {
        Player,
        Floor,
        Wall,
        Box,
        IceBox,
        Hole,
        Exit
    }

    public enum Direction {
        Up,
        Down,
        Left,
        Right
    }

    private int heuristicG = -1;

    private PieceType[][] boardRepresentation = null;

    private Coordinate currentPlayerPosition = null;

    public Board(PieceType[][] boardRep) throws PieceNotFoundException {
        boardRepresentation = boardRep;

        currentPlayerPosition = getPieceCoordinate(PieceType.Player);
    }

    public boolean movePlayer(Direction d) {
        switch (d) {
            case Up: {

                Coordinate upCoordinate = new Coordinate(currentPlayerPosition.x, currentPlayerPosition.y - 1);

                PieceType pc = getPieceAtCoordinate(upCoordinate);

                if (pc == PieceType.Floor || pc == PieceType.Exit) {
                    swap(currentPlayerPosition, upCoordinate);

                    heuristicG++;

                    return true;
                } else if (pc == PieceType.Box || pc == PieceType.IceBox) {
                    Coordinate upTwoCoordinate = new Coordinate(upCoordinate.x, upCoordinate.y - 1);

                    PieceType pcUpTwo = getPieceAtCoordinate(upTwoCoordinate);

                    if (pcUpTwo != PieceType.Hole && pcUpTwo != PieceType.Floor)
                        return false;

                    if (pc == PieceType.Box) {
                        swap(upCoordinate, upTwoCoordinate);
                        swap(currentPlayerPosition, upCoordinate);

                        heuristicG++;

                        return true;
                    } else {
                        //  Move it all the way up.
                        //  Move the player one piece.

                        heuristicG++;

                        return true;
                    }
                }

                break;
            }

            case Down:
                break;
            case Left:
                break;
            case Right:
                break;
            default:
                break;
        }

        return false;
    }

    public int getHeuristicG() {
        return heuristicG;
    }

    public double getHeuristicH() {
        //  TODO: Calculate

        try {
            Coordinate playerCoordinate = getPieceCoordinate(PieceType.Player);

            Coordinate exitCoordinate = getPieceCoordinate(PieceType.Exit);

            return Coordinate.distance(playerCoordinate, exitCoordinate);
        } catch (PieceNotFoundException e) {

        }

        return -1;
    }

    public double getHeuristicF() {
        return getHeuristicG() + getHeuristicH();
    }

    private void swap(Coordinate c1, Coordinate c2) {
        PieceType holder = boardRepresentation[c1.y][c1.x];

        boardRepresentation[c1.y][c1.x] = boardRepresentation[c2.y][c2.x];
        boardRepresentation[c2.x][c2.y] = holder;
    }

    private PieceType getPieceAtCoordinate(Coordinate c) {
        return boardRepresentation[c.y][c.x];
    }

    private Coordinate getPieceCoordinate(PieceType pc) throws PieceNotFoundException {
        for (int y = 0; y < boardRepresentation.length; y++)
            for (int x = 0; x < boardRepresentation[y].length; x++)
                if (boardRepresentation[y][x] == pc)
                    return new Coordinate(x, y);

        throw new PieceNotFoundException();
    }
}
