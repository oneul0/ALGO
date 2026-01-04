import java.io.*;
import java.util.*;

public class Main {

	static class Coord {
		int x, y;
		Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static class Passenger {
		Coord start;
		Coord end;
		int distSE; // start -> end 거리

		Passenger(Coord start, Coord end, int distSE) {
			this.start = start;
			this.end = end;
			this.distSE = distSE;
		}
	}

	static int N, M, F;
	static int[][] map;
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};

	// 택시 위치에서 모든 칸까지 거리 bfs
	static int[][] bfs(Coord start) {
		int[][] dist = new int[N + 1][N + 1];
		for (int i = 1; i <= N; i++) Arrays.fill(dist[i], -1);

		ArrayDeque<Coord> q = new ArrayDeque<>();
		q.add(start);
		dist[start.x][start.y] = 0;

		while (!q.isEmpty()) {
			Coord cur = q.poll();

			for (int d = 0; d < 4; d++) {
				int nx = cur.x + dx[d];
				int ny = cur.y + dy[d];

				if (nx < 1 || ny < 1 || nx > N || ny > N) continue;
				if (map[nx][ny] == 1 || dist[nx][ny] != -1) continue;

				dist[nx][ny] = dist[cur.x][cur.y] + 1;
				q.add(new Coord(nx, ny));
			}
		}
		return dist;
	}

	static int solve(List<Passenger> ps, Coord taxi) {

		while (!ps.isEmpty()) {

			// 택시 기준 bfs
			int[][] dist = bfs(taxi);

			int idx = -1;
			int minDist = Integer.MAX_VALUE;

			// 가장 가까운 승객 선택
			for (int i = 0; i < ps.size(); i++) {
				Passenger p = ps.get(i);
				int d = dist[p.start.x][p.start.y];

				if (d == -1 || d > F) continue;

				if (idx == -1 ||
					d < minDist ||
					(d == minDist &&
						(p.start.x < ps.get(idx).start.x ||
							(p.start.x == ps.get(idx).start.x &&
								p.start.y < ps.get(idx).start.y)))) {
					idx = i;
					minDist = d;
				}
			}

			if (idx == -1) return -1;

			Passenger chosen = ps.get(idx);

			if (chosen.distSE == -1) return -1;
			if (minDist + chosen.distSE > F) return -1;

			// 연료 계산
			F = F - minDist + chosen.distSE;

			// 택시 이동
			taxi.x = chosen.end.x;
			taxi.y = chosen.end.y;

			// 승객 제거
			ps.remove(idx);
		}

		return F;
	}

	// start -> end 최단거리 계산용 bfs
	static int bfsOne(Coord start, Coord end) {
		boolean[][] visited = new boolean[N + 1][N + 1];
		ArrayDeque<int[]> q = new ArrayDeque<>();

		q.add(new int[]{start.x, start.y, 0});
		visited[start.x][start.y] = true;

		while (!q.isEmpty()) {
			int[] cur = q.poll();
			if (cur[0] == end.x && cur[1] == end.y) return cur[2];

			for (int d = 0; d < 4; d++) {
				int nx = cur[0] + dx[d];
				int ny = cur[1] + dy[d];

				if (nx < 1 || ny < 1 || nx > N || ny > N) continue;
				if (map[nx][ny] == 1 || visited[nx][ny]) continue;

				visited[nx][ny] = true;
				q.add(new int[]{nx, ny, cur[2] + 1});
			}
		}
		return -1;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		F = Integer.parseInt(st.nextToken());

		map = new int[N + 1][N + 1];

		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		st = new StringTokenizer(br.readLine());
		Coord taxi = new Coord(
			Integer.parseInt(st.nextToken()),
			Integer.parseInt(st.nextToken())
		);

		List<Passenger> ps = new ArrayList<>();

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			Coord s = new Coord(
				Integer.parseInt(st.nextToken()),
				Integer.parseInt(st.nextToken())
			);
			Coord e = new Coord(
				Integer.parseInt(st.nextToken()),
				Integer.parseInt(st.nextToken())
			);
			int dist = bfsOne(s, e);
			ps.add(new Passenger(s, e, dist));
		}

		System.out.println(solve(ps, taxi));
	}
}
