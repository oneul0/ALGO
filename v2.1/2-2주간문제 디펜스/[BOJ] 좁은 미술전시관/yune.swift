import Foundation

let NK = readLine()!.split { $0 == " " }.map { Int(String($0))! },
    N = NK[0],
    K = NK[1]

var grid = [[0,0]]
for _ in 0..<N {
    let line = readLine()!.split { $0 == " " }.map { Int(String($0))! }
    grid.append(line)
}
_=readLine()!

var dp = [[[Int]]](repeating:[[Int]](repeating: [Int](repeating: 0, count: 3), count: K+1), count: N+1)
if K > 0 {
    dp[1][1][0] = grid[1][1]
    dp[1][1][1] = grid[1][0]
}
dp[1][0][2] = grid[1][0] + grid[1][1]

for n in 2...N {
    for k in 0...K {
        if k >= 1 {
            dp[n][k][0] = max(dp[n - 1][k - 1][0], dp[n - 1][k - 1][2]) + grid[n][1]
            dp[n][k][1] = max(dp[n - 1][k - 1][1], dp[n - 1][k - 1][2]) + grid[n][0]
        }
        if n != k {
            dp[n][k][2] = max(max(dp[n-1][k][0], dp[n-1][k][1]), dp[n-1][k][2]) + grid[n][0] + grid[n][1]
        }
    }
}

let answer = max(max(dp[N][K][0], dp[N][K][1]), dp[N][K][2])
print(answer)