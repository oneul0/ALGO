import Foundation

let n = Int(readLine()!)!

var grid = [[Int]]()
for _ in 0..<n {
    let line = readLine()!.map { Int(String($0))! }
    grid.append(line)
}

let blackRoom = 0
let whiteRoom = 1

let dr = [0, 0, -1, 1]
let dc = [-1, 1, 0, 0]

func bfs() -> Int {
    var changeCount = [[Int]](repeating: [Int](repeating: Int.max, count: n), count: n)
    var queue = [Info(r: 0, c: 0, changeCount: 0)]
    var index = 0

    while queue.count > index {
        let currentInfo = queue[index]

        for i in 0..<4 {
            let nr = currentInfo.r + dr[i]
            let nc = currentInfo.c + dc[i]

            guard isInbound(nr, nc) else { continue }

            let isBlackRoom = grid[nr][nc] == blackRoom ? 1 : 0

            guard changeCount[nr][nc] > currentInfo.changeCount + isBlackRoom else { continue }
            // 검은방이고, 그 방을 흰방으로 바꾼 횟수가 현재보다 많으면 큐에 넣지 않는다.
            changeCount[nr][nc] = currentInfo.changeCount + isBlackRoom
            queue.append(Info(r: nr, c: nc, changeCount: currentInfo.changeCount + isBlackRoom))
            // 흰방이더라도 문 연 카운트가 더 많으면 방문할 필요가 없나?
        }
        index += 1
    }
    return changeCount[n-1][n-1]
}

let answer = bfs()

print(answer)

func isInbound(_ r: Int, _ c: Int) -> Bool {
    guard (0..<n) ~= r else { return false }
    return (0..<n) ~= c
}

struct Info {
    let r: Int
    let c: Int
    var changeCount: Int
}