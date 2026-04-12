import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	public static class Edge {
		int from, to, cost;

		Edge(int from, int to, int cost) {
			this.from = from;
			this.to = to;
			this.cost = cost;
		}
	}

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static boolean bellmanFord(int n, List<Edge> edges) {
		int[] dist = new int[n + 1];

		for (int i = 1; i <= n; i++) {
			boolean updated = false;

			for (Edge e : edges) {
				if (dist[e.to] > dist[e.from] + e.cost) {
					dist[e.to] = dist[e.from] + e.cost;
					updated = true;

					if (i == n) {
						return true;
					}
				}
			}

			if (!updated) break;
		}

		return false;
	}

	public static void main(String[] args) throws IOException {
		int TC = Integer.parseInt(br.readLine());

		while (TC-- > 0) {
			st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());

			List<Edge> edges = new ArrayList<>();

			// 도로
			for (int i = 0; i < m; i++) {
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken());
				int e = Integer.parseInt(st.nextToken());
				int t = Integer.parseInt(st.nextToken());

				edges.add(new Edge(s, e, t));
				edges.add(new Edge(e, s, t));
			}

			// 웜홀
			for (int i = 0; i < w; i++) {
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken());
				int e = Integer.parseInt(st.nextToken());
				int t = Integer.parseInt(st.nextToken());

				edges.add(new Edge(s, e, -t));
			}

			System.out.println(bellmanFord(n, edges) ? "YES" : "NO");
		}
	}
}