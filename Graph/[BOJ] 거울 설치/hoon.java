import java.io.*;
import java.util.*;

public class Main {
	static int N;
	static char[][] room;
	static int[][] doors = new int[2][2];
	static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
	static final int INF = 1_000_000_000;

	static class Node implements Comparable<Node>{
		int x, y, cost, dir;
		Node(int x, int y, int cost, int dir){
			this.x = x; this.y = y; this.cost = cost; this.dir = dir;
		}
		public int compareTo(Node o){ return cost - o.cost; }
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		room = new char[N][N];

		int doorIdx = 0;
		for (int i = 0; i < N; i++) {
			String line = br.readLine();
			for (int j = 0; j < N; j++) {
				room[i][j] = line.charAt(j);
				if (room[i][j] == '#') {
					doors[doorIdx][0] = i;
					doors[doorIdx++][1] = j;
				}
			}
		}
		br.close();

		System.out.println(dijkstra());
	}

	static int dijkstra() {
		int[][][] dist = new int[N][N][4];
		for (int[][] a : dist)
			for (int[] b : a)
				Arrays.fill(b, INF);

		PriorityQueue<Node> pq = new PriorityQueue<>();
		int sx = doors[0][0], sy = doors[0][1];
		int ex = doors[1][0], ey = doors[1][1];

		for (int d = 0; d < 4; d++) {
			dist[sx][sy][d] = 0;
			pq.offer(new Node(sx, sy, 0, d));
		}

		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			if (cur.x == ex && cur.y == ey) return cur.cost;
			if (cur.cost > dist[cur.x][cur.y][cur.dir]) continue;

			int nx = cur.x + dx[cur.dir];
			int ny = cur.y + dy[cur.dir];
			if (!isValid(nx, ny) || room[nx][ny] == '*') continue;

			// 직진
			if (dist[nx][ny][cur.dir] > cur.cost) {
				dist[nx][ny][cur.dir] = cur.cost;
				pq.offer(new Node(nx, ny, cur.cost, cur.dir));
			}

			// 거울
			if (room[nx][ny] == '!') {
				for (int nd : turn(cur.dir)) {
					if (dist[nx][ny][nd] > cur.cost + 1) {
						dist[nx][ny][nd] = cur.cost + 1;
						pq.offer(new Node(nx, ny, cur.cost + 1, nd));
					}
				}
			}
		}
		return -1;
	}

	static int[] turn(int dir) {
		return (dir == 0 || dir == 1) ? new int[]{2,3} : new int[]{0,1};
	}

	static boolean isValid(int x, int y) {
		return x >= 0 && y >= 0 && x < N && y < N;
	}
}
