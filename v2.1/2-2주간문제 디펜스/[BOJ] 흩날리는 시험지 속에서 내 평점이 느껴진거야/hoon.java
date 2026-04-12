import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        br.close();
        int[] papers = new int[N];
        int sum = 0;
        for(int i = 0; i<N; i++){
            papers[i] = Integer.parseInt(st.nextToken());
            sum += papers[i];
        }
        System.out.println(getMaxTotalScore(papers, K, sum));
    }

    public static boolean canDivide(int[] papers, int K, long target){
        long curSum = 0;
        int groups = 0;
        for(int p : papers){
            curSum+= p;
            if(curSum >= target){
                curSum = 0;
                groups++;
            }
        }
        return groups >= K;
    }

    public static long getMaxTotalScore(int[] papers, int K, int sum){
        long low = 0, high = sum;
        long ans = 0;
        while(low <= high){
            long mid = (low + high) >>> 1;

            if(canDivide(papers, K, mid)){
                ans = mid;
                low = mid+1;
            }
            else{
                high = mid-1;
            }
        }
        return ans;
    }
}
//K개의 그룹으로 나눠서 그 중 가장 작은 그룹의 점수가 최대가 되게
//K개의 그룹으로 나눌 때 순서를 주어진 그대로 해야함
//목표 점수를 설정 후 현재 그룹이 목표 점수를 충족한다면
//새로운 그룹으로 넘어가기
//그렇게 만든 그룹의 수가 K개가 넘어간다면 목표 점수를 높여보기
//K개보다 작다면 목표 점수를 낮춰보기
//K개의 그룹을 만족하는 목표 점수 중 가장 높은 점수를 구하기