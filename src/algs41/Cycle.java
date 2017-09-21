package algs41;
import stdlib.*;
import algs13.Stack;
/* ***********************************************************************
 *  Compilation:  javac Cycle.java
 *  Dependencies: Graph.java Stack.java
 *
 *  Identifies a cycle.
 *  Runs in O(E + V) time.
 *
 *************************************************************************/

public class Cycle {
	private boolean[] marked;
	private int[] edgeTo;
	private Stack<Integer> cycle;

	public Cycle(Graph G) {
		if (hasSelfLoop(G)) return;
		if (hasParallelEdges(G)) return;
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		for (int v = 0; v < G.V(); v++)
			if (!marked[v] && hasCycleFrom(G, -1, v))
				return;
	}


	// does this graph have a self loop?
	// side effect: initialize cycle to be self loop
	private boolean hasSelfLoop(Graph G) {
		for (int v = 0; v < G.V(); v++) {
			for (int w : G.adj(v)) {
				if (v == w) {
					cycle = new Stack<>();
					cycle.push(v);
					cycle.push(v);
					return true;
				}
			}
		}
		return false;
	}

	// does this graph have two parallel edges?
	// side effect: initialize cycle to be two parallel edges
	private boolean hasParallelEdges(Graph G) {
		marked = new boolean[G.V()];
		for (int v = 0; v < G.V(); v++) {
			// check for parallel edges incident to v
			for (int w : G.adj(v)) {
				if (marked[w]) {
					cycle = new Stack<>();
					cycle.push(v);
					cycle.push(w);
					cycle.push(v);
					return true;
				}
				marked[w] = true;
			}

			// reset so marked[v] = false for all v
			for (int w : G.adj(v)) {
				marked[w] = false;
			}
		}
		return false;
	}

	public boolean hasCycle()        { return cycle != null; }
	public Iterable<Integer> cycle() { return cycle;         }

	private boolean hasCycleFrom(Graph G, int u, int v) {
		//StdOut.format ("dfs(%d, %d)\n", u, v);
		marked[v] = true;
		edgeTo[v] = u;
		for (int w : G.adj(v))
			if ((marked[w] && w != u) || (!marked[w] && hasCycleFrom (G, v, w))) {
				if (cycle == null) {
					cycle = new Stack<>();
					cycle.push(w);
					for (int x = v; x != w; x = edgeTo[x])
						cycle.push(x);
					cycle.push(w);
				}
				return true;
			}
		return false;
	}

	// test client
	public static void main(String[] args) {
		//        args = new String [] { "10", "5" };
		//        final int V = Integer.parseInt(args[0]);
		//        final int E = Integer.parseInt(args[1]);
		//        final Graph G = new Graph(V, E);
		//        StdOut.println(G);

		//args = new String [] { "data/tinyAG.txt" };
		args = new String [] { "data/tinyG.txt" };
		In in = new In(args[0]);
		Graph G = new Graph(in);
		StdOut.println(G);
		G.toGraphviz ("g.png");

		Cycle finder = new Cycle(G);
		if (finder.hasCycle()) {
			for (int v : finder.cycle()) {
				StdOut.print(v + " ");
			}
			StdOut.println();
		}
		else {
			StdOut.println("Graph is acyclic");
		}
	}


}

