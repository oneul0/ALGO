import Foundation

let first = readLine()!.split(separator: " ").map { Int($0)! },
    M = first[0],
    N = first[1]

var map = [[Int]]()
for _ in 0..<M {
    map.append(readLine()!.split(separator: " ").map { Int($0)! })
}

var dp = Array(repeating: Array(repeating: -1, count: N), count: M)

let dr = [-1, 1, 0, 0]
let dc = [0, 0, -1, 1]

func dfs(_ r: Int, _ c: Int) -> Int {
    if r == M - 1 && c == N - 1 {
        return 1
    }
    
    if dp[r][c] != -1 {
        return dp[r][c]
    }
    
    dp[r][c] = 0
    
    for i in 0..<4 {
        let nr = r + dr[i]
        let nc = c + dc[i]
        
        if nr < 0 || nr >= M || nc < 0 || nc >= N {
            continue
        }
        
        if map[nr][nc] < map[r][c] {
            dp[r][c] += dfs(nr, nc)
        }
    }
    
    return dp[r][c]
}

print(dfs(0, 0))