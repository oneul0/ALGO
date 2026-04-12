let n = Int(readLine()!)!
let m = Int(readLine()!)!
var isVIP = [Bool](repeating: false, count: n+1)
var dp = [Int](repeating: 1, count: n+1)
for _ in 0..<m {
    let i = Int(readLine()!)!
    isVIP[i] = true
}
var lastCount = 1
for i in 1...n {
    if isVIP[i] {continue }
    dp[i] = dp[i-1]
    if i-2 >= 0, !isVIP[i-1] {
        dp[i] = dp[i] + dp[i - 2]
    }
    if i+1 < n+1, isVIP[i+1] {
        lastCount *= dp[i]
        continue
    }
    if i == n {
        lastCount *= dp[i]
    }
}

print(lastCount)