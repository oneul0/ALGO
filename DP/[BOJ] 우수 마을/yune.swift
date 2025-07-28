let n = Int(readLine()!)!
let cost = [0] + readLine()!.split(separator: " ").map { Int($0)! }
var tree: [Int: [Int]] = [:]
var visited = [Bool](repeating: false, count: n + 1)
for _ in 0..<n - 1 {
    let input = readLine()!.split(separator: " ").map { Int($0)! }
    let a = input[0], b = input[1]
    tree[a, default: []].append(b)
    tree[b, default: []].append(a)
}

var cache = [[Int]](repeating: [Int](repeating: 0, count: 2), count: n + 1)

func dfs(node: Int) {
    visited[node] = true
    
    cache[node][0] = 0
    cache[node][1] = cost[node]
    
    for nextNode in tree[node, default: []] {
        if !visited[nextNode] {
            dfs(node: nextNode)
            cache[node][0] += max(cache[nextNode][0], cache[nextNode][1])
            cache[node][1] += cache[nextNode][0]
        }
    }
}

dfs(node: 1)

print(max(cache[1][0], cache[1][1]))