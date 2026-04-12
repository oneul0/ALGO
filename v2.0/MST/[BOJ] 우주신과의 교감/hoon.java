import java.io.*;
import java.util.*;

public class Main {
	public static class Coord {
		int x;
		int y;
		Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static class Edge implements Comparable<Edge> {
		int to;
		double weight;

		Edge(int to, double weight) {
			this.to = to;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge o) {
			return Double.compare(this.weight, o.weight);
		}
	}

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static List<Coord> coords;
	static boolean[][] isConnected;
	static int N;
	static int M;

	public static double getDist(int curIndex, int nextIndex) {
		Coord curCoord = coords.get(curIndex);
		Coord nextCoord = coords.get(nextIndex);

		double dx = nextCoord.x - curCoord.x;
		double dy = nextCoord.y - curCoord.y;

		return Math.hypot(dx, dy);
	}

	public static double prim(int start) {
		boolean[] isVisited = new boolean[N + 1];

		PriorityQueue<Edge> pq = new PriorityQueue<>();

		pq.offer(new Edge(start, 0.0));

		double totalDist = 0.0;
		int visitedCount = 0;

		while (!pq.isEmpty()) {
			Edge currentEdge = pq.poll();
			int u = currentEdge.to;

			if (isVisited[u]) continue;

			isVisited[u] = true;
			totalDist += currentEdge.weight;
			visitedCount++;

			if (visitedCount == N) break;

			for (int v = 1; v <= N; v++) {
				if (u == v || isVisited[v]) continue;

				double weight;

				if (isConnected[u][v]) {
					weight = 0.0;
				} else {
					weight = getDist(u, v);
				}

				pq.offer(new Edge(v, weight));
			}
		}
		return totalDist;
	}

	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		isConnected = new boolean[N + 1][N + 1];
		coords = new ArrayList<>(N + 1);

		coords.add(null);

		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			coords.add(new Coord(x, y));
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			isConnected[a][b] = true;
			isConnected[b][a] = true;
		}

		double result = prim(1);

		System.out.printf("%.2f", result);

		br.close();
	}
}