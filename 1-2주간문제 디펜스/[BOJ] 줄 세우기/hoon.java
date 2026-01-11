import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        List<List<Integer>> gr = new ArrayList<>();
        int[] indegrees = new int[N+1];
        for(int i = 0; i<=N; i++){
            gr.add(new ArrayList<>());
        }

        for(int i = 0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            int a= Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            gr.get(a).add(b);
            indegrees[b]++;
        }

        Queue<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[N+1];
        List<Integer> result = new ArrayList<>();
        for(int i = 1; i<=N; i++){
            if(indegrees[i] == 0){
                q.add(i);
                visited[i] = true;
            }
        }

        while(!q.isEmpty()){
            int cur = q.poll();
            result.add(cur);

            for(int child : gr.get(cur)){
                indegrees[child]--;
                if(visited[child]) continue;
                if(indegrees[child] <= 0){
                    q.add(child);
                    visited[child] = true;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i : result){
            sb.append(i).append(" ");
        }
        System.out.println(sb);
    }
}
