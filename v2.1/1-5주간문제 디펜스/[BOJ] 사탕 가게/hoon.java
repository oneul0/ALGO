import java.io.*;
import java.util.*;

public class Main {
    public static class Candy implements Comparable<Candy>{
        double price;
        int cal;
        Candy(int cal,double price){
            this.price = price;
            this.cal = cal;
        }
        @Override
        public int compareTo(Candy o){
            if(this.cal != o.cal) return Integer.compare(o.cal, this.cal);
            return Double.compare(this.price, o.price);
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        while(true){
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            double m =Double.parseDouble(st.nextToken());
            if(n == 0 && m == 0.00) break;
            List<Candy> candies = new ArrayList<>();
            for(int i = 0; i<n; i++){
                st = new StringTokenizer(br.readLine());
                candies.add(new Candy(Integer.parseInt(st.nextToken()), Double.parseDouble(st.nextToken())));
            }
            Collections.sort(candies);
            int mInt = (int) (m * 100 + 0.5);
            int[] dp = new int[mInt + 1];

            for (int i = 0; i < n; i++) {
                int calorie = candies.get(i).cal;
                int price = (int) (candies.get(i).price * 100 + 0.5);

                for (int j = price; j <= mInt; j++) {
                    dp[j] = Math.max(dp[j], dp[j - price] + calorie);
                }
            }
            System.out.println(dp[mInt]);
        }

    }
}
