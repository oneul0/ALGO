import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        long[] p = new long[n];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i<n; i++){
            p[i] = Long.parseLong(st.nextToken());
        }
        long k = Long.parseLong(br.readLine());

        long totalSum = 0;
        for(long num : p){
            long tmp = k;
            while(tmp >= num){
                totalSum += tmp/num;
                tmp /= num;
            }
        }

        System.out.println(totalSum);

    }
}
