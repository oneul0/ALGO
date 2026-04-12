import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int R, C;
	static char[][] arr;
	static boolean[][] visited;

	// 우상, 우, 우하
	static int[] dr = {-1, 0, 1};
	static int[] dc = {1, 1, 1};

	static boolean dfs(int r, int c) {
		visited[r][c] = true;

		if (c == C - 1) return true;

		for (int d = 0; d < 3; d++) {
			int nr = r + dr[d];
			int nc = c + dc[d];

			if (nr < 0 || nr >= R || nc < 0 || nc >= C) continue;
			if (visited[nr][nc]) continue;
			if (arr[nr][nc] == 'x') continue;

			if (dfs(nr, nc)) return true;
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		arr = new char[R][C];
		for (int i = 0; i < R; i++) {
			arr[i] = br.readLine().toCharArray();
		}

		visited = new boolean[R][C];

		int ans = 0;
		for (int r = 0; r < R; r++) {
			if (!visited[r][0] && dfs(r, 0)) ans++;
		}

		System.out.println(ans);
	}
}
