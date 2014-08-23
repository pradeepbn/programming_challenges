import java.util.Comparator;

public class kdTree {
    private int size;
    private Comparator cmpY;
    private Comparator cmpX;
    private double xmax = 1;
    private double ymax = 1;
    private double xmin = 0;
    private double ymin = 0;

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
        rectHV rect = null;
        kdNode = insert(kdNode, p, 0, rect);
    }
    /*
     * Orientation refers to the parent orientation
     * Every child should reverse orientation passed by the parent
     */
    private Node insert(Node node, point2D point, int orientation, RectHV rect, int cmp) {
        int cmp;

        if (orientation == 0) { orientation = 1; }
        else if (orientation == 1) { orientation = 0; }

        if (node == null) {
            node = new Node(point);
            size++;
            //create a rect using the orientation
            if (rect == null) {
                node.rect = new RectHV(xmin, ymin, xmax, ymax);
                return node;
            }
            double xpmin = node.rect.xmin();
            double ypmin = node.rect.ymin();
            double xpmax = node.rect.xmax();
            double ypmax = node.rect.ymax();

            if (orientation == 0) {
                //create a vertical split rect
                if (cmp == -1) {
                    node.rect = new RectHV(xpmin, ypmin, point.x(), ypmax);
                } else if (cmp == 1) {
                    node.rect = new RectHV(point.x(), ypmin, xpmax, ypmax);
                }
            else if (orientation == 1) {
                //create a horizontal split rect
                if (cmp == -1) {
                    node.rect = new RectHV(xpmin, ypmin, xpmax, point.y());
                } else if (cmp == 1) {
                    node.rect = new RectHV(xpmin, point.y(), xpmax, ypmax);
                }
            }
            return node;
        }

        if (orientation == 0) {
            //check if the point lies on the up or down of the axis
            cmpY = point.Y_ORDER;
            cmp = cmpY.compare(point, node.p);
        else if (orientation == 1) {
            //check if the point lies on the right or left of the axis
            cmpX = point.X_ORDER;
            cmp = cmpX.compare(point, node.p);
        }

        if (cmp == -1) {
           node.lb = insert(node.lb, point, orientation, node.rect, cmp);
        } else if (cmp == 1) {
           node.rt = insert(node.rt, point, orientation, node.rect, cmp);
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
        int orientation = 0;
        int count = 1;
        int level = 1;
        for (Node node : levelOrder()) {
            if (count > level) {
                level = level << 1;
                orientation = orientation ? 0 : 1;
            }
            if (orientation == 0) {
                //draw blue
                StdDraw.setPenColor(StdDraw.Blue);
                node.rect.draw();
                StdDraw.setPenColor(StdDraw.Black);
                node.p.draw();
            } else {
                //draw red
                StdDraw.setPenColor(StdDraw.Red);
                node.rect.draw();
                StdDraw.setPenColor(StdDraw.Black);
                node.p.draw();
            }
            count++;
        }
    }

    private Iterable<Key> levelOrder() {
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
        return keys;
    }

    public Iterable<Point2D> range(RectHV rect) {
    // all points in the set that are inside the rectangle
    }
    public Point2D nearest(Point2D p) {
    // a nearest neighbor in the set to p; null if set is empty
    }
}
