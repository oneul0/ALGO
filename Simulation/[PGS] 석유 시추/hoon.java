import java.util.*;

class Solution {
    int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
    boolean[][] chk;
    int n, m, idx = 1;
    int[][] land;
    ArrayList<Integer> oil = new ArrayList<>();

    public int solution(int[][] land) {
        this.land = land;
        n = land.length;
        m = land[0].length;
        chk = new boolean[n][m];
        oil.add(0);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (land[i][j] == 1 && !chk[i][j]) {
                    bfs(i, j);
                }
            }
        }

        int answer = 0;
        for (int j = 0; j < m; j++) {
            int curSum = 0;
            Set<Integer> visitedOil = new HashSet<>();
            for (int i = 0; i < n; i++) {
                if (land[i][j] > 0) {
                    visitedOil.add(land[i][j]);
                }
            }

            for (int oilIdx : visitedOil) {
                curSum += oil.get(oilIdx);
            }
            answer = Math.max(answer, curSum);
        }

        return answer;
    }

    public void bfs(int sx, int sy) {
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{sx, sy});
        chk[sx][sy] = true;
        land[sx][sy] = idx;

        int result = 0;
        result++;

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];

                if (nx < 0 || ny < 0 || nx >= n || ny >= m || land[nx][ny] == 0 || chk[nx][ny]) continue;

                q.offer(new int[]{nx, ny});
                chk[nx][ny] = true;
                land[nx][ny] = idx;
                result++;
            }
        }
        oil.add(result);
        idx++;
    }
}