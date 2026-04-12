import java.io.*;
import java.util.*;

public class Main {
	public static class Coord implements Comparable<Coord>{
		int x, y, dist;
		Coord(int x, int y, int dist){
			this.x=x;
			this.y=y;
			this.dist=dist;
		}
		@Override
		public int compareTo(Coord o){
			return this.dist - o.dist;
		}
	}
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
	static int[][] arr, islands;
	static int[][] bridge;
	static int N, M;
	static List<List<int[]>> islandEdges = new ArrayList<>();

	public static boolean isEdge(int x, int y){
		for(int k=0; k<4; k++){
			int nx = x + dx[k];
			int ny = y + dy[k];
			if(isValid(nx, ny) && arr[nx][ny] == 0) return true;
		}
		return false;
	}

	public static boolean isValid(int x, int y){
		return (x>=0&&y>=0&& x<N && y<M);
	}

	public static void findIsland(int sx, int sy, int curIdx){
		Deque<Coord> q = new ArrayDeque<>();
		q.offer(new Coord(sx, sy, 0));
		islands[sx][sy] = curIdx;

		List<int[]> edges = new ArrayList<>();

		while(!q.isEmpty()){
			Coord cur = q.poll();

			for(int i=0; i<4; i++){
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];

				if(!isValid(nx, ny)) continue;

				if(arr[nx][ny] == 0){
					if(isEdge(cur.x, cur.y)){
						edges.add(new int[]{cur.x, cur.y});
					}
					continue;
				}

				if(islands[nx][ny] != 0) continue;

				islands[nx][ny] = curIdx;
				q.offer(new Coord(nx, ny, 0));
			}
		}

		islandEdges.add(edges);
	}



	public static void findBridges(int totalCnt){
		bridge = new int[totalCnt+1][totalCnt+1];

		for(int i=1; i<=totalCnt; i++){
			for(int j=1; j<=totalCnt; j++){
				bridge[i][j] = (i==j ? 0 : 1000000);
			}
		}

		for(int island=1; island<=totalCnt; island++){
			for(int[] edge : islandEdges.get(island-1)){
				int x = edge[0], y = edge[1];

				for(int d=0; d<4; d++){
					int nx = x + dx[d];
					int ny = y + dy[d];
					int dist = 0;

					while(isValid(nx, ny) && islands[nx][ny] == 0){
						nx += dx[d];
						ny += dy[d];
						dist++;
					}

					if(isValid(nx, ny)){
						int other = islands[nx][ny];
						if(other != island && dist >= 2){
							bridge[island][other] = Math.min(bridge[island][other], dist);
						}
					}
				}
			}
		}
	}

	public static int[] prim(int totalCnt){
		boolean[] visited = new boolean[totalCnt+1];
		PriorityQueue<Coord> pq = new PriorityQueue<>();
		pq.offer(new Coord(1, 1, 0));

		int cost = 0;
		int count = 0;

		while(!pq.isEmpty()){
			Coord cur = pq.poll();

			if(visited[cur.x]) continue;
			visited[cur.x] = true;
			cost += cur.dist;
			count++;

			for(int next=1; next<=totalCnt; next++){
				if(!visited[next] && bridge[cur.x][next] < 1000000){
					pq.offer(new Coord(next, next, bridge[cur.x][next]));
				}
			}
		}

		return new int[]{count, cost};
	}

	public static void main(String[] args) throws IOException{
		StringTokenizer st= new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N][M];
		islands = new int[N][M];
		for(int i = 0; i<N; i++){
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<M; j++){
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int totalCnt = 0;
		for(int i=0; i<N; i++){
			for(int j=0; j<M; j++){
				if(arr[i][j] == 1 && islands[i][j] == 0){
					totalCnt++;
					findIsland(i, j, totalCnt);
				}
			}
		}

		findBridges(totalCnt);

		int[] result = prim(totalCnt);

		if(result[0] != totalCnt) System.out.println(-1);
		else System.out.println(result[1]);

	}

}
