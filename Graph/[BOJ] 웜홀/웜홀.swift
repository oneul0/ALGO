import Foundation

let tc = Int(readLine()!)!

for _ in 0..<tc {
    let input = readLine()!.split(separator: " ").map { Int($0)! }
    let N = input[0]
    let M = input[1]
    let W = input[2]

    var edges: [(Int, Int, Int)] = []

    for _ in 0..<M {
        let e = readLine()!.split(separator: " ").map { Int($0)! }
        let s = e[0], v = e[1], t = e[2]
        edges.append((s, v, t))
        edges.append((v, s, t))
    }

    for _ in 0..<W {
        let e = readLine()!.split(separator: " ").map { Int($0)! }
        let s = e[0], v = e[1], t = e[2]
        edges.append((s, v, -t))
    }

    var dist = Array(repeating: 0, count: N + 1)

    var hasNegativeCycle = false

    for i in 1...N {
        var updated = false
        for (u, v, w) in edges {
            if dist[v] > dist[u] + w {
                dist[v] = dist[u] + w
                updated = true
                if i == N {
                    hasNegativeCycle = true
                    break
                }
            }
        }
        if hasNegativeCycle { break }
        if !updated { break }
    }

    print(hasNegativeCycle ? "YES" : "NO")
}