class Solution {

    public List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<>();
        dfs("", 0, 0, n, list);
        return list;
    }

    public void dfs(String str, int open, int close, int n, List<String> list) {
        if (str.length() == n+n) {
            list.add(str);
            return;
        }

        if (open < n) {
            dfs(str + "(", open + 1, close, n, list);
        }

        if (close < open) {
            dfs(str + ")", open, close + 1, n, list);
        }
    }
}