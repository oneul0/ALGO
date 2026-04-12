import java.io.*;
import java.util.*;

public class Main {
	public static class Node implements Comparable<Node> {
		int to, cost;
		public Node(int to, int cost) {
			this.to = to;
			this.cost = cost;
		}
		@Override
		public int compareTo(Node o) {
			return Integer.compare(cost, o.cost);
		}
	}
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int n, m, t;
	static int s, g, h;
	static List<List<Node>> gr;
	static List<Integer> candidates;
	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		while (T-- > 0) {
			init();
			//후보군까지의 최단거리
			//무조건 지나야하는 간선을 지날 때의 최단거리
			int[] S = dijikstra(s), G = dijikstra(g), H = dijikstra(h);

			//두 최단거리 비교
			List<Integer> ans = new ArrayList<>();
			for(int candidate : candidates) {
				//만약 무조건 지나야하는 교차로를 신경쓰지 않고 탐색한 최단거리가(S[x])
				//무조건 지나야하는 교차로를 포함하여 탐색한 최단거리와 같으면
				// (s->x == (s->g + g->h + h->x) || s->x == (s->h + h->g + g->x))
				//최단거리에 교차로가 포함되는 것
				if(S[candidate] == (S[g] + G[h] + H[candidate]) || S[candidate] == (S[h] + H[g] + G[candidate])) {
					ans.add(candidate);
				}
			}
			Collections.sort(ans);
			for(int a : ans) {
				System.out.print(a + " ");
			}
		}
		br.close();
	}

	public static int[] dijikstra(int start){
		PriorityQueue<Node> pq = new PriorityQueue<>();
		int[] dist = new int[n+1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		pq.offer(new Node(start, 0));
		dist[start] = 0;
		while(!pq.isEmpty()){
			Node cur = pq.poll();

			for(Node next : gr.get(cur.to)) {
				int newCost = cur.cost + next.cost;
				if(dist[next.to] > newCost){
					dist[next.to] = newCost;
					pq.offer(new Node(next.to, newCost));
				}
			}

		}
		return dist;
	}


	public static void init() throws IOException {
		gr = new ArrayList<>();
		candidates = new ArrayList<>();
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		t = Integer.parseInt(st.nextToken());
		for(int i = 0; i <= n; i++) {
			gr.add(new ArrayList<>());
		}
		st = new StringTokenizer(br.readLine());
		s = Integer.parseInt(st.nextToken());
		g = Integer.parseInt(st.nextToken());
		h = Integer.parseInt(st.nextToken());

		for(int i = 0; i<m; i++){
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			gr.get(a).add(new Node(b, d));
			gr.get(b).add(new Node(a, d));
		}
		for(int i = 0; i<t; i++){
			int x = Integer.parseInt(br.readLine());
			candidates.add(x);
		}
	}
}

//후보군까지의 최단거리를 구하고
//무조건 지나야하는 간선을 지날 때의 최단거리를 구한 뒤
//후보군 중 최단거리가 다르게 측정된 후보군을 제외