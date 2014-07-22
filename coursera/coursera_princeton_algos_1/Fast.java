//import java.util.HashMap;
//import java.util.Map;
import java.util.Arrays;
public class Fast {
    public static void main(String[] args) {
        In in = new In("/home/pbaligan/Projects/programming_challenges/coursera/coursera_princeton_algos_1/collinear/input10.txt");
        int N = in.readInt();         // Number of points
        Point[] p = new Point[N];
        double[] slopes = new double[N];
        //Map
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
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                //if (j != i) {
                    slopes[j] = p[i].slopeTo(p[j]);
                //}
            }
            
            Arrays.sort(slopes);
            for (int k = 0; k < N; k++) {
                System.out.print(slopes[k] + ",");
            }
            System.out.println();
        }
    }
}
