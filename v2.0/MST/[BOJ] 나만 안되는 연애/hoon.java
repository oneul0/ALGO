import java.io.*;
import java.util.*;

public class Main {
	public static class Node implements Comparable<Node>{
		int to;
		int dist;
		Node(int to, int dist){
			this.to = to;
			this.dist = dist;
		}
		@Override
		public int compareTo(Node o){
			return this.dist - o.dist;
		}

	}
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, M;
	static List<List<Node>> gr = new ArrayList<>();
	static char[] gender;
	public static int prim(int start){
		boolean[] visited = new boolean[N+1];
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.offer(new Node(start, 0));

		int result = 0;
		int count = 0;
		while (!pq.isEmpty()){
			Node cur = pq.poll();
			if(visited[cur.to]) continue;
			visited[cur.to] = true;
			result+=cur.dist;
			count++;
			for(Node next : gr.get(cur.to)){
				if(!visited[next.to] && gender[cur.to] != gender[next.to]){
					pq.add(next);
				}
			}
		}

		return count == N ? result : -1;
	}

	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		for(int i = 0; i<=N; i++){
			gr.add(new ArrayList<>());
		}
		gender = new char[N+1];
		st = new StringTokenizer(br.readLine());
		for(int i = 1; i<=N; i++){
			gender[i] = st.nextToken().charAt(0);
		}
		for(int i = 0; i<M; i++){
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int dist = Integer.parseInt(st.nextToken());
			gr.get(a).add(new Node(b, dist));
			gr.get(b).add(new Node(a, dist));
		}

		int result = prim(1);
		System.out.println(result);
	}
}
