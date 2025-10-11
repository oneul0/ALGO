import java.io.*;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int N, answer = 0;
	static int[][] arr;
	static int[][] dp;
	static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		arr = new int[N][N];
		dp = new int[N][N];
		StringTokenizer st;
		for(int i = 0; i<N; i++){
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<N; j++){
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for(int i = 0; i<N; i++){
			for(int j = 0; j<N; j++){
				answer = Math.max(answer, dfs(i, j));
			}
		}
		System.out.println(answer);
		br.close();
	}

	public static int dfs(int cx, int cy){
		if(dp[cx][cy] != 0){
			return dp[cx][cy];
		}

		int maxLen = 1;

		for(int i = 0; i<4; i++){
			int nx = cx + dx[i];
			int ny = cy + dy[i];
			if(nx<0 || ny<0 || nx>=N || ny>=N) continue;
			if(arr[cx][cy] >= arr[nx][ny]) continue;

			maxLen = Math.max(maxLen, dfs(nx, ny)+1);
		}
		dp[cx][cy] = maxLen;

		return dp[cx][cy];
	}
}
