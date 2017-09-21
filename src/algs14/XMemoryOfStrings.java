package algs14;
import stdlib.*;
import java.util.LinkedList;
import com.javamex.classmexer.MemoryUtil;

/* ***********************************************************************
 *  Compilation:  javac -cp .:classmexer.jar XMemoryOfStrings.java
 *  Execution:    java  -cp .:classmexer.jar -javaagent:classmexer.jar XMemoryOfStrings
 *  Dependencies: StdOut.java StdRandom.java classmexer.jar
 *
 *  In eclipse, add
 *    -javaagent:classmexer.jar
 *  under Run -> Run Configurations... -> Arguments -> VM arguments
 *
 *  % java -cp .:classmexer.jar -javaagent:classmexer.jar XMemoryOfStrings
 *  String of length N        = 2.00 N + 64.00  (R^2 = 1.000)
 *  char[] array of length N  = 2.00 N + 24.00  (R^2 = 1.000)
 *  String[] of N suffixes    = 50.00 N + 48.00  (R^2 = 1.000)
 *  memory of genome = "CGCCTGGCGTCTGTAC"    = 96
 *  memory of codon = genome.substring(6, 9) = 96
 *  total memory of genome and codon         = 136
 *
 *************************************************************************/

public class XMemoryOfStrings {

	// random string of length N consisting of only uppercase letters
	public static String random(int N) {
		if (N == 0) return "";
		if (N == 1) {
			char c = (char) ('A' + StdRandom.uniform(26));
			return c + "";
		}
		return random(N/2) + random(N - N/2);
	}


	// string of given length
	public static void string() {
		int[] sizes = {
				64, 128, 192, 256, 320, 384, 448, 512, 576, 640, 704, 768, 832, 896, 960, 1024
		};
		int M = sizes.length;

		double[] x = new double[M];
		double[] memory = new double[M];

		for (int i = 0; i < M; i++) {
			String s = random(sizes[i]);
			x[i] = s.length();
			memory[i] = MemoryUtil.deepMemoryUsageOf(s);
		}

		XLinearRegression regression = new XLinearRegression(x, memory);
		StdOut.println("String of length N        = " + regression);
	}

	// character array
	public static void charArray() {
		int[] sizes = {
				64, 128, 192, 256, 320, 384, 448, 512, 576, 640, 704, 768, 832, 896, 960, 1024
		};
		int M = sizes.length;

		double[] x = new double[M];
		double[] memory = new double[M];

		for (int i = 0; i < M; i++) {
			char[] s = random(sizes[i]).toCharArray();
			x[i] = s.length;
			memory[i] = MemoryUtil.deepMemoryUsageOf(s);
		}

		XLinearRegression regression = new XLinearRegression(x, memory);
		StdOut.println("char[] array of length N  = " + regression);
	}

	// array of suffixes
	public static void suffixes() {
		int[] sizes = {
				64, 128, 192, 256, 320, 384, 448, 512, 576, 640, 704, 768, 832, 896, 960, 1024
		};
		int M = sizes.length;

		double[] x = new double[M];
		double[] memory = new double[M];

		for (int i = 0; i < M; i++) {
			String s = random(sizes[i]);
			String[] suffixes = new String[s.length()];
			for (int j = 0; j < s.length(); j++)
				suffixes[j] = s.substring(j);
			x[i] = s.length();
			memory[i] = MemoryUtil.deepMemoryUsageOf(suffixes);
		}

		XLinearRegression regression = new XLinearRegression(x, memory);
		StdOut.println("String[] of N suffixes    = " + regression);
	}

	// substring
	public static void substring() {
		String genome = "CGCCTGGCGTCTGTAC";
		String codon = genome.substring(6, 9);
		LinkedList<String> list = new LinkedList<>();
		list.add(genome);
		list.add(codon);

		StdOut.println("shallow memory of genome = \"CGCCTGGCGTCTGTAC\"    = " + MemoryUtil.memoryUsageOf(genome));
		StdOut.println("shallow memory of codon = genome.substring(6, 9) = " + MemoryUtil.memoryUsageOf(codon));
		StdOut.println("shallow memory of list                           = " + MemoryUtil.memoryUsageOf(list));

		StdOut.println("deep memory of genome = \"CGCCTGGCGTCTGTAC\"    = " + MemoryUtil.deepMemoryUsageOf(genome));
		StdOut.println("deep memory of codon = genome.substring(6, 9) = " + MemoryUtil.deepMemoryUsageOf(codon));
		StdOut.println("deep memory of both                           = " + MemoryUtil.deepMemoryUsageOfAll(list));
	}

	public static void main(String[] args) {
		//string();
		//charArray();
		//suffixes();
		substring();
	}
}
