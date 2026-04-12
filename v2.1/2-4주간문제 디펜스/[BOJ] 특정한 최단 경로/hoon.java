import java.io.*;
import java.util.*;

public class Main {
    static class Node implements Comparable<Node> {
        int val;
        int dist;
        Node(int val, int dist){
            this.val = val;
            this.dist = dist;
        }
        @Override
        public int compareTo(Node o){
            return this.dist - o.dist;
        }
    }
    static int N, E, v1, v2;
    static List<List<Node>> gr = new ArrayList<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        for(int i = 0; i<=N; i++){
            gr.add(new ArrayList<>());
        }
        for(int i = 0; i<E; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            gr.get(a).add(new Node(b, c));
            gr.get(b).add(new Node(a, c));
        }
        st = new StringTokenizer(br.readLine());
        v1 = Integer.parseInt(st.nextToken());
        v2 = Integer.parseInt(st.nextToken());

        long[] distFrom1 = getShortestDist(1);
        long[] distFromV1 = getShortestDist(v1);
        long[] distFromV2 = getShortestDist(v2);

        long path1 = distFrom1[v1] + distFromV1[v2] + distFromV2[N];
        long path2 = distFrom1[v2] + distFromV2[v1] + distFromV1[N];

        long answer = Math.min(path1, path2);
        System.out.println(answer >= 1_000_000_000 ? -1 : answer);
    }
    public static long[] getShortestDist(int start){
        PriorityQueue<Node> pq = new PriorityQueue<>();
        long[] dists = new long[N+1];
        Arrays.fill(dists, 1_000_000_000);
        pq.offer(new Node(start, 0));
        dists[start] = 0;
        while (!pq.isEmpty()){
            Node cur = pq.poll();
            if(cur.dist > dists[cur.val]) continue;
            for(Node next : gr.get(cur.val)){
                int newDist = cur.dist + next.dist;
                if(newDist< dists[next.val]) {
                    pq.offer(new Node(next.val, newDist));
                    dists[next.val] = newDist;
                }
            }
        }
        return dists;
    }
}
