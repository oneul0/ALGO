import java.io.*;
import java.util.*;

public class Main {
	static int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};

	static class Pair {
		int x, y;
		Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static int bfs(int h, int w, char[][] arr, boolean[] hasKey) {
		Queue<Pair> q = new ArrayDeque<>();
		boolean[][] visited = new boolean[h + 2][w + 2];

		// 문 대기 큐
		List<Queue<Pair>> waitDoors = new ArrayList<>();
		for (int i = 0; i < 26; i++) {
			waitDoors.add(new ArrayDeque<>());
		}

		q.add(new Pair(0, 0));
		visited[0][0] = true;

		int count = 0;

		while (!q.isEmpty()) {
			Pair cur = q.poll();

			for (int d = 0; d < 4; d++) {
				int nx = cur.x + dx[d];
				int ny = cur.y + dy[d];

				if (nx < 0 || ny < 0 || nx >= h + 2 || ny >= w + 2) continue;
				if (visited[nx][ny]) continue;

				char next = arr[nx][ny];
				if (next == '*') continue;

				// 문
				if (next >= 'A' && next <= 'Z') {
					int idx = next - 'A';
					//맞는 열쇠 없으면 저장하고 대기
					if (!hasKey[idx]) {
						waitDoors.get(idx).add(new Pair(nx, ny));
						continue;
					}
				}

				// 열쇠
				if (next >= 'a' && next <= 'z') {
					int idx = next - 'a';
					//찾은 열쇠가 있으면
					if (!hasKey[idx]) {
						hasKey[idx] = true;
						//대기중이던 위치에서 진행
						Queue<Pair> waitQueue = waitDoors.get(idx);
						while(!waitQueue.isEmpty()){
							Pair p = waitQueue.poll();
							if (!visited[p.x][p.y]) {
								visited[p.x][p.y] = true;
								q.add(p);
							}
						}
					}
					arr[nx][ny] = '.';
				}

				// 문서
				if (next == '$') {
					count++;
					arr[nx][ny] = '.';
				}

				visited[nx][ny] = true;
				q.add(new Pair(nx, ny));
			}
		}
		return count;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int tc = Integer.parseInt(br.readLine());

		while (tc-- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int h = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());

			char[][] arr = new char[h + 2][w + 2];
			for (int i = 0; i < h + 2; i++) {
				Arrays.fill(arr[i], '.');
			}

			for (int i = 1; i <= h; i++) {
				String line = br.readLine();
				for (int j = 1; j <= w; j++) {
					arr[i][j] = line.charAt(j - 1);
				}
			}

			boolean[] hasKey = new boolean[26];
			String keyLine = br.readLine();
			if (!keyLine.equals("0")) {
				for (char c : keyLine.toCharArray()) {
					hasKey[c - 'a'] = true;
				}
			}

			System.out.println(bfs(h, w, arr, hasKey));
		}
	}
}
