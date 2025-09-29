import java.util.*;
import java.io.*;

public class Main {
    static class Pair {
        int x, y, time;
        Pair(int x, int y, int time) {
            this.x = x;
            this.y = y;
            this.time = time;
        }
    }
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, M, count = 0;
    static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
    static int[][] lab;
    static List<Pair> canLoc = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        lab = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                lab[i][j] = Integer.parseInt(st.nextToken());
                if (lab[i][j] == 0) count++;
                if (lab[i][j] == 2) canLoc.add(new Pair(i, j, 0));
            }
        }

        if (count == 0) {
            System.out.println(0);
            return;
        }

        int answer = solve(0, new ArrayList<>());
        if (answer == Integer.MAX_VALUE) answer = -1;
        System.out.println(answer);
        br.close();
    }

    public static int solve(int sIdx, List<Pair> selected) {
        int result = Integer.MAX_VALUE;
        if (selected.size() == M) {
            Deque<Pair> q = new ArrayDeque<>();
            for (Pair p : selected) q.offer(new Pair(p.x, p.y, 0));
            return bfs(q);
        }
        for (int i = sIdx; i < canLoc.size(); i++) {
            Pair cur = canLoc.get(i);
            selected.add(cur);
            result = Math.min(solve(i + 1, selected), result);
            selected.remove(selected.size() - 1);
        }
        return result;
    }

    public static int bfs(Deque<Pair> q) {
        int result = 0;
        int activated = 0;
        boolean[][] chk = new boolean[N][N];
        for (Pair tmp : q) chk[tmp.x][tmp.y] = true;

        while (!q.isEmpty()) {
            Pair cur = q.remove();
            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if (nx < 0 || ny < 0 || nx >= N || ny >= N || chk[nx][ny]) continue;
                if (lab[nx][ny] == 1) continue;
                if (lab[nx][ny] == 0) {
                    activated++;
                    result = Math.max(result,cur.time + 1);
                }
                q.offer(new Pair(nx, ny, cur.time + 1));
                chk[nx][ny] = true;
            }
        }
        if (count != activated) return Integer.MAX_VALUE;
        return result;
    }
}
