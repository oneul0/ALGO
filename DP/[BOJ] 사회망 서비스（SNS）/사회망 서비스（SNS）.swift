import Foundation

let N = Int(readLine()!)!
var adj = [[Int]](repeating: [], count: N + 1)
for _ in 0..<N-1 {
    let parts = readLine()!.split(separator: " ").map { Int($0)! }
    let u = parts[0], v = parts[1]
    adj[u].append(v)
    adj[v].append(u)
}

var dp = Array(repeating: [0, 0], count: N + 1)

func dfs(_ u: Int, _ parent: Int) {
    dp[u][1] = 1    // u가 얼리 어답터인 경우
    dp[u][0] = 0    // u가 얼리 어답터가 아닌 경우
    
    for v in adj[u] {
        if v == parent { continue }
        dfs(v, u)
        // u가 얼리 어답터가 아니면 자식 v는 반드시 얼리 어답터여야 함
        dp[u][0] += dp[v][1]
        // u가 얼리 어답터면 자식 v는 얼리 어답터여도 되고 아니어도 됨
        dp[u][1] += min(dp[v][0], dp[v][1])
    }
}

dfs(1, 0)

let result = min(dp[1][0], dp[1][1])
print(result)