import java.io.*;
import java.util.*;

public class Main {
    public static class Player {
        char name;
        int r, c;
        Player(char name, int r, int c) {
            this.name = name;
            this.r = r;
            this.c = c;
        }
    }

    // 탐색용
    static class Pair {
        int r, c, t;
        Pair(int r, int c, int t) {
            this.r = r;
            this.c = c;
            this.t = t;
        }
    }

    // 시뮬용
    static class Attacker implements Comparable<Attacker> {
        int arrivalTime;
        int damage;
        Attacker(int arrivalTime, int damage) {
            this.arrivalTime = arrivalTime;
            this.damage = damage;
        }
        @Override
        public int compareTo(Attacker o) {
            return this.arrivalTime - o.arrivalTime;
        }
    }

    static int M, N, P;
    static char[][] map;
    static int[] dps = new int[26];
    static List<Player> players = new ArrayList<>();
    static int bossHp;
    static int targetR, targetC; // 보스 위치
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());

        map = new char[M][N];
        for (int i = 0; i < M; i++) {
            String line = br.readLine();
            for (int j = 0; j < N; j++) {
                map[i][j] = line.charAt(j);
                if (map[i][j] >= 'a' && map[i][j] <= 'z') {
                    players.add(new Player(map[i][j], i, j));
                } else if (map[i][j] == 'B') {
                    targetR = i;
                    targetC = j;
                }
            }
        }

        for (int i = 0; i < P; i++) {
            st = new StringTokenizer(br.readLine());
            int idx = st.nextToken().charAt(0) - 'a';
            dps[idx] = Integer.parseInt(st.nextToken());
        }
        bossHp = Integer.parseInt(br.readLine());

        System.out.println(simulation());
    }

    public static int simulation() {
        List<Attacker> attackers = new ArrayList<>();

        for (Player p : players) {
            int time = bfs(p);
            if (time != -1) {
                attackers.add(new Attacker(time, dps[p.name - 'a']));
            }
        }

        Collections.sort(attackers);

        int time = 0;
        int currentTotalDps = 0;
        int attackerIdx = 0;

        while (bossHp > 0) {
            while (attackerIdx < attackers.size() && attackers.get(attackerIdx).arrivalTime == time) {
                currentTotalDps += attackers.get(attackerIdx).damage;
                attackerIdx++;
            }

            bossHp -= currentTotalDps;
            if (bossHp <= 0) break;
            time++;

            if (attackerIdx == attackers.size() && currentTotalDps == 0) break;
        }

        return attackerIdx;
    }

    static int[] dr = {0, 0, -1, 1}, dc = {1, -1, 0, 0};
    public static int bfs(Player start) {
        Queue<Pair> q = new ArrayDeque<>();
        q.offer(new Pair(start.r, start.c, 0));

        boolean[][] visited = new boolean[M][N];
        visited[start.r][start.c] = true;

        while (!q.isEmpty()) {
            Pair cur = q.poll();

            if (cur.r == targetR && cur.c == targetC) return cur.t;

            for (int i = 0; i < 4; i++) {
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];

                if (nr < 0 || nc < 0 || nr >= M || nc >= N) continue;
                if (map[nr][nc] == 'X' || visited[nr][nc]) continue;

                visited[nr][nc] = true;
                q.offer(new Pair(nr, nc, cur.t + 1));
            }
        }
        return -1;
    }
}