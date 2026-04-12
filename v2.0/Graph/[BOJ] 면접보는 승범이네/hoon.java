import java.io.*;
import java.util.*;

public class Main {
	static class Pair implements Comparable<Pair> {
		int to;
		long cost;
		Pair(int to, long cost){
			this.to = to;
			this.cost = cost;
		}

		@Override
		public int compareTo(Pair o){
			return (int)(this.cost - o.cost);
		}
	}

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, M, K;
	static List<List<Pair>> gr = new ArrayList<>();
	static Set<Integer> rooms = new HashSet<>();
	static long[] dist;

	public static void main(String[] args) throws IOException {
		init();
		dijkstra();

		long maxNode = 0, maxDist = -1;
		for (int i = 1; i <= N; i++) {
			if (dist[i] > maxDist) {
				maxDist = dist[i];
				maxNode = i;
			}
		}

		System.out.println(maxNode);
		System.out.println(maxDist);
	}

	public static void dijkstra() {
		PriorityQueue<Pair> pq = new PriorityQueue<>();
		dist = new long[N + 1];
		Arrays.fill(dist, Long.MAX_VALUE);

		for (int room : rooms) {
			dist[room] = 0;
			pq.offer(new Pair(room, 0));
		}

		while (!pq.isEmpty()) {
			Pair cur = pq.poll();
			if (cur.cost > dist[cur.to]) continue;

			for (Pair next : gr.get(cur.to)) {
				long newCost = cur.cost + next.cost;
				if (newCost < dist[next.to]) {
					dist[next.to] = newCost;
					pq.offer(new Pair(next.to, newCost));
				}
			}
		}
	}

	public static void init() throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		for (int i = 0; i <= N; i++) {
			gr.add(new ArrayList<>());
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			gr.get(v).add(new Pair(u, c));
		}

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < K; i++) {
			rooms.add(Integer.parseInt(st.nextToken()));
		}
	}
}
