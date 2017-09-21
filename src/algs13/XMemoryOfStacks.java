package algs13;
import stdlib.*;
import com.javamex.classmexer.MemoryUtil;

/* ***********************************************************************
 *  Compilation:  javac -cp .:classmexer.jar XMemoryOfStacks.java
 *  Execution:    java -cp  .:classmexer.jar -javaagent:classmexer.jar XMemoryOfStacks
 *
 *  Note: the Integer values -128 to 127 are cached once created.
 *
 *  % java -cp .:classmexer.jar -javaagent:classmexer.jar XMemoryOfStacks 10
 *  size of Integer  = 24
 *  Stack of N null references   = 40.00 N + 32.00  (R^2 = 1.000)
 *  Stack<Integer> of size N     = 64.00 N + 32.00  (R^2 = 1.000)
 *  StackOfInts of size N        = 32.00 N + 32.00  (R^2 = 1.000)
 *  StackOfIntegers of size N    = 56.00 N + 32.00  (R^2 = 1.000)
 *  Queue of N null references   = 40.00 N + 40.00  (R^2 = 1.000)
 *  Bag of N null references     = 40.00 N + 32.00  (R^2 = 1.000)
 *
 *************************************************************************/

public class XMemoryOfStacks {

	public static void nullStack() {
		int[] sizes = { 64, 128, 192, 256, 320, 384, 448, 512, 576,
				640, 704, 768, 832, 896, 960, 1024 };
		int M = sizes.length;

		double[] x = new double[M];
		double[] memory = new double[M];

		for (int i = 0; i < M; i++) {
			int N = sizes[i];
			Stack<Integer> stack = new Stack<>();
			for (int j = 0; j < N; j++)
				stack.push(null);
			x[i] = stack.size();
			memory[i] = MemoryUtil.deepMemoryUsageOf(stack);
		}

		XLinearRegression regression = new XLinearRegression(x, memory);
		StdOut.println("Stack of N null references   = " + regression);
	}

	public static void IntegerStack() {
		int[] sizes = { 64, 128, 192, 256, 320, 384, 448, 512, 576,
				640, 704, 768, 832, 896, 960, 1024 };
		int M = sizes.length;

		double[] x = new double[M];
		double[] memory = new double[M];

		for (int i = 0; i < M; i++) {
			int N = sizes[i];
			Stack<Integer> stack = new Stack<>();
			for (int j = 0; j < N; j++)
				stack.push(128 + StdRandom.uniform(100000));
			x[i] = stack.size();
			memory[i] = MemoryUtil.deepMemoryUsageOf(stack);
		}

		XLinearRegression regression = new XLinearRegression(x, memory);
		StdOut.println("Stack<Integer> of size N     = " + regression);
	}

	public static void StackOfInts() {
		int[] sizes = { 64, 128, 192, 256, 320, 384, 448, 512, 576,
				640, 704, 768, 832, 896, 960, 1024 };
		int M = sizes.length;

		double[] x = new double[M];
		double[] memory = new double[M];

		for (int i = 0; i < M; i++) {
			int N = sizes[i];
			Stack<Integer> stack = new Stack<>();
			for (int j = 0; j < N; j++)
				stack.push(128 + StdRandom.uniform(100000));
			x[i] = stack.size();
			memory[i] = MemoryUtil.deepMemoryUsageOf(stack);
		}

		XLinearRegression regression = new XLinearRegression(x, memory);
		StdOut.println("StackOfInts of size N        = " + regression);
	}


	public static void StackOfIntegers() {
		int[] sizes = { 64, 128, 192, 256, 320, 384, 448, 512, 576,
				640, 704, 768, 832, 896, 960, 1024 };
		int M = sizes.length;

		double[] x = new double[M];
		double[] memory = new double[M];

		for (int i = 0; i < M; i++) {
			int N = sizes[i];
			Stack<Integer> stack = new Stack<>();
			for (int j = 0; j < N; j++)
				stack.push(128 + StdRandom.uniform(100000));
			x[i] = stack.size();
			memory[i] = MemoryUtil.deepMemoryUsageOf(stack);
		}

		XLinearRegression regression = new XLinearRegression(x, memory);
		StdOut.println("StackOfIntegers of size N    = " + regression);
	}

	public static void nullQueue() {
		int[] sizes = { 64, 128, 192, 256, 320, 384, 448, 512, 576,
				640, 704, 768, 832, 896, 960, 1024 };
		int M = sizes.length;

		double[] x = new double[M];
		double[] memory = new double[M];

		for (int i = 0; i < M; i++) {
			int N = sizes[i];
			Queue<Integer> queue = new Queue<>();
			for (int j = 0; j < N; j++)
				queue.enqueue(null);
			x[i] = queue.size();
			memory[i] = MemoryUtil.deepMemoryUsageOf(queue);
		}

		XLinearRegression regression = new XLinearRegression(x, memory);
		StdOut.println("Queue of N null references   = " + regression);
	}

	public static void nullBag() {
		int[] sizes = { 64, 128, 192, 256, 320, 384, 448, 512, 576,
				640, 704, 768, 832, 896, 960, 1024 };
		int M = sizes.length;

		double[] x = new double[M];
		double[] memory = new double[M];

		for (int i = 0; i < M; i++) {
			int N = sizes[i];
			Bag<Integer> bag = new Bag<>();
			for (int j = 0; j < N; j++)
				bag.add(null);
			x[i] = bag.size();
			memory[i] = MemoryUtil.deepMemoryUsageOf(bag);
		}

		XLinearRegression regression = new XLinearRegression(x, memory);
		StdOut.println("Bag of N null references     = " + regression);
	}


	public static void main(String[] args) {
		Integer x = new Integer(123456);
		StdOut.println("size of Integer  = " + MemoryUtil.memoryUsageOf(x));

		nullStack();
		IntegerStack();
		StackOfInts();
		StackOfIntegers();
		nullQueue();
		nullBag();
		/*
        resizingNullStack();
        resizingIntStack();
		 */
	}

}
