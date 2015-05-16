package pt.up.fe.ia.a4_2;

public class Solver {

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

    public Solver(Board b) {
        g = new Graph(new Node<Board>(b));
    }

    public Node solve() {
        while (true) {
            Node<Board> bestNode = null;
            double bestHeuristic = Double.POSITIVE_INFINITY;

            for (Node<Board> n : g.getLeaves())
                if (n.getValue().getHeuristicF() < bestHeuristic && !n.getUsed()) {
                    bestNode = n;
                    bestHeuristic = n.getValue().getHeuristicF();
                }

            System.out.println("Number of graph leaves: " + g.getLeaves().size());

            bestNode.setUsed(true);

            try {
                parseNode(bestNode);
            } catch (SolutionFoundException e) {
                return e.getSolution();
            }
        }
    }

    private void parseNode(Node<Board> n) throws SolutionFoundException {
        Board copy = n.getValue().copy();

        if (copy.movePlayer(Board.Direction.Up)) {
            Node<Board> child = new Node<Board>(copy);
            n.addChild(child);

            if (child.getValue().getIsSolution())
                throw new SolutionFoundException(child);
        }

        copy = n.getValue().copy();

        if (copy.movePlayer(Board.Direction.Down)) {
            Node<Board> child = new Node<Board>(copy);
            n.addChild(child);

            if (child.getValue().getIsSolution())
                throw new SolutionFoundException(child);
        }

        copy = n.getValue().copy();

        if (copy.movePlayer(Board.Direction.Left)) {
            Node<Board> child = new Node<Board>(copy);
            n.addChild(child);

            if (child.getValue().getIsSolution())
                throw new SolutionFoundException(child);
        }

        copy = n.getValue().copy();

        if (copy.movePlayer(Board.Direction.Right)) {
            Node<Board> child = new Node<Board>(copy);
            n.addChild(child);

            if (child.getValue().getIsSolution())
                throw new SolutionFoundException(child);
        }
    }

}
