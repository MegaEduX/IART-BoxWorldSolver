package pt.up.fe.ia.a4_2.algorithms;

import pt.up.fe.ia.a4_2.logic.Board;
import pt.up.fe.ia.a4_2.utils.Node;

public class SolutionFoundException extends Exception {
    private Node<Board> sol;

    public SolutionFoundException(Node<Board> s) {
        super();

        sol = s;
    }

    public Node<Board> getSolution() {
        return sol;
    }
}
