import java.util.*;
import java.io.*;

public class Main {
    static final int MOD = 998_244_353;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        //1 이상 M 이하의 정수로 이루어진 길이 N의 수열
        //길이가 N에 도달할 때까지 바텀업으로 쌓아가기

        //도달한 길이 l에 대해 길이를 하나 더 추가하면
        //현재 위치의 요소 a 에 대해
        //이전의 요소 pa, 이후의 추가할 요소 aa라고 할 때
        //이전의 요소가 나보다 낮으면(pa<a)
        //가능한 경우는 나랑 같거나 나보다 높은 것만 가능(a<=aa)
        //이전의 요소가 나랑 같거나 높으면
        //모두 가능(1~M)

        // dp[길이][현재숫자][상태]
        // 상태 0: 이전 숫자 >= 현재 숫자 (다음 숫자에 제약 없음)
        // 상태 1: 이전 숫자 < 현재 숫자 (다음 숫자는 현재보다 크거나 같아야 함)
        long[][][] dp = new long[N + 1][M + 1][2];

        for (int j = 1; j <= M; j++) {
            dp[1][j][0] = 1;
        }

        for (int i = 2; i <= N; i++) {
            long[] sum0 = new long[M + 2]; // dp[i-1][k][0]의 합
            long[] sum1 = new long[M + 2]; // (dp[i-1][k][0] + dp[i-1][k][1])의 합

            for (int j = 1; j <= M; j++) {
                sum0[j] = (sum0[j - 1] + dp[i - 1][j][0]) % MOD;
                sum1[j] = (sum1[j - 1] + dp[i - 1][j][0] + dp[i - 1][j][1]) % MOD;
            }

            for (int j = 1; j <= M; j++) {
                // 1. 상태 0이 되는 경우: 이전 숫자 k >= 현재 숫자 j
                // 제약이 없던 상태(0)에서는 k가 [j~M] 범위면 됨
                // 제약이 있던 상태(1)에서는 k가 현재 j보다 작거나 같아야 하는데
                // k >= j 조건과 합치면 k == j인 경우만 가능
                long suffixSum0 = (sum0[M] - sum0[j - 1] + MOD) % MOD;
                dp[i][j][0] = (suffixSum0 + dp[i - 1][j][1]) % MOD;

                // 2. 상태 1이 되는 경우: 이전 숫자 k < 현재 숫자 j
                // 제약이 없던 상태(0)나 있던 상태(1) 모두 k < j면 상태 1로 전이됨
                // (상태 1의 조건인 'k <= 현재'를 만족하면서 k < j인 경우)
                dp[i][j][1] = sum1[j - 1];
            }
        }

        long ans = 0;
        for (int j = 1; j <= M; j++) {
            ans = (ans + dp[N][j][0] + dp[N][j][1]) % MOD;
        }

        System.out.println(ans);
    }

}
