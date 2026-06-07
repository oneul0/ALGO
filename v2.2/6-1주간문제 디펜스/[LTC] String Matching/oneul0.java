//Q1. Repeated Substring Pattern
class Solution {
    public boolean repeatedSubstringPattern(String s) {
        int n = s.length();

        for (int len=1; len<=n/2; len++) {
            if (n%len!=0) continue;

            String pattern = s.substring(0, len);
            StringBuilder sb = new StringBuilder();

            for (int i=0; i<n/len; i++) {
                sb.append(pattern);
            }

            if (sb.toString().equals(s)) return true;
        }

        return false;
    }
}
//Q2. Rotate String
class Solution {
    public boolean rotateString(String s, String goal) {
        if(s.length() != goal.length()) return false;
        if(s.equals(goal)) return true;

        String str = goal+goal;
        return str.substring(1, str.length()-1).contains(s);
    }
}
//Q3. Repeated String Match
class Solution {
    public int repeatedStringMatch(String a, String b) {
        int count = (b.length()+a.length()-1)/a.length();
        String str = a.repeat(count);
        if(str.contains(b)) return count;
        str+=a;
        if(str.contains(b)) return count+1;
        return -1;
    }
}