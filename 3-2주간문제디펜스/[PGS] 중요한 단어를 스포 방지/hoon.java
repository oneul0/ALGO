import java.util.*;

class Solution {

    public int solution(String message, int[][] spoiler_ranges) {
        Set<String> not_importants = getNotImportants(message, spoiler_ranges);
        Set<String> importants = new HashSet<>();

        for (int[] range : spoiler_ranges) {
            String word = getCompleteWord(range, message);
            String[] splitWords = word.split("\\s+");
            for(String w : splitWords){
                String tw = w.trim();
                if (!tw.isEmpty() && !not_importants.contains(tw)) {
                    importants.add(tw);
                }
            }
        }

        return importants.size();
    }

    public String getCompleteWord(int[] range, String origin) {
        int start = range[0];
        int end = range[1];
        int limit = origin.length();

        if (start < 0 || end >= limit || start > end) return "";

        while (start > 0 && origin.charAt(start - 1) != ' ') {
            start--;
        }
        while (end < limit - 1 && origin.charAt(end + 1) != ' ') {
            end++;
        }

        return origin.substring(start, end + 1).trim();
    }

    public Set<String> getNotImportants(String origin, int[][] spoiler_ranges) {
        Set<String> not_importants = new HashSet<>();
        boolean[] isSpoiled = new boolean[origin.length()];

        for (int[] range : spoiler_ranges) {
            for (int i = range[0]; i <= range[1]; i++) {
                if (i >= 0 && i < origin.length()) {
                    isSpoiled[i] = true;
                }
            }
        }

        int i = 0;
        while (i < origin.length()) {
            if (origin.charAt(i) == ' ') {
                i++;
                continue;
            }

            int start = i;
            while (i < origin.length() && origin.charAt(i) != ' ') {
                i++;
            }

            int end = i - 1;

            boolean hasSpoiler = false;
            for (int j = start; j <= end; j++) {
                if (isSpoiled[j]) {
                    hasSpoiler = true;
                    break;
                }
            }

            if (!hasSpoiler) {
                not_importants.add(origin.substring(start, end + 1));
            }
        }

        return not_importants;
    }
}