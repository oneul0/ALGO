import java.io.*;
import java.util.*;

public class Main {
    static int n, m;
    static char[][] arr;
    static int[][] hp;
    static Queue<int[]> q = new ArrayDeque<>();
    static int totalBuildings = 0;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        arr = new char[n][m];
        hp = new int[n][m];

        int sx = 0, sy = 0;

        for(int i=0; i<n; i++){
            String s = br.readLine();
            for(int j=0; j<m; j++){
                arr[i][j] = s.charAt(j);
                if(arr[i][j] == '@') {
                    sx = i; sy = j;
                } else if(arr[i][j] == '*') {
                    hp[i][j] = 1;
                    totalBuildings++;
                } else if(arr[i][j] == '#') {
                    hp[i][j] = 2;
                    totalBuildings++;
                }
            }
        }

        first(sx, sy);

        second();

        int fall = 0;
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if((arr[i][j] == '*' || arr[i][j] == '#') && hp[i][j] <= 0) {
                    fall++;
                }
            }
        }

        System.out.println(fall + " " + (totalBuildings - fall));
    }

    // 본진
    static void first(int sx, int sy) {
        for(int i=0; i<4; i++) {
            for(int dist=1; dist<=2; dist++) {
                int nx = sx + dx[i] * dist;
                int ny = sy + dy[i] * dist;

                if(isOutOfRange(nx, ny)) break;
                if(arr[nx][ny] == '|') break;

                if(arr[nx][ny] == '*' || arr[nx][ny] == '#') {
                    hitBuilding(nx, ny);
                }
            }
        }
    }

    // 여진
    static void second() {
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int cx = cur[0];
            int cy = cur[1];

            for(int i=0; i<4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if(isOutOfRange(nx, ny)) continue;
                if(arr[nx][ny] == '|') continue;

                if(arr[nx][ny] == '*' || arr[nx][ny] == '#') {
                    hitBuilding(nx, ny);
                }
            }
        }
    }

    static void hitBuilding(int r, int c) {
        if(hp[r][c] <= 0) return;

        hp[r][c]--;

        if(hp[r][c] == 0) {
            q.offer(new int[]{r, c});
        }
    }

    static boolean isOutOfRange(int r, int c) {
        return r < 0 || c < 0 || r >= n || c >= m;
    }
}
