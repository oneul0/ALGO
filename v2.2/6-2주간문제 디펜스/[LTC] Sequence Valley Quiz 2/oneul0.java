//Q1. Reformat Date
class Solution {
    public String reformatDate(String date) {
        String[] splitStr = date.split(" ");
        Map<String, String> month = new HashMap<>();
        String[] m = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        for(int i = 1; i<=12; i++){
            month.put(m[i-1], i+"");
        }
        String yy = splitStr[2];
        String mm = month.get(splitStr[1]);
        mm = mm.length() < 2 ? "0"+mm : mm;
        String dd = splitStr[0].replaceAll("(st|th|rd|nd)$","");
        dd = dd.length() < 2 ? "0"+dd : dd;

        return yy+"-"+mm+"-"+dd;
    }
}

//Q2. Maximum Repeating Substring
class Solution {
    public int maxRepeating(String sequence, String word) {
        int count = 0;

        while (sequence.contains(word.repeat(count + 1))) {
            count++;
        }

        return count;
    }
}