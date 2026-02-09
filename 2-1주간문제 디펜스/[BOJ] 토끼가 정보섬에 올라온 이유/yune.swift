import Foundation

let nm = readLine()!.split { $0 == " " }.map { Int(String($0))! },
    n = nm[0],
    m = nm[1]

let dr = [1, 0, -1]
let dc = [1, 1, 1]

//'.'은 빈 공간을, 
// '#'은 벽을, 
// 'R'은 토끼를, 
// 'C'는 당근을, 
// 'O'는 정보섬 쪽문을 나타낸다. 
// 'R'은 반드시 하나만 주어지며, 'O'는 없거나 여러 개일 수 있다.

var grid = [[Character]]()

var startR = 0
var startC = 0

for r in 0..<n {
    let line = Array(readLine()!)
    for c in 0..<m {
        if line[c] == "R" {
            startR = r
            startC = c
        }
    }
    grid.append(line)
}

var dp = [[Int]](repeating: [Int](repeating: -1, count: m), count: n)
dp[startR][startC] = 0

var answer = -1

for c in startC..<m {
    for r in 0..<n {
        guard dp[r][c] != -1 else {continue }
        for i in 0..<3 {
            let nr = r + dr[i]
            let nc = c + dc[i]
            guard isInbound(nr, nc) else { continue }
            guard grid[nr][nc] != "#" else { continue }

            let bonus = (grid[nr][nc] == "C" ? 1 : 0)

            dp[nr][nc] = max(dp[nr][nc], dp[r][c] + bonus)
            
            if grid[nr][nc] == "O" {
                answer = max(answer, dp[nr][nc])
            }
        }
    }
}

print(answer)

func isInbound(_ r: Int, _ c: Int) -> Bool {
    guard (0..<n) ~= r else { return false }
    return (0..<m) ~= c
}