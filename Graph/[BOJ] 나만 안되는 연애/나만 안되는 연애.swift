import Foundation

let nm = readLine()!.split { $0 == " " }.map { Int(String($0))! },
    n = nm[0],
    m = nm[1]

let genders = readLine()!.split { $0 == " " }.map { String($0) }

var edges = [Edge]()

for _ in 0..<m {
    let uvd = readLine()!.split { $0 == " " }.map { Int(String($0))! },
        u = uvd[0]-1,
        v = uvd[1]-1,
        d = uvd[2]
    edges.append(Edge(g: u, v: v, cost: d))
}

edges.sort { $0.cost < $1.cost }

var parent = Array(0..<n)

var totalCost = 0

for edge in edges {
    if find(edge.g) == find(edge.v) { continue }
    if genders[edge.g] == "M" && genders[edge.v] == "M" { continue }
    if genders[edge.g] == "W" && genders[edge.v] == "W" { continue }

    union(edge.g, edge.v)

    totalCost += edge.cost
}

let isAllConnected = Set(parent.map { find($0) }).count == 1
print(isAllConnected ? totalCost : -1)

struct Edge {
    let g: Int
    let v: Int
    let cost: Int
}

func union(_ g: Int, _ v: Int) {
    var g = parent[g]
    var v = parent[v]

    if g == v { 
        return
    }

    if g < v {
        swap(&g, &v)
    }

    parent[v] = g
}

func find(_ g: Int) -> Int {
    if parent[g] == g { return g }
    parent[g] = find(parent[g])

    return parent[g]
}