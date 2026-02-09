import java.io.*;
import java.util.*;

public class Main {
    static int[] cards;
    static int[][] dp;
    static int[] pSum;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());
        while (T-->0){
            int N = Integer.parseInt(br.readLine());
            cards = new int[N];
            st = new StringTokenizer(br.readLine());
            for(int i = 0; i<N; i++){
                cards[i] = Integer.parseInt(st.nextToken());
            }
            dp = new int[N][N];
            pSum = new int[N+1];

            for(int i=0; i<N; i++) {
                pSum[i+1] = pSum[i] + cards[i];
            }

            System.out.println(solve(0, N-1));
        }
    }

    //구간의 합으로 계산
    static int solve(int l, int r) {
        if (l == r) {
            return cards[l];
        }

        if (dp[l][r] != 0) {
            return dp[l][r];
        }

        int curSum = pSum[r+1] - pSum[l];

        int pickLeft = curSum - solve(l + 1, r);
        int pickRight = curSum - solve(l, r - 1);

        dp[l][r] = Math.max(pickLeft, pickRight);

        return dp[l][r];
    }
}
