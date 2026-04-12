let t = Int(readLine()!)!
var output = ""


var dp = [[Int]](repeating: [Int](repeating: 0, count: 2001), count: 11)

(1...2000).forEach { j in dp[1][j] = 1 }

for i in 2...10 {
    for j in 1...2000 {
        for l in stride(from: 1, through: j/2, by: 1) {
            dp[i][j] += dp[i-1][l]
        }
    }
}

for _ in 0..<t {
    let nm = readLine()!.split { $0 == " " }.map { Int($0)! },
        n = nm[0],
        m = nm[1]
    let answer = dp[n].prefix(m+1).reduce(0, +)
    output.write("\(answer)\n")
}

print(output)