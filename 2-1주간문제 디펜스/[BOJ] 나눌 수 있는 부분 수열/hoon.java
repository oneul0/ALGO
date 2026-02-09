import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        int c = Integer.parseInt(br.readLine());
        while(c-->0){
            st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken());
            int n = Integer.parseInt(st.nextToken());

            //(prefixSum[j] - prefixSum[i-1])%d == 0
            //prefixSum[j]%d == prefixSum[i-1]%d
            long[] remainCnt = new long[d];
            remainCnt[0] = 1;

            long curSum = 0;
            st = new StringTokenizer(br.readLine());
            for(int i = 0; i<n; i++){
                int num = Integer.parseInt(st.nextToken());
                curSum += num;
                int remain = (int)(curSum%d);
                remainCnt[remain]++;
            }

            long ans = 0;
            for(int i = 0; i<d; i++){
                long count = remainCnt[i];
                if(count >= 2) {
                    ans += (count * (count - 1)) / 2;
                }
            }
            bw.write(ans +"\n");
        }
        bw.flush();
        br.close();bw.close();
    }
}
