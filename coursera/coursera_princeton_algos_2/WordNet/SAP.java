import java.util.*;

public class SAP {
   // constructor takes a digraph (not necessarily a DAG)
   private Digraph iGraph;
   public SAP(Digraph G) {
	   iGraph = new Digraph(G);
	}

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w) {
		List<Integer> array = new ArrayList<Integer>();
		array.add(v);
		array.add(w);
		DeluxeBFS dBfs = new DeluxeBFS(iGraph, array);
   		return dBfs.length();
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w) {
		List<Integer> array = new ArrayList<Integer>();
		array.add(v);
		array.add(w);
		DeluxeBFS dBfs = new DeluxeBFS(iGraph, array);
   		return dBfs.ancestor();
   }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w) {
   		return 0;
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
   		return 0;
   }

   // do unit testing of this class
	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		while (!StdIn.isEmpty()) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			StdOut.println(sap.ancestor(v, w));
			StdOut.println(sap.length(v, w));
		}
	}
}
