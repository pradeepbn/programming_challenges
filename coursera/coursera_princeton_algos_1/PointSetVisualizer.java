public class PointSetVisualizer {
    public static void main(String[] args) {
        In in = new In(args[0]);
        //StdDraw.show(0);
        PointSET pSet = new PointSET();
        //StdDraw.setXscale();
        //StdDraw.setYscale();
		Point2D p;
        RectHV rect = new RectHV(0, 0, 0.5, 0.5);
		Point2D queryPoint = new Point2D(1, 1);
        while (!in.isEmpty()) {
            p = new Point2D(in.readDouble(), in.readDouble());
			pSet.insert(p);
        }
		//StdDraw.clear();
        //StdDraw.setPenColor(StdDraw.BLACK);
		//pSet.draw();
		//StdDraw.show(50);
        for (Point2D point : pSet.range(rect)) {
            System.out.println(point.x() + " " + point.y());
        }

		p = pSet.nearest(queryPoint);

		if (p != null) {
			System.out.println("Nearest Point to (0.5,0.5):" + "(" + p.x() + "," + p.y() + ")");
		} else {
			System.out.println("No search point found");
		}
    }
}
