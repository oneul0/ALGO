import java.io.*;
import java.util.*;

public class Main {
    static int[][] players = new int[1001][2];
    static int[][][] dp = new int[1001][16][16];

    static int solve(final int last, int index, int w, int b) {
        if (w == 15 && b == 15) return 0;
        if (index == last) return 0;
        if (dp[index][w][b] != 0) return dp[index][w][b];

        int maxVal = solve(last, index + 1, w, b);

        if (w < 15) {
            maxVal = Math.max(maxVal, solve(last, index + 1, w + 1, b) + players[index][0]);
        }
        if (b < 15) {
            maxVal = Math.max(maxVal, solve(last, index + 1, w, b + 1) + players[index][1]);
        }
        return dp[index][w][b] = maxVal;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        String line = "";
        int last = 0;
        while ((line = br.readLine()) != null && !line.isEmpty()){
            st = new StringTokenizer(line);
            players[last][0] = Integer.parseInt(st.nextToken());
            players[last++][1] = Integer.parseInt(st.nextToken());
        }

        System.out.println(solve(last, 0, 0, 0));
    }
}