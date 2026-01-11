import java.io.*;
import java.util.*;

public class Main {
    static int N, K;
    static int[][] A; // 상성표
    static int[] kyung, minho; // 경희, 민호의 손동작 순서
    static int[] jiwooOrder; // 지우가 낼 손동작 순서
    static boolean[] visited; // 지우 손동작 중복 체크용

    public static void dfs(int depth){
        if(depth == N){
            if(simulate()){
                System.out.println(1);
                System.exit(0);
            }
            return;
        }
        for(int i = 1; i<=N; i++){
            if(!visited[i]){
                visited[i] = true;
                jiwooOrder[depth] = i;
                dfs(depth+1);
                visited[i] = false;
            }
        }
    }

    public static boolean simulate(){
        int[] win = new int[4]; // 1 지우, 2 경희, 3 민호
        int[] idx = new int[4]; //플레이어가 낼 손동작 idx

        int p1 = 1;
        int p2 = 2;

        while (true){
            if(win[1] == K) return true;
            if(win[2] == K || win[3] == K) return false;

            if(p1 == 1 && idx[1] >= N) return false;
            if(p2 == 1 && idx[1] >= N) return false;

            if (
                ((p1 == 2 || p2 == 2)&& idx[2] >= 20) ||
                    ((p1 == 3 || p2 == 3)&& idx[3] >= 20)
            ) return false;

            int hand1 = 0;
            int hand2 = 0;

            if(p1 == 1) hand1 = jiwooOrder[idx[1]++];
            else if(p1 == 2) hand1 = kyung[idx[2]++];
            else if(p1 == 3) hand1 = minho[idx[3]++];

            if (p2 == 1) hand2 = jiwooOrder[idx[1]++];
            else if (p2 == 2) hand2 = kyung[idx[2]++];
            else if (p2 == 3) hand2 = minho[idx[3]++];

            int winner = 0;
            if (A[hand1][hand2] == 2) winner = p1;
            else if (A[hand1][hand2] == 0) winner = p2;
            else winner = Math.max(p1, p2);

            win[winner]++;

            int nextPlayer = 6 - p1 - p2;
            p1 = winner;
            p2 = nextPlayer;
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        A = new int[N+1][N+1];

        for(int i = 1; i<=N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 1; j<=N; j++){
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 경희
        kyung = new int[20];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 20; i++) kyung[i] = Integer.parseInt(st.nextToken());

        // 민호
        minho = new int[20];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 20; i++) minho[i] = Integer.parseInt(st.nextToken());

        jiwooOrder = new int[N];
        visited = new boolean[N + 1];

        dfs(0);

        // 여기까지 왔는데 시스템 종료가 안됐다면 우승 못한 것
        System.out.println(0);

    }

}
