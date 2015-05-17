package pt.up.fe.ia.a4_2.algorithms;

import pt.up.fe.ia.a4_2.logic.Board;
import pt.up.fe.ia.a4_2.utils.Graph;
import pt.up.fe.ia.a4_2.utils.Node;

import java.util.ArrayList;

public class AStar implements Solver {

    private class SolutionFoundException extends Exception {
        private Node<Board> sol;

        public SolutionFoundException(Node<Board> s) {
            super();

            sol = s;
        }

        public Node<Board> getSolution() {
            return sol;
        }
    }

    private Graph g;

    public AStar(Board b) {
        g = new Graph(new Node<Board>(null, b));
    }

    public String getName() {
        return "A-Star (A*)";
    }

    public Graph getGraph() {
        return g;
    }

    public Node solve() {
        //  ArrayList<Node> leaves = new ArrayList<Node>();

        //  leaves.add(g.getRootNode());

        ArrayList<String> visitedStr = new ArrayList<String>();

        while (true) {
            Node<Board> bestNode = null;
            double bestHeuristic = Double.POSITIVE_INFINITY;

            for (Node<Board> n : g.getLeaves())
                if (n.getValue().getHeuristicF() < bestHeuristic && !n.getVisited() && !visitedStr.contains(n.getValue().toString())) {
                    bestNode = n;
                    bestHeuristic = n.getValue().getHeuristicF();
                }

            //  System.out.println("Number of graph leaves: " + g.getLeaves().size());

            if (bestNode == null)
                return null;

            bestNode.setVisited(true);

            //  leaves.remove(bestNode);

            visitedStr.add(bestNode.getValue().toString());

            try {
                parseNode(bestNode);

                /*  for (Node child : bestNode.getNodes())
                    leaves.add(child);  */
            } catch (SolutionFoundException e) {
                return e.getSolution();
            }
        }
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
