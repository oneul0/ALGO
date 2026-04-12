import java.io.*;
import java.util.*;

public class Main {
    static class Node {
        int r, c;
        int left, right;
        Node(int r, int c, int left, int right){
            this.r = r;
            this.c = c;
            this.left = left;
            this.right = right;
        }
    }

    static int R, C, K;
    static int[][] maze;
    //동서남북
    static int[] dr = {0,1,0,-1}, dc = {1, 0, -1, 0};
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        maze = new int[R][C];
        for(int i = 0; i<R; i++){
            String line = br.readLine();
            for(int j = 0 ;j<C; j++){
                char cur = line.charAt(j);
                int dir = 0;
                if(cur == 'D') dir = 1;
                else if(cur == 'L') dir = 2;
                else if(cur == 'U') dir = 3;
                maze[i][j] = dir;
            }
        }

        if(bfs(0,0, K, K)) System.out.println("Yes");
        else System.out.println("No");
    }

    public static boolean bfs(int sr, int sc, int left, int right){
        Queue<Node> q = new ArrayDeque<>();
        q.offer(new Node(sr, sc, left, right));
        boolean[][][][] visited = new boolean[R][C][2][2];
        visited[sr][sc][left][right] = true;
        while(!q.isEmpty()){
            Node cur = q.poll();

            if(cur.r == R-1 && cur.c == C-1) return true;

            //l, r 쓰는 경우와 안 쓰는 경우
            int curArrow = maze[cur.r][cur.c];
            int nr = cur.r+dr[curArrow];
            int nc = cur.c+dc[curArrow];
            if(isValid(nr, nc) && !visited[nr][nc][cur.left][cur.right]){
                q.offer(new Node(nr, nc, cur.left, cur.right));
                visited[nr][nc][cur.left][cur.right] = true;
            }

            if(cur.left>0){
                int rotated = rotateArrowIdx(curArrow, false);
                nr = cur.r+dr[rotated];
                nc = cur.c+dc[rotated];
                if(isValid(nr, nc) && !visited[nr][nc][cur.left-1][cur.right]){
                    q.offer(new Node(nr, nc, cur.left-1, cur.right));
                    visited[nr][nc][cur.left-1][cur.right] = true;
                }
            }
            if(cur.right>0){
                int rotated = rotateArrowIdx(curArrow, true);
                nr = cur.r+dr[rotated];
                nc = cur.c+dc[rotated];
                if(isValid(nr, nc) && !visited[nr][nc][cur.left][cur.right-1]){
                    q.offer(new Node(nr, nc, cur.left, cur.right-1));
                    visited[nr][nc][cur.left][cur.right-1] = true;
                }
            }
        }


        return false;
    }


    //실제로 화살표를 바꾸는게 아니라 이동만 시켜줘보기
    //현재 화살표와 주문서 넣으면 주문서 방향으로 돌려주기
    //dr, dc 인덱스만 반환
    public static int rotateArrowIdx(int curArrow, boolean isRightScroll){
        int idx = curArrow;
        //right로 돌리면 시계
        if(isRightScroll){
            idx = (idx+1)%4;
        }
        else {
            //left로 돌리면 반시계
            idx = (idx + 3) % 4;
        }

        return idx;
    }

    public static boolean isValid(int r, int c){
        return (r>=0 && c>=0 && r<R && c<C);
    }
}
