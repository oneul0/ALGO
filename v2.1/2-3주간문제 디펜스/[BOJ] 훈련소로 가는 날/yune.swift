import Foundation

let MOD = 998244353

let NM = readLine()!.split(separator: " ").map { Int($0)! },
    N = NM[0],
    M = NM[1]

if N == 1 {
    print(M)
    exit(0)
}

var dp = Array(
    repeating: Array(
        repeating: Array(repeating: 0, count: 2),
        count: M + 1
    ),
    count: N + 1
)

for v in 1...M {
    dp[1][v][0] = 1
}

for i in 2...N {
    for u in 1...M {
        for s in 0...1 {
            let current = dp[i-1][u][s]
            if current == 0 { continue }
            
            for v in 1...M {
                if v > u {
                    dp[i][v][1] = (dp[i][v][1] + current) % MOD
                } else if v == u {
                    dp[i][v][0] = (dp[i][v][0] + current) % MOD
                } else {
                    if s == 0 {
                        dp[i][v][0] = (dp[i][v][0] + current) % MOD
                    }
                }
            }
        }
    }
}

var answer = 0
for v in 1...M {
    for s in 0...1 {
        answer = (answer + dp[N][v][s]) % MOD
    }
}

print(answer)