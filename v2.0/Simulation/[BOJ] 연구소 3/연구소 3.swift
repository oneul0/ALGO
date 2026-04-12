import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let n = input[0], m = input[1]

var lab = [[Int]]()
for _ in 0..<n {
    lab.append(readLine()!.split(separator: " ").map { Int($0)! })
}

var viruses = [(Int, Int)]()
for i in 0..<n {
    for j in 0..<n {
        if lab[i][j] == 2 {
            viruses.append((i, j))
        }
    }
}

var emptyCount = 0
for i in 0..<n {
    for j in 0..<n {
        if lab[i][j] == 0 {
            emptyCount += 1
        }
    }
}

let dx = [-1, 1, 0, 0]
let dy = [0, 0, -1, 1]

var answer = Int.max

// M개 활성화 시킨 조합
func combinations<T>(_ arr: [T], _ k: Int) -> [[T]] {
    if k == 0 { return [[]] }
    if arr.isEmpty { return [] }
    let head = arr[0]
    let subcomb = combinations(Array(arr.dropFirst()), k - 1).map { [head] + $0 }
    return subcomb + combinations(Array(arr.dropFirst()), k)
}

func bfs(_ activeViruses: [(Int, Int)]) {
    var visited = Array(repeating: Array(repeating: -1, count: n), count: n)
    var q = [(Int, Int)]()
    var head = 0
    
    for (x, y) in activeViruses {
        visited[x][y] = 0
        q.append((x, y))
    }
    
    var filled = 0
    var time = 0
    
    while head < q.count {
        let (x, y) = q[head]
        head += 1
        
        for dir in 0..<4 {
            let nx = x + dx[dir]
            let ny = y + dy[dir]
            
            if nx < 0 || nx >= n || ny < 0 || ny >= n { continue }
            if lab[nx][ny] == 1 { continue }
            if visited[nx][ny] != -1 { continue }
            
            visited[nx][ny] = visited[x][y] + 1
            q.append((nx, ny))
            
            if lab[nx][ny] == 0 {
                filled += 1
                time = visited[nx][ny]
            }
        }
    }
    
    if filled == emptyCount {
        answer = min(answer, time)
    }
}

let comb = combinations(viruses, m)
for active in comb {
    bfs(active)
}

print(answer == Int.max ? -1 : answer)
