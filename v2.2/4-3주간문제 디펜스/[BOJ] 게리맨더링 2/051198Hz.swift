import Foundation

let n = Int(readLine()!)!
var board = [[Int]]()

for _ in 0..<n {
    board.append(readLine()!.split(separator: " ").map { Int($0)! })
}

let total = board.flatMap { $0 }.reduce(0, +)

func boardSum(_ x: Int, _ y: Int, _ d1: Int, _ d2: Int) -> Int {
    var newBoard = Array(repeating: Array(repeating: 0, count: n), count: n)

    var b1 = 0, b2 = 0, b3 = 0, b4 = 0
    var bMin = Int.max
    var bMax = 0

    for i in 0...d1 {
        newBoard[x + i - 1][y - i - 1] = 5
    }
    for i in 0...d2 {
        newBoard[x + i - 1][y + i - 1] = 5
    }
    for i in 0...d2 {
        newBoard[x + d1 + i - 1][y - d1 + i - 1] = 5
    }
    for i in 0...d1 {
        newBoard[x + d2 + i - 1][y + d2 - i - 1] = 5
    }

    for i in 1..<(x + d1) {
        for j in 1...(y) {
            if newBoard[i - 1][j - 1] == 5 { break }
            newBoard[i - 1][j - 1] = 1
            b1 += board[i - 1][j - 1]
        }
    }
    bMin = min(bMin, b1)
    bMax = max(bMax, b1)

    for i in 1...(x + d2) {
        var j = n
        while j > y {
            if newBoard[i - 1][j - 1] == 5 { break }
            newBoard[i - 1][j - 1] = 2
            b2 += board[i - 1][j - 1]
            j -= 1
        }
    }
    bMin = min(bMin, b2)
    bMax = max(bMax, b2)

    for i in (x + d1)...n {
        for j in 1..<(y - d1 + d2) {
            if newBoard[i - 1][j - 1] == 5 { break }
            newBoard[i - 1][j - 1] = 3
            b3 += board[i - 1][j - 1]
        }
    }
    bMin = min(bMin, b3)
    bMax = max(bMax, b3)

    for i in (x + d2 + 1...n){
        var j = n
        while j >= (y - d1 + d2) {
            if newBoard[i - 1][j - 1] == 5 { break }
            newBoard[i - 1][j - 1] = 4
            b4 += board[i - 1][j - 1]
            j -= 1
        }
    }
    bMin = min(bMin, b4)
    bMax = max(bMax, b4)

    let b5 = total - b1 - b2 - b3 - b4
    bMin = min(bMin, b5)
    bMax = max(bMax, b5)

    return bMax - bMin
}

var answer = Int.max

for d1 in 1..<n {
    for d2 in 1..<n {
        for x in stride(from: 1, through: n - d1 - d2, by: 1) {
            for y in (d1 + 1)...(n - d2) {
                let result = boardSum(x, y, d1, d2)
                answer = min(answer, result)
            }
        }
    }
}

print(answer)