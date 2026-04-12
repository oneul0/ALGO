import java.io.*;
import java.util.*;

public class Main {
    static int N, M, T;
    static int[][] discs;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        discs = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                discs[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int t = 0; t < T; t++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            rotateMultiples(x, d, k);

            boolean removed = removeAdjacentSame();
            if (!removed) {
                adjustByAvg();
            }
        }

        System.out.println(sumAll());
    }

    // x의 배수 번호 원판 회전
    public static void rotateMultiples(int x, int d, int k) {
        for (int discNum = x; discNum <= N; discNum += x) {
            rotateOne(discs[discNum - 1], d, k);
        }
    }

    public static void rotateOne(int[] disc, int d, int k) {
        int[] temp = new int[M];
        k %= M;

        for (int i = 0; i < M; i++) {
            int nextIdx;
            if (d == 0) { // clockwise
                nextIdx = (i + k) % M;
            } else { // other clockwise
                nextIdx = (i - k + M) % M;
            }
            temp[nextIdx] = disc[i];
        }

        System.arraycopy(temp, 0, disc, 0, M);
    }

    // 전체 판에서 인접한 같은 수 제거
    public static boolean removeAdjacentSame() {
        boolean[][] remove = new boolean[N][M];
        boolean found = false;

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                int cur = discs[r][c];
                if (cur == 0) continue;

                int right = (c + 1) % M;
                int left = (c - 1 + M) % M;

                // 같은 원판 좌우
                if (discs[r][right] == cur) {
                    remove[r][c] = true;
                    remove[r][right] = true;
                    found = true;
                }

                if (discs[r][left] == cur) {
                    remove[r][c] = true;
                    remove[r][left] = true;
                    found = true;
                }

                // 위 원판
                if (r - 1 >= 0 && discs[r - 1][c] == cur) {
                    remove[r][c] = true;
                    remove[r - 1][c] = true;
                    found = true;
                }

                // 아래 원판
                if (r + 1 < N && discs[r + 1][c] == cur) {
                    remove[r][c] = true;
                    remove[r + 1][c] = true;
                    found = true;
                }
            }
        }

        if (!found) return false;

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (remove[r][c]) {
                    discs[r][c] = 0;
                }
            }
        }

        return true;
    }

    // 제거된 수가 없을 때 전체 평균 기준으로 조정
    public static void adjustByAvg() {
        int sum = 0;
        int count = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (discs[i][j] != 0) {
                    sum += discs[i][j];
                    count++;
                }
            }
        }

        if (count == 0) return;

        double avg = (double) sum / count;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (discs[i][j] == 0) continue;

                if (discs[i][j] > avg) {
                    discs[i][j]--;
                } else if (discs[i][j] < avg) {
                    discs[i][j]++;
                }
            }
        }
    }

    public static int sumAll() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                sum += discs[i][j];
            }
        }
        return sum;
    }
}