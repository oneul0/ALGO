import java.io.*;
import java.util.*;

public class Main {
    static class Pair implements Comparable<Pair>{
        char alphabet;
        int score;
        Pair(char al, int s){
            this.alphabet = al;
            this.score = s;
        }
        @Override
        public int compareTo(Pair o){
            return o.score - this.score;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        String[] words = new String[n];
        for(int i = 0; i<n; i++){
            words[i] = br.readLine();
        }
        Pair[] scores = new Pair[26];
        for(int i = 0; i<26; i++){
            scores[i] = new Pair((char)('A'+i), 0);
        }

        for(String w : words){
            for(int i = 0; i<w.length(); i++){
                Pair cur = scores[w.charAt(i) -'A'];
                cur.score += (int)Math.pow(10,(w.length()-1-i));
            }
        }
        Arrays.sort(scores);
        int num = 9;
        long answer = 0;
        for (int i = 0; i < 26; i++) {
            if (scores[i].score == 0) break;
            answer += (long) scores[i].score * num;
            num--;
        }
        System.out.println(answer);
    }
}
