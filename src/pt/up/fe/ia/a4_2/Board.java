package pt.up.fe.ia.a4_2;

import java.util.Arrays;

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

    private int heuristicG = 0;

    private boolean isSolution = false;

    private PieceType[][] boardRepresentation = null;

    private Coordinate currentPlayerPosition = null;

    public Board(PieceType[][] boardRep) throws PieceNotFoundException {
        boardRepresentation = boardRep;

        currentPlayerPosition = getPieceCoordinate(PieceType.Player);
    }

    private void updatePlayerPosition() {
        try {
            currentPlayerPosition = getPieceCoordinate(PieceType.Player);
        } catch (PieceNotFoundException e) {
            //  At this point, this can not happen.
        }
    }

    public Board copy() {
        try {
            PieceType[][] brCpy = new PieceType[boardRepresentation.length][];

            int i = 0;

            for (PieceType[] inner : boardRepresentation) {
                PieceType[] innerCopy = Arrays.copyOf(inner, inner.length);

                brCpy[i++] = innerCopy;
            }

            Board ret = new Board(brCpy);

            ret.setHeuristicG(heuristicG);

            return ret;

        } catch (PieceNotFoundException e) {
            //  This can never happen, honestly.

            return null;
        }
    }

    @Override public String toString() {
        String s = "";

        for (PieceType[] row : boardRepresentation) {
            for (PieceType p : row) {
                switch (p) {
                    case Player:

                        s += "P";

                        break;

                    case Floor:

                        s += "_";

                        break;

                    case Wall:

                        s += "X";

                        break;

                    case Box:

                        s += "B";

                        break;

                    case IceBox:

                        s += "I";

                        break;

                    case Hole:

                        s += "H";

                        break;

                    case Exit:

                        s += "E";

                        break;
                }
            }

            s += "\n";
        }

        return s;
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

                    if (pc == PieceType.Exit) {
                        isSolution = true;

                        setPieceAtCoordinate(PieceType.Floor, currentPlayerPosition);
                    }

                    updatePlayerPosition();

                    return true;
                } else if (pc == PieceType.Box || pc == PieceType.IceBox) {
                    Coordinate upTwoCoordinate = new Coordinate(upCoordinate.x, upCoordinate.y - 1);

                    PieceType pcUpTwo = getPieceAtCoordinate(upTwoCoordinate);

                    if (pcUpTwo != PieceType.Hole && pcUpTwo != PieceType.Floor)
                        return false;

                    if (pc == PieceType.Box) {
                        if (pcUpTwo == PieceType.Floor) {
                            swap(upCoordinate, upTwoCoordinate);
                            swap(currentPlayerPosition, upCoordinate);
                        } else {
                            setPieceAtCoordinate(PieceType.Floor, upCoordinate);
                            setPieceAtCoordinate(PieceType.Floor, upTwoCoordinate);

                            swap(currentPlayerPosition, upCoordinate);
                        }
                    } else {
                        //  Move it all the way up.
                        //  Move the player one piece.

                        for (int i = 1; ; i++) {
                            PieceType cpc = getPieceAtCoordinate(new Coordinate(upCoordinate.x, upCoordinate.y - 1));

                            if (cpc == PieceType.Floor)
                                continue;

                            if (cpc == PieceType.Hole) {
                                setPieceAtCoordinate(PieceType.Floor, new Coordinate(upCoordinate.x, upCoordinate.y - i));
                                setPieceAtCoordinate(PieceType.Floor, upCoordinate);

                                swap(currentPlayerPosition, upCoordinate);
                            } else {
                                swap(upCoordinate, new Coordinate(upCoordinate.x, upCoordinate.y - (i + 1)));
                                swap(currentPlayerPosition, upCoordinate);
                            }

                            break;
                        }

                        //  foo

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

                    updatePlayerPosition();

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

                    if (pc == PieceType.Exit) {
                        isSolution = true;

                        setPieceAtCoordinate(PieceType.Floor, currentPlayerPosition);
                    }

                    updatePlayerPosition();

                    return true;
                } else if (pc == PieceType.Box || pc == PieceType.IceBox) {
                    Coordinate downTwoCoordinate = new Coordinate(downCoordinate.x, downCoordinate.y - 1);

                    PieceType pcDownTwo = getPieceAtCoordinate(downTwoCoordinate);

                    if (pcDownTwo != PieceType.Hole && pcDownTwo != PieceType.Floor)
                        return false;

                    if (pc == PieceType.Box) {
                        if (pcDownTwo == PieceType.Floor) {
                            swap(downCoordinate, downTwoCoordinate);
                            swap(currentPlayerPosition, downCoordinate);
                        } else {
                            setPieceAtCoordinate(PieceType.Floor, downCoordinate);
                            setPieceAtCoordinate(PieceType.Floor, downTwoCoordinate);

                            swap(currentPlayerPosition, downCoordinate);
                        }
                    } else {
                        //  Move it all the way down.
                        //  Move the player one piece.

                        for (int i = 1; ; i++) {
                            PieceType cpc = getPieceAtCoordinate(new Coordinate(downCoordinate.x, downCoordinate.y + i));

                            if (cpc == PieceType.Floor)
                                continue;

                            if (cpc == PieceType.Hole) {
                                setPieceAtCoordinate(PieceType.Floor, new Coordinate(downCoordinate.x, downCoordinate.y + i));
                                setPieceAtCoordinate(PieceType.Floor, downCoordinate);

                                swap(currentPlayerPosition, downCoordinate);
                            } else {
                                swap(downCoordinate, new Coordinate(downCoordinate.x, downCoordinate.y + (i - 1)));
                                swap(currentPlayerPosition, downCoordinate);
                            }

                            break;
                        }
                    }

                    heuristicG++;

                    updatePlayerPosition();

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

                    if (pc == PieceType.Exit) {
                        isSolution = true;

                        setPieceAtCoordinate(PieceType.Floor, currentPlayerPosition);
                    }

                    updatePlayerPosition();

                    return true;
                } else if (pc == PieceType.Box || pc == PieceType.IceBox) {
                    Coordinate leftTwoCoordinate = new Coordinate(leftCoordinate.x - 1, leftCoordinate.y);

                    PieceType pcLeftTwo = getPieceAtCoordinate(leftTwoCoordinate);

                    if (pcLeftTwo != PieceType.Hole && pcLeftTwo != PieceType.Floor)
                        return false;

                    if (pc == PieceType.Box) {
                        if (pcLeftTwo == PieceType.Floor) {
                            swap(leftCoordinate, leftTwoCoordinate);
                            swap(currentPlayerPosition, leftCoordinate);
                        } else {
                            setPieceAtCoordinate(PieceType.Floor, leftCoordinate);
                            setPieceAtCoordinate(PieceType.Floor, leftTwoCoordinate);

                            swap(currentPlayerPosition, leftCoordinate);
                        }
                    } else {
                        //  Move it all the way left.
                        //  Move the player one piece.

                        for (int i = 1; ; i++) {
                            PieceType cpc = getPieceAtCoordinate(new Coordinate(leftCoordinate.x - i, leftCoordinate.y));

                            if (cpc == PieceType.Floor)
                                continue;

                            if (cpc == PieceType.Hole) {
                                setPieceAtCoordinate(PieceType.Floor, new Coordinate(leftCoordinate.x - i, leftCoordinate.y));
                                setPieceAtCoordinate(PieceType.Floor, leftCoordinate);

                                swap(currentPlayerPosition, leftCoordinate);
                            } else {
                                swap(leftCoordinate, new Coordinate(leftCoordinate.x - (i + 1), leftCoordinate.y));
                                swap(currentPlayerPosition, leftCoordinate);
                            }

                            break;
                        }
                    }

                    heuristicG++;

                    updatePlayerPosition();

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

                    if (pc == PieceType.Exit) {
                        isSolution = true;

                        setPieceAtCoordinate(PieceType.Floor, currentPlayerPosition);
                    }

                    updatePlayerPosition();

                    return true;
                } else if (pc == PieceType.Box || pc == PieceType.IceBox) {
                    Coordinate rightTwoCoordinate = new Coordinate(rightCoordinate.x + 1, rightCoordinate.y);

                    PieceType pcRightTwo = getPieceAtCoordinate(rightTwoCoordinate);

                    if (pcRightTwo != PieceType.Hole && pcRightTwo != PieceType.Floor)
                        return false;

                    if (pc == PieceType.Box) {
                        if (pcRightTwo == PieceType.Floor) {
                            swap(rightCoordinate, rightTwoCoordinate);
                            swap(currentPlayerPosition, rightCoordinate);
                        } else {
                            setPieceAtCoordinate(PieceType.Floor, rightCoordinate);
                            setPieceAtCoordinate(PieceType.Floor, rightTwoCoordinate);

                            swap(currentPlayerPosition, rightCoordinate);
                        }
                    } else {
                        //  Move it all the way right.
                        //  Move the player one piece.

                        for (int i = 1; ; i++) {
                            PieceType cpc = getPieceAtCoordinate(new Coordinate(rightCoordinate.x + i, rightCoordinate.y));

                            if (cpc == PieceType.Floor)
                                continue;

                            if (cpc == PieceType.Hole) {
                                setPieceAtCoordinate(PieceType.Floor, new Coordinate(rightCoordinate.x + i, rightCoordinate.y));
                                setPieceAtCoordinate(PieceType.Floor, rightCoordinate);

                                swap(currentPlayerPosition, rightCoordinate);
                            } else {
                                swap(rightCoordinate, new Coordinate(rightCoordinate.x + (i - 1), rightCoordinate.y));
                                swap(currentPlayerPosition, rightCoordinate);
                            }

                            break;
                        }
                    }

                    heuristicG++;

                    updatePlayerPosition();

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

            double distance = Coordinate.distance(playerCoordinate, exitCoordinate);

            int boxes = getPieceCount(PieceType.Box);
            int iceBoxes = getPieceCount(PieceType.IceBox);
            int holes = getPieceCount(PieceType.Hole);

            return distance + (boxes + iceBoxes) * 10 + holes * 30;
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
        boardRepresentation[c2.y][c2.x] = holder;
    }

    private PieceType getPieceAtCoordinate(Coordinate c) {
        return boardRepresentation[c.y][c.x];
    }

    private void setPieceAtCoordinate(PieceType pc, Coordinate c) {
        boardRepresentation[c.y][c.x] = pc;
    }

    private Coordinate getPieceCoordinate(PieceType pc) throws PieceNotFoundException {
        for (int y = 0; y < boardRepresentation.length; y++)
            for (int x = 0; x < boardRepresentation[y].length; x++)
                if (boardRepresentation[y][x] == pc)
                    return new Coordinate(x, y);

        throw new PieceNotFoundException();
    }

    private int getPieceCount(PieceType pc) {
        int counter = 0;

        for (int y = 0; y < boardRepresentation.length; y++)
            for (int x = 0; x < boardRepresentation[y].length; x++)
                if (boardRepresentation[y][x] == pc)
                    counter++;

        return counter;
    }
}
