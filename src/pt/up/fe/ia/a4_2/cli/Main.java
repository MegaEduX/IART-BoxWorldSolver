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

import pt.up.fe.ia.a4_2.algorithms.*;

import pt.up.fe.ia.a4_2.io.Reader;
import pt.up.fe.ia.a4_2.logic.Board;
import pt.up.fe.ia.a4_2.utils.BinaryTuple;
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

            BinaryTuple bfsTime = solve(new BFS(b));
            BinaryTuple dfsTime = solve(new DFS(b));
            BinaryTuple aStarTime = solve(new AStar(b));

            System.out.println("[BFS] Time: " + bfsTime.getFirst());
            System.out.println("[BFS] Steps: " + bfsTime.getSecond());

            System.out.println();

            System.out.println("[DFS] Time: " + dfsTime.getFirst());
            System.out.println("[DFS] Steps: " + dfsTime.getSecond());

            System.out.println();

            System.out.println("[A-Star] Time: " + aStarTime.getFirst());
            System.out.println("[A-Star] Steps: " + aStarTime.getSecond());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BinaryTuple<Double, Integer> solve(Solver s) {
        long startTime = System.nanoTime();

        Node result = s.solve();

        if (result == null) {
            System.out.println("No solution was found.");

            System.exit(1);
        }

        ArrayList<Node> fp = s.getGraph().getPathToNode(result);

        long endTime = System.nanoTime();

        long duration = (endTime - startTime);

        System.out.println("Steps for " + s.getName() + "...");

        System.out.println("");

        for (int i = 0; i < fp.size(); i++) {
            Board cb = (Board) fp.get(i).getValue();

            if (s.usesHeuristic())
                System.out.println("State " + i + " (Heuristic: " + cb.getHeuristicF() + "):");
            else
                System.out.println("State " + i + ":");

            System.out.println("");

            System.out.println(cb.toString());
            System.out.println("");
        }

        return new BinaryTuple<Double, Integer>((duration / 1000000000.0), fp.size());
    }
}

/*

State 21 (Heuristic: 22.0):

XXXXXXXXXXXX
XXXXXXXXXXXX
XXXXXXXXXXXX
XXX_______PE
XX________XX
X_________XX
X__________X
X__________X
XX_________X
XXXXXXXXXXXX
XXXXXXXXXXXX
XXXXXXXXXXXX


  - - - - - - - - - - - -
| X X X X X X X X X X X X |
| X X X X X X X X X X X X |
| X X X X X X X X X X X X |
| X X X _ _ _ _ _ _ _ P E |
| X X _ _ _ _ _ _ _ _ X X |
| X _ _ _ _ _ _ _ _ _ X X |
| X__________X
| X__________X
| XX_________X
| X X X X X X X X X X X X |
| X X X X X X X X X X X X |
| X X X X X X X X X X X X |
  - - - - - - - - - - - -



 */
