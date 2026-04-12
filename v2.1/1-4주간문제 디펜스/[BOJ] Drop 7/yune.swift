import Foundation

let H = 7, W = 7

var board = Array(repeating: Array(repeating: 0, count: W), count: H)
for i in 0..<H {
    board[i] = readLine()!.split(separator: " ").map { Int($0)! }
}
let drop = Int(readLine()!)!

func applyGravity(_ b: inout [[Int]]) {
    for c in 0..<W {
        var stack: [Int] = []
        for r in stride(from: H - 1, through: 0, by: -1) {
            if b[r][c] != 0 {
                stack.append(b[r][c])
            }
        }
        for r in stride(from: H - 1, through: 0, by: -1) {
            b[r][c] = stack.isEmpty ? 0 : stack.removeFirst()
        }
    }
}

func explode(_ b: inout [[Int]]) -> Bool {
    var remove = Array(repeating: Array(repeating: false, count: W), count: H)

    for r in 0..<H {
        var c = 0
        while c < W {
            if b[r][c] == 0 { c += 1; continue }
            let start = c
            while c < W && b[r][c] != 0 { c += 1 }
            let len = c - start
            for x in start..<c {
                if b[r][x] == len {
                    remove[r][x] = true
                }
            }
        }
    }

    for c in 0..<W {
        var r = 0
        while r < H {
            if b[r][c] == 0 { r += 1; continue }
            let start = r
            while r < H && b[r][c] != 0 { r += 1 }
            let len = r - start
            for y in start..<r {
                if b[y][c] == len {
                    remove[y][c] = true
                }
            }
        }
    }

    var any = false
    for r in 0..<H {
        for c in 0..<W {
            if remove[r][c] {
                b[r][c] = 0
                any = true
            }
        }
    }
    return any
}

func simulate(column: Int) -> Int {
    var b = board

    var row = -1
    for r in stride(from: H - 1, through: 0, by: -1) {
        if b[r][column] == 0 {
            row = r
            break
        }
    }
    if row == -1 { return Int.max }

    b[row][column] = drop

    while true {
        if !explode(&b) { break }
        applyGravity(&b)
    }

    return b.flatMap { $0 }.filter { $0 != 0 }.count
}

var answer = Int.max
for c in 0..<W {
    answer = min(answer, simulate(column: c))
}

print(answer)