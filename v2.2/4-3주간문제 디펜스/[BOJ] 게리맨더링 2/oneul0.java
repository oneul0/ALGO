import java.io.*;
import java.util.*;

public class Main {
    static int N;
    static int[][] A;
    static int total;
    static int answer = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        A = new int[N][N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
                total += A[i][j];
            }
        }

        //완탐
        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                for (int d1 = 1; d1 < N; d1++) {
                    for (int d2 = 1; d2 < N; d2++) {
                        if (!isValid(x, y, d1, d2)) continue;
                        answer = Math.min(answer, simulate(x, y, d1, d2));
                    }
                }
            }
        }

        System.out.println(answer);
    }

    static boolean isValid(int x, int y, int d1, int d2) {
        return x + d1 + d2 < N && y - d1 >= 0 && y + d2 < N;
    }

    static int simulate(int x, int y, int d1, int d2) {
        boolean[][] border = new boolean[N][N];

        // 5번 경계선 표시
        for (int i = 0; i <= d1; i++) {
            border[x + i][y - i] = true; // 1번 경계
            border[x + d2 + i][y + d2 - i] = true; // 4번 경계
        }
        for (int i = 0; i <= d2; i++) {
            border[x + i][y + i] = true; // 2번 경계
            border[x + d1 + i][y - d1 + i] = true; // 3번 경계
        }

        int[] people = new int[5];

        // 1번
        for (int r = 0; r < x + d1; r++) {
            for (int c = 0; c <= y; c++) {
                if (border[r][c]) break;
                people[0] += A[r][c];
            }
        }

        // 2번
        for (int r = 0; r <= x + d2; r++) {
            for (int c = N - 1; c > y; c--) {
                if (border[r][c]) break;
                people[1] += A[r][c];
            }
        }

        // 3번
        for (int r = x + d1; r < N; r++) {
            for (int c = 0; c < y - d1 + d2; c++) {
                if (border[r][c]) break;
                people[2] += A[r][c];
            }
        }

        // 4번
        for (int r = x + d2 + 1; r < N; r++) {
            for (int c = N - 1; c >= y - d1 + d2; c--) {
                if (border[r][c]) break;
                people[3] += A[r][c];
            }
        }

        // 5번 = 전체-(1~4번)
        people[4] = total - people[0] - people[1] - people[2] - people[3];

        int max = people[0];
        int min = people[0];
        for (int i = 1; i < 5; i++) {
            max = Math.max(max, people[i]);
            min = Math.min(min, people[i]);
        }

        return max - min;
    }
}