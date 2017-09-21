package algs41;

import java.util.Arrays;
import stdlib.*;

public class GraphGenerator {

	public static Graph complete (int V) {
		return simple (V, V * (V - 1) / 2);
	}
	public static Graph simple (int V, int E) {
		if (V < 0 || E < 0) throw new IllegalArgumentException ();
		if (E > V * (V - 1) / 2) throw new IllegalArgumentException ("Number of edges must be less than V*(V-1)/2");
		Graph G = new Graph (V);
		newEdge: while (E > 0) {
			int v = StdRandom.uniform (V);
			int w = StdRandom.uniform (V);
			if (v == w) continue;
			for (int w2 : G.adj (v))
				if (w == w2) continue newEdge;
			G.addEdge (v, w);
			E--;
		}
		return G;
	}
	public static Graph simpleConnected (int V, int E) {
		if (V < 0 || E < 0) throw new IllegalArgumentException ();
		if (E > V * (V - 1) / 2) throw new IllegalArgumentException ("Number of edges must be less than V*(V-1)/2");
		Graph G = spanningTree (V);
		newEdge: while (G.E () < E) {
			int v = StdRandom.uniform (V);
			int w = StdRandom.uniform (V);
			if (v == w) continue;
			for (int w2 : G.adj (v))
				if (w == w2) continue newEdge;
			G.addEdge (v, w);
			E--;
		}
		return G;
	}
	public static Graph connected (int V, int E) {
		if (V < 0 || E < 0) throw new IllegalArgumentException ();
		Graph G = spanningTree (V);
		while (G.E () < E) {
			int v = StdRandom.uniform (V);
			int w = StdRandom.uniform (V);
			G.addEdge (v, w);
		}
		return G;
	}
	public static Graph random (int V, int E) {
		if (V < 0 || E < 0) throw new IllegalArgumentException ();
		Graph G = new Graph (V);
		while (G.E () < E) {
			int v = StdRandom.uniform (V);
			int w = StdRandom.uniform (V);
			G.addEdge (v, w);
		}
		return G;
	}
	public static Graph spanningTree (int V) {
		if (V < 1) throw new IllegalArgumentException ();
		int[] vertices = new int[V];
		for (int i = 0; i < V; i++)
			vertices[i] = i;
		StdRandom.shuffle (vertices);
		Graph G = new Graph (V);
		for (int i = 1; i < V; i++) {
			int v = vertices[StdRandom.uniform (i)];
			int w = vertices[i];
			G.addEdge (v, w);
		}
		return G;
	}
	public static Graph path(int V) {
		if (V < 1) throw new IllegalArgumentException ();
		Graph G = new Graph(V);
		int[] vertices = new int[V];
		for (int i = 0; i < V; i++) vertices[i] = i;
		StdRandom.shuffle(vertices);
		for (int i = 0; i < V-1; i++) {
			G.addEdge(vertices[i], vertices[i+1]);
		}
		return G;
	}
	public static Graph cycle(int V) {
		if (V < 1) throw new IllegalArgumentException ();
		Graph G = new Graph(V);
		int[] vertices = new int[V];
		for (int i = 0; i < V; i++) vertices[i] = i;
		StdRandom.shuffle(vertices);
		for (int i = 0; i < V-1; i++) {
			G.addEdge(vertices[i], vertices[i+1]);
		}
		G.addEdge(vertices[V-1], vertices[0]);
		return G;
	}
	public static Graph binaryTree (int V) {
		if (V < 1) throw new IllegalArgumentException ();
		Graph G = new Graph (V);
		int[] vertices = new int[V];
		for (int i = 0; i < V; i++)
			vertices[i] = i;
		StdRandom.shuffle (vertices);
		for (int i = 1; i < V; i++) {
			G.addEdge (vertices[i], vertices[(i - 1) / 2]);
		}
		return G;
	}
	public static Graph connected(int V, int E, int c) {
		if (c >= V || c <= 0)
			throw new IllegalArgumentException("Number of components must be between 1 and V");
		if (E <= (V-c))
			throw new IllegalArgumentException("Number of edges must be at least (V-c)");
		if (E > V * (V - 1) / 2)
			throw new IllegalArgumentException("Too many edges");

		int[] label = new int[V];
		for (int v = 0; v < V; v++) {
			label[v] = StdRandom.uniform(c);
		}
		// The following hack ensures that each color appears at least once
		{
			Arrays.sort (label);
			label[0] = 0;
			for (int v = 1; v < V; v++) {
				if (label[v]-label[v-1] > 1 || V-v == c-label[v-1]-1)
					label[v] = label[v-1]+1;
			}
			StdRandom.shuffle (label);
		}

		// make all vertices with label c a connected component
		Graph G = new Graph(V);
		for (int i = 0; i < c; i++) {
			// how many vertices in component c
			int count = 0;
			for (int v = 0; v < V; v++) {
				if (label[v] == i) count++;
			}
			int[] vertices = new int[count];
			{
				int j = 0;
				for (int v = 0; v < V; v++)
					if (label[v] == i) vertices[j++] = v;
			}
			StdRandom.shuffle(vertices);

			for (int j = 1; j < count; j++) {
				int v = vertices[StdRandom.uniform (j)];
				int w = vertices[j];
				G.addEdge (v, w);
			}
		}

		while (G.E() < E) {
			int v = StdRandom.uniform(V);
			int w = StdRandom.uniform(V);
			if (v != w && label[v] == label[w]) {
				G.addEdge(v, w);
			}
		}

		return G;
	}
	public static void print (Graph G, String filename) {
		if (G == null) return;
		System.out.println (filename);
		System.out.println (G);
		System.out.println ();
		G.toGraphviz (filename + ".png");
	}
	public static void main (String[] args) {
		//StdRandom.setSeed (10);
		args = new String[] { "6", "10", "3" };

		int V = Integer.parseInt (args[0]);
		int E = Integer.parseInt (args[1]);
		int c = Integer.parseInt (args[2]);

		print (GraphGenerator.random (V, E), "random-" + V + "-" + E);
		print (GraphGenerator.simple (V, E), "simple-" + V + "-" + E);
		print (GraphGenerator.complete (V), "complete-" + V);
		print (GraphGenerator.spanningTree (V), "spanningTree-" + V);
		print (GraphGenerator.simpleConnected (V, E), "simpleConnected-" + V + "-" + E);
		print (GraphGenerator.connected (V, E), "connected-" + V + "-" + E);
		print (GraphGenerator.path (V), "path-" + V);
		print (GraphGenerator.binaryTree (V), "binaryTree-" + V);
		print (GraphGenerator.cycle (V), "cycle-" + V);
		if (E <= (V - c)) E = (V - c) + 1;
		print (GraphGenerator.connected (V, E, c), "connected-" + V + "-" + E + "-" + c);
	}
}
