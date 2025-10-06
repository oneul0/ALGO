import Foundation

let A = readLine()!
let B = readLine()!
let C = readLine()!

let n = A.count
let m = B.count
let p = C.count

let a = Array(A)
let b = Array(B)
let c = Array(C)

var dp = Array(
    repeating: Array(
        repeating: Array(repeating: 0, count: p + 1),
        count: m + 1
    ),
    count: n + 1
)

for i in 1...n {
    for j in 1...m {
        for k in 1...p {
            if a[i - 1] == b[j - 1] && b[j - 1] == c[k - 1] {
                dp[i][j][k] = dp[i - 1][j - 1][k - 1] + 1
            } else {
                dp[i][j][k] = max(
                    dp[i - 1][j][k],
                    dp[i][j - 1][k],
                    dp[i][j][k - 1]
                )
            }
        }
    }
}

print(dp[n][m][p])