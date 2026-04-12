import java.io.*;
import java.util.*;

public class Main {
    static int N, M, K;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        int[][] wSum = new int[N+1][M+1];

        for(int i = 1; i<=N; i++){
            String line = br.readLine();
            for(int j = 1; j<=M; j++){
                char cur = line.charAt(j-1);

                int val = 0;
                boolean isEven = (i+j) % 2 == 0;
                char target = isEven ? 'W' : 'B';
                if(cur != target) val = 1;
                wSum[i][j] = val + wSum[i-1][j] + wSum[i][j-1] - wSum[i-1][j-1];
            }
        }
        int answer = Integer.MAX_VALUE;

        for (int i = K; i <= N; i++) {
            for (int j = K; j <= M; j++) {
                int S = wSum[i][j] - wSum[i - K][j] - wSum[i][j - K] + wSum[i - K][j - K];
                //W시작패턴(0,0 -> W)인 경우 고치는 개수는 B 시작 패턴으로 고치는 개수의 반대
                int count = Math.min(S, K * K - S);
                answer = Math.min(answer, count);
            }
        }
        System.out.println(answer);
    }


}
