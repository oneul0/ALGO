import java.io.*;
import java.util.*;

public class Main {
    static int n, m, k;
    static char[][] arr;
    static int[] dx = {-1,1,0,0, 1,-1,1,-1}, dy = {0,0,-1,1, 1,-1,-1,1};
    static Map<String, Integer> count = new HashMap<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        arr = new char[n][m];
        for(int i = 0; i<n; i++){
            arr[i] = br.readLine().toCharArray();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dfs(i, j, String.valueOf(arr[i][j]));
            }
        }

        for (int ki = 0; ki < k; ki++) {
            String line = br.readLine();
            System.out.println(count.get(line) != null ? count.get(line) : 0);
        }
    }

    public static void dfs(int x, int y, String word) {
        if (word.length() > 5) return;

        count.put(word, count.getOrDefault(word, 0) + 1);

        for (int i = 0; i < 8; i++) {
            int nx = (x + dx[i] + n) % n;
            int ny = (y + dy[i] + m) % m;
            dfs(nx, ny, word + arr[nx][ny]);
        }
    }

}
//idx 관리할 때는 모듈러 연산