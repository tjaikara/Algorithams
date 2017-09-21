package algs14;
import stdlib.*;
import algs13.ResizingArrayStack;
import algs13.XResizingSlowStack;

// previous version of this was called XTestResizingStacks
public class XPerformanceOfArrays {
	public static XResizingSlowStack<Object> makeArrayWithAddition (int N) {
		Object o = new Object ();
		XResizingSlowStack<Object> stack = new XResizingSlowStack<>();
		for (int j=0; j<N; j++)
			stack.push (o);
		return stack;
	}
	public static ResizingArrayStack<Object> makeArrayWithMultiplication (int N) {
		Object o = new Object ();
		ResizingArrayStack<Object> stack = new ResizingArrayStack<>();
		for (int j=0; j<N; j++)
			stack.push (o);
		return stack;
	}
	public static double timeTrial(int N) {
		int T = 1; // number of tests
		double sum = 0;
		for (int t = 0; t < T; t++) {
			Stopwatch s = new Stopwatch();
			makeArrayWithAddition (N);
			//makeArrayWithMultiplication (N);
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

