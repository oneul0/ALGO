import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let N = input[0]
let M = input[1]
var fuel = input[2]

let dr = [-1, 0, 0, 1]
let dc = [0, -1, 1, 0]

var map = [[Int]]()
for _ in 0..<N {
    map.append(readLine()!.split(separator: " ").map { Int($0)! })
}

var taxiPos = readLine()!.split(separator: " ").map { Int($0)! - 1 }

var passengers = [Int: (Int, Int)]()
for _ in 0..<M {
    let p = readLine()!.split(separator: " ").map { Int($0)! - 1 }
    let key = p[0] * N + p[1]
    passengers[key] = (p[2], p[3])
}

func bfs(_ sr: Int, _ sc: Int) -> [[Int]] {
    var dist = Array(repeating: Array(repeating: -1, count: N), count: N)
    var q = [(sr, sc)]
    var idx = 0
    dist[sr][sc] = 0
    
    while idx < q.count {
        let (r, c) = q[idx]
        idx += 1
        
        for d in 0..<4 {
            let nr = r + dr[d]
            let nc = c + dc[d]
            if nr < 0 || nr >= N || nc < 0 || nc >= N { continue }
            if map[nr][nc] == 1 || dist[nr][nc] != -1 { continue }
            dist[nr][nc] = dist[r][c] + 1
            q.append((nr, nc))
        }
    }
    return dist
}

for _ in 0..<M {
    let distMap = bfs(taxiPos[0], taxiPos[1])
    
    var candidate: (Int, Int, Int)? = nil  // (거리, r, c)
    
    for (key, _) in passengers {
        let r = key / N
        let c = key % N
        let d = distMap[r][c]
        if d == -1 { continue }
        
        if candidate == nil ||
            d < candidate!.0 ||
            (d == candidate!.0 && (r < candidate!.1 || (r == candidate!.1 && c < candidate!.2))) {
            candidate = (d, r, c)
        }
    }
    
    if candidate == nil || fuel < candidate!.0 {
        print(-1)
        exit(0)
    }
    
    fuel -= candidate!.0
    taxiPos = [candidate!.1, candidate!.2]
    
    let dest = passengers.removeValue(forKey: candidate!.1 * N + candidate!.2)!
    let distToDest = bfs(taxiPos[0], taxiPos[1])
    let d = distToDest[dest.0][dest.1]
    
    if d == -1 || fuel < d {
        print(-1)
        exit(0)
    }
    
    fuel -= d
    fuel += d * 2
    taxiPos = [dest.0, dest.1]
}

print(fuel)