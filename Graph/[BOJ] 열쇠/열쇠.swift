import Foundation

let T = Int(readLine()!)!

let dx = [1, -1, 0, 0]
let dy = [0, 0, 1, -1]

for _ in 0..<T {
    let hw = readLine()!.split(separator: " ").map { Int($0)! }
    let h = hw[0]
    let w = hw[1]

    var map = Array(
        repeating: Array(repeating: Character("."), count: w + 2),
        count: h + 2
    )

    for i in 1...h {
        let line = Array(readLine()!)
        for j in 1...w {
            map[i][j] = line[j - 1]
        }
    }

    let keyInput = readLine()!
    var hasKey = Array(repeating: false, count: 26)

    if keyInput != "0" {
        for ch in keyInput {
            let idx = Int(ch.asciiValue! - Character("a").asciiValue!)
            hasKey[idx] = true
        }
    }

    var visited = Array(repeating: Array(repeating: false, count: w + 2), count: h + 2)
    var doorWaiting = Array(repeating: [(Int, Int)](), count: 26)

    var queue = [(Int, Int)]()
    var head = 0

    queue.append((0, 0))
    visited[0][0] = true

    var documents = 0

    while head < queue.count {
        let (x, y) = queue[head]
        head += 1

        for dir in 0..<4 {
            let nx = x + dx[dir]
            let ny = y + dy[dir]

            if nx < 0 || ny < 0 || nx > h + 1 || ny > w + 1 {
                continue
            }
            if visited[nx][ny] { continue }
            let cell = map[nx][ny]
            if cell == "*" { continue }
            // 문서
            if cell == "$" {
                documents += 1
                map[nx][ny] = "."
                queue.append((nx, ny))
            }
            // 열쇠
            else if cell >= "a" && cell <= "z" {
                let idx = Int(cell.asciiValue! - Character("a").asciiValue!)
                if !hasKey[idx] {
                    hasKey[idx] = true
                    for (dx, dy) in doorWaiting[idx] {
                        queue.append((dx, dy))
                    }
                    doorWaiting[idx].removeAll()
                }
                map[nx][ny] = "."
                queue.append((nx, ny))
            }
            // 문
            else if cell >= "A" && cell <= "Z" {
                let idx = Int(cell.asciiValue! - Character("A").asciiValue!)
                if hasKey[idx] {
                    map[nx][ny] = "."
                    queue.append((nx, ny))
                } else {
                    doorWaiting[idx].append((nx, ny))
                    continue
                }
            }
            // 빈 칸
            else {
                queue.append((nx, ny))
            }
            visited[nx][ny] = true
        }
    }
    print(documents)
}