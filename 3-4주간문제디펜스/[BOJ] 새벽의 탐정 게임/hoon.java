import java.io.*;
import java.util.*;

public class Main {
    static class Node {
        int r, c, hole, dist;
        Node(int r, int c, int hole, int dist) {
            this.r = r;
            this.c = c;
            this.hole = hole;
            this.dist = dist;
        }
    }

    static int N, M;
    static char[][] board;
    static int targetR, targetC;
    static int[] dr = {-1, 1, 0, 0}, dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new char[N][M];

        int startR = 0, startC = 0;
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                board[i][j] = line.charAt(j);
                if (board[i][j] == 'D') {
                    startR = i;
                    startC = j;
                    board[i][j] = '.';
                } else if (board[i][j] == 'R') {
                    targetR = i;
                    targetC = j;
                    board[i][j] = '.';
                }
            }
        }

        System.out.println(bfs(startR, startC));
    }

    public static int bfs(int sR, int sC) {
        Queue<Node> q = new ArrayDeque<>();
        // visited[행][열][구멍 위치]
        // 구멍 위치: 1:위, 2:뒤, 3:우, 4:좌, 5:앞, 6:밑
        boolean[][][] visited = new boolean[N][M][7];

        // 시작 시 탐정 위치 'D', 구멍은 바닥(6)
        q.offer(new Node(sR, sC, 6, 0));
        visited[sR][sC][6] = true;

        while (!q.isEmpty()) {
            Node curr = q.poll();

            for (int i = 0; i < 4; i++) {
                int nr = curr.r + dr[i];
                int nc = curr.c + dc[i];

                if (nr < 0 || nc < 0 || nr >= N || nc >= M || board[nr][nc] == '#') continue;

                // 주사위를 굴린 후의 새로운 구멍 위치
                int nHole = getNextHole(curr.hole, i);

                // 도둑의 위치에 도달했을 때
                if (nr == targetR && nc == targetC) {
                    if (nHole == 6) return curr.dist + 1; // 구멍이 바닥이면 검거
                    else continue; // 구멍이 바닥이 아니면 패배(이 경로는 종료)
                }

                // 일반 빈칸인 경우
                if (!visited[nr][nc][nHole]) {
                    visited[nr][nc][nHole] = true;
                    q.offer(new Node(nr, nc, nHole, curr.dist + 1));
                }
            }
        }
        return -1;
    }

    // 1:위, 2:뒤, 3:우, 4:좌, 5:앞, 6:밑
    public static int getNextHole(int currentHole, int dir) {
        int[] dice = new int[7];
        dice[currentHole] = 1; // 현재 구멍이 있는 곳 1
        int[] next;

        if (dir == 0) { // Up
            next = new int[]{0, dice[5], dice[1], dice[3], dice[4], dice[6], dice[2]};
        } else if (dir == 1) { // Down
            next = new int[]{0, dice[2], dice[6], dice[3], dice[4], dice[1], dice[5]};
        } else if (dir == 2) { // Left
            next = new int[]{0, dice[3], dice[2], dice[6], dice[1], dice[5], dice[4]};
        } else { // Right
            next = new int[]{0, dice[4], dice[2], dice[1], dice[6], dice[5], dice[3]};
        }

        for (int i = 1; i <= 6; i++) {
            if (next[i] == 1) return i;
        }
        return -1;
    }
}