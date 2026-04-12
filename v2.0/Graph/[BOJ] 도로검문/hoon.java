import java.io.*;
import java.util.*;

public class Main {
	static class Node implements Comparable<Node> {
		int to, time;
		Node(int to, int time){
			this.to = to;
			this.time = time;
		}
		@Override
		public int compareTo(Node o) {
			return this.time - o.time;
		}
	}

	static final int INF = 1_000_000_000;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, M;

	static List<List<Node>> gr = new ArrayList<>();
	static int[] parent;
	static boolean[][] blocked;

	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		for(int i = 0; i <= N; i++){
			gr.add(new ArrayList<>());
		}

		for(int i = 0; i<M; i++){
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			gr.get(a).add(new Node(b, c));
			gr.get(b).add(new Node(a, c));
		}

		parent = new int[N + 1];
		blocked = new boolean[N + 1][N + 1];

		int origin = dijkstraSavePath(1, N);

		List<int[]> path = new ArrayList<>();
		int cur = N;
		while(cur != 1){
			int p = parent[cur];
			path.add(new int[]{p, cur});
			cur = p;
		}

		int maxDelay = 0;

		for(int[] edge : path){
			int a = edge[0];
			int b = edge[1];

			blocked[a][b] = true;
			blocked[b][a] = true;

			int newDist = dijkstra(1, N);

			if(newDist == INF) {
				maxDelay = Math.max(maxDelay, INF);
			} else {
				maxDelay = Math.max(maxDelay, newDist - origin);
			}

			blocked[a][b] = false;
			blocked[b][a] = false;
		}

		System.out.println(maxDelay == INF ? -1 : maxDelay);
	}

	//경로저장 다익스트라
	public static int dijkstraSavePath(int start, int end){
		int[] dist = new int[N + 1];
		Arrays.fill(dist, INF);

		PriorityQueue<Node> pq = new PriorityQueue<>();
		dist[start] = 0;
		pq.add(new Node(start, 0));

		while(!pq.isEmpty()){
			Node cur = pq.poll();
			if(cur.time > dist[cur.to]) continue;

			for(Node next : gr.get(cur.to)){
				if(blocked[cur.to][next.to]) continue;

				int newTime = cur.time + next.time;

				if(newTime < dist[next.to]){
					dist[next.to] = newTime;
					parent[next.to] = cur.to;
					pq.add(new Node(next.to, newTime));
				}
			}
		}
		return dist[end];
	}

	public static int dijkstra(int start, int end){
		int[] dist = new int[N + 1];
		Arrays.fill(dist, INF);

		PriorityQueue<Node> pq = new PriorityQueue<>();
		dist[start] = 0;
		pq.add(new Node(start, 0));

		while(!pq.isEmpty()){
			Node cur = pq.poll();
			if(cur.time > dist[cur.to]) continue;

			for(Node next : gr.get(cur.to)){
				if(blocked[cur.to][next.to]) continue;

				int newTime = cur.time + next.time;

				if(newTime < dist[next.to]){
					dist[next.to] = newTime;
					pq.add(new Node(next.to, newTime));
				}
			}
		}

		return dist[end];
	}
}
