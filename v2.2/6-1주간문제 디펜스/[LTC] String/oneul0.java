//Q1. Detect Capital
class Solution {
    public boolean detectCapitalUse(String word) {
        if(Character.isLowerCase(word.charAt(0))){
            if(isLowerString(word.substring(1, word.length()))) return true;
            return false;
        }
        else{
            String sub = word.substring(1, word.length());
            if(isLowerString(sub) || isUpperString(sub)) return true;
            return false;
        }
    }
    public boolean isLowerString(String str){
        for(int i = 0; i<str.length(); i++){
            if(Character.isUpperCase(str.charAt(i))) return false;
        }
        return true;
    }
    public boolean isUpperString(String str){
        for(int i = 0; i<str.length(); i++){
            if(Character.isLowerCase(str.charAt(i))) return false;
        }
        return true;
    }
}
//첫번째는 대문자 또는 소문자 가능
//첫번째가 대문자이면 모두 대문자 나머지 소문자 가능
//첫번째가 소문자이면 모두 소문자만 가능

//Q2. License Key Formatting
class Solution {
    public String licenseKeyFormatting(String s, int k) {
        Deque<Character> stack = new ArrayDeque<>();
        for(int i = 0; i<s.length(); i++){
            if(s.charAt(i) == '-') continue;
            else {
                char c = s.charAt(i);
                if(c>='a' && c<='z') c = Character.toUpperCase(c);
                stack.push(c);
            }
        }
        StringBuilder sb = new StringBuilder();
        int count = 0;
        while(!stack.isEmpty()){
            if(count<k){
                sb.append(stack.pop());
                count++;
            }
            else{
                sb.append("-");
                count = 0;
            }
        }
        return sb.reverse().toString();
    }
}
//Q3. Masking Personal Information
class Solution {
    public String maskPII(String s) {
        String result;
        //email
        if(s.indexOf('@') != -1){
            result = maskingEmail(s);
        }
        //phone
        else{
            result = maskingNumber(s);
        }

        return result;
    }


    //email
    public String maskingEmail(String s){
        s = s.toLowerCase();
        String[] sub = s.split("@");
        StringBuilder sb = new StringBuilder();
        sb.append(sub[0].charAt(0)).append("*****").append(sub[0].charAt(sub[0].length()-1));
        sb.append("@").append(sub[1]);
        return sb.toString();
    }

    //phone
    public String maskingNumber(String s){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<s.length(); i++){
            if(s.charAt(i) >= '0' && s.charAt(i) <= '9') sb.append(s.charAt(i));
        }

        int len = sb.length();
        String str = sb.toString();
        if(len == 10){
            return "***-***-"+str.substring(len-4, len);
        }
        else if(len == 11){
            return "+*-***-***-"+str.substring(len-4, len);
        }
        else if(len == 12){
            return "+**-***-***-"+str.substring(len-4, len);
        }
        else if(len == 13){
            return "+***-***-***-"+str.substring(len-4, len);
        }

        return "";
    }

}