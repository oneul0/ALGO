import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let R = input[0]
let C = input[1]

var board = [[Character]]()
var player = (0, 0)
var robots = [(Int, Int)]()

for i in 0..<R {
    let row = Array(readLine()!)
    board.append(row)
    
    for j in 0..<C {
        if row[j] == "I" {
            player = (i, j)
        } else if row[j] == "R" {
            robots.append((i, j))
        }
    }
}

let moves = Array(readLine()!).map { Int(String($0))! }

let dr = [0, 1, 1, 1, 0, 0, 0, -1, -1, -1]
let dc = [0, -1, 0, 1, -1, 0, 1, -1, 0, 1]

for turn in 0..<moves.count {
    let dir = moves[turn]
    
    player.0 += dr[dir]
    player.1 += dc[dir]
    
    // 충돌 체크
    for r in robots {
        if r.0 == player.0 && r.1 == player.1 {
            print("kraj \(turn + 1)")
            exit(0)
        }
    }
    
    var newRobots = [(Int, Int)]()
    var count = Array(
        repeating: Array(repeating: 0, count: C),
        count: R
    )
    
    for r in robots {
        let nr = r.0 + sign(player.0 - r.0)
        let nc = r.1 + sign(player.1 - r.1)
        
        // 겹치면
        if nr == player.0 && nc == player.1 {
            print("kraj \(turn + 1)")
            exit(0)
        }
        
        newRobots.append((nr, nc))
        count[nr][nc] += 1
    }
    
    // BOOOOOOOM
    robots = []
    for r in newRobots {
        if count[r.0][r.1] == 1 {
            robots.append(r)
        }
    }
}

var result = Array(
    repeating: Array(repeating: ".", count: C),
    count: R
)

result[player.0][player.1] = "I"

for r in robots {
    result[r.0][r.1] = "R"
}

for i in 0..<R {
    print(result[i].joined())
}


func sign(_ x: Int) -> Int {
    if x > 0 { return 1 }
    if x < 0 { return -1 }
    return 0
}