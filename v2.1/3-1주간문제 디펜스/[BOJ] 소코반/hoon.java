import java.io.*;
import java.util.*;

public class Main {
    public static class Point{
        int x, y;
        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int idx = 0;
        StringBuilder sb = new StringBuilder();
        while(true){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            if(N == 0 && M == 0) break;

            Point curPos = new Point(0,0);
            List<Point> goals = new ArrayList<>();

            sb.append("Game ").append(++idx).append(": ");
            char[][] wareHouse = new char[N][M];
            for(int i = 0; i<N; i++){
                String line = br.readLine();
                for(int j = 0; j<M; j++){
                    wareHouse[i][j] = line.charAt(j);
                    if(wareHouse[i][j] == '+'){
                        goals.add(new Point(i, j));
                    }
                    else if(wareHouse[i][j] == 'w'){
                        curPos = new Point(i, j);
                    }
                    else if(wareHouse[i][j] == 'W'){
                        goals.add(new Point(i, j));
                        curPos = new Point(i, j);
                    }
                    else if(wareHouse[i][j] == 'B'){
                        goals.add(new Point(i, j));
                    }
                }
            }
            sb.append(simulation(br.readLine(), wareHouse, N, M, curPos, goals)).append("\n");
            for(char[] line : wareHouse){
                for(int i = 0; i<line.length; i++){
                    sb.append(line[i]);
                }
                sb.append("\n");
            }
        }
        System.out.println(sb);

    }
    public static boolean chkGoal(char[][] wareHouse, List<Point> goals){
        for(Point goal : goals){
            if(wareHouse[goal.x][goal.y] != 'B') return false;
        }
        return true;
    }
    public static String simulation(String command, char[][] wareHouse, final int N, final int M, Point curPos, List<Point> goals){
        if(chkGoal(wareHouse, goals)) return "complete";

        for(char c : command.toCharArray()){
            move(c, wareHouse, N, M, curPos, goals);
            if(chkGoal(wareHouse, goals)) {
                return "complete";
            }
        }
        return "incomplete";
    }
    public static void move(char c, char[][] wareHouse, final int N, final int M, Point curPos, List<Point> goals){
        switch (c){
            case 'U':
                push(0, wareHouse, N, M, curPos, goals);
                break;
            case 'D':
                push(1, wareHouse, N, M, curPos, goals);
                break;
            case 'L':
                push(2, wareHouse, N, M, curPos, goals);
                break;
            case 'R':
                push(3, wareHouse, N, M, curPos, goals);
                break;
            default:
                break;
        }
    }
    static int[] dx = {-1, 1, 0,0}, dy = {0,0,-1,1};
    public static void push(int dir, char[][] wareHouse, final int N, final int M, Point curPos, List<Point> goals){
        // 방향 밀기
        int nx = curPos.x + dx[dir];
        int ny = curPos.y + dy[dir];
        if(!isValid(nx, ny, N, M)) return;
        if(wareHouse[nx][ny] == '#') return;
        if(wareHouse[nx][ny] == '.' || wareHouse[nx][ny] == '+'){
            wareHouse[curPos.x][curPos.y] = wareHouse[curPos.x][curPos.y] == 'W' ? '+' : '.';
            wareHouse[nx][ny] = wareHouse[nx][ny] == '+' ? 'W' : 'w';
            curPos.x = nx;
            curPos.y = ny;
        }
        // 상자면 같은 방향 다음 칸 체크
        else if(wareHouse[nx][ny] == 'b' || wareHouse[nx][ny] == 'B'){
            int nnx = nx + dx[dir];
            int nny = ny + dy[dir];
            if(!isValid(nnx, nny, N, M)) return;
            //상자 있으면 이동 불가
            if(wareHouse[nnx][nny] == '#' || wareHouse[nnx][nny] == 'b' || wareHouse[nnx][nny] == 'B') return;

            //빈공간이면 그냥 밀고
            if(wareHouse[nnx][nny] == '.'){
                wareHouse[nnx][nny] = 'b';
                wareHouse[nx][ny] = wareHouse[nx][ny] == 'b' ? 'w' : 'W';
                wareHouse[curPos.x][curPos.y] = wareHouse[curPos.x][curPos.y] == 'W' ? '+' : '.';
            }
            //목표 위치라면 대문자로
            else if(wareHouse[nnx][nny] == '+'){
                wareHouse[nnx][nny] = 'B';
                wareHouse[nx][ny] = wareHouse[nx][ny] == 'b' ? 'w' : 'W';
                wareHouse[curPos.x][curPos.y] = wareHouse[curPos.x][curPos.y] == 'W' ? '+' : '.';
            }
            curPos.x = nx;
            curPos.y = ny;
        }
        // 아니라면 이동 불가
    }
    public static boolean isValid(int x, int y, final int N, final int M){
        return (x>=0 && y>=0 && x<N && y<M);
    }

}
//현재 위치에서 다음으로 이동했을 때 벽, 상자를 미는 상태에서 상자면 이동 불가
//목표, 빈공간, 상자를 밀지 않는 경우면 상자도 가능
//모든 목표점에 상자가 있으면 완료 아니면 미완료