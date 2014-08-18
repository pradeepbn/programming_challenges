public class PointSetVisualizer {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();         // Number of points
        StdDraw.show(0);
        PointSET pSet = new PointSET();
        StdDraw.setXscale();
        StdDraw.setYscale();
		Point2D p = new Point2D(x, y);
        for (int i = 0; i < N; i++) {
            p[i] = new Point(in.readInt(), in.readInt());
            StdDraw.setPenColor(StdDraw.BLACK);
			pSet.insert(p);
        }
		StdDraw.clear();
		pSet.draw();
        //while (true) {
        //    if (StdDraw.mousePressed()) {
        //        double x = StdDraw.mouseX();
        //        double y = StdDraw.mouseY();
        //        System.out.printf("%8.6f %8.6f\n", x, y);
        //        Point2D p = new Point2D(x, y);
        //        pSet.insert(p);
        //        StdDraw.clear();
        //        pSet.draw();
        //    }
        //    StdDraw.show(50);
        //}
		StdDraw.show(50);

    }
}
