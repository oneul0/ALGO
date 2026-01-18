import java.io.*;
import java.util.*;

public class Main {
    public static class Pair{
        int x;
        long dist;
        Pair(int x, long dist){
            this.x = x;
            this.dist = dist;
        }
    }
    final static int MAX = Integer.MAX_VALUE-1, MIN = Integer.MIN_VALUE+1;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        Queue<Pair> q = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();
        for(int i = 0; i<n; i++){
            int cur = Integer.parseInt(st.nextToken());
            q.offer(new Pair(cur, 0));
            visited.add(cur);
        }

        long result = 0;
        int count = 0;
        while(!q.isEmpty()){
            Pair cur = q.poll();
            int left = cur.x-1;
            int right = cur.x+1;
            if(left>=MIN && !visited.contains(left)) {
                q.offer(new Pair(left, cur.dist+1));
                visited.add(left);
                result += cur.dist+1;
                count++;
            }
            if(k==count) break;

            if(right<=MAX && !visited.contains(right)){
                q.offer(new Pair(right, cur.dist+1));
                visited.add(right);
                result += cur.dist+1;
                count++;
            }
            if(k==count) break;
        }

        System.out.println(result);

    }
}
