import java.io.*;

public class Main {
    static String line;
    static int cur = 0;

    public static int recursive() {
        int len = 0;

        while (cur < line.length()) {
            char c = line.charAt(cur);
            if (c == '(') {
                len--;
                int times = line.charAt(cur - 1) - '0';
                cur++;
                int insideLen = recursive();
                len += (times*insideLen);
            }
            else if (c == ')') {
                cur++;
                return len;
            }
            else {
                len++;
                cur++;
            }
        }
        return len;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        line = br.readLine();
        System.out.println(recursive());
    }
}
//재귀 보내고 업데이트된 인덱스로 다시 순회