public class kdTree {
	private int size;

	private static class Node {
	   private Point2D p;      // the point
	   private RectHV rect;    // the axis-aligned rectangle corresponding to this node
	   private Node lb;        // the left/bottom subtree
	   private Node rt;        // the right/top subtree

	   public Node() {
		   p = null;
		   rect = null;
		   lb = null;
		   rt = null;
	   }
	}

	private Node kdNode;

	public kdTree() {
	  // construct an empty set of points
	  kdNode = null;
	  size = 0;
	}
	public boolean isEmpty() {
	   // is the set empty?
	   if (kdNode == null) {
		   return true;
	   }
	}
	public int size() {
	// number of points in the set
	}
	public void insert(Point2D p) {
	// add the point p to the set (if it is not already in the set)
		Node node;
		if (kdNode == null) {
			kdNode = new kdNode();
			kdNode.p = p;
			node = kdNode;
		}

		if (splitAxis == 0) {
			node.rect = new rect();
		}
	}
	public boolean contains(Point2D p) {
	// does the set contain the point p?
	}
	public void draw() {
	// draw all of the points to standard draw
	}
	public Iterable<Point2D> range(RectHV rect) {
	// all points in the set that are inside the rectangle
	}
	public Point2D nearest(Point2D p) {
	// a nearest neighbor in the set to p; null if set is empty
	}
}
