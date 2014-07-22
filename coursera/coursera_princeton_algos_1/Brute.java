public class Brute {
    public static void main(String[] args) {
        In in = new In("/home/pbaligan/Projects/programming_challenges/coursera/coursera_princeton_algos_1/collinear/vertical25.txt");
        int N = in.readInt();         // Number of points
        Point[] p = new Point[N];
        Point min;// = new Point();
        Point max;
        //StdDraw.setPenRadius(.01);
        
        StdDraw.setXscale(0, 32276);
        StdDraw.setYscale(0, 32278);
        //StdDraw.show(0);
        
        for (int i = 0; i < N; i++) {
            //System.out.println(in.readInt() + " " + in.readInt());
            p[i] = new Point(in.readInt(), in.readInt());
            /*if (i > 0) {
                p[i].drawTo(p[i - 1]);
            }*/
            //System.out.println(p[i].toString());
        }
        for (int i = 0; i < (N - 3); i++) {
            for (int j = i + 1; j < (N - 2); j++) {
                for (int k = j + 1; k < (N - 1); k++) {
                    if ((p[i].slopeTo(p[j]) != p[j].slopeTo(p[k]))) {
                        continue;
                    }
                    for (int l = k + 1; l < (N); l++) {
                        min = p[i];
                        max = p[i];
                        if (min.compareTo(p[j]) == -1) {
                            min = p[j];
                        } 
                        if (max.compareTo(p[j]) == 1) {
                            max = p[j];
                        }
                        if (min.compareTo(p[k]) == -1) {
                            min = p[k];
                        } 
                        if (max.compareTo(p[k]) == 1) {
                            max = p[k];
                        }                        
                        if (min.compareTo(p[l]) == -1) {
                            min = p[l];
                        } 
                        if (max.compareTo(p[l]) == 1) {
                            max = p[l];
                        }
                        if ((p[i].slopeTo(p[j]) == p[j].slopeTo(p[k])) &&
                            (p[j].slopeTo(p[k]) == p[k].slopeTo(p[l]))) {
                            //StdDraw.setPenRadius(.001);
                            StdDraw.setPenColor(StdDraw.BLACK);
                            p[i].draw();
                            p[j].draw();
                            p[k].draw();
                            p[l].draw();
                            //StdDraw.setPenRadius(.001);
                            StdDraw.setPenColor(StdDraw.BLUE);
                            //p[i].drawTo(p[l]);
                            min.drawTo(max);
                            //System.out.println((i + 1) + "->" + (j + 1) + "->" + (k + 1) + "->" + (l + 1));
                            //System.out.println(p[i].toString() + "->" + p[j].toString() + "->" + p[k].toString() + "->" + p[l].toString());
                            //System.out.println(p[i].slopeTo(p[j]) + "->" + p[j].slopeTo(p[k]) + "->" + p[k].slopeTo(p[l]));
                        }
                    }
                }
            }
        }
        StdDraw.show(0);
    }
}
