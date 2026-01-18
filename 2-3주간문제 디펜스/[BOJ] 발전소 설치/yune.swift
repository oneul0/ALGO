import Foundation

let first = readLine()!.split(separator: " ").map { Int($0)! }
let N = first[0]
let W = first[1]

let M = Double(readLine()!)!

var pos = [(Double, Double)]()
for _ in 0..<N {
    let p = readLine()!.split(separator: " ").map { Double($0)! }
    pos.append((p[0], p[1]))
}

var adj = Array(repeating: [(Int, Double)](), count: N)

for _ in 0..<W {
    let e = readLine()!.split(separator: " ").map { Int($0)! - 1 }
    adj[e[0]].append((e[1], 0.0))
    adj[e[1]].append((e[0], 0.0))
}

let INF = Double.greatestFiniteMagnitude
var dist = Array(repeating: INF, count: N)
dist[0] = 0.0

var pq = [(Double, Int)]()
pq.append((0.0, 0))

while !pq.isEmpty {
    pq.sort { $0.0 < $1.0 }
    let (curCost, u) = pq.removeFirst()

    if curCost > dist[u] { continue }
    if u == N - 1 { break }

    for (v, cost) in adj[u] {
        let nd = curCost + cost
        if nd < dist[v] {
            dist[v] = nd
            pq.append((nd, v))
        }
    }

    for v in 0..<N {
        if v == u { continue }
        let dx = pos[u].0 - pos[v].0
        let dy = pos[u].1 - pos[v].1
        let d = sqrt(dx*dx + dy*dy)

        if d <= M {
            let nd = curCost + d
            if nd < dist[v] {
                dist[v] = nd
                pq.append((nd, v))
            }
        }
    }
}

let result = Int(dist[N - 1] * 1000.0)
print(result)