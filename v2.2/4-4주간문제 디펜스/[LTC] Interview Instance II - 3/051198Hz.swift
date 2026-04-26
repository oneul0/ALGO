class Solution {
    func generateParenthesis(_ n: Int) -> [String] {
        var result = [String]()
        
        func dfs(_ str: String, _ open: Int, _ close: Int) {
            if str.count == 2 * n {
                result.append(str)
                return
            }
            
            if open < n {
                dfs(str + "(", open + 1, close)
            }
            
            if close < open {
                dfs(str + ")", open, close + 1)
            }
        }
        
        dfs("", 0, 0)
        return result
    }
}
