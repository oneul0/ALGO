import Foundation

let MAX = 15
let NEG = -1_000_000_000

var dp = Array(
    repeating: Array(repeating: NEG, count: MAX + 1),
    count: MAX + 1
)

dp[0][0] = 0

while let line = readLine() {
    let parts = line.split(separator: " ").map { Int($0)! }
    if parts.count != 2 { continue }

    let white = parts[0]
    let black = parts[1]

    var next = dp

    for w in 0...MAX {
        for b in 0...MAX {
            let cur = dp[w][b]
            if cur == NEG { continue }

            if w + 1 <= MAX {
                next[w + 1][b] = max(next[w + 1][b], cur + white)
            }

            if b + 1 <= MAX {
                next[w][b + 1] = max(next[w][b + 1], cur + black)
            }
        }
    }
    dp = next
}

print(dp[15][15])