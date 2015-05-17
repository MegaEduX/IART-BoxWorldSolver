package pt.up.fe.ia.a4_2.cli;

/*

->  [A-Star]    Completed in 0.018561392 seconds.
->  [DFS]       Completed in 0.001941777 seconds.

-> Intel(R) Core(TM) i7-3770 CPU @ 3.40GHz

XXXXXXXX
XXXXXXXX
XP____HE
XX__I__X
XXX____X
XXXXXXXX
XXXXXXXX
XXXXXXXX

->  [A-Star]    Completed in 0.105011276 seconds.
->  [DFS]       Completed in 0.091899736 seconds.

-> Intel(R) Core(TM) i7-3770 CPU @ 3.40GHz

XXXXXXXXXXXX
XXXXXXXXXXXX
XXXXXXXXXXXX
XXX______H_E
XX________XX
XP_______BXX
X________H_X
X________B_X
XX_________X
XXXXXXXXXXXX
XXXXXXXXXXXX
XXXXXXXXXXXX

->  [A-Star]    Completed in 14.194481196 seconds.
->  [DFS]       Completed in 0.132446333 seconds.

-> Intel(R) Core(TM) i7-3770 CPU @ 3.40GHz

XXXXXXXXXXXX
XXXXXXXXXXXX
XXXXXXXXXXXX
XXX_______HE
XX_________X
XP___B____XX
X_____H__H_X
X____I___B_X
XX_________X
XXXXXXXXXXXX
XXXXXXXXXXXX
XXXXXXXXXXXX

-> Too long?

XXXXXXXXXX
X_BHHHHHHE
X__BHHHHHX
XH__BHHHHX
XHH__BHHHX
XHHH__BXHX
X__HH__BHX
XBBBBB__BX
XPBHHHH__X
XXXXXXXXXX

*/

import pt.up.fe.ia.a4_2.algorithms.AStar;
import pt.up.fe.ia.a4_2.algorithms.DFS;
import pt.up.fe.ia.a4_2.algorithms.Solver;
import pt.up.fe.ia.a4_2.io.Reader;
import pt.up.fe.ia.a4_2.logic.Board;
import pt.up.fe.ia.a4_2.utils.Node;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Board.PieceType[][] pt;

        while (true) {
            try {
                System.out.print("Path to level file: ");

                Reader rd = new Reader(in.nextLine());

                pt = rd.read();

                break;
            } catch (Exception e) {
                System.out.println("An error has occurred: " + e.getMessage());
                System.out.println("");
            }
        }

        try {
            Board b = new Board(pt);

            solve(new DFS(b));
            solve(new AStar(b));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void solve(Solver s) {
        long startTime = System.nanoTime();

        Node result = s.solve();

        if (result == null) {
            System.out.println("No solution was found.");

            System.exit(1);
        }

        ArrayList<Node> fp = s.getGraph().getPathToNode(result);

        long endTime = System.nanoTime();

        long duration = (endTime - startTime);

        System.out.println("[" + s.getName() + "] Completed in " + duration / 1000000000.0 + " seconds.");

        System.out.println("");

        for (int i = 0; i < fp.size(); i++) {
            Board cb = (Board) fp.get(i).getValue();

            System.out.println("State " + i + " (Heuristic: " + cb.getHeuristicF() + "):");
            System.out.println("");
            System.out.println(cb.toString());
            System.out.println("");
        }
    }
}
