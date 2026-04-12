import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, M;
	static char[][] board;
	static boolean[][] visited;
	static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};

	public static void main(String[] args) throws IOException {
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		board = new char[N][M];
		visited = new boolean[N][M];
		for(int i = 0; i<N; i++){
			String line = br.readLine();
			for(int j = 0; j<M; j++){
				board[i][j] = line.charAt(j);
			}
		}

		boolean hasCycle = false;
		for(int i = 0; i<N; i++){
			for(int j = 0; j<M; j++){
				if(!visited[i][j]){
					if(dfs(i, j, -1, -1)){
						hasCycle = true;
						break;
					}
				}
			}
			if(hasCycle) break;
		}

		if(hasCycle) System.out.println("Yes");
		else  System.out.println("No");
	}

	public static boolean dfs(int x, int y, int parentX, int parentY){
		visited[x][y] = true;

		for(int i = 0; i<4; i++){
			int nx = x + dx[i];
			int ny = y + dy[i];

			if(nx < 0 || ny < 0 || nx >= N || ny >= M || board[x][y] != board[nx][ny]) continue;

			if(nx == parentX && ny == parentY) continue;

			if(visited[nx][ny]){
				return true;
			}

			if(dfs(nx, ny, x, y)){
				return true;
			}
		}
		return false;
	}
}