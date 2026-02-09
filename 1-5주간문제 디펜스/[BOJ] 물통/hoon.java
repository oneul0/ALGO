import java.io.*;
import java.util.*;

public class Main {

    static int A, B, C;
    static boolean[][] visited;
    static boolean[] possible;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        visited = new boolean[A + 1][B + 1];
        possible = new boolean[C + 1];

        dfs(0, 0);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= C; i++) {
            if (possible[i]) {
                sb.append(i).append(" ");
            }
        }
        System.out.println(sb.toString().trim());
    }

    static void dfs(int a, int b) {
        if (visited[a][b]) return;
        visited[a][b] = true;

        int c = C - a - b;

        if (a == 0) {
            possible[c] = true;
        }

        pour(a, b, c, B, 0, 1);
        pour(a, b, c, C, 0, 2);
        pour(a, b, c, A, 1, 0);
        pour(a, b, c, C, 1, 2);
        pour(a, b, c, A, 2, 0);
        pour(a, b, c, B, 2, 1);
    }

    static void pour(int a, int b, int c, int toCap, int from, int to) {
        int[] water = {a, b, c};
        int move = Math.min(water[from], toCap - water[to]);
        water[from] -= move;
        water[to] += move;
        dfs(water[0], water[1]);
    }
}
