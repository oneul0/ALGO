import java.io.*;
import java.util.*;

public class Main {
    static class Pair{
        int p, q;
        Pair(int p, int q){
            this.p = p;
            this.q = q;
        }

    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        Pair[] arr = new Pair[N];
        for(int i = 0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            arr[i] = new Pair(a, b);
        }
        br.close();
        Arrays.sort(arr, (a, b) -> a.p - b.p);
        PriorityQueue<int[]> uesd = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        PriorityQueue<Integer> empty = new PriorityQueue<>();

        int[] usage = new int[N+1];
        int maxIdx = 0;
        for(Pair time : arr){
            while(!uesd.isEmpty() && uesd.peek()[0] <= time.p){
                empty.offer(uesd.poll()[1]);
            }
            int cur;
            if(empty.isEmpty()){
                maxIdx++;
                cur = maxIdx;
            }
            else{
                cur = empty.poll();
            }

            usage[cur]++;
            uesd.offer(new int[]{time.q, cur});
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(maxIdx+"\n");
        for(int i = 1; i<=maxIdx; i++){
            bw.write(usage[i] +" ");
        }
        bw.flush();
        bw.close();
    }
}
