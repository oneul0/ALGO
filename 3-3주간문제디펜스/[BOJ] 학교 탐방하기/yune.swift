import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let N = input[0], M = input[1]

var edges = [(Int, Int, Int)]()

for _ in 0...M {
    let line = readLine()!.split(separator: " ").map { Int($0)! },
        a = line[0],
        b = line[1],
        c = line[2]
    
    // 오르막: 1, 내리막: 0
    edges.append((a, b, c == 0 ? 1 : 0))
}

func find(_ x: Int, _ parent: inout [Int]) -> Int {
    if parent[x] != x {
        parent[x] = find(parent[x], &parent)
    }
    return parent[x]
}

func union(_ a: Int, _ b: Int, _ parent: inout [Int]) -> Bool {
    let pa = find(a, &parent)
    let pb = find(b, &parent)
    
    if pa == pb { return false }
    parent[pb] = pa
    return true
}

func kruskal(_ edges: [(Int, Int, Int)], _ ascending: Bool) -> Int {
    var parent = Array(0...N)
    var sortedEdges = edges
    
    if ascending {
        sortedEdges.sort { $0.2 < $1.2 } // 최소 오르막
    } else {
        sortedEdges.sort { $0.2 > $1.2 } // 최대 오르막
    }
    
    var uphillCount = 0
    
    for (a, b, cost) in sortedEdges {
        if union(a, b, &parent) {
            uphillCount += cost
        }
    }
    
    return uphillCount
}

let minUp = kruskal(edges, true)
let maxUp = kruskal(edges, false)

print(maxUp * maxUp - minUp * minUp)