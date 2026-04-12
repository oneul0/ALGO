import Foundation

let INF = Int(1e15)

let first = readLine()!.split(separator: " ").map { Int($0)! },
    V = first[0],
    M = first[1]

var graph = Array(repeating: [(Int, Int)](), count: V + 1)

for _ in 0..<M {
    let line = readLine()!.split(separator: " ").map { Int($0)! }
    let a = line[0]
    let b = line[1]
    let c = line[2]
    graph[a].append((b, c))
    graph[b].append((a, c))
}

let last = readLine()!.split(separator: " ").map { Int($0)! },
    J = last[0],
    S = last[1]

let distJ = dijkstra(J)
let distS = dijkstra(S)

var minSum = INF

for i in 1...V {
    if i == J || i == S { continue }
    minSum = min(minSum, distJ[i] + distS[i])
}

var candidates = [Int]()

for i in 1...V {
    if i == J || i == S { continue }
    if distJ[i] + distS[i] == minSum {
        if distJ[i] <= distS[i] {
            candidates.append(i)
        }
    }
}

if candidates.isEmpty {
    print(-1)
} else {
    candidates.sort {
        if distJ[$0] == distJ[$1] {
            return $0 < $1
        }
        return distJ[$0] < distJ[$1]
    }
    print(candidates[0])
}

func dijkstra(_ start: Int) -> [Int] {
    var dist = Array(repeating: INF, count: V + 1)
    dist[start] = 0
    
    var pq = [(Int, Int)]()
    pq.append((0, start))
    
    while !pq.isEmpty {
        pq.sort { $0.0 < $1.0 }
        let (d, node) = pq.removeFirst()
        
        if d > dist[node] { continue }
        
        for (next, cost) in graph[node] {
            let nd = d + cost
            if nd < dist[next] {
                dist[next] = nd
                pq.append((nd, next))
            }
        }
    }
    
    return dist
}