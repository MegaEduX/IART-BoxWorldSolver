package pt.up.fe.ia.a4_2.algorithms;

import pt.up.fe.ia.a4_2.utils.Graph;
import pt.up.fe.ia.a4_2.utils.Node;

public interface Solver {
    Graph getGraph();

    Node solve();

    String getName();
}
