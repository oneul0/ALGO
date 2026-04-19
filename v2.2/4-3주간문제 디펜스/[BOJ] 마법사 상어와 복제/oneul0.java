import java.io.*;
import java.util.*;

public class Main {
    static final int SIZE = 4;

    // 물고기 방향 ←, ↖, ↑, ↗, →, ↘, ↓, ↙
    static int[] fr = {0, -1, -1, -1, 0, 1, 1, 1};
    static int[] fc = {-1, -1, 0, 1, 1, 1, 0, -1};

    // 상어 방향 상 좌 하 우
    static int[] srMove = {-1, 0, 1, 0};
    static int[] scMove = {0, -1, 0, 1};

    static int M, S;
    static int[][][] fish = new int[SIZE][SIZE][8];
    static int[][] smell = new int[SIZE][SIZE];

    static int sharkR, sharkC;

    static int maxEat;
    static int[] bestPath;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken()) - 1;
            fish[r][c][d]++;
        }

        st = new StringTokenizer(br.readLine());
        sharkR = Integer.parseInt(st.nextToken()) - 1;
        sharkC = Integer.parseInt(st.nextToken()) - 1;

        for (int turn = 1; turn <= S; turn++) {
            int[][][] copy = copyFish(fish);
            moveFish(turn);
            moveShark(turn);
            applyCopy(copy);
        }

        System.out.println(countFish());
    }

    static int[][][] copyFish(int[][][] src) {
        int[][][] dst = new int[SIZE][SIZE][8];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                for (int d = 0; d < 8; d++) {
                    dst[r][c][d] = src[r][c][d];
                }
            }
        }
        return dst;
    }

    static void applyCopy(int[][][] copy) {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                for (int d = 0; d < 8; d++) {
                    fish[r][c][d] += copy[r][c][d];
                }
            }
        }
    }

    static void moveFish(int turn) {
        int[][][] next = new int[SIZE][SIZE][8];

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                for (int d = 0; d < 8; d++) {
                    int cnt = fish[r][c][d];
                    if (cnt == 0) continue;

                    boolean moved = false;
                    int nd = d;

                    for (int rot = 0; rot < 8; rot++) {
                        int nr = r + fr[nd];
                        int nc = c + fc[nd];

                        if (isIn(nr, nc) &&
                            !(nr == sharkR && nc == sharkC) &&
                            !hasSmell(nr, nc, turn)) {
                            next[nr][nc][nd] += cnt;
                            moved = true;
                            break;
                        }

                        nd = (nd + 7) % 8; // 반시계 45도
                    }

                    if (!moved) {
                        next[r][c][d] += cnt;
                    }
                }
            }
        }

        fish = next;
    }

    static boolean hasSmell(int r, int c, int turn) {
        return smell[r][c] > 0 && turn - smell[r][c] <= 2;
    }

    static void moveShark(int turn) {
        maxEat = -1;
        bestPath = new int[3];

        dfsShark(0, sharkR, sharkC, 0, new int[3], new boolean[SIZE][SIZE]);

        for (int step = 0; step < 3; step++) {
            sharkR += srMove[bestPath[step]];
            sharkC += scMove[bestPath[step]];

            if (cellFishCount(sharkR, sharkC) > 0) {
                for (int d = 0; d < 8; d++) {
                    fish[sharkR][sharkC][d] = 0;
                }
                smell[sharkR][sharkC] = turn;
            }
        }
    }

    static void dfsShark(int depth, int r, int c, int eat, int[] path, boolean[][] visited) {
        if (depth == 3) {
            if (eat > maxEat) {
                maxEat = eat;
                bestPath = path.clone();
            }
            return;
        }

        for (int dir = 0; dir < 4; dir++) { // 상, 좌, 하, 우 순서
            int nr = r + srMove[dir];
            int nc = c + scMove[dir];

            if (!isIn(nr, nc)) continue;

            path[depth] = dir;

            boolean firstVisit = !visited[nr][nc];
            int gained = 0;

            if (firstVisit) {
                gained = cellFishCount(nr, nc);
                visited[nr][nc] = true;
            }

            dfsShark(depth + 1, nr, nc, eat + gained, path, visited);

            if (firstVisit) {
                visited[nr][nc] = false;
            }
        }
    }

    static int cellFishCount(int r, int c) {
        int sum = 0;
        for (int d = 0; d < 8; d++) {
            sum += fish[r][c][d];
        }
        return sum;
    }

    static int countFish() {
        int sum = 0;
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                for (int d = 0; d < 8; d++) {
                    sum += fish[r][c][d];
                }
            }
        }
        return sum;
    }

    static boolean isIn(int r, int c) {
        return 0 <= r && r < SIZE && 0 <= c && c < SIZE;
    }
}