package algs41;
import stdlib.*;
/* ***********************************************************************
 *  Compilation:  javac DegreesOfSeparation.java
 *  Execution:    java DegreesOfSeparation filename delimiter source
 *  Dependencies: SymbolGraph.java Graph.java BreadthFirstPaths.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/41undirected/routes.txt
 *                http://algs4.cs.princeton.edu/41undirected/movies.txt
 *
 *
 *  %  java DegreesOfSeparation routes.txt " " "JFK"
 *  LAS
 *     JFK
 *     ORD
 *     DEN
 *     LAS
 *  DFW
 *     JFK
 *     ORD
 *     DFW
 *  EWR
 *     Not in database.
 *
 *  % java DegreesOfSeparation movies.txt "/" "Bacon, Kevin"
 *  Kidman, Nicole
 *     Bacon, Kevin
 *     Woodsman, The (2004)
 *     Grier, David Alan
 *     Bewitched (2005)
 *     Kidman, Nicole
 *  Grant, Cary
 *     Bacon, Kevin
 *     Planes, Trains & Automobiles (1987)
 *     Martin, Steve (I)
 *     Dead Men Don't Wear Plaid (1982)
 *     Grant, Cary
 *
 *  % java DegreesOfSeparation movies.txt "/" "Animal House (1978)"
 *  Titanic (1997)
 *     Animal House (1978)
 *     Allen, Karen (I)
 *     Raiders of the Lost Ark (1981)
 *     Taylor, Rocky (I)
 *     Titanic (1997)
 *  To Catch a Thief (1955)
 *     Animal House (1978)
 *     Vernon, John (I)
 *     Topaz (1969)
 *     Hitchcock, Alfred (I)
 *     To Catch a Thief (1955)
 *
 *************************************************************************/

public class DegreesOfSeparation {
	public static void main(String[] args) {
		args =  new String[] { "data/routes.txt", " ", "JFK" };
		StdIn.fromString ("LAS\nDEN\n");

		//        args = new String[] { "data/movies.txt", "/", "Bacon, Kevin" };
		//        StdIn.fromString (""
		//                + "Kidman, Nicole\n"
		////                + "Belushi, John\n"
		////                + "Winslet, Kate\n"
		////                + "Grant, Cary\n"
		////                + "Olofsson, Joakim\n"
		////                + "Bacon, Kevin\n"
		//                );

		String filename  = args[0];
		String delimiter = args[1];
		String source    = args[2];

		// StdOut.println("Source: " + source);

		SymbolGraph sg = new SymbolGraph(filename, delimiter);
		Graph G = sg.G();
		if (!sg.contains(source)) {
			StdOut.println(source + " not in database." + G.V());
			return;
		}

		int s = sg.index(source);
		//G.toGraphviz ("g.png");
		BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);
		//final DepthFirstPaths bfs = new DepthFirstPaths(G, s);

		while (!StdIn.isEmpty()) {
			String sink = StdIn.readLine();
			if (sg.contains(sink)) {
				StdOut.println(sink + ":");
				int t = sg.index(sink);
				if (bfs.hasPathTo(t)) {
					for (int v : bfs.pathTo(t)) {
						StdOut.println("   " + sg.name(v));
					}
				} else {
					StdOut.println(sink + " not connected");
				}
			} else {
				if (!"".equals (sink)) StdOut.println(sink + " not in database..");
			}
		}
	}
}
