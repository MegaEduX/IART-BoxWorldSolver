package pt.up.fe.ia.a4_2;

import java.util.ArrayList;

public class Graph {
    private Node root;

    public Graph(Node rootNode) {
        root = rootNode;
    }

    public Node getRootNode() {
        return root;
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
