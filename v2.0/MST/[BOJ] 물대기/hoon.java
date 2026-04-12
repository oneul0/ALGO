import java.io.*;
import java.util.*;

public class Main {
	public static class Pair implements Comparable<Pair>{
		int to;
		int cost;
		Pair(int to, int cost){
			this.to = to;
			this.cost = cost;
		}
		@Override
		public int compareTo(Pair o){
			return this.cost - o.cost;
		}
	}

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N;
	static int[] field;
	static int[][] costs;

	public static long prim(){
		boolean[] visited = new boolean[N];
		PriorityQueue<Pair> pq = new PriorityQueue<>();
		for(int i = 0; i<N; i++){
			pq.offer(new Pair(i, field[i]));
		}
		long result = 0;
		int count = 0;
		while(!pq.isEmpty() && count < N){
			Pair cur = pq.poll();
			if(visited[cur.to]) continue;

			visited[cur.to] = true;
			result += cur.cost;
			count++;

			for(int i = 0; i<N; i++){
				if(visited[i]) continue;
				pq.offer(new Pair(i, costs[cur.to][i]));
			}
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		field = new int[N];
		costs = new int[N][N];

		for(int i = 0; i<N; i++){
			field[i] = Integer.parseInt(br.readLine());
		}
		StringTokenizer st;
		for(int i = 0; i<N; i++){
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<N; j++){
				costs[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		//prim
		System.out.println(prim());
	}

}
