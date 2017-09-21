// Exercise 4.1.3 (Solution published at http://algs4.cs.princeton.edu/)
package algs41;
import stdlib.*;
import algs13.Bag;
import algs13.Stack;

/**
 *  The <tt>Graph</tt> class represents an undirected graph of vertices
 *  named 0 through V-1.
 *  It supports the following operations: add an edge to the graph,
 *  iterate over all of the neighbors adjacent to a vertex.
 *  Parallel edges and self-loops are permitted.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/51undirected">Section 5.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class Graph {
	private final int V;
	private int E;
	private final Bag<Integer>[] adj;

	/**
	 * Create an empty graph with V vertices.
	 */
	@SuppressWarnings("unchecked")
	public Graph(int V) {
		if (V < 0) throw new Error("Number of vertices must be nonnegative");
		this.V = V;
		this.E = 0;
		this.adj = new Bag[V];
		for (int v = 0; v < V; v++) {
			adj[v] = new Bag<>();
		}
	}

	/**
	 * Create a random graph with V vertices and E edges with no parallel edges or self loops.
	 * Expected running time is proportional to V + E.
	 */
	public Graph(int V, int E) { this (V, E, false); }
	/**
	 * Create a random graph with V vertices and E edges.
	 * Expected running time is proportional to V + E.
	 */
	public Graph(int V, int E, boolean allowParallelEdgesAndSelfLoops) {
		this(V);
		if (E < 0) throw new Error("Number of edges must be nonnegative");
		if (allowParallelEdgesAndSelfLoops) {
			for (int i = 0; i < E; i++) {
				int v = (int) (Math.random() * V);
				int w = (int) (Math.random() * V);
				addEdge(v, w);
			}
		} else {
			if (E > V*(V-1)/2) throw new Error("Number of edges must be less than V*(V-1)/2");
			newEdge: while (E>0) {
				int v = (int) (Math.random() * V);
				int w = (int) (Math.random() * V);
				if (v == w) continue;
				for (int w2 : adj[v])
					if (w == w2)
						continue newEdge;
				addEdge(v, w);
				E--;
			}
		}
	}

	/**
	 * Create a digraph from input stream.
	 */
	public Graph(In in) {
		this(in.readInt());
		int E = in.readInt();
		for (int i = 0; i < E; i++) {
			int v = in.readInt();
			int w = in.readInt();
			addEdge(v, w);
		}
	}

	/**
	 * Copy constructor.
	 */
	public Graph(Graph G) {
		this(G.V());
		this.E = G.E();
		for (int v = 0; v < G.V(); v++) {
			// reverse so that adjacency list is in same order as original
			Stack<Integer> reverse = new Stack<>();
			for (int w : G.adj[v]) {
				reverse.push(w);
			}
			for (int w : reverse) {
				adj[v].add(w);
			}
		}
	}

	/**
	 * Return the number of vertices in the graph.
	 */
	public int V() { return V; }

	/**
	 * Return the number of edges in the graph.
	 */
	public int E() { return E; }


	/**
	 * Add the undirected edge v-w to graph.
	 * @throws java.lang.IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
	 */
	public void addEdge(int v, int w) {
		if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
		if (w < 0 || w >= V) throw new IndexOutOfBoundsException();
		E++;
		adj[v].add(w);
		adj[w].add(v);
	}


	/**
	 * Return the list of neighbors of vertex v as in Iterable.
	 * @throws java.lang.IndexOutOfBoundsException unless 0 <= v < V
	 */
	public Iterable<Integer> adj(int v) {
		if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
		return adj[v];
	}


	/**
	 * Return a string representation of the graph.
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		String NEWLINE = System.getProperty("line.separator");
		s.append(V + " vertices, " + E + " edges " + NEWLINE);
		for (int v = 0; v < V; v++) {
			s.append(v + ": ");
			for (int w : adj[v]) {
				s.append(w + " ");
			}
			s.append(NEWLINE);
		}
		return s.toString();
	}

	/**
	 * Save a graphviz representation of the graph.
	 * See <a href="http://www.graphviz.org/">graphviz.org</a>.
	 */
	public void toGraphviz(String filename) {
		GraphvizBuilder gb = new GraphvizBuilder ();
		for (int v = 0; v < V; v++) {
			gb.addNode (v);
			boolean showSelfLoop = false;
			for (int w : adj[v]) {
				if (v < w) // only once each edge
					gb.addEdge (v, w);
				if (v == w) {
					showSelfLoop = !showSelfLoop;
					if (showSelfLoop)
						gb.addEdge (v, w);
				}
			}
		}
		gb.toFileUndirected (filename);
	}

	/**
	 * Test client.
	 */
	public static void main(String[] args) {
		args = new String [] { "data/tinyCG.txt" };
		//args = new String [] { "data/tinyG.txt" };
		args = new String [] { "20", "40" };

		Graph G;
		if (args.length == 1) {
			In in = new In(args[0]);
			G = new Graph(in);
		} else {
			int V = Integer.parseInt (args[0]);
			int E = Integer.parseInt (args[1]);
			G = new Graph(V, E, true);
		}
		StdOut.println(G);
		G.toGraphviz ("g.png");
	}
}
