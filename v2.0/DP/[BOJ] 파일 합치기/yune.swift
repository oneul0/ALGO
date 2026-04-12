import Foundation

let T = Int(readLine()!)!

for _ in 0..<T {
    let K = Int(readLine()!)!
    let files = readLine()!.split(separator: " ").map { Int($0)! }
    
    var dp = Array(repeating: Array(repeating: 0, count: K), count: K)
    var sum = Array(repeating: 0, count: K+1)
    
    // 누적합 계산
    for i in 0..<K {
        sum[i+1] = sum[i] + files[i]
    }
    
    for length in 2...K {
        for i in 0...(K - length) {
            let j = i + length - 1
            dp[i][j] = Int.max
            for k in i..<j {
                dp[i][j] = min(dp[i][j], dp[i][k] + dp[k+1][j] + sum[j+1] - sum[i])
            }
        }
    }
    
    print(dp[0][K-1])
}