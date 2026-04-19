import Foundation

let dx = [-1, 0, 1, 0]
let dy = [0, 1, 0, -1]

let nm = readLine()!.split(separator: " ").map { Int($0)! }
let n = nm[0], m = nm[1]

var grid = [[Int]]()
var ari = (0, 0)
var boss = (0, 0)

for i in 0..<n {
    let row = readLine()!.split(separator: " ").map { Int($0)! }
    for j in 0..<m {
        if row[j] == 2 { ari = (i, j) }
        if row[j] == 3 { boss = (i, j) }
    }
    grid.append(row)
}

let stats = readLine()!.split(separator: " ").map { Int($0)! }
var ariHP = stats[0]
let D = stats[1]
var bossHP = stats[2]
let E = stats[3]

var bossDir = 0
for d in 0..<4 {
    let nx = boss.0 + dx[d]
    let ny = boss.1 + dy[d]
    if nx == ari.0 && ny == ari.1 {
        bossDir = d
    }
}

var ariDir = bossDir

while true {
    
    bossHP -= D
    if bossHP <= 0 {
        print("VICTORY!")
        break
    }
    
    let prevAri = ari
    var moved = false
    var moveDir = ariDir
    
    for _ in 0..<4 {
        let nx = ari.0 + dx[ariDir]
        let ny = ari.1 + dy[ariDir]
        
        if inRange(nx, ny, n, m)
            && grid[nx][ny] != 1
            && !(nx == boss.0 && ny == boss.1) {
            
            ari = (nx, ny)
            moved = true
            moveDir = ariDir
            break
            
        } else {
            ariDir = rotateRight(ariDir)
            ariHP -= 1
            if ariHP <= 0 {
                print("CAVELIFE...")
                exit(0)
            }
        }
    }
    
    if let spike = spiralSearch(start: boss, dir: bossDir, grid: grid) {
        let dmg = monsterDamage(
            start: spike,
            target: ari,
            grid: grid,
            boss: boss,
            E: E
        )
        
        ariHP -= dmg
        if ariHP <= 0 {
            print("CAVELIFE...")
            break
        }
    }
    
    if moved {
        boss = prevAri
        bossDir = moveDir
    }
}

func inRange(_ x: Int, _ y: Int, _ n: Int, _ m: Int) -> Bool {
    return x >= 0 && x < n && y >= 0 && y < m
}

func rotateRight(_ d: Int) -> Int {
    return (d + 1) % 4
}

func spiralSearch(
    start: (Int, Int),
    dir: Int,
    grid: [[Int]]
) -> (Int, Int)? {
    
    let n = grid.count
    let m = grid[0].count
    
    var x = start.0
    var y = start.1
    
    var length = 1
    var d = dir
    
    let total = n * m
    var checked = 1 // 시작 위치 포함
    
    if grid[x][y] == 1 {
        return (x, y)
    }
    
    while true {
        for _ in 0..<2 {
            for _ in 0..<length {
                x += dx[d]
                y += dy[d]
                
                if inRange(x, y, n, m) {
                    checked += 1
                    
                    if grid[x][y] == 1 {
                        return (x, y)
                    }
                    
                    if checked == total {
                        return nil
                    }
                }
            }
            d = (d + 1) % 4
        }
        length += 1
    }
}

func monsterDamage(
    start: (Int, Int),
    target: (Int, Int),
    grid: [[Int]],
    boss: (Int, Int),
    E: Int
) -> Int {
    
    let n = grid.count
    let m = grid[0].count
    
    var visited = Array(repeating: Array(repeating: false, count: m), count: n)
    var q: [(Int, Int, Int)] = []
    
    visited[start.0][start.1] = true
    q.append((start.0, start.1, 0))
    
    var idx = 0
    
    while idx < q.count {
        let (x, y, d) = q[idx]
        idx += 1
        
        if x == target.0 && y == target.1 {
            if d <= E {
                return E - d
            } else {
                return 0
            }
        }
        
        for k in 0..<4 {
            let nx = x + dx[k]
            let ny = y + dy[k]
            
            if !inRange(nx, ny, n, m) { continue }
            if visited[nx][ny] { continue }
            
            if grid[nx][ny] == 1 { continue }
            if nx == boss.0 && ny == boss.1 { continue }
            
            visited[nx][ny] = true
            q.append((nx, ny, d + 1))
        }
    }
    
    return 0
}