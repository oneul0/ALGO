import java.io.*;
import java.util.*;

public class Main {
    static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static int N, K;
    static Point[] points;
    static int[] depth; // depth[x] = x열의 바닥 y
    static int[] surface; // surface[x] = 현재 남아 있는 물의 수면 y

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        points = new Point[N];

        int maxX = 0;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            points[i] = new Point(x, y);
            maxX = Math.max(maxX, x);
        }

        // 0 ~ maxX-1
        depth = new int[maxX];
        surface = new int[maxX];

        // 바닥 채우기
        // 경계는 (0,0)에서 시작해 반시계 방향으로 주어지고
        // 바닥 수평선분은 1~2, 3~4 이런식이니까 바닥만 추출
        for (int i = 1; i < N - 1; i += 2) {
            Point p1 = points[i];
            Point p2 = points[i+1];

            int y = p1.y;
            int left = Math.min(p1.x, p2.x);
            int right = Math.max(p1.x, p2.x);

            // left, right 구간의 바닥 y 채우기
            for (int x = left; x < right; x++) {
                depth[x] = y;
            }
        }

        K = Integer.parseInt(br.readLine());

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int holeX = Integer.parseInt(st.nextToken());
            int holeY = Integer.parseInt(st.nextToken());

            // 왼쪽으로 스캔
            int cur = holeY;
            for (int x = holeX; x >= 0; x--) {
                cur = Math.min(cur, depth[x]);
                surface[x] = Math.max(surface[x], cur);
            }

            // 오른쪽으로 스캔
            cur = holeY;
            for (int x = holeX; x < maxX; x++) {
                cur = Math.min(cur, depth[x]);
                surface[x] = Math.max(surface[x], cur);
            }
        }

        long answer = 0;
        for (int x = 0; x < maxX; x++) {
            answer += (depth[x] - surface[x]);
        }

        System.out.println(answer);
    }
}

//구멍보다 아래있는 물의 리터를 구하면 될 듯