import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Knightmare {
	static final int INF = 987654321;
	static final int MIN = -(int)(1e9 + 10);
	static final int MAX = (int)(2e9 + 10);

	static class KTuple {
		public int[] keys;
		public long[] values;
		public int lazy;
		
		public KTuple(int K) {
			keys = new int[K];
			values = new long[K];
			Arrays.fill(keys, INF);
		}
		
		public KTuple(int K, int key) {
			this(K, key, 1);
		}
		
		public KTuple(int K, int key, long value) {
			this(K);
			put(0, key, value);
		}
		
		public KTuple(KTuple tuple) {
			this.keys = (int[])tuple.keys.clone();
			this.values = (long[])tuple.values.clone();
		}
		
		public boolean isLazy() {
			return lazy != 0;
		}
		
		public void increment(int delta) {
			for (int i = 0; i < keys.length; i++) { 
				if (keys[i] < INF) {
					keys[i] += delta;
				}
			}
		}
		
		void put(int idx, int key, long value) {
			keys[idx] = key;
			values[idx] = value;
		}
		
		static KTuple combine(KTuple a, KTuple b) {
			if (a == null) return new KTuple(b);
			if (b == null) return new KTuple(a);
			int K = a.keys.length;
			KTuple ret = new KTuple(K);
			int i = 0, j = 0;
			for(int k = 0; k < K; k++) {
				if(a.keys[i] == b.keys[j]) {
					ret.put(k, a.keys[i], a.values[i] + b.values[j]);
					i++;
					j++;
				} else if (a.keys[i] < b.keys[j]) {
					ret.put(k, a.keys[i], a.values[i]);
					i++;
				} else {
					ret.put(k, b.keys[j], b.values[j]);
					j++;
				}
			}
			return ret;
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < keys.length; i++) {
				if (i != 0) sb.append(", ");
				sb.append(String.format("(%d, %d)", keys[i], values[i]));
			}
			return sb.toString();
		}
	}
	
	
	static class KMinIntervalTree {
		KTuple[] tree;
		int N, K;
		
		public KMinIntervalTree(int N, int K) {
			this.N = N;
			this.K = K;
			tree = new KTuple[4*N];
		}
		
		public KMinIntervalTree(long[] input, int K) {
			this(input.length, K);
			build(input);
		}
		
		public void build(long[] input) {
			build(1, 0, N-1, input);
		}
		
		public void build(int node, int start, int end, long[] input) {
			if (start == end) {
				tree[node] = new KTuple(K, 0, input[start]);
				return;
			}
			int mid = (start + end) / 2;
			build(2 * node, start, mid, input);
			build(2 * node + 1, mid + 1, end, input);
			tree[node] = KTuple.combine(tree[2 * node], tree[2 * node + 1]);
		}
		
		public KTuple query(int from, int to) {
			return query(1, 0, N-1, from, to);
		}
		
		KTuple query(int node, int start, int end, int from, int to) {
			if (start > end || end < from || start > to) {
				return null;
			}
			if (tree[node].isLazy()) {
				tree[node].increment(tree[node].lazy);
				if (start != end) {
					tree[2 * node].lazy += tree[node].lazy;
					tree[2 * node + 1].lazy += tree[node].lazy;
				}
				tree[node].lazy = 0;
			}
			if (from <= start && end <= to) {
				return tree[node];
			}
			int mid = (start + end) / 2;
			KTuple left = query(2 * node, start, mid, from, to);
			KTuple right = query(2 * node + 1, mid + 1, end, from, to);
			return KTuple.combine(left, right);
		}
		
		public void update(int from, int to, int value) {
			update(1, 0, N-1, from, to, value);
		}
		
		void update(int node, int start, int end, int from, int to, int value) {
			if (tree[node].isLazy()) {
				increment(node, start, end, tree[node].lazy);
				tree[node].lazy = 0;

			}
			if (start > end || end < from || start > to) {
				return;
			}
			
			if (from <= start && end <= to) {
				increment(node, start, end, value);
				return;
			}
			if (start == end) {
				tree[node].increment(value);
				return;
			}
			int mid = (start + end) / 2;
			update(2 * node, start, mid, from, to, value);
			update(2 * node + 1, mid + 1, end, from, to, value);
			tree[node] = KTuple.combine(tree[2 * node], tree[2 * node + 1]);
		}
		
		void increment(int node, int start, int end, int delta) {
			tree[node].increment(delta);
			if (start != end) {
				tree[2 * node].lazy += delta;
				tree[2 * node + 1].lazy += delta;
			}
		}
	}
	
	static class Rectangle {
		int x, y, xx, yy;
		public Rectangle(int x, int y, int xx, int yy) {
			this.x = Math.min(x, xx);
			this.xx = Math.max(x, xx) + 1;
			this.y = Math.min(y, yy);
			this.yy = Math.max(y, yy);
		}
		
		public String toString() {
			return String.format("(%d, %d)-(%d, %d)", x, y, xx, yy);
		}
	}
	
	static class Event implements Comparable<Event> {
		int ystart, yend;
		int xpos;
		boolean entering;
		
		public Event(int xpos, int ystart, int yend, boolean entering) {
			this.xpos = xpos;
			this.ystart = ystart;
			this.yend = yend;
			this.entering = entering;
		}
		
		public int compareTo(Event other) {
			return new Integer(xpos).compareTo(other.xpos);
		}
		
		public String toString() {
			return String.format("(%d, [%d, %d]) %b", xpos, ystart, yend, entering);
		}
	}
	
	void addRectangles(List<Rectangle> rects, int r, int c, int a, int b) {
		if (a == b) {
			for (int sr = -1; sr <= 1; sr += 2) 
				for (int sc = -1; sc <= 1; sc += 2) {
					Rectangle rect = new Rectangle(r + sr, c + sc, r + sr * a, c + sc * a);
					addRectangle(rects, rect);
				}
			return;
		}
		if (a < b) {
			addRectangles(rects, r, c, b, a);
			return;
		}
		for (int sr = -1; sr <= 1; sr += 2)
			for (int sc = -1; sc <= 1; sc += 2) {
				addRectangle(rects, new Rectangle(r + sr, c + sc, r + sr * b, c + sc * a));
				addRectangle(rects, new Rectangle(r + sr * (b + 1), c + sc, r + sr * a, c + sc * b));
			}
	}
	
	Set<Integer> yset = new TreeSet<Integer>();
	Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
	void addRectangle(List<Rectangle> rects, Rectangle rect) {
		rects.add(rect);
		yset.add(rect.y);
		yset.add(rect.yy);
		yset.add(rect.yy+1);
	}
	
	long[] compress(List<Rectangle> rects) {
		int M = yset.size();
		long[] sizes = new long[M];
		
		M = 0;
		map.clear();
		for (int key: yset) {
			map.put(key, M);
			sizes[M++] = key;
		}
		
		for (int i = 0; i + 1 < M; i++) {
			sizes[i] = sizes[i + 1] - sizes[i];
		}
		sizes[M-1] = 1;
		return sizes;
	}
	
	List<Event> createEvents(List<Rectangle> rects) {
		List<Event> events = new ArrayList<Event>();
		for (Rectangle rect: rects) {
			int my = map.get(rect.y), myy = map.get(rect.yy);
			Event open = new Event(rect.x, my, myy, true);
			Event close = new Event(rect.xx, my, myy, false);
			events.add(open);
			events.add(close);
		}
		events.add(new Event(MIN, map.get(MIN), map.get(MAX), true));
		events.add(new Event(MAX+1, map.get(MIN), map.get(MAX), false));
		Collections.sort(events);
		return events;
	}
	
	long solve(List<Rectangle> rects) {
		long[] sizes = compress(rects);
		List<Event> events = createEvents(rects);
		KMinIntervalTree tree = new KMinIntervalTree(sizes, K);
		long ret = (long) MAX - (long) MIN + 1;
		ret *= ret;
		
		int E = events.size();
		long prevSweep = MIN;
		for (int i = 0; i < E; i++) {
			long currSweep = events.get(i).xpos;
			KTuple tuple = tree.query(0, sizes.length);
			for (int p = 0; p < K; p++) {
				if (tuple.keys[p] <= K) {
					ret -= tuple.values[p] * (currSweep - prevSweep);
				}
			}
			int j = i;
			for (; j < E && events.get(j).xpos == currSweep; j++) {
				Event evt = events.get(j);
				tree.update(evt.ystart, evt.yend, evt.entering ? 1 : -1);
			}
			i = j - 1;
			prevSweep = currSweep;
		}
				
		return ret;
	}
	
	int N, K;
	public Knightmare() throws Exception {
		Scanner sc = new Scanner(new File("knightmare.in"));
		int T = sc.nextInt();
		while (T-- > 0) {
			yset.clear();
			N = sc.nextInt();
			K = sc.nextInt();
			List<Rectangle> rects = new ArrayList<Rectangle>();
			for (int i = 0; i < N; i++) {
				addRectangles(rects, sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
			}
			yset.add(MIN);
			yset.add(MAX);			
			System.out.println(solve(rects));
		}
	}

	public static void main(String[] args) throws Exception {
		long time = -System.currentTimeMillis();
		new Knightmare();
		time += System.currentTimeMillis();
		System.err.printf("Total runtime = %.3fs\n", time / 1000.0);
	}
}
