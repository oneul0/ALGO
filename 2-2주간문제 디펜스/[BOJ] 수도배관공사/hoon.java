import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static class Pipe{
        int l, c;
        Pipe(int l, int c){
            this.l = l;
            this.c = c;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int D = Integer.parseInt(st.nextToken());
        int P = Integer.parseInt(st.nextToken());

        int[] dp = new int[D+1]; //[길이] = 용량
        //만약 이 길이를 추가하기 전[i-l]과 현재 용량 중 작은 것
        //길이 D 중 가장 큰 값

        Pipe[] pipes = new Pipe[P];
        for(int i = 0; i<P; i++){
            st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            pipes[i] = new Pipe(l, c);
        }

        dp[0] = Integer.MAX_VALUE;
        for(Pipe p : pipes){
            for(int i = D; i>=p.l; i--){
                if(dp[i-p.l] != 0){
                    dp[i] = Math.max(dp[i], Math.min(dp[i-p.l], p.c));
                }
            }
        }
        System.out.println(dp[D]);

    }
}
//길이(용량) D를 만들기 위한 최대 용량(이때, 용량은 구성 파이프의 용량 중 최솟값)
//0-1dp