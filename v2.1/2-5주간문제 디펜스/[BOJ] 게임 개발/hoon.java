import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static List<List<Integer>> gr = new ArrayList<>();
    static int[] inBound;
    static int[] buildTimes;
    static int[] compliteTimes;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        for(int i = 0; i<=N; i++){
            gr.add(new ArrayList<>());
        }
        inBound = new int[N+1];
        buildTimes = new int[N+1];
        compliteTimes = new int[N+1];
        Queue<Integer> q = new ArrayDeque<>();
        for(int i = 1; i<=N; i++){
            st = new StringTokenizer(br.readLine());
            buildTimes[i] =Integer.parseInt(st.nextToken());
            while(st.hasMoreTokens()){
                int node = Integer.parseInt(st.nextToken());
                if(node == -1) break;
                inBound[i]++;
                gr.get(node).add(i);
            }
        }
        for(int i = 1; i<=N; i++){
            if(inBound[i] == 0){
                q.offer(i);
                compliteTimes[i] = buildTimes[i];
            }
        }

        while(!q.isEmpty()){
            int cur = q.poll();

            for(int child : gr.get(cur)){
                inBound[child]--;
                compliteTimes[child] = Math.max(compliteTimes[child], buildTimes[child] + compliteTimes[cur]);
                if(inBound[child] == 0){
                    q.offer(child);
                }
            }
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for(int i = 1; i<=N; i++){
            bw.write(compliteTimes[i]+"\n");
        }
        bw.flush();
    }
}
