//14442번 벽 부수고 이동하기2

import Foundation

func bfs() -> Int {
    let dx = [1,-1,0,0]
    let dy = [0,0,1,-1]
    
    var visit = Array(repeating: Array(repeating: k+1, count: m), count: n)
    visit[0][0] = 0
    
    var queue = [(0,0,1)]
    var idx = 0
    
    while queue.count > idx {
        let (x,y,dist) = queue[idx]
        idx += 1

        if x == n-1 && y == m-1 { return dist }
        
        for i in 0..<4 {
            let nx = x + dx[i]
            let ny = y + dy[i]
            
            guard (0..<n) ~= nx && (0..<m) ~= ny else { continue }
            
            let wallCount = graph[nx][ny] + visit[x][y]

            if wallCount < visit[nx][ny] && wallCount <= k {
                visit[nx][ny] = wallCount
                queue.append((nx,ny,dist + 1))
            }
        }
    }
    
    return -1
}

let t = readLine()!.components(separatedBy: " ").map{Int($0)!}
let (n, m, k) = (t[0], t[1], t[2])

var graph = [[Int]]()
(0..<n).forEach { _ in graph.append(Array(readLine()!).map{Int(String($0))!}) }

print(bfs())
