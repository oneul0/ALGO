import Foundation

let nm = readLine()!.split(separator: " ").map { Int($0)! }
let N = nm[0], M = nm[1]

var grid = [[Character]]()
var sr = 0, sc = 0

for i in 0..<N {
    let row = Array(readLine()!)
    grid.append(row)
    if let j = row.firstIndex(of: "@") {
        sr = i
        sc = j
    }
}

let dr = [-1, 1, 0, 0]
let dc = [0, 0, -1, 1]

var hitCount = Array(repeating: Array(repeating: 0, count: M), count: N)
var isCollapsed = Array(repeating: Array(repeating: false, count: M), count: N)
var aftershockQueue = [(Int, Int)]()
var qIdx = 0

func applyEarthquake(r: Int, c: Int) {
    if isCollapsed[r][c] { return }
    
    let type = grid[r][c]
    if type == "." || type == "@" { return }
    
    hitCount[r][c] += 1
    
    if type == "*" {
        isCollapsed[r][c] = true
        aftershockQueue.append((r, c))
    } else if type == "#" && hitCount[r][c] >= 2 {
        isCollapsed[r][c] = true
        aftershockQueue.append((r, c))
    }
}

for d in 0..<4 {
    for step in 1...2 {
        let nr = sr + dr[d] * step
        let nc = sc + dc[d] * step
        
        if nr < 0 || nr >= N || nc < 0 || nc >= M || grid[nr][nc] == "|" {
            break
        }
        applyEarthquake(r: nr, c: nc)
    }
}

while qIdx < aftershockQueue.count {
    let (r, c) = aftershockQueue[qIdx]
    qIdx += 1
    
    for d in 0..<4 {
        let nr = r + dr[d]
        let nc = c + dc[d]
        
        if nr < 0 || nr >= N || nc < 0 || nc >= M || grid[nr][nc] == "|" {
            continue
        }
        applyEarthquake(r: nr, c: nc)
    }
}

var broken = 0
var intact = 0

for r in 0..<N {
    for c in 0..<M {
        if grid[r][c] == "*" || grid[r][c] == "#" {
            if isCollapsed[r][c] {
                broken += 1
            } else {
                intact += 1
            }
        }
    }
}

print("\(broken) \(intact)")