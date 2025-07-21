let tw = readLine()!.split { $0 == " " }.map { Int(String($0))! }
let treeOrder = [0] + (0..<tw[0]).map { _ in
    Int(readLine()!)!
}
var dp = [[Int]](repeating: [Int](repeating: 0, count: tw[1]+1), count: tw[0]+1)
// dp[t][w] t는 시간, w는 움직인 횟수
dp[1][0] = treeOrder[1] % 2
dp[1][1] = treeOrder[1] / 2

for t in 1...tw[0] {
    for w in 0...tw[1] {
        let j = w % 2 == 0 ? treeOrder[t] % 2 : treeOrder[t] / 2
        dp[t][w] = dp[t-1][0...w].max()! + j
    }
}
print(dp[tw[0]].max()!)