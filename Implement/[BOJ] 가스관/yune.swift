import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let R = input[0], C = input[1]

var board = Array(repeating: Array(repeating: Character("."), count: C), count: R)
var notConnected = Array(
    repeating: Array(repeating: [Bool](repeating: false, count: 4), count: C),
    count: R
)

let blocks: [Character] = ["|", "-", "+", "1", "2", "3", "4"]

let dr = [-1, 0, 1, 0]
let dc = [0, -1, 0, 1]

let adjs: [[Bool]] = [
    [true, false, true, false],   // |
    [false, true, false, true],   // -
    [true, true, true, true],     // +
    [false, false, true, true],   // 1
    [true, false, false, true],   // 2
    [true, true, false, false],   // 3
    [false, true, true, false]    // 4
]

func getAdjacent(_ r: Int, _ c: Int) -> [Bool] {
    switch board[r][c] {
    case "|": return adjs[0]
    case "-": return adjs[1]
    case "+": return adjs[2]
    case "1": return adjs[3]
    case "2": return adjs[4]
    case "3": return adjs[5]
    case "4": return adjs[6]
    default: return []
    }
}

func checkConnected(_ r: Int, _ c: Int) {
    let adj = getAdjacent(r, c)

    for i in 0..<4 {
        if !adj[i] { continue }

        let nr = r + dr[i]
        let nc = c + dc[i]

        if nr < 0 || nr >= R || nc < 0 || nc >= C { continue }

        if board[nr][nc] == "." {
            notConnected[nr][nc][(i + 2) % 4] = true
        }
    }
}

func calcBlock(_ r: Int, _ c: Int) -> Character {
    for i in 0..<7 {
        if notConnected[r][c] == adjs[i] {
            return blocks[i]
        }
    }
    return "?"
}

for i in 0..<R {
    let line = Array(readLine()!)
    board[i] = line
}

for i in 0..<R {
    for j in 0..<C {
        let ch = board[i][j]
        if ch == "." || ch == "M" || ch == "Z" { continue }
        checkConnected(i, j)
    }
}

for i in 0..<R {
    for j in 0..<C {
        for k in 0..<4 {
            if notConnected[i][j][k] {
                let block = calcBlock(i, j)
                print(i + 1, j + 1, block)
                exit(0)
            }
        }
    }
}
