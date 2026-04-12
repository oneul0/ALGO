import java.io.*;
import java.util.*;

public class Main {
	static int N, M, K;
	static int[][] map;
	static boolean[][][] visited;
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static class Point {
		int x, y, wall, dist;

		Point(int x, int y, int wall, int dist) {
			this.x = x;
			this.y = y;
			this.wall = wall;
			this.dist = dist;
		}
	}

	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		visited = new boolean[N][M][K + 1];

		for (int i = 0; i < N; i++) {
			String line = br.readLine();
			for (int j = 0; j < M; j++) {
				map[i][j] = line.charAt(j) - '0';
			}
		}

		System.out.println(bfs());
	}

	static int bfs() {
		Deque<Point> q = new ArrayDeque<>();
		q.offer(new Point(0, 0, 0, 1));
		visited[0][0][0] = true;

		while (!q.isEmpty()) {
			Point curr = q.poll();

			if (curr.x == N - 1 && curr.y == M - 1) {
				return curr.dist;
			}

			for (int i = 0; i < 4; i++) {
				int nx = curr.x + dx[i];
				int ny = curr.y + dy[i];

				if (nx < 0 || nx >= N || ny < 0 || ny >= M) {
					continue;
				}

				if (map[nx][ny] == 0 && !visited[nx][ny][curr.wall]) {
					visited[nx][ny][curr.wall] = true;
					q.offer(new Point(nx, ny, curr.wall, curr.dist + 1));
				}

				if (map[nx][ny] == 1 && curr.wall < K && !visited[nx][ny][curr.wall + 1]) {
					visited[nx][ny][curr.wall + 1] = true;
					q.offer(new Point(nx, ny, curr.wall + 1, curr.dist + 1));
				}
			}
		}

		return -1;
	}
}