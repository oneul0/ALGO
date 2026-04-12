import java.util.*;
import java.io.*;

public class Main {
    static class Pair {
        int a; // 물고기 번호
        int b; // 방향 0~7

        Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    static int maxSum = 0;

    // ↑, ↖, ←, ↙, ↓, ↘, →, ↗
    static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dc = {0, -1, -1, -1, 0, 1, 1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        Pair[][] aquarium = new Pair[4][4];

        for (int i = 0; i < 4; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 4; j++) {
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken()) - 1; // 꼭 -1
                aquarium[i][j] = new Pair(a, b);
            }
        }

        dfs(0, 0, aquarium, 0);
        System.out.println(maxSum);
    }

    public static void dfs(int sr, int sc, Pair[][] board, int total) {
        Pair[][] curBoard = copyArr(board);

        // 현재 칸의 물고기를 먹음
        Pair eaten = curBoard[sr][sc];
        total += eaten.a;
        int sharkDir = eaten.b;
        curBoard[sr][sc] = null;

        // 물고기 이동
        fishMove(curBoard, sr, sc);

        // 상어가 갈 수 있는 다음 칸 탐색
        boolean canMove = false;

        for (int step = 1; step <= 3; step++) {
            int nr = sr + dr[sharkDir] * step;
            int nc = sc + dc[sharkDir] * step;

            if (!isInRange(nr, nc)) break;
            if (curBoard[nr][nc] == null) continue;

            canMove = true;
            dfs(nr, nc, curBoard, total);
        }

        // 더 이상 갈 수 있는 물고기가 없으면 종료
        if (!canMove) {
            maxSum = Math.max(maxSum, total);
        }
    }

    public static void fishMove(Pair[][] status, int sr, int sc) {
        for (int fishNum = 1; fishNum <= 16; fishNum++) {
            int r = -1, c = -1;

            // fishNum 물고기 찾기
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (status[i][j] != null && status[i][j].a == fishNum) {
                        r = i;
                        c = j;
                        break;
                    }
                }
                if (r != -1) break;
            }

            // 이미 먹힌 물고기
            if (r == -1) continue;

            int dir = status[r][c].b;

            for (int t = 0; t < 8; t++) {
                int nd = (dir + t) % 8;
                int nr = r + dr[nd];
                int nc = c + dc[nd];

                if (!isInRange(nr, nc) || (nr == sr && nc == sc)) {
                    continue;
                }

                // 방향 갱신
                status[r][c].b = nd;

                // swap
                Pair temp = status[nr][nc];
                status[nr][nc] = status[r][c];
                status[r][c] = temp;
                break;
            }
        }
    }

    public static Pair[][] copyArr(Pair[][] origin) {
        Pair[][] newArr = new Pair[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (origin[i][j] != null) {
                    newArr[i][j] = new Pair(origin[i][j].a, origin[i][j].b);
                }
            }
        }
        return newArr;
    }

    public static boolean isInRange(int r, int c) {
        return r >= 0 && c >= 0 && r < 4 && c < 4;
    }
}