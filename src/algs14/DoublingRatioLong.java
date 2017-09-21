package algs14;
import stdlib.*;
/* ***********************************************************************
 *  Compilation:  javac DoublingRatioLong.java
 *  Execution:    java DoublingRatioLong
 *  Dependencies: Stopwatch.java StdOut.java
 *
 *  This version is suited for testing functions that require very large N
 *  to run long enough to measure.
 *
 *  % java DoublingRatioLong
 *      512 6.48
 *     1024 8.30
 *     2048 7.75
 *     4096 8.00
 *     8192 8.05
 *   ...
 *
 *************************************************************************/

public class DoublingRatioLong {
	static private long f1 (long N) {
		long sum = 0;
		for (long i = 1; i <= N; i += 1) {
			sum++;
		}
		return sum;
	}
	static private long f2(long N) {
		long sum = 0;
		for (long i = 1; i <= N; i += 1) {
			for (long j = 1; j <= N; j += 1) {
				sum++;
			}
		}
		return sum;
	}
	static private long f3(long N) {
		long sum = 0;
		for (long i = 1; i <= N; i += 1) {
			for (long j = i; j <= N; j += 1) {
				for (long k = j; k <= N; k += 1) {
					sum++;
				}
			}
		}
		return sum;
	}
	public static double timeTrial(long N) {
		long T = 1; // number of tests
		double sum = 0;
		for (long t = 0; t < T; t++) {
			Stopwatch s = new Stopwatch();
			//sum += f1(N);
			f1(N);  sum +=  s.elapsedTime();
		}
		return sum/T;
	}

	private static final long MIN = 10;
	private static final long MAX = 40960L;
	//private static final long MAX = Long.MAX_VALUE/2;
	public static void main(String[] args) {
		double prev = timeTrial(MIN);
		for (long N = MIN*2; N<=MAX; N += N) {
			double time = timeTrial(N);
			StdOut.format("%19d %9.3f %5.1f\n", N, time, time/prev);
			prev = time;
		}
	}
}

