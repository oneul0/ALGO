import java.io.*;
import java.util.*;

public class Main {
    static class Pair{
        int w, price;
        Pair(int w, int price){
            this.w = w;
            this.price = price;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        Pair[] arr = new Pair[N];
        for(int i  =0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            int w = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            arr[i] = new Pair(w, p);
        }

        Arrays.sort(arr, (a, b) -> {
            if (a.price != b.price) {
                return Integer.compare(a.price, b.price);
            }
            return Integer.compare(b.w, a.w);
        });

        long totalWeight = 0;
        long ans = Long.MAX_VALUE;

        int samePriceCount = 0;
        int currentPrice = -1;

        for (int i = 0; i < N; i++) {
            Pair x = arr[i];
            if (x.price != currentPrice) {
                currentPrice = x.price;
                samePriceCount = 0;
            }

            totalWeight += x.w;
            samePriceCount++;

            if (totalWeight >= M) {
                long cost = (long)currentPrice * samePriceCount;
                if (cost < ans) ans = cost;
            }
        }

        System.out.println(ans == Long.MAX_VALUE ? -1 : ans);

    }
}