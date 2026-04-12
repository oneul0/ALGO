import java.io.*;
import java.util.*;

public class Main {
    static class Pair {
        int r, c;
        Pair(int r, int c) {
            this.r = r; this.c = c;
        }
    }

    static int N, M, K;
    static int[][] notebook;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        notebook = new int[N][M];

        for (int k = 0; k < K; k++) {
            st = new StringTokenizer(br.readLine());
            int R = Integer.parseInt(st.nextToken());
            int C = Integer.parseInt(st.nextToken());
            List<Pair> sticker = new ArrayList<>();

            for (int r = 0; r < R; r++) {
                st = new StringTokenizer(br.readLine());
                for (int c = 0; c < C; c++) {
                    if (Integer.parseInt(st.nextToken()) == 1) {
                        sticker.add(new Pair(r, c));
                    }
                }
            }

            solve(sticker, R, C);
        }

        System.out.println(getStickCount());
    }

    static void solve(List<Pair> sticker, int R, int C) {
        // 90 * i
        for (int rot = 0; rot < 4; rot++) {
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < M; c++) {
                    if (canStick(sticker, r, c)) {
                        stick(sticker, r, c);
                        return;
                    }
                }
            }
            if (rot < 3) {
                for (Pair p : sticker) {
                    int oldR = p.r;
                    p.r = p.c;
                    p.c = (R - 1) - oldR;
                }
                int tmp = R;
                R = C;
                C = tmp;
            }
        }
    }

    //붙일 수 있는지
    static boolean canStick(List<Pair> sticker, int sr, int sc) {
        for (Pair p : sticker) {
            int nr = sr + p.r;
            int nc = sc + p.c;
            if (nr < 0 || nr >= N || nc < 0 || nc >= M || notebook[nr][nc] == 1) {
                return false;
            }
        }
        return true;
    }

    //붙이기
    static void stick(List<Pair> sticker, int sr, int sc) {
        for (Pair p : sticker) {
            notebook[sr + p.r][sc + p.c] = 1;
        }
    }

    static int getStickCount() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (notebook[i][j] == 1) count++;
            }
        }
        return count;
    }
}