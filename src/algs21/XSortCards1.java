package algs21;
import stdlib.*;
import algs12.XCard;

public class XSortCards1 {
	public static void show (XCard[] d) {
		for (int i=0; i<4; i++) {
			for (int j=0; j<13; j++) {
				StdOut.format ("%3s ", d[i*13+j]);
			}
			StdOut.println ();
		}
		StdOut.println ();
	}
	public static void main (String[] args) {
		XCard[] d = XCard.newDeck ();
		show (d);
		StdRandom.shuffle (d);
		show (d);
		Insertion.sort (d);
		show (d);
	}
}
