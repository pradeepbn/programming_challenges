import java.util.*;

public class SAP {
   // constructor takes a digraph (not necessarily a DAG)
   private Digraph iGraph;
   boolean graphProcessed; 
   private ST<Integer, Integer> st = new ST<Integer, Integer>();
   public SAP(Digraph G) {
	   graphProcessed = false;
	   iGraph = new Digraph(G);
	}

   private processorShortestAncestor(int v, int w) {
		private static final int INFINITY = Integer.MAX_VALUE;
		Digraph lGraph1 = new Digraph();
		Digraph lGraph2 = new Digraph();
		BreadthFirstDirectedPaths bfs1;
		BreadthFirstDirectedPaths bfs2;
		Queue<Integer> q1 = new Queue<Integer>();	
		Queue<Integer> q2 = new Queue<Integer>();	
		Queue<Integer> commonNode = new Queue<Integer>();
		int cv = INFINITY;//INFINI

		graphProcessed= true;
		q1.enqueue(v):
		q2.enqueue(w):
		while (!q1.empty()) {
			int m = q1.dequeue();
			for (int x : iGraph.adj[m]) {
				q1.enqueue(w);
				if (bfs2.hasPathTo(x)) {
					commonNode.enqueue(x);
				}
				lGraph1.addEdge(m, x);
			}
			bfs1 = new BreadthFirstDirectedPaths(lGraph1, v);
		}

		while (!q2.empty()) {
			int n = q2.dequeue();
			for (int y : iGraph.adj[n]) {
				q2.enqueue(w);
				if (bfs1.hasPathTo(y)) {
					commonNode.enqueue(y);
				}
				lGraph2.addEdge(n, y);
			}
			bfs2 = new BreadthFirstDirectedPaths(lGraph2, w);

			//Check if there are any common nodes between bfs1 and bfs2 in the current level
			while (!commonNode.empty()) {
				cv = commonNode.dequeue();
				int distToCV = bfs1.distTo(cv) + bfs2.distTo(cv);
				st.put(cv, distToCV);
			}
		}

		// Process the graph one level lower until there is no common node
		//get EdgeTo from the upper one
		Stack<Integer> lowerLevelVertex;
		int lCV; // local common vertex variable
		int ldistToCV;
		if (cv < INFINITY) {
			lowerLevelVertex = bfs1.pathTo(cv);
			while (!lowerLevelVertex.empty()) {
				lCV = lowerLevelVertex.pop();
				if (!bfs2.hasPathTo(lCV)) {
					break;
				}
				ldistToCV = bfs1.distTo(lCV) + bfs2.distTo(lCV);
				st.put(lCV, distToCV);
			}
		}
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w) {
		//List<Integer> array = new ArrayList<Integer>();
		//array.add(v);
		//array.add(w);
		//DeluxeBFS dBfs = new DeluxeBFS(iGraph, array);
   		//return dBfs.length();
		if (!graphProcessed) {
			processorShortestAncestor(v, w);
		}
		return st.get(st.min());
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w) {
		//List<Integer> array = new ArrayList<Integer>();
		//array.add(v);
		//array.add(w);
		//DeluxeBFS dBfs = new DeluxeBFS(iGraph, array);
   		//return dBfs.ancestor();
		if (!graphProcessed) {
			processorShortestAncestor(v, w);
		}
		return st.min();
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
