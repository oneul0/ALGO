import java.io.*;
import java.util.*;

public class Main {
    static class Group {
        int size;
        int rainbow;
        int stdR, stdC;
        ArrayList<int[]> cells = new ArrayList<>();
    }

    static int N, M;
    static int[][] grid;
    static boolean[][] visited;
    static final int EMPTY = -2;

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        grid = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = 0;

        while (true) {
            visited = new boolean[N][N];

            Group best = findBestGroup();
            if (best == null) break;

            answer += removeGroup(best);
            applyGravity();
            rotateCCW();
            applyGravity();
        }

        System.out.println(answer);
    }

    static Group findBestGroup() {
        Group best = null;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] > 0 && !visited[i][j]) {
                    Group g = bfs(i, j, grid[i][j]);
                    if (g == null) continue;

                    if (best == null || better(g, best)) {
                        best = g;
                    }
                }
            }
        }

        return best;
    }

    static Group bfs(int sr, int sc, int color) {
        Queue<int[]> q = new ArrayDeque<>();
        ArrayList<int[]> cells = new ArrayList<>();
        ArrayList<int[]> rainbowCells = new ArrayList<>();

        visited[sr][sc] = true;
        q.offer(new int[]{sr, sc});
        cells.add(new int[]{sr, sc});

        int size = 1;
        int rainbow = 0;

        // 무지개 제외하고 행이 가장 작고 열이 가장 작은 블록
        int stdR = sr;
        int stdC = sc;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0];
            int c = cur[1];

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr < 0 || nc < 0 || nr >= N || nc >= N) continue;
                if (visited[nr][nc]) continue;
                if (grid[nr][nc] == -1 || grid[nr][nc] == EMPTY) continue;

                // 같은 색 일반 블록
                if (grid[nr][nc] == color) {
                    visited[nr][nc] = true;
                    q.offer(new int[]{nr, nc});
                    cells.add(new int[]{nr, nc});
                    size++;

                    // 무지개 제외 블록 중 행, 열 가장 작은 것
                    if (nr < stdR || (nr == stdR && nc < stdC)) {
                        stdR = nr;
                        stdC = nc;
                    }
                }
                // 무지개 블록
                else if (grid[nr][nc] == 0) {
                    visited[nr][nc] = true;
                    q.offer(new int[]{nr, nc});
                    cells.add(new int[]{nr, nc});
                    rainbowCells.add(new int[]{nr, nc});
                    size++;
                    rainbow++;
                }
            }
        }

        // 무지개 방문 해제
        for (int[] rc : rainbowCells) {
            visited[rc[0]][rc[1]] = false;
        }

        if (size < 2) return null;

        Group g = new Group();
        g.size = size;
        g.rainbow = rainbow;
        g.stdR = stdR;
        g.stdC = stdC;
        g.cells = cells;
        return g;
    }

    static int removeGroup(Group g) {
        for (int[] cell : g.cells) {
            grid[cell[0]][cell[1]] = EMPTY;
        }
        return g.size * g.size;
    }

    static void applyGravity() {
        for (int c = 0; c < N; c++) {
            int write = N - 1; // 현재 블록이 떨어질 위치

            for (int r = N - 1; r >= 0; r--) {
                // 검은색 블록 만나면 그 위 구간 따로 처리
                if (grid[r][c] == -1) {
                    write = r - 1;
                }
                // 일반/무지개 블록이면 아래로 내리기
                else if (grid[r][c] >= 0) {
                    int val = grid[r][c];
                    grid[r][c] = EMPTY;
                    grid[write][c] = val;
                    write--;
                }
            }
        }
    }

    static void rotateCCW() {
        int[][] next = new int[N][N];

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                next[N - 1 - c][r] = grid[r][c];
            }
        }

        grid = next;
    }

    static boolean better(Group a, Group b) {
        if (a.size != b.size) return a.size > b.size;
        if (a.rainbow != b.rainbow) return a.rainbow > b.rainbow;
        if (a.stdR != b.stdR) return a.stdR > b.stdR;
        return a.stdC > b.stdC;
    }
}