package algs53; // section 5.3
import stdlib.*;
/* *************************************************************
 *  Compilation:  javac BoyerMoore.java
 *  Execution:    java BoyerMoore pattern text
 *
 *  Reads in two strings, the pattern and the input text, and
 *  searches for the pattern in the input text using the
 *  bad-character rule part of the Boyer-Moore algorithm.
 *  (does not implement the strong good suffix rule)
 *
 *  % java BoyerMoore abracadabra abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:               abracadabra
 *
 *  % java BoyerMoore rab abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:         rab
 *
 *  % java BoyerMoore bcara abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:                                   bcara
 *
 *  % java BoyerMoore rabrabracad abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:                        rabrabracad
 *
 *  % java BoyerMoore abacad abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern: abacad
 *
 ***************************************************************/

public class BoyerMoore {
	private final int R;     // the radix
	private final int[] right;     // the bad-character skip array

	private char[] pattern;  // store the pattern as a character array
	private String pat;      // or as a string

	// pattern provided as a string
	public BoyerMoore(String pat) {
		this.R = 256;
		this.pat = pat;

		// position of rightmost occurrence of c in the pattern
		right = new int[R];
		for (int c = 0; c < R; c++)
			right[c] = -1;
		for (int j = 0; j < pat.length(); j++)
			right[pat.charAt(j)] = j;
	}

	// pattern provided as a character array
	public BoyerMoore(char[] pattern, int R) {
		this.R = R;
		this.pattern = new char[pattern.length];
		for (int j = 0; j < pattern.length; j++)
			this.pattern[j] = pattern[j];

		// position of rightmost occurrence of c in the pattern
		right = new int[R];
		for (int c = 0; c < R; c++)
			right[c] = -1;
		for (int j = 0; j < pattern.length; j++)
			right[pattern[j]] = j;
	}

	// return offset of first match; N if no match
	public int search(String txt) {
		int M = pat.length();
		int N = txt.length();
		int skip;
		for (int i = 0; i <= N - M; i += skip) {
			skip = 0;
			for (int j = M-1; j >= 0; j--) {
				if (pat.charAt(j) != txt.charAt(i+j)) {
					skip = Math.max(1, j - right[txt.charAt(i+j)]);
					break;
				}
			}
			if (skip == 0) return i;    // found
		}
		return N;                       // not found
	}


	// return offset of first match; N if no match
	public int search(char[] text) {
		int M = pattern.length;
		int N = text.length;
		int skip;
		for (int i = 0; i <= N - M; i += skip) {
			skip = 0;
			for (int j = M-1; j >= 0; j--) {
				if (pattern[j] != text[i+j]) {
					skip = Math.max(1, j - right[text[i+j]]);
					break;
				}
			}
			if (skip == 0) return i;    // found
		}
		return N;                       // not found
	}



	// test client
	public static void main(String[] args) {
		//args = new String[] { "abracadabra", "abacadabrabracabracadabrabrabracad" };
		//args = new String[] { "rab",         "abacadabrabracabracadabrabrabracad" };
		//args = new String[] { "bcara",       "abacadabrabracabracadabrabrabracad" };
		//args = new String[] { "rabrabracad", "abacadabrabracabracadabrabrabracad" };
		args = new String[] { "abacad",      "abacadabrabracabracadabrabrabracad" };
		String pat1 = args[0];
		String txt1 = args[1];
		char[] pat2 = pat1.toCharArray();
		char[] txt2 = txt1.toCharArray();

		BoyerMoore bm1 = new BoyerMoore(pat1);
		BoyerMoore bm2 = new BoyerMoore(pat2, 256);
		int offset1 = bm1.search(txt1);
		int offset2 = bm2.search(txt2);

		// print results
		StdOut.println("text:    " + txt1);

		StdOut.print("pattern: ");
		for (int i = 0; i < offset1; i++)
			StdOut.print(" ");
		StdOut.println(pat1);

		StdOut.print("pattern: ");
		for (int i = 0; i < offset2; i++)
			StdOut.print(" ");
		StdOut.println(pat2);
	}
}
