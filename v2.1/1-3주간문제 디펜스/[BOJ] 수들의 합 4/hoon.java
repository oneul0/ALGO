import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        Map<Long, Long> sumCnt = new HashMap<>();
        long sum = 0;
        long count = 0;
        for(int i = 0; i<n; i++){
            sum += Long.parseLong(st.nextToken());
            if(sum == k) count++;
            if(sumCnt.containsKey(sum-k)){
                count += sumCnt.get(sum-k);
            }
            sumCnt.put(sum, sumCnt.getOrDefault(sum, 0L)+1);
        }
        System.out.println(count);
    }
}