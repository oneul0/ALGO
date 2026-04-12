import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		int n = Integer.parseInt(br.readLine());
		int[][] board = new int[n][3];
		StringTokenizer st;
		for(int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < 3; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int[][] maxDp = new int[n][3], minDp = new int[n][3];
		for(int i = 0; i < n; i++) {
			Arrays.fill(minDp[i], Integer.MAX_VALUE);
		}
		for(int i = 0; i< 3; i++){
			maxDp[0][i] = minDp[0][i] = board[0][i];
		}
		for(int i = 1; i < n; i++) {
			maxDp[i][0] = Math.max(maxDp[i][0], Math.max(maxDp[i-1][0], maxDp[i-1][1]))+board[i][0];
			maxDp[i][1] = Math.max(maxDp[i][1], Math.max(Math.max(maxDp[i-1][0], maxDp[i-1][1]), maxDp[i-1][2]))+board[i][1];
			maxDp[i][2] = Math.max(maxDp[i][2], Math.max(maxDp[i-1][1], maxDp[i-1][2]))+board[i][2];
			minDp[i][0] = Math.min(minDp[i][0], Math.min(minDp[i-1][0], minDp[i-1][1]))+board[i][0];
			minDp[i][1] = Math.min(minDp[i][1], Math.min(Math.min(minDp[i-1][0], minDp[i-1][1]), minDp[i-1][2]))+board[i][1];
			minDp[i][2] = Math.min(minDp[i][2], Math.min(minDp[i-1][1], minDp[i-1][2]))+board[i][2];
		}

		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
		for(int i = 0; i<3; i++){
			min = Math.min(min, minDp[n-1][i]);
			max = Math.max(max, maxDp[n-1][i]);
		}
		System.out.println(max +" " + min);
	}
}
