/**
 * 
 */
import java.util.*;
import java.io.*;

/**
 * @author nadeem
 * 
 */
public class polycake {
	Point[] pts;
	int N, Y;
	boolean DEBUG = false;
	
	public static void main(String[] args) throws Exception {
		new polycake();

	}
	public polycake() throws Exception {
		Scanner sc = new Scanner(new File("polycake.in"));
		int T = sc.nextInt();
		for(int t = 1; t <= T; t++) {
		    N = sc.nextInt();
		    Y = sc.nextInt();
		    pts = new Point[N];
		    for(int i = 0; i < N; i++)
			pts[i] = new Point(sc.nextDouble(), sc.nextDouble());
		    runCase(t);
		    System.out.println();
		}
	}

	// Solve a single case. Note that this algorithm takes advantage of the special
	// constraints of the problem, which rules out situations like a cut going through
	// a vertex or a completely horizontal side.
	void runCase(int caseNum) {
		// The two polygons we will get after clipping.
		ArrayList<ArrayList<Point>> polygons = new ArrayList<ArrayList<Point>>();
		polygons.add(new ArrayList<Point>());
		polygons.add(new ArrayList<Point>());
		
		int p = 0;	// The current polygon.
		for (int i = 0; i < N; i++) {
			Point a = pts[i], b = pts[(i + 1) % N];
			add(polygons, p, a);
			if (intersects(a, b, Y)) {
				Point c = intersection(a, b, Y);
				add(polygons, p, c);
				p = 1 - p;
				add(polygons, p, c);
			}
		}

		if(DEBUG) 
			for(ArrayList<Point> poly: polygons)
				System.out.println(poly);
		
		double[] ret = new double[2];
		for (int i = 0; i < 2; i++)
			ret[i] = perimeter(polygons.get(i));
		
		// Need to print in order.
		if (ret[0] > ret[1]) {
			double t = ret[0];
			ret[0] = ret[1];
			ret[1] = t;
		}

		System.out.printf("Case #%d: %.3f %.3f\n", caseNum, ret[0] + EPS, ret[1] + EPS);
	}

	double perimeter(ArrayList<Point> polygon) {
		double ret = 0;
		int P = polygon.size();
		for (int i = 0; i < P; i++) {
			Point a = polygon.get(i), b = polygon.get((i + 1) % P);
			ret += a.sub(b).length();
		}
		return ret;
	}

	// Returns true if the line segment AB is intersected by a horizontal line.
	boolean intersects(Point a, Point b, int y) {
		return a.y <= y && y <= b.y || b.y <= y && y <= a.y;
	}

	// Assumes that an intersection DOES take place. Results
	// may not be valid if called otherwise.
	Point intersection(Point a, Point b, int y) {
		// Side ab is horizontal too.
		if (Math.abs(a.y - b.y) < EPS)
			return a;
		// Otherwise.
		double t = (y - a.y) / (b.y - a.y);
		return new Point(a.x + t * (b.x - a.x), y);
	}

	// Add point to the right list, ensuring that it isn't equal to the last
	// element.
	static void add(ArrayList<ArrayList<Point>> polygons, int ind, Point p) {
		add(polygons.get(ind), p);
	}

	// Add point to a list, ensuring that it isn't equal to the last element.
	static void add(ArrayList<Point> polygon, Point p) {
		if (polygon.isEmpty() || !p.equals(last(polygon)))
			polygon.add(p);
	}

	// Last element of a polygon.
	static Point last(ArrayList<Point> polygon) {
		assert polygon.size() > 0;
		return polygon.get(polygon.size() - 1);
	}

	final double EPS = 1e-9;

	class Point {
		public double x, y;
		public Point(double x, double y) { this.x = x; this.y = y; }
		public double dot(Point p) { return x * p.x + y * p.y; }
		public Point sub(Point p) { return new Point(x - p.x, y - p.y); }
		public double length() { return Math.sqrt(dot(this)); }
		public boolean equals(Point p) { return sub(p).length() < EPS; }
		public String toString() { return String.format("(%f, %f)", x, y); }
	}

}