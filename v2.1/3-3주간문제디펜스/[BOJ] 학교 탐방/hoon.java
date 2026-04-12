import java.io.*;
import java.util.*;

public class Main {
    static class Pair{
        int to, dist;
        Pair(int to, int dist){
            this.to = to;
            this.dist = dist;
        }
    }
    static int N, M;
    static List<List<Pair>> gr = new ArrayList<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        for(int i = 0; i<=N; i++){
            gr.add(new ArrayList<>());
        }
        for(int i = 0; i<=M; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            c = c == 0 ? 1 : 0;
            gr.get(a).add(new Pair(b, c));
            gr.get(b).add(new Pair(a, c));
        }

        int minFatigue = prim(0, true);
        int maxFatigue = prim(0, false);
        int answer = maxFatigue-minFatigue;
        System.out.println(answer);

    }

    public static int prim(int start, boolean isMinHeap){
        PriorityQueue<Pair> pq;
        boolean[] visited = new boolean[N+1];
        if(isMinHeap) pq = new PriorityQueue<>(
            (a, b) -> a.dist - b.dist
        );
        else pq = new PriorityQueue<>(
            (a, b) -> b.dist - a.dist
        );

        pq.offer(new Pair(start, 0));

        int fatigue = 0;
        int count = 0;
        while(!pq.isEmpty()){
            Pair cur = pq.poll();
            if(visited[cur.to]) continue;
            fatigue += cur.dist;
            count++;
            visited[cur.to] = true;

            if(count == N+1) break;
            for(Pair next : gr.get(cur.to)){
                if(visited[next.to]) continue;
                pq.offer(next);
            }
        }

        return fatigue*fatigue;
    }

}
