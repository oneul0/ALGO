import Foundation

let nk = readLine()!.split(separator: " ").map { Int($0)! }
let N = nk[0]
let K = nk[1]

let s = readLine()!.split(separator: " ").map { Int($0)! }
let h = readLine()!.split(separator: " ").map { Int($0)! }

let NEG = -1_000_000_000
var dp = Array(repeating: NEG, count: 101)
dp[100] = 0

for i in 0..<N {
    var next = Array(repeating: NEG, count: 101)
    for hp in 0...100 where dp[hp] != NEG {
        let recovered = min(100, hp + K)

        next[recovered] = max(next[recovered], dp[hp])

        if recovered >= h[i] {
            next[recovered - h[i]] =
                max(next[recovered - h[i]], dp[hp] + s[i])
        }
    }
    dp = next
}

print(dp.max()!)