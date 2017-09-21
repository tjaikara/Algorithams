package algs14;
import stdlib.*;
public class XPerformanceOfStrings {
	/** create a string consisting of N asterisks */
	public static String makeStringUsingConcat (int N) {
		String result = "";
		for (int i=0; i<N; i++) 
			result = result + "*";
		return result;
	}
	/** create a string consisting of N asterisks */
	public static String makeStringUsingBuffer (int N) {
		StringBuffer result = new StringBuffer ();
		for (int i=0; i<N; i++) 
			result.append ("*");
		return result.toString ();
	}
	public static double timeTrial(int N) {
		int T = 1; // number of tests
		double sum = 0;
		for (int t = 0; t < T; t++) {
			Stopwatch s = new Stopwatch();
			//makeStringUsingConcat (N);
			makeStringUsingBuffer (N);
			sum +=  s.elapsedTime();
		}
		return sum/T;
	}

	private static final int MIN = 8000;
	private static final int MAX = 32768000;
	public static void main(String[] args) {
		double prev = timeTrial(MIN);
		for (int N = MIN*2; N<=MAX; N += N) {
			double time = timeTrial(N);
			StdOut.format("%10d %9.3f %5.1f\n", N, time, time/prev);
			prev = time;
		}
	}
}

