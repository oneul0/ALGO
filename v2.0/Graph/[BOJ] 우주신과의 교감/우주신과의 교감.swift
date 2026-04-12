import Foundation

let nm = readLine()!.split { $0 == " " }.map { Int(String($0))! },
    n = nm[0],
    m = nm[1]

var parent = Array(0..<n)
var nodes = [Pos]()
var edges = [Edge]()
var totalCost: Double = 0

for _ in 0..<n {
    let xy = readLine()!.split { $0 == " " }.map { Int(String($0))! },
        x = xy[0]-1,
        y = xy[1]-1
    nodes.append(Pos(x: x, y: y))
}

for _ in 0..<m {
    let gv = readLine()!.split { $0 == " " }.map { Int(String($0))! },
        g = gv[0]-1,
        v = gv[1]-1
    
    union(g, v)
}

for i in 0..<n {
    for j in 0..<n {
        if i == j { continue }
        edges.append(Edge(g: i, v: j, cost: distance(nodes[i], nodes[j])))
    }
}

edges.sort { $0.cost < $1.cost }

for edge in edges {
    if find(edge.g) == find(edge.v) {
        continue
    }
    union(edge.g, edge.v)
    totalCost += edge.cost
}

print(String(format: "%.2f", totalCost))

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

func distance(_ g: Pos, _ v: Pos) -> Double {
    let distX = (v.x - g.x)*(v.x - g.x)
    let distY = (v.y - g.y)*(v.y - g.y)

    return sqrt(Double(distX+distY))
}

struct Edge {
    let g: Int
    let v: Int
    let cost: Double
}

struct Pos {
    let x: Int
    let y: Int
}