import java.io.*;
import java.util.*;

public class Main {
    static int n, m;
    static List<List<Integer>> graph = new ArrayList<>();
    static List<List<Integer>> reverseGraph = new ArrayList<>();

    static void bfs(int start, List<List<Integer>> gr, boolean[] visited) {
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(start);
        visited[start] = true;

        while (!q.isEmpty()) {
            int cur = q.poll();

            for (int next : gr.get(cur)) {
                if (!visited[next]) {
                    visited[next] = true;
                    q.offer(next);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
            reverseGraph.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            graph.get(u).add(v);
            reverseGraph.get(v).add(u);
        }

        boolean[] canReachFromStart = new boolean[n + 1];
        bfs(1, graph, canReachFromStart);

        boolean[] canReachEnd = new boolean[n + 1];
        bfs(n, reverseGraph, canReachEnd);

        int t = Integer.parseInt(br.readLine());

        for (int i = 0; i < t; i++) {
            int bombPos = Integer.parseInt(br.readLine());
            if (canReachFromStart[bombPos] && canReachEnd[bombPos]) {
                bw.write("Defend the CTP\n");
            } else {
                bw.write("Destroyed the CTP\n");
            }
        }
        bw.flush();
        bw.close();
        br.close();
    }
}