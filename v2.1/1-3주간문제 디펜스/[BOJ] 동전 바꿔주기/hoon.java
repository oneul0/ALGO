import java.io.*;
import java.util.*;

public class Main {
    public static class Coin {
        int val, cnt;
        Coin(int val, int cnt){
            this.val = val;
            this.cnt = cnt;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());
        int k = Integer.parseInt(br.readLine());

        Coin[] coins = new Coin[k];
        for(int i = 0; i<k; i++){
            st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            coins[i] = new Coin(v, c);
        }

        int[] dp = new int[t + 1];
        dp[0] = 1;

        for (int i = 0; i < k; i++) {
            int val = coins[i].val;
            int cnt = coins[i].cnt;

            for (int j = t; j >= 0; j--) {
                for (int num = 1; num <= cnt; num++) {
                    int prev = j - (num * val);
                    if (prev < 0) break;
                    dp[j] += dp[prev];
                }
            }
        }
        System.out.println(dp[t]);
    }
}
//각 동전이 각 금액에 도달했을 때 dp
//1000 * 100