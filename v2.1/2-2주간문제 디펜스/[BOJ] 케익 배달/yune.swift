import Foundation

// 상하좌우중
let dx = [-1, 1, 0, 0, 0]
let dy = [0, 0, -1, 1, 0]

let N = Int(readLine()!)!
let startPoint = readLine()!.split { $0 == " " }.map { Int(String($0))! }
var pointList = [[Int]]()

for _ in 0..<N {
    let xy = readLine()!.split { $0 == " " }.map { Int(String($0))! }
    pointList.append(xy)
}

var dp = [[Int]](repeating: [Int](repeating: Int.max, count: 5), count: N)
for i in 0..<5 {
    let nx = pointList[0][0] + dx[i]
    let ny = pointList[0][1] + dy[i]
    dp[0][i] = abs(nx - startPoint[0]) + abs(ny - startPoint[1])
}

for n in 1..<N {
    let customerPos = pointList[n]
    let prevPoint = pointList[n-1]

    for i in 0..<5 {
        let prevX = prevPoint[0] + dx[i]
        let prevY = prevPoint[1] + dy[i]

        for j in 0..<5 {
            let currentX = customerPos[0] + dx[j]
            let currentY = customerPos[1] + dy[j]
            let cost = dp[n-1][i] + abs(currentX - prevX) + abs(currentY - prevY)

            dp[n][j] = min(dp[n][j], cost)
        }
    }
}

print(dp[N-1].min()!)

func abs(_ x: Int) -> Int {
    return x > 0 ? x : -x
}