import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 지도 세로 크기
        int M = Integer.parseInt(st.nextToken()); // 지도 가로 크기
        int x = Integer.parseInt(st.nextToken()); // 주사위 초기 x좌표
        int y = Integer.parseInt(st.nextToken()); // 주사위 초기 y좌표
        int K = Integer.parseInt(st.nextToken()); // 명령 개수

        int[][] map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[] dice = new int[6];

        int[] dx = {0, 0, 0, -1, 1};
        int[] dy = {0, 1, -1, 0, 0};

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            int dir = Integer.parseInt(st.nextToken());

            int nx = x + dx[dir];
            int ny = y + dy[dir];

            if (nx < 0 || nx >= N || ny < 0 || ny >= M) {
                continue;
            }

            x = nx;
            y = ny;

            rollDice(dice, dir);

            if (map[x][y] == 0) {
                map[x][y] = dice[5];
            } else {
                dice[5] = map[x][y];
                map[x][y] = 0;
            }

            System.out.println(dice[0]);
        }
    }

    private static void rollDice(int[] dice, int dir) {
        int temp;

        switch (dir) {
            case 1: // 동쪽으로 굴리기
                temp = dice[0];
                dice[0] = dice[3];
                dice[3] = dice[5];
                dice[5] = dice[2];
                dice[2] = temp;
                break;

            case 2: // 서쪽으로 굴리기
                temp = dice[0];
                dice[0] = dice[2];
                dice[2] = dice[5];
                dice[5] = dice[3];
                dice[3] = temp;
                break;

            case 3: // 북쪽으로 굴리기
                temp = dice[0];
                dice[0] = dice[4];
                dice[4] = dice[5];
                dice[5] = dice[1];
                dice[1] = temp;
                break;

            case 4: // 남쪽으로 굴리기
                temp = dice[0];
                dice[0] = dice[1];
                dice[1] = dice[5];
                dice[5] = dice[4];
                dice[4] = temp;
                break;
        }
    }
}
