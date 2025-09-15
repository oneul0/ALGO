struct Edge {
    let from: Int
    let to: Int
    let cost: Int
}

let n = Int(readLine()!)!
var edges = [Edge]()
var parent = Array(0..<n)
var answer = 0

for i in 0..<n {
    let line = readLine()!.map { $0.toNum }
    for j in 0..<n {
        if line[j] == 0 { continue }
        edges.append( Edge(from: i, to: j, cost: line[j]))
        answer += line[j]
    }
}

edges.sort { $0.cost < $1.cost }

for edge in edges {
    let g = edge.from
    let v = edge.to
    let cost = edge.cost

    if find(g) == find(v) {
        continue
    }
    union(g, v)
    answer -= cost
}

for i in 0..<n {
    _=find(i)
}

if Set(parent).count > 1 {
    answer = -1
}

print(answer)

func union(_ g: Int, _ v: Int) {
    var parentG = find(g)
    var parentV = find(v)

    if parentV > parentG {
        swap(&parentG, &parentV)
    }

    parent[parentV] = parentG
}

func find(_ g: Int) -> Int {
    if parent[g] == g {
        return parent[g]
    }
    let parentG = find(parent[g])
    parent[g] = parentG

    return parent[g]
}

extension Character {
    var toNum: Int {
        if self == "0" { return 0 }
        guard let asciiValue = self.asciiValue else { return -1 }

        if 97...122 ~= asciiValue {
            return Int(asciiValue - 96)
        }

        if 65...90 ~= asciiValue {
            return Int(asciiValue - 38)
        }

        return -1
    }
}