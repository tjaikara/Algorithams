package algs54; // section 5.4
import stdlib.*;
import algs13.Bag;
import algs13.Stack;
import algs42.Digraph;
import algs42.DirectedDFS;
/* ***********************************************************************
 *  Compilation:  javac NFA.java
 *  Execution:    java NFA regexp text
 *  Dependencies: Stack.java Bag.java Digraph.java DirectedDFS.java
 *
 *  % java NFA "(A*B|AC)D" AAAABD
 *  true
 *
 *  % java NFA "(A*B|AC)D" AAAAC
 *  false
 *
 *  % java NFA "(a|(bc)*d)*" abcbcd
 *  true
 *
 *  % java NFA "(a|(bc)*d)*" abcbcbcdaaaabcbcdaaaddd
 *  true
 *
 *  Remarks
 *  -----------
 *    - This version does not currently suport multiway or, wildcard,
 *      or the + operator.
 *
 *************************************************************************/

public class NFA {

	private final Digraph G;         // digraph of epsilon transitions
	private final String regexp;     // regular expression
	private final int M;             // number of characters in regular expression

	// Create the NFA for the given RE
	public NFA(String regexp) {
		this.regexp = regexp;
		M = regexp.length();
		Stack<Integer> ops = new Stack<>();
		G = new Digraph(M+1);
		for (int i = 0; i < M; i++) {
			int lp = i;
			if (regexp.charAt(i) == '(' || regexp.charAt(i) == '|')
				ops.push(i);
			else if (regexp.charAt(i) == ')') {
				int or = ops.pop();
				if (regexp.charAt(or) == '|') {
					lp = ops.pop();
					G.addEdge(lp, or+1);
					G.addEdge(or, i);
				}
				else if (regexp.charAt(or) == '(')
					lp = or;
				else assert false;
			}

			// Lookahead;
			if (i < M-1 && regexp.charAt(i+1) == '*') {
				G.addEdge(lp, i+1);
				G.addEdge(i+1, lp);
			}
			if (regexp.charAt(i) == '(' || regexp.charAt(i) == '*' || regexp.charAt(i) == ')')
				G.addEdge(i, i+1);
		}
	}

	// Does the NFA recognize txt?
	public boolean recognizes(String txt) {
		DirectedDFS dfs = new DirectedDFS(G, 0);
		Bag<Integer> pc = new Bag<>();
		for (int v = 0; v < G.V(); v++)
			if (dfs.marked(v)) pc.add(v);

		// Compute possible NFA states for txt[i+1].
		for (int i = 0; i < txt.length(); i++) {
			Bag<Integer> match = new Bag<>();
			for (int v : pc) {
				if (v == M) continue;
				if ((regexp.charAt(v) == txt.charAt(i)) || regexp.charAt(v) == '.')
					match.add(v+1);
			}
			dfs = new DirectedDFS(G, match);
			pc = new Bag<>();
			for (int v = 0; v < G.V(); v++)
				if (dfs.marked(v)) pc.add(v);

			// optimization if no states reachable
			if (pc.size() == 0) return false;
		}

		// check for accept state
		for (int v : pc)
			if (v == M) return true;
		return false;
	}


	public static void main(String[] args) {
		args = new String[] { "(a|(bc)*d)*", "abcbcbcdaaaabcbcdaaaddd" };

		String regexp = "(" + args[0] + ")";
		String txt = args[1];
		NFA nfa = new NFA(regexp);
		StdOut.println(nfa.recognizes(txt));
	}

}
