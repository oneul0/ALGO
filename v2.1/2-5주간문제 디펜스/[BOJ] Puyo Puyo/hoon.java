import java.io.*;
import java.util.*;

public class Main {
    static class Pair {
        int r, c;
        Pair(int r , int c){
            this.r = r;
            this.c = c;
        }
    }
    static final int height = 12, width = 6;
    static char[][] field = new char[height][width];
    static int[] dr = {-1,1,0,0}, dc = {0,0,-1,1};
    static boolean[][] visited = new boolean[height][width];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for(int i = 0; i<height; i++){
            field[i] = br.readLine().toCharArray();
        }
        int answer = 0;
        while(true){
            boolean isUpdated = false;
            visited = new boolean[height][width];

            for(int i = 0; i<height; i++){
                for(int j = 0; j<width; j++){
                    if(field[i][j] != '.' && !visited[i][j]){
                        List<Pair> result = bfs(field[i][j], i, j);
                        if(result.size() >= 4){
                            isUpdated = true;

                            for(Pair node : result){
                                field[node.r][node.c] = '.';
                            }
                        }

                    }
                }
            }
            if(!isUpdated) break;
            for(int c = 0; c<width; c++){
                dropColumn(c);
            }
            answer++;
        }
        System.out.println(answer);
    }

    //col에 빈공간 있는지 체크 후 내리는 함수 호출하는 함수
    public static void dropColumn(int c) {
        int bottomIdx = height - 1;

        for (int r = height - 1; r >= 0; r--) {
            if (field[r][c] != '.') {
                char temp = field[r][c];
                field[r][c] = '.';
                field[bottomIdx][c] = temp;
                bottomIdx--;
            }
        }
    }

    //시작 뿌요 색과 같은 색이 몇 개 연결되어있는지 체크(4이상이면 true)
    public static List<Pair> bfs(char color, int sr, int sc){
        Queue<Pair> q = new ArrayDeque<>();
        q.offer(new Pair(sr, sc));
        visited[sr][sc] = true;
        List<Pair> chained = new ArrayList<>();
        chained.add(new Pair(sr,sc));
        while(!q.isEmpty()){
            Pair cur = q.poll();

            for(int i = 0; i<4; i++){
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];
                if(nr<0 || nc<0 || nr>=12 || nc >=6 || color != field[nr][nc] || visited[nr][nc]) continue;
                q.offer(new Pair(nr, nc));
                visited[nr][nc] = true;
                chained.add(new Pair(nr, nc));
            }
        }
        return chained;
    }
}
