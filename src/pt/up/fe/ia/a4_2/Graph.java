package pt.up.fe.ia.a4_2;

import java.util.ArrayList;
import java.util.Collections;

public class Graph {
    private Node root;

    public Graph(Node rootNode) {
        root = rootNode;
    }

    public Node getRootNode() {
        return root;
    }

    public ArrayList<Node> getPathToNode(Node n) {
        ArrayList<Node> ret = gpnAux(n, new ArrayList<Node>());

        Collections.reverse(ret);

        return ret;
    }

    //  Aquele recursivo maroto...

    public ArrayList<Node> gpnAux(Node n, ArrayList<Node> aux) {
        aux.add(n);

        if (n.getParent() != null)
            return gpnAux(n.getParent(), aux);

        return aux;
    }

    public ArrayList<Node> getLeaves() {
        return getLeavesFromNode(root);
    }

    public ArrayList<Node> getLeavesFromNode(Node n) {
        if (n.hasNodes()) {
            ArrayList<Node> nodes = new ArrayList<Node>();

            for (Node n2 : (ArrayList<Node>) n.getNodes())
                nodes.addAll(getLeavesFromNode(n2));

            return nodes;
        }

        ArrayList<Node> al = new ArrayList<Node>();

        al.add(n);

        return al;
    }
}
