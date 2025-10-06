import Foundation

let n = Int(readLine()!)!

var forest = [[Int]](repeating: [Int](), count: n)
for i in 0..<n {
    let row = readLine()!.split(separator: " ").map { Int($0)! }
    forest[i] = row
}

var dp = [[Int]](repeating: [Int](repeating: 0, count: n), count: n)

let dr = [-1, 1, 0, 0]
let dc = [0, 0, -1, 1]

func dfs(_ r: Int, _ c: Int) -> Int {
    if dp[r][c] != 0 {
        return dp[r][c]
    }

    dp[r][c] = 1
    
    for i in 0..<4 {
        let nr = r + dr[i]
        let nc = c + dc[i]

        if nr < 0 || nr >= n || nc < 0 || nc >= n {
            continue
        }

        if forest[nr][nc] > forest[r][c] {
            let candidate = dfs(nr, nc) + 1
            if candidate > dp[r][c] {
                dp[r][c] = candidate
            }
        }
    }
    return dp[r][c]
}

var answer = 0
for r in 0..<n {
    for c in 0..<n {
        let len = dfs(r, c)
        if len > answer {
            answer = len
        }
    }
}

print(answer)