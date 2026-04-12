import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	static int n, m;
	static int[][] table;
	static List<List<int[]>> gr = new ArrayList<>();
	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		table = new int[n+1][n+1];
		for(int i = 0; i<=n; i++){
			gr.add(new ArrayList<>());
		}

		for(int i = 0; i<m; i++){
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			gr.get(a).add(new int[]{b, c});
			gr.get(b).add(new int[]{a, c});

		}

		for(int i = 1; i<=n; i++){
			dijkstra(i);
		}

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (i == j) System.out.print("- ");
				else System.out.print(table[i][j] + " ");
			}
			System.out.println();
		}

	}

	public static void dijkstra(int sx){
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
		int[] dist = new int[n + 1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		pq.offer(new int[]{sx, 0});
		dist[sx] = 0;

		while(!pq.isEmpty()){
			int[] cur = pq.poll();
			int now = cur[0];
			int nowDist = cur[1];

			for(int[] next : gr.get(now)){
				int nxt = next[0];
				int newDist = nowDist + next[1];
				if(dist[nxt] > newDist){
					dist[nxt] = newDist;

					if(now == sx) table[sx][nxt] = nxt;
					else table[sx][nxt] = table[sx][now];

					pq.offer(new int[]{nxt, newDist});
				}
			}
		}
	}

}
