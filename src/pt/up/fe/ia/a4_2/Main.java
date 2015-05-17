package pt.up.fe.ia.a4_2;

/*

-> Before Optimization: Completed in 0.05354203 seconds.
-> After Optimization: Completed in 0.022481025 seconds.
-> (~250% speed up)

-> Intel(R) Core(TM) i7-3770 CPU @ 3.40GHz

XXXXXXXX
XXXXXXXX
XP____HE
XX__I__X
XXX____X
XXXXXXXX
XXXXXXXX
XXXXXXXX

-> Before Optimization: Completed in 33.935145387 seconds.
-> After Optimization: Completed in 0.098680506 seconds.
-> (~35000% speed up)

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

-> Completed in 14.733648867 seconds.

-> Intel(R) Core(TM) i7-3770 CPU @ 3.40GHz

XXXXXXXXXXXX
XXXXXXXXXXXX
XXXXXXXXXXXX
XXX_______HE
XX________XX
XP___B____XX
X_____H__H_X
X____I___B_X
XX_________X
XXXXXXXXXXXX
XXXXXXXXXXXX
XXXXXXXXXXXX

*/

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
                System.out.println("An error has occoured: " + e.getMessage());
                System.out.println("");
            }
        }

        try {
            long startTime = System.nanoTime();

            Board b = new Board(pt);

            Solver s = new Solver(b);

            Node result = s.solve();

            ArrayList<Node> fp = s.getGraph().getPathToNode(result);

            long endTime = System.nanoTime();

            long duration = (endTime - startTime);

            System.out.println("Completed in " + duration / 1000000000.0 + " seconds.");

            System.out.println("");

            for (int i = 0; i < fp.size(); i++) {
                Board cb = (Board) fp.get(i).getValue();

                System.out.println("State " + i + " (Heuristic: " + cb.getHeuristicF() + "):");
                System.out.println("");
                System.out.println(cb.toString());
                System.out.println("");
            }

            System.out.println("Found Exit!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
