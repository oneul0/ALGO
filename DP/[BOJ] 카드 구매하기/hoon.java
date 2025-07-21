import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N+1];
		StringTokenizer st= new StringTokenizer(br.readLine());
		for(int i = 1; i<=N; i++){
			arr[i] = Integer.parseInt(st.nextToken());
		}

		//dp[i] = arr 인덱스 i 까지 도달했을 때의 최댓값
		int[] dp = new int[N+1];
		for(int i = 1; i<=N; i++){
			for(int j = 1; j<=i; j++){
				dp[i] = Math.max(dp[i], dp[i-j]+arr[j]);
			}
		}

		System.out.println(dp[N]);
	}
}
