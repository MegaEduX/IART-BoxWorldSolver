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

    private boolean isSolution = false;

    private PieceType[][] boardRepresentation = null;

    private Coordinate currentPlayerPosition = null;

    public Board(PieceType[][] boardRep) throws PieceNotFoundException {
        boardRepresentation = boardRep;

        currentPlayerPosition = getPieceCoordinate(PieceType.Player);
    }

    public Board copy() {
        try {

            Board ret = new Board(boardRepresentation);

            ret.setHeuristicG(heuristicG);

            return ret;

        } catch (PieceNotFoundException e) {
            //  This can never happen, honestly.

            return null;
        }
    }

    public boolean movePlayer(Direction d) {
        switch (d) {
            case Up: {
                if (currentPlayerPosition.y == 0)
                    return false;

                Coordinate upCoordinate = new Coordinate(currentPlayerPosition.x, currentPlayerPosition.y - 1);

                PieceType pc = getPieceAtCoordinate(upCoordinate);

                if (pc == PieceType.Floor || pc == PieceType.Exit) {
                    swap(currentPlayerPosition, upCoordinate);

                    heuristicG++;

                    if (pc == PieceType.Exit)
                        isSolution = true;

                    return true;
                } else if (pc == PieceType.Box || pc == PieceType.IceBox) {
                    Coordinate upTwoCoordinate = new Coordinate(upCoordinate.x, upCoordinate.y - 1);

                    PieceType pcUpTwo = getPieceAtCoordinate(upTwoCoordinate);

                    if (pcUpTwo != PieceType.Hole && pcUpTwo != PieceType.Floor)
                        return false;

                    if (pc == PieceType.Box) {
                        swap(upCoordinate, upTwoCoordinate);
                        swap(currentPlayerPosition, upCoordinate);
                    } else {
                        //  Move it all the way up.
                        //  Move the player one piece.

                        int yToMoveUp = 0;

                        for (int i = 1; ; i++) {
                            PieceType cpc = getPieceAtCoordinate(new Coordinate(upCoordinate.x, upCoordinate.y - i));

                            if (cpc != PieceType.Wall) {
                                if (cpc == PieceType.Hole)
                                    yToMoveUp = i;
                                else if (cpc == PieceType.Box || cpc == PieceType.IceBox)
                                    yToMoveUp = i - 1;

                                break;
                            }
                        }

                        swap(upCoordinate, new Coordinate(upCoordinate.x, upCoordinate.y - yToMoveUp));
                        swap(currentPlayerPosition, upCoordinate);
                    }

                    heuristicG++;

                    return true;
                }

                break;
            }

            case Down: {
                if (currentPlayerPosition.y == boardRepresentation.length - 1)
                    return false;

                Coordinate downCoordinate = new Coordinate(currentPlayerPosition.x, currentPlayerPosition.y + 1);

                PieceType pc = getPieceAtCoordinate(downCoordinate);

                if (pc == PieceType.Floor || pc == PieceType.Exit) {
                    swap(currentPlayerPosition, downCoordinate);

                    heuristicG++;

                    if (pc == PieceType.Exit)
                        isSolution = true;

                    return true;
                } else if (pc == PieceType.Box || pc == PieceType.IceBox) {
                    Coordinate downTwoCoordinate = new Coordinate(downCoordinate.x, downCoordinate.y - 1);

                    PieceType pcDownTwo = getPieceAtCoordinate(downTwoCoordinate);

                    if (pcDownTwo != PieceType.Hole && pcDownTwo != PieceType.Floor)
                        return false;

                    if (pc == PieceType.Box) {
                        swap(downCoordinate, downTwoCoordinate);
                        swap(currentPlayerPosition, downCoordinate);
                    } else {
                        //  Move it all the way down.
                        //  Move the player one piece.

                        int yToMoveDown = 0;

                        for (int i = 1; ; i++) {
                            PieceType cpc = getPieceAtCoordinate(new Coordinate(downCoordinate.x, downCoordinate.y + i));

                            if (cpc != PieceType.Wall) {
                                if (cpc == PieceType.Hole)
                                    yToMoveDown = i;
                                else if (cpc == PieceType.Box || cpc == PieceType.IceBox)
                                    yToMoveDown = i - 1;

                                break;
                            }
                        }

                        swap(downCoordinate, new Coordinate(downCoordinate.x, downCoordinate.y + yToMoveDown));
                        swap(currentPlayerPosition, downCoordinate);
                    }

                    heuristicG++;

                    return true;
                }

                break;
            }

            case Left: {
                if (currentPlayerPosition.x == 0)
                    return false;

                Coordinate leftCoordinate = new Coordinate(currentPlayerPosition.x - 1, currentPlayerPosition.y);

                PieceType pc = getPieceAtCoordinate(leftCoordinate);

                if (pc == PieceType.Floor || pc == PieceType.Exit) {
                    swap(currentPlayerPosition, leftCoordinate);

                    heuristicG++;

                    if (pc == PieceType.Exit)
                        isSolution = true;

                    return true;
                } else if (pc == PieceType.Box || pc == PieceType.IceBox) {
                    Coordinate leftTwoCoordinate = new Coordinate(leftCoordinate.x - 1, leftCoordinate.y);

                    PieceType pcLeftTwo = getPieceAtCoordinate(leftTwoCoordinate);

                    if (pcLeftTwo != PieceType.Hole && pcLeftTwo != PieceType.Floor)
                        return false;

                    if (pc == PieceType.Box) {
                        swap(leftCoordinate, leftTwoCoordinate);
                        swap(currentPlayerPosition, leftCoordinate);
                    } else {
                        //  Move it all the way left.
                        //  Move the player one piece.

                        int xToMoveLeft = 0;

                        for (int i = 1; ; i++) {
                            PieceType cpc = getPieceAtCoordinate(new Coordinate(leftCoordinate.x - i, leftCoordinate.y));

                            if (cpc != PieceType.Wall) {
                                if (cpc == PieceType.Hole)
                                    xToMoveLeft = i;
                                else if (cpc == PieceType.Box || cpc == PieceType.IceBox)
                                    xToMoveLeft = i - 1;

                                break;
                            }
                        }

                        swap(leftCoordinate, new Coordinate(leftCoordinate.x - xToMoveLeft, leftCoordinate.y));
                        swap(currentPlayerPosition, leftCoordinate);
                    }

                    heuristicG++;

                    return true;
                }

                break;
            }

            case Right: {
                if (currentPlayerPosition.x == boardRepresentation.length - 1)
                    return false;

                Coordinate rightCoordinate = new Coordinate(currentPlayerPosition.x + 1, currentPlayerPosition.y);

                PieceType pc = getPieceAtCoordinate(rightCoordinate);

                if (pc == PieceType.Floor || pc == PieceType.Exit) {
                    swap(currentPlayerPosition, rightCoordinate);

                    heuristicG++;

                    if (pc == PieceType.Exit)
                        isSolution = true;

                    return true;
                } else if (pc == PieceType.Box || pc == PieceType.IceBox) {
                    Coordinate rightTwoCoordinate = new Coordinate(rightCoordinate.x + 1, rightCoordinate.y);

                    PieceType pcRightTwo = getPieceAtCoordinate(rightTwoCoordinate);

                    if (pcRightTwo != PieceType.Hole && pcRightTwo != PieceType.Floor)
                        return false;

                    if (pc == PieceType.Box) {
                        swap(rightCoordinate, rightTwoCoordinate);
                        swap(currentPlayerPosition, rightCoordinate);
                    } else {
                        //  Move it all the way right.
                        //  Move the player one piece.

                        int xToMoveRight = 0;

                        for (int i = 1; ; i++) {
                            PieceType cpc = getPieceAtCoordinate(new Coordinate(rightCoordinate.x + i, rightCoordinate.y));

                            if (cpc != PieceType.Wall) {
                                if (cpc == PieceType.Hole)
                                    xToMoveRight = i;
                                else if (cpc == PieceType.Box || cpc == PieceType.IceBox)
                                    xToMoveRight = i - 1;

                                break;
                            }
                        }

                        swap(rightCoordinate, new Coordinate(rightCoordinate.x + xToMoveRight, rightCoordinate.y));
                        swap(currentPlayerPosition, rightCoordinate);
                    }

                    heuristicG++;

                    return true;
                }

                break;
            }
        }

        return false;
    }

    public int getHeuristicG() {
        return heuristicG;
    }

    private void setHeuristicG(int g) {
        heuristicG = g;
    }

    public boolean getIsSolution() {
        return isSolution;
    }

    public double getHeuristicH() {
        //  Maybe also add the number of holes? Boxes?

        try {
            Coordinate playerCoordinate = getPieceCoordinate(PieceType.Player);

            Coordinate exitCoordinate = getPieceCoordinate(PieceType.Exit);

            return Coordinate.distance(playerCoordinate, exitCoordinate);
        } catch (PieceNotFoundException e) {
            //  Can this even happen?
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
