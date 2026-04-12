import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static class Node implements Comparable<Node>{
        int num, dist;

        Node(int num, int dist){
            this.num = num;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node o){
            return this.dist - o.dist;
        }
    }

    static int V, M, J, S;
    static final int INF = 1_000_000_000;
    static List<List<Node>> gr = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        V = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        for(int i = 0; i <= V; i++){
            gr.add(new ArrayList<>());
        }

        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            gr.get(a).add(new Node(b, c));
            gr.get(b).add(new Node(a, c));
        }

        st = new StringTokenizer(br.readLine());
        J = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());

        int[] jDist = new int[V+1];
        int[] sDist = new int[V+1];

        dijkstra(J, jDist);
        dijkstra(S, sDist);

        int minSum = INF;
        for(int i = 1; i <= V; i++){
            if(i == J || i == S) continue;
            if(jDist[i] == INF || sDist[i] == INF) continue;
            minSum = Math.min(minSum, jDist[i] + sDist[i]);
        }

        int answer = -1;
        int minJ = INF;
        for(int i = 1; i <= V; i++){
            if(i == J || i == S) continue;
            if(jDist[i] == INF || sDist[i] == INF) continue;
            if(jDist[i] + sDist[i] != minSum) continue;
            if(jDist[i] > sDist[i]) continue;
            if(jDist[i] < minJ){
                minJ = jDist[i];
                answer = i;
            }
            else if(jDist[i] == minJ){
                answer = Math.min(answer, i);
            }
        }

        System.out.println(answer);
    }

    static void dijkstra(int start, int[] dist){
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Arrays.fill(dist, INF);

        dist[start] = 0;
        pq.offer(new Node(start, 0));

        while(!pq.isEmpty()){
            Node cur = pq.poll();
            if(cur.dist > dist[cur.num]) continue;
            for(Node next : gr.get(cur.num)){
                int newDist = cur.dist + next.dist;
                if(newDist < dist[next.num]){
                    dist[next.num] = newDist;
                    pq.offer(new Node(next.num, newDist));
                }
            }
        }
    }
}
