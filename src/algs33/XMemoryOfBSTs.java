package algs33;
import stdlib.*;
import java.util.TreeMap;
import algs32.BST;
import com.javamex.classmexer.MemoryUtil;

/* ***********************************************************************
 *  Compilation:  javac -cp .:classmexer.jar XMemoryOfBSTs.java
 *  Execution:    java  -cp .:classmexer.jar -javaagent:classmexer.jar XMemoryOfBSTs N
 *  Dependencies: StdOut.java classmexer.jar
 *
 *
 *************************************************************************/

public class XMemoryOfBSTs {

	public static void main(String[] args) {

		int N = Integer.parseInt(args[0]);
		//int START = 1000000;

		StdOut.println("size of Integer");
		Integer x = new Integer(123456);
		StdOut.println(MemoryUtil.memoryUsageOf(x));
		StdOut.println();
		long SIZEOFINTEGER = MemoryUtil.memoryUsageOf(x);

		TreeMap<Integer, Integer> st1 = new TreeMap<>();
		StdOut.println("size of TreeMap<Integer, Integer> of given length");
		for (int i = 0; i < 8; i++) {
			Integer key = new Integer(i);
			st1.put(key, key);
			StdOut.println(i + ": " + (MemoryUtil.deepMemoryUsageOf(st1) - SIZEOFINTEGER*i));
		}
		for (int i = 8; i < N; i++) {
			Integer key = new Integer(i);
			st1.put(key, key);
		}
		StdOut.println(N + ": " + (MemoryUtil.deepMemoryUsageOf(st1) - SIZEOFINTEGER*N));
		StdOut.println();

		BST<Integer, Integer> st2 = new BST<>();
		StdOut.println("size of BST<Integer, Integer> of given length");
		for (int i = 0; i < 8; i++) {
			Integer key = new Integer(i);
			st2.put(key, key);
			StdOut.println(i + ": " + (MemoryUtil.deepMemoryUsageOf(st2) - SIZEOFINTEGER*i));
		}
		for (int i = 8; i < N; i++) {
			Integer key = new Integer(i);
			st2.put(key, key);
		}
		StdOut.println(N + ": " + (MemoryUtil.deepMemoryUsageOf(st2) - SIZEOFINTEGER*N));
		StdOut.println();


		RedBlackBST<Integer, Integer> st3 = new RedBlackBST<>();
		StdOut.println("size of RedBlackBST<Integer, Integer> of given length");
		for (int i = 0; i < 8; i++) {
			Integer key = new Integer(i);
			st3.put(key, key);
			StdOut.println(i + ": " + (MemoryUtil.deepMemoryUsageOf(st3) - SIZEOFINTEGER*i));
		}
		for (int i = 8; i < N; i++) {
			Integer key = new Integer(i);
			st3.put(key, key);
		}
		StdOut.println(N + ": " + (MemoryUtil.deepMemoryUsageOf(st3) - SIZEOFINTEGER*N));
		StdOut.println();

	}

}
