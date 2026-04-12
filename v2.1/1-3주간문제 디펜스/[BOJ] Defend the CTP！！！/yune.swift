import Foundation

final class FastScanner {
    private var data: [UInt8] = Array(FileHandle.standardInput.readDataToEndOfFile()) + [0]
    private var idx: Int = 0
    
    func readInt() -> Int {
        var num = 0
        var sign = 1
        while data[idx] == 10 || data[idx] == 13 || data[idx] == 32 { idx += 1 }
        if data[idx] == 45 { sign = -1; idx += 1 }
        while data[idx] >= 48 {
            num = num * 10 + Int(data[idx] - 48)
            idx += 1
        }
        return num * sign
    }
}

let scanner = FastScanner()

let N = scanner.readInt()
let M = scanner.readInt()

var graph = Array(repeating: [Int](), count: N + 1)
var rgraph = Array(repeating: [Int](), count: N + 1)

for _ in 0..<M {
    let x = scanner.readInt()
    let y = scanner.readInt()
    graph[x].append(y)
    rgraph[y].append(x)
}

var visited = Array(repeating: false, count: N + 1)
var order = [Int]()

func dfs1(_ u: Int) {
    visited[u] = true
    for v in graph[u] {
        if !visited[v] {
            dfs1(v)
        }
    }
    order.append(u)
}

for i in 1...N {
    if !visited[i] {
        dfs1(i)
    }
}

var sccId = Array(repeating: 0, count: N + 1)
var sccCount = 0

func dfs2(_ start: Int) {
    var stack = [start]
    sccId[start] = sccCount
    
    while let u = stack.popLast() {
        for v in rgraph[u] {
            if sccId[v] == 0 {
                sccId[v] = sccCount
                stack.append(v)
            }
        }
    }
}

for u in order.reversed() {
    if sccId[u] == 0 {
        sccCount += 1
        dfs2(u)
    }
}

var dag = Array(repeating: Set<Int>(), count: sccCount + 1)
var rdag = Array(repeating: Set<Int>(), count: sccCount + 1)

for u in 1...N {
    for v in graph[u] {
        let a = sccId[u]
        let b = sccId[v]
        if a != b {
            dag[a].insert(b)
            rdag[b].insert(a)
        }
    }
}

let start = sccId[1]
let end = sccId[N]

var fromStart = Array(repeating: false, count: sccCount + 1)
var toEnd = Array(repeating: false, count: sccCount + 1)

func dfsReach(_ start: Int, _ g: [Set<Int>], _ visited: inout [Bool]) {
    var stack = [start]
    visited[start] = true
    
    while let u = stack.popLast() {
        for v in g[u] {
            if !visited[v] {
                visited[v] = true
                stack.append(v)
            }
        }
    }
}

dfsReach(start, dag, &fromStart)
dfsReach(end, rdag, &toEnd)

let T = scanner.readInt()
var output = ""

for _ in 0..<T {
    let c = scanner.readInt()
    let s = sccId[c]
    if fromStart[s] && toEnd[s] {
        output += "Defend the CTP\n"
    } else {
        output += "Destroyed the CTP\n"
    }
}

print(output)