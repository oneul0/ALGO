//Q1. Remove Duplicate Letters
class Solution {
    public String removeDuplicateLetters(String s) {
        int[] last = new int[26];
        boolean[] used = new boolean[26];

        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i;
        }

        StringBuilder stack = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);

            if (used[cur - 'a']) {
                continue;
            }

            while (
                stack.length() > 0 &&
                    stack.charAt(stack.length() - 1) > cur &&
                    last[stack.charAt(stack.length() - 1) - 'a'] > i
            ) {
                char removed = stack.charAt(stack.length() - 1);
                stack.deleteCharAt(stack.length() - 1);
                used[removed - 'a'] = false;
            }

            stack.append(cur);
            used[cur - 'a'] = true;
        }

        return stack.toString();
    }
}