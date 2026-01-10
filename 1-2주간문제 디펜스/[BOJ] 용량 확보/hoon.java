import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int tc = Integer.parseInt(br.readLine());
        while(tc-- > 0){
            StringTokenizer st;
            //ne
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            long e = Integer.parseInt(st.nextToken());

            //S
            int[] S = new int[n];
            st = new StringTokenizer(br.readLine());
            int totalS = 0;
            for(int i = 0; i<n; i++){
                S[i] = Integer.parseInt(st.nextToken());
                totalS += S[i];
            }

            if(e <= 0) {
                System.out.println(0);
                continue;
            }
            long K = (e + 1)/2;
            if(K > totalS){
                System.out.println("FULL");
                continue;
            }

            //dp or greedy
            boolean[] dp = new boolean[totalS+1]; // long[변환된 원본 용량의 합] = 가능/불가능
            dp[0] = true;
            for(int i : S){
                for(int j = totalS; j>=i; j--){
                    if(dp[j-i]){
                        dp[j] = true;
                    }
                }
            }

            for(int j = (int)K ; j<=totalS; j++){
                if(dp[j]){
                    System.out.println(j);
                    break;
                }
            }
        }
    }
}
