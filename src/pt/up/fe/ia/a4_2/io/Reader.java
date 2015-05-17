package pt.up.fe.ia.a4_2.io;

import pt.up.fe.ia.a4_2.logic.Board;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reader {
    private String filePath;

    public Reader(String fp) {
        filePath = fp;
    }

    public Board.PieceType[][] read() throws IOException {
        ArrayList<ArrayList<Board.PieceType>> fullBoard = new ArrayList<ArrayList<Board.PieceType>>();

        BufferedReader br = new BufferedReader(new FileReader(filePath));

        for (String line; (line = br.readLine()) != null; ) {
            ArrayList<Board.PieceType> pieces = new ArrayList<Board.PieceType>();

            for (char c : line.toCharArray()) {
                switch (c) {
                    case 'P':
                        pieces.add(Board.PieceType.Player);

                        break;

                    case '_':
                        pieces.add(Board.PieceType.Floor);

                        break;

                    case 'X':
                        pieces.add(Board.PieceType.Wall);

                        break;

                    case 'B':
                        pieces.add(Board.PieceType.Box);

                        break;

                    case 'I':
                        pieces.add(Board.PieceType.IceBox);

                        break;

                    case 'H':
                        pieces.add(Board.PieceType.Hole);

                        break;

                    case 'E':
                        pieces.add(Board.PieceType.Exit);

                        break;

                    default:
                        //  Ignore for now...

                        break;

                }
            }

            fullBoard.add(pieces);
        }

        int i = 0;

        Board.PieceType ret[][] = new Board.PieceType[fullBoard.size()][];

        for (ArrayList<Board.PieceType> line : fullBoard) {
            Board.PieceType interm[] = new Board.PieceType[line.size()];

            int j = 0;

            for (Board.PieceType pt : line)
                interm[j++] = pt;

            ret[i++] = interm;
        }

        return ret;
    }
}
