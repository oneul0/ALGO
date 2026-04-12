import java.io.*;
import java.util.*;

public class Main {
    public static class Pos {
        int rx, ry;
        int bx, by;
        int count;
        Pos(int rx, int ry, int bx, int by, int count) {
            this.rx = rx;
            this.ry = ry;
            this.bx = bx;
            this.by = by;
            this.count = count;
        }
    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, M;
    static char[][] map;
    static int[] dx = {-1, 1, 0, 0}, dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new char[N][M];
        int rx = 0, ry = 0, bx = 0, by = 0;

        for(int i = 0; i < N; i++){
            String tmp = br.readLine();
            for(int j = 0; j < M; j++){
                map[i][j] = tmp.charAt(j);
                if(map[i][j] == 'R'){
                    rx = i; ry = j; map[i][j] = '.';
                }
                else if(map[i][j] == 'B'){
                    bx = i; by = j; map[i][j] = '.';
                }
            }
        }
        System.out.println(bfs(rx, ry, bx, by));
    }

    public static int bfs(int rx, int ry, int bx, int by) {
        Deque<Pos> q = new ArrayDeque<>();
        boolean[][][][] visited = new boolean[N][M][N][M];
        q.offer(new Pos(rx, ry, bx, by, 0));
        visited[rx][ry][bx][by] = true;

        while(!q.isEmpty()) {
            Pos cur = q.poll();
            if(cur.count >= 10) return -1;

            for(int i = 0; i < 4; i++){
                int[] nr = {cur.rx, cur.ry, 0};
                int[] nb = {cur.bx, cur.by, 0};

                go(nr, i);
                go(nb, i);

                if(map[nb[0]][nb[1]] == 'O') continue;
                if(map[nr[0]][nr[1]] == 'O') return cur.count + 1;

                // 겹쳤을 경우 이동 거리 비교 후 조정
                if(nr[0] == nb[0] && nr[1] == nb[1]) {
                    if(nr[2] > nb[2]) {
                        nr[0] -= dx[i];
                        nr[1] -= dy[i];
                    } else {
                        nb[0] -= dx[i];
                        nb[1] -= dy[i];
                    }
                }

                if(!visited[nr[0]][nr[1]][nb[0]][nb[1]]) {
                    visited[nr[0]][nr[1]][nb[0]][nb[1]] = true;
                    q.offer(new Pos(nr[0], nr[1], nb[0], nb[1], cur.count + 1));
                }
            }
        }
        return -1;
    }

    public static void go(int[] arr, int dir) {
        int x = arr[0];
        int y = arr[1];
        int cnt = 0;

        while(true) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            if(!isValid(nx, ny) || map[nx][ny] == '#') break;
            x = nx;
            y = ny;
            cnt++;
            if(map[x][y] == 'O') break;
        }

        arr[0] = x;
        arr[1] = y;
        arr[2] = cnt;
    }

    public static boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < M;
    }
}
