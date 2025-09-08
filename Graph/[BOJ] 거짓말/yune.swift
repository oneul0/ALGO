let nm = readLine()!.split { $0 == " " }.map { Int(String($0))! },
    n = nm[0],
    m = nm[1]

let knowPeeps = readLine()!.split { $0 == " " }.map { Int(String($0))! }

var parent = Array(0...n)
var parties = [[Int]]()
var answer = m

if knowPeeps[0] != 0 {
    for i in 1..<(knowPeeps.count-1) {
        union(knowPeeps[i], knowPeeps[i+1])
    }

    for _ in 0..<m {
        parties.append(readLine()!.split { $0 == " " }.map { Int(String($0))! })
    }

    for i in 0..<m {
        for j in 1..<parties[i].count-1 {
            union(parties[i][j], parties[i][j+1])
        }
    }

    for i in 0..<m {
        if find(parties[i][1]) == find(knowPeeps[1]) {
            answer -= 1
        }
    }
}

print(answer)

func find(_ g: Int) -> Int {
    if parent[g] == g { return g }
    parent[g] = find(parent[g])

    return parent[g]
}

func union(_ g: Int, _ v: Int) {
    var parentG = find(g)
    var parentV = find(v)

    if parentV > parentG {
        swap(&parentG, &parentV)
    }
    parent[parentG] = parentV
}