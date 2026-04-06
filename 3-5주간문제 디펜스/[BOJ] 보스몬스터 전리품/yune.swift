import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let M = input[0]
let N = input[1]
let P = input[2]

var grid = [[Character]]()
var players: [(id: Character, r: Int, c: Int)] = []
var boss = (0, 0)

for i in 0..<M {
    let row = Array(readLine()!)
    grid.append(row)
    
    for j in 0..<N {
        if row[j] == "B" {
            boss = (i, j)
        } else if row[j].isLowercase {
            players.append((row[j], i, j))
        }
    }
}

var dpsMap = [Character: Int]()
for _ in 0..<P {
    let input = readLine()!.split(separator: " ")
    let id = Character(String(input[0]))
    let dps = Int(input[1])!
    dpsMap[id] = dps
}

var hp = Int(readLine()!)!

var dist = Array(repeating: Array(repeating: -1, count: N), count: M)
var queue = [(Int, Int)]()
queue.append(boss)
dist[boss.0][boss.1] = 0

let dr = [1, -1, 0, 0]
let dc = [0, 0, 1, -1]

var idx = 0
while idx < queue.count {
    let (r, c) = queue[idx]
    idx += 1
    
    for d in 0..<4 {
        let nr = r + dr[d]
        let nc = c + dc[d]
        
        if nr < 0 || nr >= M || nc < 0 || nc >= N { continue }
        if grid[nr][nc] == "X" { continue }
        if dist[nr][nc] != -1 { continue }
        
        dist[nr][nc] = dist[r][c] + 1
        queue.append((nr, nc))
    }
}

var arr = [(time: Int, dps: Int)]()

for p in players {
    let d = dist[p.r][p.c]
    if d >= 0 {
        arr.append((d, dpsMap[p.id]!))
    }
}

arr.sort { $0.time < $1.time }

var totalDps = 0
var prevTime = 0
var count = 0

var i = 0

while i < arr.count {
    let currentTime = arr[i].time
    
    let delta = currentTime - prevTime
    hp -= totalDps * delta
    
    if hp <= 0 {
        break
    }
    
    // 동시 공격 처리
    while i < arr.count && arr[i].time == currentTime {
        totalDps += arr[i].dps
        count += 1
        i += 1
    }
    
    prevTime = currentTime
}

print(count)