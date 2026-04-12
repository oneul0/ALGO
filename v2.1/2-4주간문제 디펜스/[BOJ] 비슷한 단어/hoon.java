import java.util.*;
import java.io.*;

public class Main {
    static int n;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        List<String> origin = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            if (!origin.contains(s)) {
                origin.add(s);
            }
        }

        int maxLen = -1;
        String res1 = "", res2 = "";

        for (int i = 0; i < origin.size(); i++) {
            String s1 = origin.get(i);
            for (int j = i + 1; j < origin.size(); j++) {
                String s2 = origin.get(j);

                if (Math.min(s1.length(), s2.length()) <= maxLen) continue;

                int len = getCommonPrefixLength(s1, s2);
                if (len > maxLen) {
                    maxLen = len;
                    res1 = s1;
                    res2 = s2;
                }
            }
        }

        System.out.println(res1+"\n"+res2);
    }

    private static int getCommonPrefixLength(String s1, String s2) {
        int minLen = Math.min(s1.length(), s2.length());
        for (int i = 0; i < minLen; i++) {
            if (s1.charAt(i) != s2.charAt(i)) return i;
        }
        return minLen;
    }
}