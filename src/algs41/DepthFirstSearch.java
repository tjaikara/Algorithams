package algs41;
import stdlib.*;
/* ***********************************************************************
 *  Compilation:  javac DepthFirstSearch.java
 *  Execution:    java DepthFirstSearch filename.txt s
 *  Dependencies: Graph.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41undirected/tinyG.txt
 *
 *  Run depth first search on an undirected graph.
 *  Runs in O(E + V) time.
 *
 *  % java DepthFirstSearch tinyG.txt 0
 *  0 1 2 3 4 5 6
 *  NOT connected
 *
 *  % java DepthFirstSearch tinyG.txt 9
 *  9 10 11 12
 *  NOT connected
 *
 *************************************************************************/

public class DepthFirstSearch {
	private final boolean[] marked;    // marked[v] = is there an s-v path?
	private int count;                 // number of vertices connected to s

	// single source
	public DepthFirstSearch(Graph G, int s) {
		marked = new boolean[G.V()];
		dfs(G, s);
	}

	// depth first search from v
	private void dfs(Graph G, int v) {
		marked[v] = true;
		count++;
		for (int w : G.adj(v)) {
			if (!marked[w]) {
				dfs(G, w);
			}
		}
	}

	// is there an s-v path?
	public boolean marked(int v) {
		return marked[v];
	}

	// number of vertices connected to s
	public int count() {
		return count;
	}

	// test client
	public static void main(String[] args) {
		args = new String [] { "data/tinyG.txt", "0" };
		//args = new String [] { "data/tinyCG.txt", "0" };

		In in = new In(args[0]);
		Graph G = new Graph(in);
		StdOut.println (G);

		int s = Integer.parseInt(args[1]);
		DepthFirstSearch search = new DepthFirstSearch(G, s);

		StdOut.print("Marked=");
		for (int v = 0; v < G.V(); v++) {
			if (search.marked(v))
				StdOut.print(v + " ");
		}

		StdOut.println();
		StdOut.println("Count=" + search.count ());
		if (search.count() != G.V()) StdOut.println("NOT connected");
		else                         StdOut.println("connected");
	}

}
