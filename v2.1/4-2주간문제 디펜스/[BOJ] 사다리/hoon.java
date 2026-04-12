import java.io.*;
import java.util.*;

public class Main {
    public static class Pair{
        int l, r;
        Pair(int l, int r){
            this.l = l;
            this.r = r;
        }
    }
    static int N, L;
    static int[] len, dir;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        len = new int[N+1];
        dir = new int[N+1];
        for(int i = 1; i<=N; i++){
            st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            len[i] = l;
            dir[i] = d;
        }

        int floor = 1;
        int t = 0;
        while(floor<N){
            if(canGo(floor, floor+1, t)){
                floor++;
            }
            else{
                t++;
            }
        }
        System.out.println(t);
    }

    public static Pair getPos(int idx, int t){
        int w = len[idx];
        int d = L-w;
        if(d == 0) return new Pair(0, L);
        int m = t%(d+d);
        int x = (m<=d ? m:(d+d)-m);
        int left;
        if(dir[idx] == 1){
            left=x;
        }
        else{
            left = d-x;
        }
        return new Pair(left, left+w);
    }

    public static boolean canGo(int cur, int next, int time){
        Pair p1 = getPos(cur, time);
        Pair p2 = getPos(next, time);
        return Math.max(p1.l, p2.l) <= Math.min(p1.r, p2.r);
    }
}

//폭이 L일 때 위치 p가 오를 수 있는 시간인지 판명하는건 공식으로 가능할 듯
//왼쪽에서 시작한 막대s(side, 0 || 1 , 너비 w)가 시간 t에 위치 p에서 접근할 수 있는 위치