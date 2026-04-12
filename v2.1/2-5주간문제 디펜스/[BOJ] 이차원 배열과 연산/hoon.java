import java.io.*;
import java.util.*;

public class Main {
    static class Number implements Comparable<Number> {
        int val, cnt;
        Number(int val, int cnt){
            this.val = val;
            this.cnt = cnt;
        }
        @Override
        public int compareTo(Number o) {
            if (this.cnt == o.cnt) {
                return this.val - o.val;
            }
            return this.cnt - o.cnt;
        }
    }
    static int r, c, k;
    static int rowLen = 3, colLen = 3;
    static final int MAX = 101;
    static int[][] arr = new int[MAX][MAX];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        for(int i = 1; i<=3; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 1; j<=3; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int time = 0;
        while (time <= 100) {
            if (isSatisfy()) {
                System.out.println(time);
                return;
            }

            if (rowLen >= colLen) {
                operationR();
            } else {
                operationC();
            }
            time++;
        }
        System.out.println(-1);
    }

    public static void operationR(){
        int maxLen = 0;
        //전체 row에 대해 진행
        for(int cr = 1; cr<=rowLen; cr++){
            //빈도 수 체크
            int[] counts = new int[101];
            for(int cc = 1; cc<=colLen; cc++){
                counts[arr[cr][cc]]++;
            }
            //등장한 수에 대해 정렬
            List<Number> nums = new ArrayList<>();
            for(int i = 1; i<=100; i++){
                if(counts[i] != 0){
                    nums.add(new Number(i, counts[i]));
                }
            }
            Collections.sort(nums);
            //clear
            Arrays.fill(arr[cr], 0);
            //배열에 다시 채우기
            int limit = Math.min(nums.size(), 50);
            int idx = 1;
            for (int i = 0; i < limit; i++) {
                Number num = nums.get(i);
                arr[cr][idx++] = num.val;
                arr[cr][idx++] = num.cnt;
            }
            maxLen = Math.max(maxLen, idx-1);
        }
        colLen = maxLen;
    }

    public static void operationC(){
        int maxLen = 0;
        //전체 col에 대해 진행
        for(int cc = 1; cc<=colLen; cc++){
            //빈도 수 체크
            int[] counts = new int[101];
            for(int cr = 1; cr<=rowLen; cr++){
                counts[arr[cr][cc]]++;
            }
            //등장한 수에 대해 정렬
            List<Number> nums = new ArrayList<>();
            for(int i = 1; i<=100; i++){
                if(counts[i] != 0){
                    nums.add(new Number(i, counts[i]));
                }
            }
            Collections.sort(nums);
            //clear
            for(int i = 1; i<=100; i++){
                arr[i][cc] = 0;
            }
            //배열에 다시 채우기
            int limit = Math.min(nums.size(), 50);
            int idx = 1;
            for (int i = 0; i < limit; i++) {
                Number num = nums.get(i);
                arr[idx++][cc] = num.val;
                arr[idx++][cc] = num.cnt;
            }
            maxLen = Math.max(maxLen, idx-1);
        }
        rowLen = maxLen;
    }

    public static boolean isSatisfy(){
        return arr[r][c] == k;
    }
}
