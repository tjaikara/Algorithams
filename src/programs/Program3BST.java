package programs;
import stdlib.*;
import algs13.Queue;
/* ***********************************************************************
 *
 *  This is an implementation of a binary search tree.
 *  For the third program, you are to modify this class
 *  as described in the assignment.
 *
 *************************************************************************/
public class Program3BST<K extends Comparable<? super K>, V> {
    private Node<K,V> root;             // root of BST

    private static class Node<K extends Comparable<? super K>,V> {
        public K key;       // sorted by key
        public V val;             // associated data
        public Node<K,V> left, right;  // left and right subtrees
        public int height; //storing the height of that node
        public int size; //storing the size of the tree at that node

        public Node(K key, V val, int N) {
            this.key = key;
            this.val = val;
            this.height = 0;
            this.size = 0;
        }
    }

    // is the symbol table empty?
    public boolean isEmpty() { return root == null; }

    /* *********************************************************************
     *  Search BST for given key, and return associated value if found,
     *  return null if not found
     ***********************************************************************/
    // does there exist a key-value pair with given key?
    public boolean contains(K key) {
        return get(key) != null;
    }

    // return value associated with the given key, or null if no such key exists
    public V get(K key) { return get(root, key); }

    private V get(Node<K,V> x, K key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }

    /* *********************************************************************
     *  Insert key-value pair into BST
     *  If key already exists, update with new value
     ***********************************************************************/
    public void put(K key, V val) {
        if (val == null) { delete(key); return; }
        root = put(root, key, val);
    }

    private Node<K,V> put(Node<K,V> x, K key, V val) {
        if (x == null) return new Node<>(key, val, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0)
            x.left  = put(x.left,  key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else
            x.val   = val;
        return x;
    }

    public void delete(K key) {
        root = delete(root, key);
    }

    private Node<K,V> delete(Node<K,V> x, K key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            // Key to delete is to the left of x.
            x.left  = delete(x.left,  key);
            return x;
        }
        else if (cmp > 0) {
            // Key to delete is to the right of x.
            x.right = delete(x.right, key);
            return x;
        }
        else {
            // x contains the key we wish to delete.
            if (x.left == null && x.right == null) {
                // x is a leaf.
                return null;
            }
            else if (x.left == null) {
                // x has only a right child.
                return x.right;
            }
            else if (x.right == null) {
                // x has only a left child.
                return x.left;
            }
            else {
                // x has two children.
                Node<K,V> leftTreeMaxNode = x.left;
                // Find the node with the largest key to the left.
                while (leftTreeMaxNode.right != null) {
                    leftTreeMaxNode = leftTreeMaxNode.right;
                }
                // Copy that largest key and its value to x.
                K leftTreeMaxKey = leftTreeMaxNode.key;
                x.key = leftTreeMaxNode.key;
                x.val = leftTreeMaxNode.val;
                // Delete the node copied from.
                x.right = delete(x.right, leftTreeMaxKey);
                return x;
            }
        }
    }

    public void drawTree() {
        if (root != null) {
            StdDraw.setPenColor (StdDraw.BLACK);
            StdDraw.setCanvasSize(1200,700);
            setSizes();
            setHeights();
            drawTree(root, .5, 1, .45, 0);
        }
    }

    private void drawTree (Node<K,V> n, double x, double y, double range, int depth) {
        int CUTOFF = 10;
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.text(x, y, n.key.toString() + ":h" + n.height + ":s" + n.size);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius (.007);
        if (n.left != null && depth != CUTOFF) {
            StdDraw.line (x-range, y-.08, x-.01, y-.01);
            drawTree (n.left, x-range, y-.1, range*.5, depth+1);
        }
        if (n.right != null && depth != CUTOFF) {
            StdDraw.line (x+range, y-.08, x+.01, y-.01);
            drawTree (n.right, x+range, y-.1, range*.5, depth+1);
        }
    }

    private void setHeights() {
        if(root != null)
            setHeight(root);
    }

    /*
     * This helper method will traverses the entire tree and set height filed for each node
     * node - > size = 1 + max of child nodes
     */
    private int setHeight(Node<K, V> x){

        if(x == null)
            return -1;

        return x.height = 1 + Math.max(setHeight(x.left), setHeight(x.right));
    }

    private void setSizes() {
        if(root != null)
            setSize(root);
    }

    /*
     * This helper method will traverses the entire tree and set size filed for each node
     * node - > size = 1 + size of left child node and right child node
     */
    private int setSize(Node<K, V> x){

        if(x == null)
            return 0;

        return  x.size = 1 + setSize(x.right) + setSize(x.left);
    }

    /* ***************************************************************************
     *  Test client
     *****************************************************************************/
    public static void main(String[] args) {
        StdIn.fromString ("D F B G E A C");
        Program3BST<String, Integer> st = new Program3BST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        st.drawTree ();
        StdDraw.save("data/Program3Balancedtree.png");

        StdIn.fromString ("G A B C E F D H");
        st = new Program3BST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        st.drawTree ();
        StdDraw.save("data/Program3Unbalancedtree.png");
    }
}
