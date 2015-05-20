package pt.up.fe.ia.a4_2.algorithms;

import pt.up.fe.ia.a4_2.logic.Board;
import pt.up.fe.ia.a4_2.utils.Graph;
import pt.up.fe.ia.a4_2.utils.Node;

import java.util.ArrayList;
import java.util.Stack;

public class DFS implements Solver {

    private Graph g;

    public DFS(Board b) {
        g = new Graph(new Node<Board>(null, b));
    }

    public String getName() {
        return "Depth First Search (DFS)";
    }

    public Graph getGraph() {
        return g;
    }

    public Node solve() {
        ArrayList<String> visitedStr = new ArrayList<String>();

        Stack<Node<Board>> stack = new Stack<Node<Board>>();

        stack.push(g.getRootNode());

        try {
            while (!stack.isEmpty()) {
                Node<Board> n = stack.pop();

                Board b = n.getValue();

                if (!n.getVisited() && !visitedStr.contains(b.toString())) {
                    n.setVisited(true);

                    visitedStr.add(b.toString());

                    parseNode(n);

                    for (Node<Board> child : n.getNodes())
                        stack.push(child);
                }
            }
        } catch (SolutionFoundException e) {
            return e.getSolution();
        }

        return null;
    }

    private void parseNode(Node<Board> n) throws SolutionFoundException {
        Board copy = n.getValue().copy();

        if (copy.movePlayer(Board.Direction.Up)) {
            Node<Board> child = new Node<Board>(n, copy);
            n.addChild(child);

            if (child.getValue().getIsSolution())
                throw new SolutionFoundException(child);
        }

        copy = n.getValue().copy();

        if (copy.movePlayer(Board.Direction.Down)) {
            Node<Board> child = new Node<Board>(n, copy);
            n.addChild(child);

            if (child.getValue().getIsSolution())
                throw new SolutionFoundException(child);
        }

        copy = n.getValue().copy();

        if (copy.movePlayer(Board.Direction.Left)) {
            Node<Board> child = new Node<Board>(n, copy);
            n.addChild(child);

            if (child.getValue().getIsSolution())
                throw new SolutionFoundException(child);
        }

        copy = n.getValue().copy();

        if (copy.movePlayer(Board.Direction.Right)) {
            Node<Board> child = new Node<Board>(n, copy);
            n.addChild(child);

            if (child.getValue().getIsSolution())
                throw new SolutionFoundException(child);
        }
    }

}
