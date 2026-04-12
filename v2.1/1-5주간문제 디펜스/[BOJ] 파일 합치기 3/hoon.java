import java.io.*;
import java.util.*;

public class Main {
    static int t, k;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        t = Integer.parseInt(br.readLine());
        while(t-- > 0){
            k = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            PriorityQueue<Long> pq = new PriorityQueue<>();
            for(int i = 0; i<k; i++){
                pq.offer(Long.parseLong(st.nextToken()));
            }

            long ans = 0;

            while(!pq.isEmpty()){
                long a = pq.poll();
                if(pq.isEmpty())break;
                long b = pq.poll();

                long sum = a + b;
                ans += sum;
                pq.offer(sum);
            }

            System.out.println(ans);
        }
    }
}
