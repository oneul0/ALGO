import java.util.*;

class Solution {
    class State implements Comparable<State> {
        int mask, lastIdx, dist;
        State(int mask, int lastIdx, int dist) {
            this.mask = mask;
            this.lastIdx = lastIdx;
            this.dist = dist;
        }
        @Override
        public int compareTo(State o) {
            return this.dist - o.dist;
        }
    }

    int n, m, p;
    int[][] dists;
    int[] elevatorDist;
    int[] prereqMask; //선행관계 마스크
    int INF = 100000000;

    public int solution(int h, String[] grid, int[][] panels, int[][] seqs) {
        this.n = grid.length; this.m = grid[0].length(); this.p = panels.length;

        int er = 0, ec = 0;
        Loop: for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i].charAt(j) == '@') {
                    er = i; ec = j;
                    break Loop;
                }
            }
        }

        dists = new int[p][p];
        elevatorDist = new int[p];
        for (int i = 0; i < p; i++) {
            int[][] dMap = bfsAll(grid, panels[i][1] - 1, panels[i][2] - 1);
            for (int j = 0; j < p; j++) {
                dists[i][j] = dMap[panels[j][1] - 1][panels[j][2] - 1];
            }
            elevatorDist[i] = dMap[er][ec];
        }

        prereqMask = new int[p];
        for (int[] seq : seqs) {
            prereqMask[seq[1] - 1] |= (1 << (seq[0] - 1));
        }

        return dijkstra(panels);
    }

    private int dijkstra(int[][] panels) {
        PriorityQueue<State> pq = new PriorityQueue<>();
        int[][] minDists = new int[1 << p][p];
        for (int[] row : minDists) Arrays.fill(row, INF);

        for (int i = 0; i < p; i++) {
            // 선행 조건이 없는 패널 i를 첫 번째로 활성화하러 감
            if (prereqMask[i] == 0) {
                int d;
                if (i == 0) {
                    // 1번 패널을 바로 켤 수 있다면 이동 거리 0
                    d = 0;
                } else {
                    // 1번 패널 위치(0번 인덱스)에서 i번 패널로 이동
                    d = getRealDist(0, i, panels);
                }

                if (minDists[1 << i][i] > d) {
                    minDists[1 << i][i] = d;
                    pq.offer(new State(1 << i, i, d));
                }
            }
        }

        int fullMask = (1 << p) - 1;
        while (!pq.isEmpty()) {
            State cur = pq.poll();

            if (cur.dist > minDists[cur.mask][cur.lastIdx]) continue;
            if (cur.mask == fullMask) return cur.dist;

            for (int next = 0; next < p; next++) {
                if ((cur.mask & (1 << next)) == 0 && (cur.mask & prereqMask[next]) == prereqMask[next]) {
                    int nextMask = cur.mask | (1 << next);
                    int d = getRealDist(cur.lastIdx, next, panels);

                    if (minDists[nextMask][next] > cur.dist + d) {
                        minDists[nextMask][next] = cur.dist + d;
                        pq.offer(new State(nextMask, next, minDists[nextMask][next]));
                    }
                }
            }
        }
        return -1;
    }

    private int getRealDist(int u, int v, int[][] panels) {
        // u번째 패널 위치에서 v번째 패널 위치까지 이동 거리
        if (panels[u][0] == panels[v][0]) return dists[u][v];
        return elevatorDist[u] + Math.abs(panels[u][0] - panels[v][0]) + elevatorDist[v];
    }

    private int[][] bfsAll(String[] grid, int sr, int sc) {
        int[][] dist = new int[n][m];
        for (int[] row : dist) Arrays.fill(row, -1);
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{sr, sc});
        dist[sr][sc] = 0;
        int[] dr = {-1, 1, 0, 0}, dc = {0, 0, -1, 1};

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int i = 0; i < 4; i++) {
                int nr = cur[0] + dr[i], nc = cur[1] + dc[i];
                if (nr >= 0 && nr < n && nc >= 0 && nc < m && grid[nr].charAt(nc) != '#' && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[cur[0]][cur[1]] + 1;
                    q.offer(new int[]{nr, nc});
                }
            }
        }
        return dist;
    }
}
//위상정렬로 다음 패널 정하기
//만약, 다음으로 이동할 수 있는(inbound == 0) 패널이 여러 개면 순서대로 진행하는게 나을듯? 아니라면 가장 가까운거