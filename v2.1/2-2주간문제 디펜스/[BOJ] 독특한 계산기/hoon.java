import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static Deque<Long> nums = new ArrayDeque<>();
    static Deque<Character> ops = new ArrayDeque<>();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String origin = br.readLine();


        StringTokenizer st = new StringTokenizer(origin, "+-*/", true);
        String firstToken = st.nextToken();

        if (firstToken.equals("-")) {
            nums.addLast(Long.parseLong("-" + st.nextToken()));
        } else {
            nums.addLast(Long.parseLong(firstToken));
        }
        while (st.hasMoreTokens()) {
            ops.addLast(st.nextToken().charAt(0));
            nums.addLast(Long.parseLong(st.nextToken()));
        }

        solve();

    }
    public static void solve() {
        while (!ops.isEmpty()) {
            if (ops.size() == 1) {
                long res = calc(nums.pollFirst(), ops.pollFirst(), nums.pollFirst());
                System.out.println(res);
                return;
            }

            char fOp = ops.peekFirst();
            char bOp = ops.peekLast();

            int fPri = getPriority(fOp);
            int bPri = getPriority(bOp);

            if (fPri > bPri) {
                frontCalc();
            } else if (fPri < bPri) {
                backCalc();
            } else {
                long fRes = getFrontRes();
                long bRes = getBackRes();

                if (fRes >= bRes) {
                    frontCalc();
                } else {
                    backCalc();
                }
            }
        }
        System.out.println(nums.pollFirst());
    }

    // 진짜 계산하고 업데이트
    public static void frontCalc() {
        long n1 = nums.pollFirst();
        long n2 = nums.pollFirst();
        char op = ops.pollFirst();
        nums.addFirst(calc(n1, op, n2));
    }

    public static void backCalc() {
        long n2 = nums.pollLast();
        long n1 = nums.pollLast();
        char op = ops.pollLast();
        nums.addLast(calc(n1, op, n2));
    }

    //미리보기
    public static long getFrontRes() {
        long n1 = nums.pollFirst();
        long n2 = nums.pollFirst();
        char op = ops.peekFirst();
        long res = calc(n1, op, n2);

        nums.addFirst(n2);
        nums.addFirst(n1);
        return res;
    }

    public static long getBackRes() {
        long n2 = nums.pollLast();
        long n1 = nums.pollLast();
        char op = ops.peekLast();
        long res = calc(n1, op, n2);

        nums.addLast(n1);
        nums.addLast(n2);
        return res;
    }

    public static int getPriority(char op){
        switch (op){
            case '*':
                return 2;
            case '/':
                return 2;
            case '+':
                return 1;
            case '-':
                return 1;
            default:
                return 0;
        }
    }

    public static long calc(long a, char op, long b){
        switch (op){
            case '*':
                return a*b;
            case '/':
                return a/b;
            case '+':
                return a+b;
            case '-':
                return a-b;
            default:
                return 0;
        }
    }
}
// 1. *, /
// 2. +, -
