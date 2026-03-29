import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let N = input[0], 
    M = input[1], 
    K = input[2]

var manager = Stacker(n: N, m: M)

for _ in 0..<K {
    let size = readLine()!.split(separator: " ").map { Int($0)! }
    var r = size[0], c = size[1]
    var sticker = [[Int]]()

    for _ in 0..<r {
        sticker.append(readLine()!.split(separator: " ").map { Int($0)! })
    }

    var isAttached = false
    for _ in 0..<4 {
        if isAttached { break }

        if N >= r && M >= c {
            for i in 0...(N - r) {
                if isAttached { break }
                for j in 0...(M - c) {
                    if manager.canAttach(r: r, c: c, sticker: sticker, row: i, col: j) {
                        manager.attach(r: r, c: c, sticker: sticker, row: i, col: j)
                        isAttached = true
                        break
                    }
                }
            }
        }

        if !isAttached {
            sticker = manager.rotate(sticker)
            let temp = r
            r = c
            c = temp
        }
    }
}

print(manager.countFilled())

struct Stacker {
    var notebook: [[Int]]
    let n: Int
    let m: Int

    init(n: Int, m: Int) {
        self.n = n
        self.m = m
        self.notebook = Array(repeating: Array(repeating: 0, count: m), count: n)
    }

    func rotate(_ sticker: [[Int]]) -> [[Int]] {
        let r = sticker.count
        let c = sticker[0].count
        var rotated = Array(repeating: Array(repeating: 0, count: r), count: c)

        for i in 0..<r {
            for j in 0..<c {
                rotated[j][r - 1 - i] = sticker[i][j]
            }
        }
        return rotated
    }

    func canAttach(r: Int, c: Int, sticker: [[Int]], row: Int, col: Int) -> Bool {
        for i in 0..<r {
            for j in 0..<c {
                if sticker[i][j] == 1 && notebook[row + i][col + j] == 1 {
                    return false
                }
            }
        }
        return true
    }

    mutating func attach(r: Int, c: Int, sticker: [[Int]], row: Int, col: Int) {
        for i in 0..<r {
            for j in 0..<c {
                if sticker[i][j] == 1 {
                    notebook[row + i][col + j] = 1
                }
            }
        }
    }

    func countFilled() -> Int {
        return notebook.flatMap { $0 }.filter { $0 == 1 }.count
    }
}