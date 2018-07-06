package programs4;

import algs41.Graph;
import stdlib.In;

public class DFSTrace {



    public static void dfsPrintTrace(Graph g) {

        // *** Declare and initialize the marked array.
        boolean [] marked = new boolean[g.V()];

        dfsPrintTrace(g, 0, marked, 0);
    }


    private static void dfsPrintTrace(Graph g, int start ,boolean [] marked, int indent ){

    }

    public static void main(String[] args) {
        In input = new In("data/tinyG.txt");
        Graph g = new Graph(input);

        dfsPrintTrace(g);
    }
}
