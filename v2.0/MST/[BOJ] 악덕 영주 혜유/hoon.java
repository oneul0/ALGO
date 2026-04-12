import java.io.*;
import java.util.*;

public class Main {

	public static class Town {
		int from, to, cost;
		Town(int from, int to, int cost){
			this.from = from;
			this.to = to;
			this.cost = cost;
		}
	}

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, M;
	static int totalMstCost;
	static List<List<Town>> graph = new ArrayList<>();
	static List<List<Town>> mst = new ArrayList<>();

	public static void prim(int start){
		PriorityQueue<Town> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
		boolean[] visited = new boolean[N];
		pq.offer(new Town(start, start, 0));

		while(!pq.isEmpty()){
			Town cur = pq.poll();
			if(visited[cur.to]) continue;
			visited[cur.to] = true;
			totalMstCost += cur.cost;

			//시작노드 제외 간선 저장
			if(cur.from != cur.to){
				mst.get(cur.from).add(new Town(cur.from, cur.to, cur.cost));
				mst.get(cur.to).add(new Town(cur.to, cur.from, cur.cost));
			}

			for(Town next : graph.get(cur.to)){
				if(!visited[next.to]){
					pq.offer(next);
				}
			}
		}
	}

	public static int[] bfs(int start){
		Deque<int[]> q = new ArrayDeque<>();
		boolean[] visited = new boolean[N];
		q.offer(new int[]{start, 0});
		visited[start] = true;

		int farthestNode = start, maxDist = 0;

		while(!q.isEmpty()){
			int[] cur = q.poll();
			int node = cur[0], dist = cur[1];

			if(dist > maxDist){
				maxDist = dist;
				farthestNode = node;
			}

			for(Town next : mst.get(node)){
				if(!visited[next.to]){
					visited[next.to] = true;
					q.offer(new int[]{next.to, dist + next.cost});
				}
			}
		}

		return new int[]{farthestNode, maxDist};
	}

	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		if(N == 1){
			System.out.println(0 + "\n" + 0);
			return;
		}

		for(int i=0; i<N; i++){
			graph.add(new ArrayList<>());
			mst.add(new ArrayList<>());
		}

		for(int i=0; i<M; i++){
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			graph.get(a).add(new Town(a, b, c));
			graph.get(b).add(new Town(b, a, c));
		}

		// MST 채우기
		prim(0);

		// MST에서 가장 먼 노드 X 찾기
		int x = bfs(0)[0];

		// X에서 BFS 다시 돌려 트리 지름 계산
		int diameter = bfs(x)[1];

		System.out.println(totalMstCost + "\n" + diameter);
	}
}
