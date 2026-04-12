import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let N = input[0], M = input[1]

var board = [[UInt8]]()
var start = (0, 0), target = (0, 0)

for i in 0..<N {
    let line = Array(readLine()!)
    var row = [UInt8]()
    for j in 0..<M {
        let char = line[j]
        if char == "D" {
            start = (i, j); row.append(46)
        }  // '.'의 ASCII
        else if char == "R" {
            target = (i, j); row.append(82)
        }  // 'R'의 ASCII
        else {
            row.append(char.asciiValue!)
        }
    }
    board.append(row)
}

var visited = Array(
    repeating: Array(repeating: Array(repeating: false, count: 64), count: M), count: N)

var queue: [(r: Int, c: Int, dice: Dice, dist: Int)] = []
let initialDice = Dice(s: [0, 1, 1, 1, 1, 1])

queue.append((start.0, start.1, initialDice, 0))
visited[start.0][start.1][initialDice.id] = true

let dr = [-1, 1, 0, 0], dc = [0, 0, -1, 1]
var head = 0

while head < queue.count {
    let curr = queue[head]
    head += 1

    if curr.r == target.0 && curr.c == target.1 {
        print(curr.dist)
        exit(0)
    }

    for i in 0..<4 {
        let nr = curr.r + dr[i], nc = curr.c + dc[i]

        if board[nr][nc] == 35 { continue } 

        let nextDice = curr.dice.roll(i)

        if nr == target.0 && nc == target.1 {
            if nextDice.s[0] == 1 { continue }
        }

        let nid = nextDice.id
        if !visited[nr][nc][nid] {
            visited[nr][nc][nid] = true
            queue.append((nr, nc, nextDice, curr.dist + 1))
        }
    }
}
print("-1")

struct Dice {
    // [바닥, 천장, 앞, 뒤, 왼쪽, 오른쪽]
    var s: [Int]
    // 비트마스킹으로 주사위 천장 방향을 인덱싱, 방문체크에 사용
    var id: Int {
        var res = 0
        for i in 0..<6 {
            if s[i] == 1 { res |= (1 << i) }
        }
        return res
    }

    func roll(_ dir: Int) -> Dice {
        switch dir {
        case 0: return Dice(s: [s[2], s[3], s[1], s[0], s[4], s[5]])  // 상
        case 1: return Dice(s: [s[3], s[2], s[0], s[1], s[4], s[5]])  // 하
        case 2: return Dice(s: [s[4], s[5], s[2], s[3], s[1], s[0]])  // 좌
        case 3: return Dice(s: [s[5], s[4], s[2], s[3], s[0], s[1]])  // 우
        default: return self
        }
    }
}