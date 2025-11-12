import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, M;
	static List<List<Node>> net = new ArrayList<>();
	static final int INF = 1_000_000_000;

	static class Node implements Comparable<Node> {
		int to, cost, edges;
		Node(int to, int cost, int edges) {
			this.to = to;
			this.cost = cost;
			this.edges = edges;
		}
		public int compareTo(Node o) {
			if (this.cost == o.cost) return this.edges - o.edges;
			return this.cost - o.cost;
		}
	}

	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		for (int i = 0; i <= N; i++) net.add(new ArrayList<>());

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			net.get(a).add(new Node(b, c, 0));
			net.get(b).add(new Node(a, c, 0));
		}

		int[] dist = new int[N + 1];
		int[] edgeCount = new int[N + 1];
		int[] prev = new int[N + 1];
		Arrays.fill(dist, INF);
		Arrays.fill(edgeCount, INF);
		Arrays.fill(prev, -1);

		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.offer(new Node(1, 0, 0));
		dist[1] = 0;
		edgeCount[1] = 0;

		while (!pq.isEmpty()) {
			Node cur = pq.poll();

			if (cur.cost > dist[cur.to]) continue;
			if (cur.cost == dist[cur.to] && cur.edges > edgeCount[cur.to]) continue;

			for (Node next : net.get(cur.to)) {
				int newCost = cur.cost + next.cost;
				int newEdges = cur.edges + 1;

				if (newCost < dist[next.to] ||
					(newCost == dist[next.to] && newEdges < edgeCount[next.to])) {
					dist[next.to] = newCost;
					edgeCount[next.to] = newEdges;
					prev[next.to] = cur.to;
					pq.offer(new Node(next.to, newCost, newEdges));
				}
			}
		}

		List<int[]> result = new ArrayList<>();
		for (int v = 2; v <= N; v++) {
			if (prev[v] != -1){
				result.add(new int[]{prev[v], v});
			}
		}

		System.out.println(result.size());
		for (int[] edge : result){
			System.out.println(edge[0] + " " + edge[1]);
		}
	}
}
