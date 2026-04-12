import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		int N = Integer.parseInt(br.readLine());
		List<Integer> arr = new ArrayList<>();
		int M = Integer.parseInt(br.readLine());
		for(int i = 0; i<M; i++){
			arr.add(Integer.parseInt(br.readLine()));
		}
		int[] dp = new int[N+1];
		dp[0] = dp[1] = 1;

		for(int i = 2; i<=N; i++){
			dp[i] = dp[i-1] + dp[i-2];
		}
		int ans = 1, last = 0;
		for(int i : arr){
			int len = i - 1 - last;
			ans *= dp[len];
			last = i;
		}
		int len = N - last;
		ans *= dp[len];
		System.out.print(ans);
	}
}