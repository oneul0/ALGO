import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        String nStr = br.readLine();
        if (nStr == null) return;
        int N = Integer.parseInt(nStr);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            sb.append(solve(line)).append("\n");
        }

        System.out.print(sb);
    }

    public static String solve(String line) {
        String formatted = line.replaceAll("([^\\w\\s\\-])", " $1 ");

        List<String> tokens = new LinkedList<>(Arrays.asList(formatted.trim().split("\\s+")));

        applyOfKoreaRule(tokens);
        applyKoreaWordRule(tokens);

        return reconstruct(tokens);
    }

    public static void applyOfKoreaRule(List<String> tokens) {
        for (int i = 0; i < tokens.size() - 2; i++) {
            String word = tokens.get(i);
            String of = tokens.get(i + 1);
            String korea = tokens.get(i + 2);

            if (of.equals("of") && korea.equals("Korea")) {
                if (!isPunc(word)) {
                    String newWord = "K-" + capitalize(word);
                    tokens.set(i, newWord);

                    tokens.remove(i + 2);
                    tokens.remove(i + 1);

                    i--;
                }
            }
        }
    }

    public static void applyKoreaWordRule(List<String> tokens) {
        for (int i = tokens.size() - 2; i >= 0; i--) {
            String current = tokens.get(i);
            String next = tokens.get(i + 1);

            if (current.equals("Korea")) {
                if (!isPunc(next)) {
                    String newWord = "K-" + capitalize(next);
                    tokens.set(i + 1, newWord);

                    tokens.remove(i);
                }
            }
        }
    }

    //첫글자 대문자로
    public static String capitalize(String word) {
        if (word == null || word.isEmpty()) return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    //문장부호인지 -> -은 처리하면 안됨
    public static boolean isPunc(String token) {
        if (token.length() > 1) return false;
        return "!?,.".contains(token);
    }

    //문장 합쳐서 String으로 만들기
    public static String reconstruct(List<String> tokens) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tokens.size(); i++) {
            sb.append(tokens.get(i));

            if (i < tokens.size() - 1) {
                if (!isPunc(tokens.get(i + 1))) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }
}