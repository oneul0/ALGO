import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, M;
	static List<List<Node>> gr = new ArrayList<>();
	static final long INF = Long.MAX_VALUE / 4;

	static class Node implements Comparable<Node> {
		int to;
		long cost;
		int state;

		Node(int to, long cost, int state){
			this.to = to;
			this.cost = cost;
			this.state = state;
		}

		@Override
		public int compareTo(Node o){
			return Long.compare(this.cost, o.cost);
		}
	}

	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		for(int i = 0; i<=N; i++){
			gr.add(new ArrayList<>());
		}

		for(int i = 0; i<M; i++){
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken()) * 2;
			gr.get(a).add(new Node(b, d, 0));
			gr.get(b).add(new Node(a, d, 0));
		}


		long[][] wolfDist = wolfRun();
		long[] foxDist = foxRun();

		int count = 0;
		for(int i = 2; i <= N; i++){
			long minWolfDist = Math.min(wolfDist[i][0], wolfDist[i][1]);
			if(foxDist[i] < minWolfDist) {
				count++;
			}
		}
		System.out.println(count);
	}

	public static long[][] wolfRun(){
		PriorityQueue<Node> pq = new PriorityQueue<>();
		long[][] dist = new long[N+1][2];
		for(int i = 0; i<=N; i++){
			Arrays.fill(dist[i], INF);
		}

		pq.offer(new Node(1, 0L, 0));
		dist[1][0] = 0L;

		while(!pq.isEmpty()){
			Node cur = pq.poll();
			if(dist[cur.to][cur.state] < cur.cost) continue;

			for(Node next : gr.get(cur.to)){
				int nextState = 1 - cur.state;
				long curCost;

				if(cur.state == 0){
					curCost = next.cost / 2;
				}
				else {
					curCost = next.cost * 2;
				}

				long newCost = curCost + cur.cost;

				if(dist[next.to][nextState] > newCost){
					dist[next.to][nextState] = newCost;
					pq.offer(new Node(next.to, newCost, nextState));
				}
			}
		}
		return dist;
	}

	public static long[] foxRun(){
		PriorityQueue<Node> pq = new PriorityQueue<>();
		long[] dist = new long[N+1];
		Arrays.fill(dist, INF);

		pq.offer(new Node(1, 0L, 0));
		dist[1] = 0L;

		while(!pq.isEmpty()){
			Node cur = pq.poll();
			if(dist[cur.to] < cur.cost) continue;

			for(Node next : gr.get(cur.to)){
				long newCost = next.cost + cur.cost;
				if(dist[next.to] > newCost){
					dist[next.to] = newCost;
					pq.offer(new Node(next.to, newCost, 0));
				}
			}
		}
		return dist;
	}
}

//홀수면 짧은 거리 짝수면 긴 거리
//전체 - 늑대가 먼저 가는 그루터기