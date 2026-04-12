import java.io.*;
import java.util.*;

public class Main {

	static class Node implements Comparable<Node> {
		int idx;
		double time;
		Node(int idx, double time) {
			this.idx = idx;
			this.time = time;
		}
		public int compareTo(Node o) {
			return Double.compare(this.time, o.time);
		}
	}

	//선형거리
	static double dist(double x1, double y1, double x2, double y2) {
		double dx = x1 - x2;
		double dy = y1 - y2;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		double sx = Double.parseDouble(st.nextToken());
		double sy = Double.parseDouble(st.nextToken());

		st = new StringTokenizer(br.readLine());
		double ex = Double.parseDouble(st.nextToken());
		double ey = Double.parseDouble(st.nextToken());

		int N = Integer.parseInt(br.readLine());

		// 좌표 저장 배열
		double[] x = new double[N + 2];
		double[] y = new double[N + 2];

		// 시작점
		x[0] = sx; y[0] = sy;

		// 대포 1~N
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			x[i] = Double.parseDouble(st.nextToken());
			y[i] = Double.parseDouble(st.nextToken());
		}

		// 도착점 = N+1
		x[N + 1] = ex; y[N + 1] = ey;

		int total = N + 2;

		// 그래프
		double[][] cost = new double[total][total];

		for (int i = 0; i < total; i++) {
			for (int j = 0; j < total; j++) {
				if (i == j) {
					cost[i][j] = 0;
					continue;
				}
				double d = dist(x[i], y[i], x[j], y[j]);

				// 걷기
				double walk = d / 5.0;

				if (i == 0) {
					// 시작점은 대포 발사 불가
					cost[i][j] = walk;
				} else {
					// 대포
					double cannon = 2.0 + Math.abs(d - 50.0) / 5.0;
					cost[i][j] = Math.min(walk, cannon);
				}
			}
		}

		// 다익스트라
		double[] dist = new double[total];
		Arrays.fill(dist, Double.MAX_VALUE);
		dist[0] = 0;

		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(0, 0));

		boolean[] visited = new boolean[total];

		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			int idx = cur.idx;

			if (visited[idx]) continue;
			visited[idx] = true;

			for (int next = 0; next < total; next++) {
				double nt = dist[idx] + cost[idx][next];
				if (nt < dist[next]) {
					dist[next] = nt;
					pq.add(new Node(next, nt));
				}
			}
		}

		System.out.printf("%.6f",dist[N + 1]);
	}
}

//피타고라스 선에서 컷