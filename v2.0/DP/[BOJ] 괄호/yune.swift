let t = Int(String(readLine()!))!
var dp = Array(repeating: 0, count: 5001)
dp[0] = 1
dp[2] = 1
for n in stride(from: 4, through: 5000, by: 2) {
    for i in stride(from: 2, through: n, by: 2){
        dp[n] += dp[i - 2] * dp[n - i]
        dp[n] %= 1000000007
    }
    
}

for _ in 0..<t{
    let a = Int(String(readLine()!))!
    print(dp[a])
}