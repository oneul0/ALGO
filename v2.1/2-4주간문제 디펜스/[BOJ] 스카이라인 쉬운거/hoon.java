import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = Integer.parseInt(br.readLine());
        Deque<Integer> stack = new ArrayDeque<>();
        int count = 0;
        for(int i = 0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            // 현재 높이가 스택 제일 위보다 낮아지면 그동안 쌓인 높은 건물들은 끝난 것
            while (!stack.isEmpty() && stack.peek() > y) {
                stack.pop();
            }
            // 현재 높이가 0이 아니고, 스택이 비었거나 스택 위보다 높다면 새로운 건물 시작
            if (y > 0 && (stack.isEmpty() || stack.peek() < y)) {
                stack.push(y);
                count++;
            }
        }
        System.out.println(count);
    }
}
