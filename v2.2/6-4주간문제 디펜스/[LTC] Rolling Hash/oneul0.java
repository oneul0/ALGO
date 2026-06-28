//Q1. Shortest Palindrome
class Solution {
    public String shortestPalindrome(String s) {
        String rev = new StringBuilder(s).reverse().toString();
        String combined = s + "#" + rev;

        int[] lps = buildLPS(combined);

        int palLen = lps[combined.length() - 1];

        String suffix = s.substring(palLen);
        String prefix = new StringBuilder(suffix).reverse().toString();

        return prefix + s;
    }

    private int[] buildLPS(String str) {
        int[] lps = new int[str.length()];
        int len = 0;

        for (int i = 1; i < str.length(); i++) {
            while (len > 0 && str.charAt(i) != str.charAt(len)) {
                len = lps[len - 1];
            }

            if (str.charAt(i) == str.charAt(len)) {
                len++;
                lps[i] = len;
            }
        }

        return lps;
    }
}


/*
1. rev = reverse(s)
2. combined = s + "#" + rev
3. combined의 LPS 배열 계산
4. pal_len = lps[-1]
5. s[pal_len:] 부분을 뒤집어 앞에 붙임
*/


//Q2. Longest Happy Prefix
class Solution {
    public String longestPrefix(String s) {
        int n = s.length();
        int[] pi = new int[n];

        //kmp
        int j = 0;
        for(int i = 1; i<n; i++){
            //일치하지 않으면 이전 일치 위치로 후퇴
            while(j>0 && s.charAt(i) != s.charAt(j)){
                j = pi[j-1];
            }

            //일치하면 j 증가
            if(s.charAt(i) == s.charAt(j)){
                j++;
            }
            pi[i] = j;
        }

        //pi[i-1]  = 가장 긴 길이
        int len = pi[n-1];
        return s.substring(0,len);
    }
}


//Q3. Sum of Scores of Built Strings
class Solution {
    public long sumScores(String s) {
        int n = s.length();
        long[] z = new long[n];
        z[0] = n;  // s0 = sn 이므로 전체 길이

        int L = 0, R = 0;
        for (int i = 1; i < n; i++) {
            if (i < R) {
                // Z-box 사용
                z[i] = Math.min(z[i - L], R - i);
            }
            // 직접 확장해보고
            while (i + z[i] < n && s.charAt((int)z[i]) == s.charAt((int)(i + z[i]))) {
                z[i]++;
            }
            // Z-box 갱신
            if (i + z[i] > R) {
                L = i;
                R = (int)(i + z[i]);
            }
        }

        long sum = 0;
        for (long v : z){
            sum += v;
        }
        return sum;
    }
}

/**
 여기서 말하는 Z-bos란
 Z[i] = s[i:] 와 s[0:] 의 longest common prefix 길이

 s = "abaca"
 01234

 Z[0] = 5  (자기 자신, 전체 길이)
 Z[1] = 0  "baca" vs "abaca" → 불일치
 Z[2] = 1  "aca"  vs "abaca" → 'a' 1개 일치
 Z[3] = 0  "ca"   vs "abaca" → 불일치
 Z[4] = 1  "a"    vs "abaca" → 'a' 1개 일치

 sum = 5 + 0 + 1 + 0 + 1 = 7
 이런식으로 하나씩 잘라가며 비교*/