package algs41;
import stdlib.*;
import algs13.Queue;
/* ***********************************************************************
 *  Compilation:  javac CC.java
 *  Execution:    java CC filename.txt
 *  Dependencies: Graph.java StdOut.java Queue.java
 *  Data files:   http://algs4.cs.princeton.edu/41undirected/tinyG.txt
 *
 *  Compute connected components using depth first search.
 *  Runs in O(E + V) time.
 *
 *  %  java CC tinyG.txt
 *  3 components
 *  0 1 2 3 4 5 6
 *  7 8
 *  9 10 11 12
 *
 *************************************************************************/

public class CC {
	private final boolean[] marked;   // marked[v] = has vertex v been marked?
	private final int[] id;           // id[v] = id of connected component containing v
	private final int[] size;         // size[id] = number of vertices in component containing v
	private int count;                // number of connected components

	public CC(Graph G) {
		marked = new boolean[G.V()];
		id = new int[G.V()];
		size = new int[G.V()];
		for (int v = 0; v < G.V(); v++) {
			if (!marked[v]) {
				dfs(G, v);
				count++;
			}
		}
	}

	// depth first search
	private void dfs(Graph G, int v) {
		marked[v] = true;
		id[v] = count;
		size[count]++;
		for (int w : G.adj(v)) {
			if (!marked[w]) {
				dfs(G, w);
			}
		}
	}

	// id of connected component containing v
	public int id(int v) {
		return id[v];
	}

	// size of connected component containing v
	public int size(int v) {
		return size[id[v]];
	}

	// number of connected components
	public int count() {
		return count;
	}

	// are v and w in the same connected component?
	public boolean areConnected(int v, int w) {
		return id(v) == id(w);
	}


	// test client
	public static void anotherTest() {
		Graph G;
		do {
			G = new Graph(20,40);
		} while (new CC(G).count() != 1);
		G.toGraphviz ("g.png");
	}
	public static void main(String[] args) {
		anotherTest();
		//      args = new String [] { "10", "5" };
		//      final int V = Integer.parseInt(args[0]);
		//      final int E = Integer.parseInt(args[1]);
		//      final Graph G = new Graph(V, E);
		//      StdOut.println(G);

		//args = new String [] { "data/tinyAG.txt" };
		args = new String [] { "data/tinyG.txt" };
		In in = new In(args[0]);
		Graph G = new Graph(in);
		StdOut.println(G);

		CC cc = new CC(G);

		// number of connected components
		int M = cc.count();
		StdOut.println(M + " components");

		// compute list of vertices in each connected component
		@SuppressWarnings("unchecked")
		Queue<Integer>[] components = new Queue[M];
		for (int i = 0; i < M; i++) {
			components[i] = new Queue<>();
		}
		for (int v = 0; v < G.V(); v++) {
			components[cc.id(v)].enqueue(v);
		}

		// print results
		for (int i = 0; i < M; i++) {
			for (int v : components[i]) {
				StdOut.print(v + " ");
			}
			StdOut.println();
		}
	}
}
