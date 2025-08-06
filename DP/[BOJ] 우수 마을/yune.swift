let n = Int(readLine()!)!
let populations = [-1] + readLine()!.split { $0 == " " }.map { Int($0)! }
var tree = [Int: [Int]]()
var dp = [[Int]](repeating: [Int](repeating: 0, count: 2), count: n+1)
var isVisited = [Bool](repeating: false, count: n+1)

func dfs(_ node: Int) -> Int {
    dp[node][0] = 0
    dp[node][1] = populations[node]
    isVisited[node] = true
    let childNodes = tree[node]!
    for childNode in childNodes {
        if isVisited[childNode] { continue }
        dp[node][0] += dfs(childNode)
        dp[node][1] += dp[childNode][0]
    }
    return max(dp[node][1], dp[node][0])
}

for _ in 0..<n-1 {
    let gv = readLine()!.split { $0 == " " }.map { Int($0)! },
        g = gv[0],
        v = gv[1]

    if tree[g] == nil {
        tree.updateValue([], forKey: g)
    }
    if tree[v] == nil {
        tree.updateValue([], forKey: v)
    }
    tree[g]!.append(v)
    tree[v]!.append(g)
}

let answer = dfs(1)
print(answer)