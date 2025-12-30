import java.io.*;
import java.util.*;

public class Main {

	static class Point {
		int x, y, z, dist;
		Point(int x, int y, int z, int dist){
			this.x = x;
			this.y = y;
			this.z = z;
			this.dist = dist;
		}
	}

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	// boards, rotated 는 판(z축을 기준으로 나눈 것) 기준으로
	// maze는 탐색하는 판 안쪽(2차원)을 기준으로 나눔
	// 통일하면 헷갈려서..

	static int[][][] boards = new int[5][5][5]; // boards[z][x][y] 입력 판 5장
	static int[][][] rotated = new int[5][5][5]; // rotated[z][x][y] 순열+회전이 적용된 판 5장
	static int[][][] maze = new int[5][5][5]; // maze[x][y][z] 3D 미로

	static int[] order = new int[5];
	static boolean[] used = new boolean[5];

	static int[] dx = {-1, 1, 0, 0, 0, 0};
	static int[] dy = {0, 0, -1, 1, 0, 0};
	static int[] dz = {0, 0, 0, 0, -1, 1};

	static int answer = Integer.MAX_VALUE;

	static boolean isValid(int x, int y, int z){
		return (x >= 0 && y >= 0 && z >= 0 && x < 5 && y < 5 && z < 5);
	}

	static void buildMaze() {
		for (int z = 0; z < 5; z++) {
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					maze[x][y][z] = rotated[z][x][y];
				}
			}
		}
	}

	static int[][] getBoard2D(int idx) {
		int[][] dst = new int[5][5];
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				dst[x][y] = boards[idx][x][y];
			}
		}
		return dst;
	}

	// 반시계 90도 회전
	static int[][] rotateCCW(int[][] src) {
		int[][] dst = new int[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				dst[4 - j][i] = src[i][j];
			}
		}
		return dst;
	}

	static void rotateDFS(int depth) {
		if (answer == 12) return;

		if (depth == 5) {
			buildMaze();
			int res = bfs();
			if (res != -1) answer = Math.min(answer, res);
			return;
		}

		int idx = order[depth];
		int[][] cur = getBoard2D(idx);

		for (int r = 0; r < 4; r++) {
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					rotated[depth][x][y] = cur[x][y];
				}
			}

			rotateDFS(depth + 1);
			cur = rotateCCW(cur);
		}
	}

	static void permute(int depth) {
		if (answer == 12) return;

		if (depth == 5) {
			rotateDFS(0);
			return;
		}

		for (int i = 0; i < 5; i++) {
			if (used[i]) continue;
			used[i] = true;
			order[depth] = i;
			permute(depth + 1);
			used[i] = false;
		}
	}

	static int bfs() {
		if (maze[0][0][0] == 0 || maze[4][4][4] == 0) return -1;

		boolean[][][] visited = new boolean[5][5][5];
		Deque<Point> q = new ArrayDeque<>();
		q.offer(new Point(0, 0, 0, 0));
		visited[0][0][0] = true;

		while (!q.isEmpty()) {
			Point cur = q.poll();

			if (cur.dist >= answer) continue;

			if (cur.x == 4 && cur.y == 4 && cur.z == 4) {
				return cur.dist;
			}

			for (int d = 0; d < 6; d++) {
				int nx = cur.x + dx[d];
				int ny = cur.y + dy[d];
				int nz = cur.z + dz[d];

				if (!isValid(nx, ny, nz)) continue;
				if (visited[nx][ny][nz]) continue;
				if (maze[nx][ny][nz] == 0) continue;

				visited[nx][ny][nz] = true;
				q.offer(new Point(nx, ny, nz, cur.dist + 1));
			}
		}
		return -1;
	}

	public static void main(String[] args) throws Exception {
		for (int z = 0; z < 5; z++) {
			for (int x = 0; x < 5; x++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int y = 0; y < 5; y++) {
					boards[z][x][y] = Integer.parseInt(st.nextToken());
				}
			}
		}

		permute(0);

		System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
	}
}