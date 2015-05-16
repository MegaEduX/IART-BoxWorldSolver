package pt.up.fe.ia.a4_2;

/*

!!! Working Board !!!

XXXXXX
XP___E
XXXXXX
XXXXXX
XXXXXX
XXXXXX

Target Board

XXXXXXXXXXXX
XXX_______HE
XX________XX
XP___B____XX
X_____H__H_X
X____I___B_X
XX_________X
XXXXXXXXXXXX

 */

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Reader rd = new Reader("/Users/MegaEduX/easy.bwlevel");

        try {
            Board.PieceType[][] pt = rd.read();

            Board b = new Board(pt);

            Solver s = new Solver(b);

            Node result = s.solve();

            ArrayList<Node> fp = s.getGraph().getPathToNode(result);

            System.out.println("Found Exit!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
