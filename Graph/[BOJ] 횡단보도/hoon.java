import java.util.*;
import java.io.*;

public class Main {
	static class Node implements Comparable<Node> {
		int to, period;
		long time;
		public Node(int to, long time, int period) {
			this.to = to;
			this.period = period;
			this.time = time;
		}
		@Override
		public int compareTo(Node o) {
			return Long.compare(this.time, o.time);
		}
	}

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, M;
	static List<List<int[]>> gr = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		for (int i = 0; i <= N; i++) gr.add(new ArrayList<>());

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			gr.get(a).add(new int[]{b, i});
			gr.get(b).add(new int[]{a, i});
		}

		System.out.println(dijkstra());
	}

	static long dijkstra() {
		long[] dist = new long[N + 1];
		Arrays.fill(dist, Long.MAX_VALUE);
		dist[1] = 0;

		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.offer(new Node(1, 0, 0)); // node, time, period

		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			int node = cur.to;
			long time = cur.time;
			int period = cur.period;

			if (dist[node] < time) continue;

			for (int[] next : gr.get(node)) {
				int nextNode = next[0];
				int edgeIndex = next[1];
				int edgePeriod = edgeIndex % M;

				int wait = (edgePeriod - period + M) % M;
				long newTime = time + wait + 1;
				int newPeriod = (period + wait + 1) % M;

				if (dist[nextNode] > newTime) {
					dist[nextNode] = newTime;
					pq.offer(new Node(nextNode, newTime, newPeriod));
				}
			}
		}

		return dist[N];
	}
}
