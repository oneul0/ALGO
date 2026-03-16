import java.util.*;

class Solution {
    class Pipe {
        int to, type;
        Pipe(int to, int type) {
            this.to = to;
            this.type = type;
        }
    }

    List<List<Pipe>> tree = new ArrayList<>();
    boolean[] isInfected;
    List<Integer> infectedList = new ArrayList<>();
    int n, k, maxInfected = 0;

    public int solution(int n, int infection, int[][] edges, int k) {
        this.n = n;
        this.k = k;
        this.isInfected = new boolean[n + 1];

        for (int i = 0; i <= n; i++) tree.add(new ArrayList<>());
        for (int[] edge : edges) {
            tree.get(edge[0]).add(new Pipe(edge[1], edge[2]));
            tree.get(edge[1]).add(new Pipe(edge[0], edge[2]));
        }

        isInfected[infection] = true;
        infectedList.add(infection);

        comb(0, -1);

        return maxInfected;
    }

    private void comb(int depth, int lastType) {
        if (depth == k) {
            maxInfected = Math.max(maxInfected, infectedList.size());
            return;
        }

        for (int type = 1; type <= 3; type++) {
            if (type == lastType) continue;

            List<Integer> newlyInfected = spread(type);

            comb(depth + 1, type);

            for (int node : newlyInfected) {
                isInfected[node] = false;
                infectedList.remove(infectedList.size() - 1);
            }
        }
    }

    private List<Integer> spread(int type) {
        List<Integer> added = new ArrayList<>();
        Queue<Integer> q = new ArrayDeque<>();

        for (int node : infectedList) {
            q.offer(node);
        }

        while (!q.isEmpty()) {
            int cur = q.poll();

            for (Pipe next : tree.get(cur)) {
                if (next.type == type && !isInfected[next.to]) {
                    isInfected[next.to] = true;
                    added.add(next.to);
                    infectedList.add(next.to);
                    q.offer(next.to);
                }
            }
        }
        return added;
    }
}