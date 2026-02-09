import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static class Food implements Comparable<Food> {
        int a;
        int b;
        Food(int a, int b){
            this.a = a;
            this.b = b;
        }
        @Override
        public int compareTo(Food o){
            if(this.a == o.a) return Integer.compare(this.b, o.b);
            return Integer.compare(this.a, o.a);
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st= new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());
        Food[] foods = new Food[N];
        for(int i = 0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            foods[i] = new Food(a,b);
        }
        Arrays.sort(foods);

        for (int i = 0; i<Q; i++){
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            int minSpicy = Math.min(u, v);
            int maxSpicy = Math.max(v, u);
            int minSweet = Math.min(x, y);
            int maxSweet = Math.max(x, y);

            int sIdx = getLowerBound(foods, minSpicy);
            int eIdx = getUpperBound(foods, maxSpicy);

            int matched = 0;
            for(int idx = sIdx; idx<eIdx; idx++){
                if(foods[idx].b >= minSweet && foods[idx].b<=maxSweet){
                    matched++;
                }
            }
            System.out.println(matched);
        }
    }

    //찾고자 하는 값 이상이 처음 나타나는 위치
    public static int getLowerBound(Food[] arr, int u){
        int l = 0, r = arr.length;
        while(l<r){
            int mid = (l+r)>>>1;
            long val = arr[mid].a;
            if(val < u){
                l = mid+1;
            }
            else{
                r = mid;
            }
        }
        return r;
    }
    //찾고자 하는 값 초과가 처음 나타나는 위치
    public static int getUpperBound(Food[] arr, int v){
        int l = 0, r = arr.length;
        while(l<r){
            int mid = (l+r) >>> 1;
            long val = arr[mid].a;
            if(val <= v) {
                l = mid+1;
            }
            else{
                r = mid;
            }
        }
        return r;
    }
}
//시작 끝 인덱스 찾고 끝-시작
//만약 분리한다면?
//음식 자체가 분리되서 맞지 않음
//이분탐색 시 2가지 값 모두 비교한다면?
//가능할 듯