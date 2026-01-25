import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int[] s = new int[n+1];
        for(int i = 1; i<=n; i++){
            s[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        int[] h = new int[n+1];
        for(int i = 1; i<=n; i++){
            h[i] = Integer.parseInt(st.nextToken());
        }

        int[][][] dp = new int[n+1][101][2]; //step, hp, played = score :: play = 1, not play = 0
        for(int i = 0; i<dp.length; i++){
            for(int j = 0; j<dp[i].length; j++){
                Arrays.fill(dp[i][j], -1);
            }
        }
        dp[0][100][0] = 0;
        int maxScore = 0;
        for(int step =1; step<=n; step++){
            for(int hp = 100; hp>=0; hp--){
                if(dp[step-1][hp][0] != -1){
                    int prevScore = dp[step-1][hp][0];
                    int newHp = Math.min(100, hp + k);
                    dp[step][newHp][0] = Math.max(dp[step][newHp][0], prevScore);
                    if(hp - h[step] >= 0){
                        int newPlayedHp = Math.min(100, hp - h[step] + k);
                        dp[step][newPlayedHp][1] = Math.max(dp[step][newPlayedHp][1], prevScore + s[step]);
                    }
                }
                if(dp[step-1][hp][1] != -1){
                    int prevScore = dp[step-1][hp][1];
                    int newHp = Math.min(100, hp + k);
                    dp[step][newHp][0] = Math.max(dp[step][newHp][0], prevScore);
                    if(hp - h[step] >= 0){
                        int newPlayedHp = Math.min(100, hp - h[step] + k);
                        dp[step][newPlayedHp][1] = Math.max(dp[step][newPlayedHp][1], prevScore + s[step]);
                    }
                }
            }
        }

        for (int hp = 0; hp <= 100; hp++) {
            maxScore = Math.max(maxScore, dp[n][hp][0]);
            maxScore = Math.max(maxScore, dp[n][hp][1]);
        }
        System.out.println(maxScore);
    }
}
