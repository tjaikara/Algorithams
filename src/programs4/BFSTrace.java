package programs4;

import algs13.LinkedList;
import algs41.Graph;
import stdlib.In;

import java.util.Iterator;

public class BFSTrace {


    public static void bfsPrintTrace(Graph g){

        boolean visited [] = new boolean[g.V()];
        LinkedList<Integer> queue = new LinkedList<>();

        int s = 0;
        visited[s]= true;

        System.out.println( "Enqueuing "+s);
        queue.add(s);

        while (queue.size() != 0){

            s = dequeuing(queue);

            Iterable<Integer> iterable = g.adj(s);
            for(Integer n : iterable){
                if(!visited[n]){
                    visited[n] = true;
                    System.out.println( "Enqueuing "+n);
                    queue.add(n);
                }
            }
        }
    }

    public static int dequeuing(LinkedList<Integer> queue){

        int removedValue = 0;
        Iterator<Integer> iterator = queue.iterator();

        while(iterator.hasNext()){
            removedValue = iterator.next();
            break;
        }
        System.out.println( "Dequeuing "+removedValue);
        iterator.remove();
        return removedValue;
    }

    public static void main(String [] args){

        In input = new In("data/tinyG.txt");
        Graph g = new Graph(input);

        bfsPrintTrace(g);
    }
}
