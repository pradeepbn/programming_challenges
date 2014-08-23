import java.util.Comparator;

public class kdTree {
    private int size;
    private Comparator<Point2D> cmpY;
    private Comparator<Point2D> cmpX;
    private double xmax = 1;
    private double ymax = 1;
    private double xmin = 0;
    private double ymin = 0;

    private static class Node {
       private Point2D p;      // the point
       private RectHV rect;    // the axis-aligned rectangle corresponding to this node
       private Node lb;        // the left/bottom subtree
       private Node rt;        // the right/top subtree
	   private char orientation;

       public Node(Point2D point) {
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
	   return false;
    }
    public int size() {
        // number of points in the set
        return size;
    }
    public void insert(Point2D p) {
        // add the point p to the set (if it is not already in the set)
        // 1 - vertical orientation; 0 - horizontal orientation
        //RectHV rect = null;
		
		//System.out.println("Point-" + p.x() + "," + p.y());
        kdNode = insert(kdNode, p, 1, null, 0);
    }
    /*
     * Orientation refers to the parent orientation
     * Every child should reverse orientation passed by the parent
     */
    private Node insert(Node node, Point2D point, int orientation, 
						Node parent, int cmp) {

        //if (orientation == 0) { orientation = 1; }
        //else if (orientation == 1) { orientation = 0; }

        if (node == null) {
            node = new Node(point);
            size++;
            //create a rect using the orientation
			node.orientation = (char)orientation;
            if (parent == null) {
                node.rect = new RectHV(xmin, ymin, xmax, ymax);
                return node;
            }
            double xpmin = parent.rect.xmin();
            double ypmin = parent.rect.ymin();
            double xpmax = parent.rect.xmax();
            double ypmax = parent.rect.ymax();

            if (orientation == 0) {
                //create a vertical split rect
                if (cmp == -1) {
					//System.out.println("Left");
                    node.rect = new RectHV(xpmin, ypmin, parent.p.x(), ypmax);
                } else if (cmp == 1) {
					//System.out.println("Right");
                    node.rect = new RectHV(parent.p.x(), ypmin, xpmax, ypmax);
                }
			} else if (orientation == 1) {
                //create a horizontal split rect
                if (cmp == -1) {
					//System.out.println("Down");
                    node.rect = new RectHV(xpmin, ypmin, xpmax, parent.p.y());
                } else if (cmp == 1) {
					//System.out.println("Up");
                    node.rect = new RectHV(xpmin, parent.p.y(), xpmax, ypmax);
                }
            }
			//System.out.println("xmin:" + node.rect.xmin() +
			//		" ymin:" + node.rect.ymin() + " xmax" + node.rect.xmax() +
			//		" ymax:" + node.rect.ymax());
		    //System.out.println("Ornt" + node.orientation);
			//System.out.println("Point add-" + point.x() + "," + point.y());
            return node;
        }

        if (orientation == 0) {
            //check if the point lies on the up or down of the axis
            cmp = Point2D.Y_ORDER.compare(point, node.p);
			cmp = (cmp == 0) ? 1 : cmp;
			//System.out.println("Checking Up/ Down:" + cmp);
		} else if (orientation == 1) {
            //check if the point lies on the right or left of the axis
            //cmpX = point.X_ORDER;
			//System.out.println("Nodex:" + node.p.x() + "Nodey:" + node.p.y());
            cmp = Point2D.X_ORDER.compare(point, node.p);
			cmp = (cmp == 0) ? 1 : cmp;
			//System.out.println("Checking Right/ left:" + cmp);
        }

		orientation = (orientation != 0) ? 0 : 1;
        if (cmp == -1) {
           node.lb = insert(node.lb, point, orientation, node, cmp);
        } else if (cmp == 1) {
			//System.out.println("Node:" + node);
           node.rt = insert(node.rt, point, orientation, node, cmp);
        }

		//System.out.println();
        return node;
    }
    public boolean contains(Point2D p) {
    // does the set contain the point p?
        //Node node = kdNode;
        //while (node != null) {
        //    if (node.p.compare(p) == 0) {
        //        return true;
        //    } else if (node.p.compare(p) == -1) {
        //        node = kdNode.lb;
        //    } else if (node.p.compare(p) == 1) {
        //        node = kdNode.rt;
        //    }
        //    return false;
        //}
		return false;
    }
    public void draw() {
    // draw all of the points to standard draw
        int orientation = 0;
        int count = 0;
        //int level = 1;
		StdDraw.setPenColor(StdDraw.BLACK);
		kdNode.rect.draw();
        for (Node node : levelOrder()) {
            //if (count > level) {
            //    level = level << 1;
            //    orientation = (orientation != 0) ? 0 : 1;
            //}
            if (node.orientation == 0) {
                //draw blue
				StdDraw.setPenRadius(.001);
                StdDraw.setPenColor(StdDraw.BLUE);
                //node.rect.draw();
				StdDraw.line(node.rect.xmin(), node.p.y(), 
							node.rect.xmax(), node.p.y());
				StdDraw.setPenRadius(.01);
                StdDraw.setPenColor(StdDraw.BLACK);
                node.p.draw();
            } else {
                //draw red
				StdDraw.setPenRadius(.001);
                StdDraw.setPenColor(StdDraw.RED);
				StdDraw.line(node.p.x(), node.rect.ymin(),
							node.p.x(), node.rect.ymax());
                //node.rect.draw();
				StdDraw.setPenRadius(.01);
                StdDraw.setPenColor(StdDraw.BLACK);
                node.p.draw();
            }
            count++;
        }
		//System.out.println("draw():" + count);
    }

    private Iterable<Node> levelOrder() {
        Queue<Node> keys = new Queue<Node>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(kdNode);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            keys.enqueue(x);
            queue.enqueue(x.lb);
            queue.enqueue(x.rt);
        }
		//System.out.println("Keys size:" + keys.size());
        return keys;
    }

    public Iterable<Point2D> range(RectHV rect) {
    // all points in the set that are inside the rectangle
		return null;
    }
    public Point2D nearest(Point2D p) {
    // a nearest neighbor in the set to p; null if set is empty
		return null;
    }
}
