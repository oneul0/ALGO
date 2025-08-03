import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static final int MOD = 1_000_000_007;
	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		long[] dp = new long[5001];
		Arrays.fill(dp, 0);
		dp[0] = 1;
		for(int i = 2; i <= 5000; i+=2) {
			for(int j = 0; j <= i-2; j+=2) {
				dp[i] = (dp[i] + (dp[j] * dp[i - 2 - j]) % MOD) % MOD;
			}
		}

		for (int t = 1; t <= T; t++) {
			int L = Integer.parseInt(br.readLine());
			if(L%2 != 0) System.out.println(0);
			else System.out.println(dp[L]);
		}
	}
}

/**
 * 올바른 괄호 문자열은 짝수일 때
 * 올바른 + 올바른 = 올바른
 * 올바른 + 안올바른 = 안올바른
 * */
