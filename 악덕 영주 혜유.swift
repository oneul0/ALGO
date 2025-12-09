import Foundation

let nk = readLine()!.split { $0 == " " }.map { Int(String($0))! },
    n = nk[0],
    k = nk[1]

var parent = Array(0..<n)
var mst = [[Edge]](repeating: [Edge](), count: n)
var edges = [Edge]()
var totalCost = 0

for _ in 0..<k {
    let abc = readLine()!.split { $0 == " " }.map { Int(String($0))! },
        a = abc[0],
        b = abc[1],
        c = abc[2]
    edges.append(Edge(g: a, v: b, cost: c))
}

edges.sort { $0.cost < $1.cost }

for edge in edges {
    if find(edge.g) == find(edge.v) {
        continue
    }
    union(edge.g, edge.v)

    totalCost += edge.cost
    mst[edge.g].append(Edge(g: edge.g, v: edge.v, cost: edge.cost))
    mst[edge.v].append(Edge(g: edge.v, v: edge.g, cost: edge.cost))
}

let e1 = farPath(0, mst)
let e2 = farPath(e1.v, mst)

print(totalCost)
print(e2.cost)

func farPath(_ g: Int, _ mst: [[Edge]]) -> Edge {
    var stack = mst[g]
    var isVisited = [Bool](repeating: false, count: mst.count)
    isVisited[g] = true
    var farNode = g
    var totalCost = Int.min

    while !stack.isEmpty {
        let path = stack.popLast()!
        let node = path.v
        if isVisited[node] {
            continue
        }
        isVisited[node] = true
        if totalCost < path.cost {
            totalCost = path.cost
            farNode = path.v
        }

        for edge in mst[node] {
            let v = edge.v
            if v == path.g {
                continue
            }
            stack.append(Edge(g: node, v: v, cost: path.cost + edge.cost))
        }
    }

    return Edge(g: g, v: farNode, cost: totalCost)
}

func union(_ g: Int, _ v: Int) {
    var g = find(g)
    var v = find(v)

    if g == v  {
        return
    }

    if v < g {
        swap(&g, &v)
    }

    parent[v] = g
}

func find(_ g: Int) -> Int {
    if parent[g] == g { return g }
    parent[g] = find(parent[g])
    
    return parent[g]
}

struct Edge {
    let g: Int
    let v: Int
    let cost: Int
}