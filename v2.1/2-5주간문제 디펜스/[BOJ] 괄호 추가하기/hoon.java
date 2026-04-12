import java.io.*;
import java.util.*;

public class Main {
    static int N, answer = Integer.MIN_VALUE;
    static List<Integer> numbers = new ArrayList<>();
    static List<Character> ops = new ArrayList<>();
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        String expression = br.readLine();
        for(char c : expression.toCharArray()){
            if('0' <= c && c <= '9'){
                numbers.add(c-'0');
            }
            else{
                ops.add(c);
            }
        }
        dfs(0, numbers.get(0));
        System.out.println(answer);
    }

    public static void dfs(int opIdx, int curResult) {
        // 모든 연산자를 다 탐색하면 종료
        if(opIdx >= ops.size()){
            answer = Math.max(answer, curResult);
            return;
        }

        // 괄호 없이 순차적으로 계산 (opIdx + 1 로 이동)
        dfs(opIdx+1, calc(curResult, numbers.get(opIdx+1), ops.get(opIdx)));
        // 뒤쪽 수식에 괄호를 치고 계산 (opIdx + 2 로 이동)
        if(opIdx + 1 < ops.size()){
            int bracketed = calc(numbers.get(opIdx+1), numbers.get(opIdx+2), ops.get(opIdx+1));
            int nextResult = calc(curResult, bracketed, ops.get(opIdx));
            dfs(opIdx+2, nextResult);
        }
    }

    public static int calc(int a, int b, char op){
        switch (op){
            case '+':
                return a+b;
            case '-':
                return a-b;
            case '*':
                return a*b;
            default:
                break;
        }
        return 0;
    }
}
