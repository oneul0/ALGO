import Foundation

let T = Int(readLine()!)!
let k = Int(readLine()!)!

var dp = Array(repeating: 0, count: T + 1)
dp[0] = 1

for _ in 0..<k {
    let input = readLine()!.split(separator: " ").map { Int($0)! }
    let value = input[0]
    let count = input[1]

    var nextDP = dp
    for cur in 0...T {
        if dp[cur] == 0 { continue }
        for c in 1...count {
            let next = cur + value * c
            if next > T { break }
            nextDP[next] += dp[cur]
        }
    }
    dp = nextDP
}

print(dp[T])