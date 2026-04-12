import Foundation

let dy = [-1, 1, 0, 0]
let dx = [0, 0, -1, 1]

struct Edge {
    let from: Int, to: Int, length: Int
}

func labelIslands(_ map: inout [[Int]]) -> Int {
    let N = map.count
    let M = map[0].count
    var islandID = 1
    
    for y in 0..<N {
        for x in 0..<M {
            if map[y][x] == 1 {
                islandID += 1
                var queue = [(y, x)]
                map[y][x] = islandID
                var idx = 0
                while idx < queue.count {
                    let (cy, cx) = queue[idx]; idx += 1
                    for dir in 0..<4 {
                        let ny = cy + dy[dir], nx = cx + dx[dir]
                        if ny >= 0, ny < N, nx >= 0, nx < M, map[ny][nx] == 1 {
                            map[ny][nx] = islandID
                            queue.append((ny, nx))
                        }
                    }
                }
            }
        }
    }
    return islandID - 1
}

func findBridges(_ map: [[Int]]) -> [Edge] {
    let N = map.count, M = map[0].count
    var edges: [Edge] = []
    
    for y in 0..<N {
        for x in 0..<M {
            let id = map[y][x]
            if id <= 1 { continue }
            
            for dir in 0..<4 {
                var ny = y + dy[dir], nx = x + dx[dir]
                var length = 0
                
                while ny >= 0, ny < N, nx >= 0, nx < M {
                    if map[ny][nx] == id { break }
                    if map[ny][nx] > 1 {
                        if length >= 2 {
                            let from = id, to = map[ny][nx]
                            edges.append(Edge(from: from, to: to, length: length))
                        }
                        break
                    }
                    length += 1
                    ny += dy[dir]
                    nx += dx[dir]
                }
            }
        }
    }
    return edges
}

func kruskal(_ edges: [Edge], _ islandCount: Int) -> Int {
    var parent = Array(0...(islandCount+1))
    
    func find(_ u: Int) -> Int {
        if parent[u] != u { parent[u] = find(parent[u]) }
        return parent[u]
    }
    
    func union(_ u: Int, _ v: Int) {
        let pu = find(u), pv = find(v)
        if pu != pv { parent[pu] = pv }
    }
    
    let sortedEdges = edges.sorted { $0.length < $1.length }
    var total = 0, connected = 0
    
    for e in sortedEdges {
        if find(e.from) != find(e.to) {
            union(e.from, e.to)
            total += e.length
            connected += 1
        }
    }
    
    return connected == islandCount - 1 ? total : -1
}

let nm = readLine()!.split(separator: " ").map { Int($0)! }
let N = nm[0], M = nm[1]
var map: [[Int]] = []
for _ in 0..<N {
    guard let line = readLine() else { fatalError("input error") }
    let row = line.split(separator: " ").map { Int($0)! }
    map.append(row)
}

let islandCount = labelIslands(&map)
let edges = findBridges(map)
let result = kruskal(edges, islandCount)
print(result)