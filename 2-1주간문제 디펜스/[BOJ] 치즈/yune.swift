import Foundation

let hw = readLine()!.split(separator: " ").map { Int($0)! },
    h = hw[0],
    w = hw[1]

var board = Array(repeating: Array(repeating: 0, count: w), count: h)

for i in 0..<h {
    board[i] = readLine()!.split(separator: " ").map { Int($0)! }
}

let dx = [1, -1, 0, 0]
let dy = [0, 0, 1, -1]

func bfsMelt() -> Int {
    var visited = Array(repeating: Array(repeating: false, count: w), count: h)
    var queue = [(Int, Int)]()
    var idx = 0

    queue.append((0, 0))
    visited[0][0] = true

    var meltList = [(Int, Int)]()

    while idx < queue.count {
        let (x, y) = queue[idx]
        idx += 1

        for d in 0..<4 {
            let nx = x + dx[d]
            let ny = y + dy[d]

            if nx < 0 || ny < 0 || nx >= h || ny >= w || visited[nx][ny] {
                continue
            }

            visited[nx][ny] = true

            if board[nx][ny] == 1 {
                meltList.append((nx, ny))
            } else {
                queue.append((nx, ny))
            }
        }
    }

    for (x, y) in meltList {
        board[x][y] = 0
    }

    return meltList.count
}

var time = 0
var lastCheese = 0

while true {
    let melted = bfsMelt()
    if melted == 0 {
        break
    }
    lastCheese = melted
    time += 1
}

print(time)
print(lastCheese)
