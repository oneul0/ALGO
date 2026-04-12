import java.io.*;
import java.util.*;

public class Main {
    static int N, M, X;
    static List<List<Node>> graph = new ArrayList<>();
    static List<List<Node>> revGraph = new ArrayList<>();

    static class Node implements Comparable<Node> {
        int idx, cost;
        public Node(int idx, int cost) {
            this.idx = idx;
            this.cost = cost;
        }
        @Override
        public int compareTo(Node o) {
            return this.cost - o.cost;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
            revGraph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            graph.get(u).add(new Node(v, w));
            revGraph.get(v).add(new Node(u, w));
        }

        int[] goHome = dijkstra(graph, X);

        int[] goToParty = dijkstra(revGraph, X);

        int maxTime = 0;
        for (int i = 1; i <= N; i++) {
            int roundTrip = goToParty[i] + goHome[i];
            maxTime = Math.max(maxTime, roundTrip);
        }

        System.out.println(maxTime);
    }

    static int[] dijkstra(List<List<Node>> adj, int start) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] dist = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        pq.offer(new Node(start, 0));
        dist[start] = 0;

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int curIdx = current.idx;
            int curCost = current.cost;

            if (curCost > dist[curIdx]) continue;

            for (Node next : adj.get(curIdx)) {
                if (dist[next.idx] > dist[curIdx] + next.cost) {
                    dist[next.idx] = dist[curIdx] + next.cost;
                    pq.offer(new Node(next.idx, dist[next.idx]));
                }
            }
        }
        return dist;
    }
}