package algs34;
import stdlib.*;
import java.util.HashSet;

public final class XGoodPoint {
	static class GoodPoint2 {
		private final double x;
		private final double y;
		public GoodPoint2(double x, double y) {
			if (Double.isNaN (x)      || Double.isNaN (y))      throw new IllegalArgumentException();
			if (Double.isInfinite (x) || Double.isInfinite (y)) throw new IllegalArgumentException();
			this.x = (x==0) ? 0 : x; // get rid of -0
			this.y = (y==0) ? 0 : y; // get rid of -0
		}
		public String toString() { return "(" + x + ", " + y + ")";  }

		public boolean equals(Object other) {
			if (other == this) return true;
			if (other == null || other.getClass() != this.getClass()) return false;
			GoodPoint2 that = (GoodPoint2) other;
			if (this.x != that.x) return false;
			if (this.y != that.y) return false;
			return true;
		}
		public int hashCode() {
			int h = 17;
			h = 31*h + Double.hashCode(x);
			h = 31*h + Double.hashCode(y);
			return h;
		}
	}

	static class GoodPoint1 {
		private final double x;
		private final double y;
		public GoodPoint1(double x, double y) { this.x = x; this.y = y; }
		public String toString() { return "(" + x + ", " + y + ")";  }

		public boolean equals(Object other) {
			if (other == this) return true;
			if (other == null || other.getClass() != this.getClass()) return false;
			GoodPoint1 that = (GoodPoint1) other;
			if (Double.compare(this.x,that.x) != 0) return false;
			if (Double.compare(this.y,that.y) != 0) return false;
			return true;
		}
		public int hashCode() {
			int h = 17;
			h = 31*h + Double.hashCode(x);
			h = 31*h + Double.hashCode(y);
			return h;
		}
	}


	public static void main(String[] args) {
		GoodPoint1 a = new GoodPoint1(0.0, 0.0);
		GoodPoint1 b = new GoodPoint1(0.0, 0.0);
		GoodPoint1 e = new GoodPoint1(0.0,-0.0);
		StdOut.format("a = %s [hashcode=%d]\n", a, a.hashCode ());
		StdOut.format("b = %s [hashcode=%d]\n", b, b.hashCode ());
		StdOut.format("e = %s [hashcode=%d]\n", e, e.hashCode ());

		HashSet<GoodPoint1> set = new HashSet<>();
		set.add(a);
		StdOut.println("Added a");
		StdOut.println("a == b:      " + (a == b));
		StdOut.println("a.equals(b): " + (a.equals(b)));
		StdOut.println("contains b:  " + set.contains(b));
		StdOut.println("a == e:      " + (a == e));
		StdOut.println("a.equals(e): " + (a.equals(e)));
		StdOut.println("contains e:  " + set.contains(e));
	}
}
