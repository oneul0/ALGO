import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        long MIN_INF = -10_000_000_000L;
        // dp1: -A[i]
        // dp2: -A[i] + A[j]
        // dp3: -A[i] + A[j] - A[k]
        // dp4: -A[i] + A[j] - A[k] + A[l]
        long dp1 = MIN_INF;
        long dp2 = MIN_INF;
        long dp3 = MIN_INF;
        long dp4 = MIN_INF;

        for(int i = 0; i < n; i++){
            //이전 시점의 결과를 쓰기 위해 dp1~4를 역순으로 배치
            //0~n-1 까지의 선택
            //반복문으로도 구현 가능
            long num = Long.parseLong(st.nextToken());

            dp4 = Math.max(dp4, dp3 + num);

            dp3 = Math.max(dp3, dp2 - num);

            dp2 = Math.max(dp2, dp1 + num);

            dp1 = Math.max(dp1, -num);
        }

        System.out.println(dp4);
    }
}
