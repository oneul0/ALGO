import Foundation

let T = Int(readLine()!)!
var output = ""

for _ in 0..<T {
    let first = readLine()!.split(separator: " ").map { Int($0)! }
    let N = first[0]
    let E = first[1]

    let S = readLine()!.split(separator: " ").map { Int($0)! }

    var A = [Int]()
    var sum = 0

    for s in S {
        let v = s * 2
        A.append(v)
        sum += v
    }

    if sum < E {
        output += "FULL\n"
        continue
    }

    var dp = Array(repeating: 0, count: sum + 1)

    for v in A {
        if v > sum { continue }
        for j in stride(from: sum, through: v, by: -1) {
            dp[j] = max(dp[j], dp[j - v] + v)
        }
    }

    for j in 0...sum {
        if dp[j] >= E {
            output += "\(j / 2)\n"
            break
        }
    }
}

print(output)