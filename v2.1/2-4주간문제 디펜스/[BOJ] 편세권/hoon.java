import java.io.*;
import java.util.*;

public class Main {
    static int N, M, R, C;
    static int[][] dist;

    static class Pos {
        int r, c;
        Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static class Room {
        int r, c, p;
        Room(int r, int c, int p) {
            this.r = r;
            this.c = c;
            this.p = p;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        List<Room> rooms = new ArrayList<>();
        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            rooms.add(new Room(r, c, p));
        }

        dist = new int[N+1][M+1];
        for (int i = 1; i <= N; i++) {
            Arrays.fill(dist[i], -1);
        }

        Queue<Pos> queue = new ArrayDeque<>();
        for (int i = 0; i < C; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            dist[r][c] = 0;
            queue.add(new Pos(r, c));
        }

        int[] dr = {0, 0, 1, -1}, dc = {1, -1, 0, 0};

        while (!queue.isEmpty()) {
            Pos curr = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nr = curr.r + dr[i];
                int nc = curr.c + dc[i];

                if (nr >= 1 && nr <= N && nc >= 1 && nc <= M) {
                    if (dist[nr][nc] == -1) {
                        dist[nr][nc] = dist[curr.r][curr.c] + 1;
                        queue.add(new Pos(nr, nc));
                    }
                }
            }
        }

        long minScore = Long.MAX_VALUE;
        for (Room room : rooms) {
            int d = dist[room.r][room.c];
            if (d != -1) {
                minScore = Math.min(minScore, (long) d * room.p);
            }
        }

        System.out.println(minScore);
    }
}