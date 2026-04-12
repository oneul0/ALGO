import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());

        long[] pSum = new long[N+1];
        pSum[0] = 0;
        for(int i = 1; i<=N; i++){
            int num = Integer.parseInt(st.nextToken());
            pSum[i] = pSum[i-1] + num;
        }

        int len = 100_001;
        int l = N, r = N;

        while (l >= 0) {
            long curSum = pSum[r] - pSum[l];

            if (curSum >= S) {
                len = Math.min(len, r - l);
                r--;
                if (r < l) l = r;
            } else {
                l--;
            }
        }
        System.out.println(len == 100_001 ? 0 : len);


        //인덱스 역순으로 범위를 늘리거나 좁혀나가면서 S가 충족되는지 체크
        //충족된다면 min(cur, ans)
    }

}