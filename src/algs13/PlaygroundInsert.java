package algs13;
import java.text.DecimalFormat;
import stdlib.*;
public class PlaygroundInsert {
  private Node first;
  static class Node { 
    public double item; 
    public Node next; 
    public Node (double item, Node next) { this.item = item; this.next = next; }
  }
  public void insert (double item) {  
    // TODO: insert item into a SORTED list
  }

  /* ToString method to print */
  public String toString () { 
    // Use DecimalFormat #.### rather than String.format 0.3f to leave off trailing zeroes
    DecimalFormat format = new DecimalFormat ("#.###");
    StringBuilder result = new StringBuilder ("[ ");
    for (Node x = first; x != null; x = x.next) {
      result.append (format.format (x.item));
      result.append (" ");
    }
    result.append ("]");
    return result.toString ();
  }

  /* Method to create lists */
  public static PlaygroundInsert of(String s) {
    Node first = null;
    String[] nums = s.split (" ");
    for (int i = nums.length-1; i >= 0; i--) {
      try { 
        double num = Double.parseDouble (nums[i]); 
        first = new Node (num, first);      
      } catch (NumberFormatException e) {
        // ignore anything that is not a double
      }
    }
    PlaygroundInsert result = new PlaygroundInsert ();
    result.first = first;
    return result;
  }

  private static void testInsert (String expected, String list, double item) {
    PlaygroundInsert aList = PlaygroundInsert.of (list); 
    aList.insert (item);
    String actual = aList.toString ();
    if (! expected.equals (actual)) {
      StdOut.format ("Failed [%s].insert(%f): Expecting (%s) Actual (%s)\n", list, item, expected, actual);
    }
  }
  public static void main (String[] args) {
    testInsert ("[ 11 ]", "", 11);
    testInsert ("[ 11 21 31 41 51 ]", "21 31 41 51", 11);
    testInsert ("[ 11 21 31 41 51 ]", "11 31 41 51", 21);
    testInsert ("[ 11 21 31 41 51 ]", "11 21 41 51", 31);
    testInsert ("[ 11 21 31 41 51 ]", "11 21 31 51", 41);
    testInsert ("[ 11 21 31 41 51 ]", "11 21 31 41", 51);
    StdOut.println ("Finished tests");
  }
  public static void main2 (String[] args) {
    Trace.drawStepsOfMethod ("insert");
    Trace.drawStepsOfMethod ("insertH");
    Trace.run ();
    PlaygroundInsert list = PlaygroundInsert.of ("11 21 31 41");
    list.insert (25);
    StdOut.println ("result: " + list);
  }
}