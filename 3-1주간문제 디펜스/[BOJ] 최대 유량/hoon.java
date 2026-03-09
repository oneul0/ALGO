import java.io.*;
import java.util.*;

public class Main {
    //에드몬드-카프 알고리즘
    static int[][] capacity = new int[52][52];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int from = charToInt(st.nextToken().charAt(0));
            int to = charToInt(st.nextToken().charAt(0));
            int cap = Integer.parseInt(st.nextToken());

            capacity[from][to] += cap;
            capacity[to][from] += cap;
        }

        System.out.println(maxFlow(charToInt('A'), charToInt('Z')));
    }

    static int charToInt(char c) {
        if (c >= 'A' && c <= 'Z') return c - 'A';
        return c - 'a' + 26;
    }

    static int maxFlow(int start, int end) {
        int totalFlow = 0;

        while (true) {
            // 1. BFS로 경로 탐색
            int[] parent = new int[52];
            Arrays.fill(parent, -1);
            Queue<Integer> q = new ArrayDeque<>();

            q.offer(start);
            parent[start] = start;

            while (!q.isEmpty() && parent[end] == -1) {
                int cur = q.poll();
                for (int next = 0; next < 52; next++) {
                    // 남은 용량이 있고 방문하지 않은 노드라면
                    if (capacity[cur][next] > 0 && parent[next] == -1) {
                        q.offer(next);
                        parent[next] = cur;
                    }
                }
            }

            // 2. 더 이상 Z까지 가는 경로가 없으면 종료
            if (parent[end] == -1) break;

            // 3. 찾은 경로에서 보낼 수 있는 최소 유량 찾기
            int flow = Integer.MAX_VALUE;
            for (int i = end; i != start; i = parent[i]) {
                flow = Math.min(flow, capacity[parent[i]][i]);
            }

            // 4. 경로상의 간선 용량 업데이트
            for (int i = end; i != start; i = parent[i]) {
                capacity[parent[i]][i] -= flow; // 순방향은 차감
                capacity[i][parent[i]] += flow; // 역방향은 추가
            }

            totalFlow += flow;
        }

        return totalFlow;
    }
}