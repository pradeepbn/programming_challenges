public class kdTree {
	private int size;

	private static class Node {
	   private Point2D p;      // the point
	   private RectHV rect;    // the axis-aligned rectangle corresponding to this node
	   private Node lb;        // the left/bottom subtree
	   private Node rt;        // the right/top subtree

	   public Node(point2D point) {
		   p = point;
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
		return size;
	}
	public void insert(Point2D p) {
		// add the point p to the set (if it is not already in the set)
		// 1 - vertical orientation; 0 - horizontal orientation
		Node node;
		kdNode = insert(kdNode, p, 0);
	}
	/* 
	 * Orientation refers to the parent orientation
	 * Every child should reverse orientation passed by the parent
	 */
	private Node insert(Node node, point2D point, int orientation) {

		if (node == null) {
		   	node = new Node(point);
			size++;
			//create a rect using the orientation
			if (orientation == 0) {
			} else {
			}

			return node;
		}
		int cmp = node.p.compare(point);

		if (cmp == -1) {
			if (orientation == 0) { orientation = 1; }
			else { orientation = 0; }
		   	node.lb = insert(node.lb, point, orientation);
		} else if (cmp == 1) {
			if (orientation == 0) { orientation = 1; }
			else { orientation = 0; }
		   	node.rt = insert(node.rt, point, orientation);
	   	}
		return node;
	}
	public boolean contains(Point2D p) {
	// does the set contain the point p?
		Node node = kdNode;
		while (node != null) {
			if (node.p.compare(p) == 0) {
				return true;
			} else if (node.p.compare(p) == -1) {
				node = kdNode.lb;
			} else if (node.p.compare(p) == 1) {
				node = kdNode.rt;
			}
			return false;
		}
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
