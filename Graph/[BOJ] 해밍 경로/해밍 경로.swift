import Foundation

let firstLine = readLine()!.split(separator: " ").map { Int($0)! }
let N = firstLine[0]
let K = firstLine[1]

var codes = [Int](repeating: 0, count: N + 1)
var indexOf = [Int: Int]()

for i in 1...N {
    let s = readLine()!
    let mask = toMask(s)
    codes[i] = mask
    indexOf[mask] = i
}

var dist = [Int](repeating: -1, count: N + 1)
var parent = [Int](repeating: -1, count: N + 1)

var queue = [Int]()
var head = 0

dist[1] = 0
queue.append(1)

// BFS 시작 (1번 코드 기준)
while head < queue.count {
    let cur = queue[head]
    head += 1
    
    let curMask = codes[cur]
    
    for bit in 0..<K {
        let nextMask = curMask ^ (1 << bit)
        if let next = indexOf[nextMask], dist[next] == -1 {
            dist[next] = dist[cur] + 1
            parent[next] = cur
            queue.append(next)
        }
    }
}

// 질의 처리
let M = Int(readLine()!)!

for _ in 0..<M {
    let target = Int(readLine()!)!
    
    if dist[target] == -1 {
        print(-1)
        continue
    }
    
    var path = [Int]()
    var cur = target
    while cur != -1 {
        path.append(cur)
        cur = parent[cur]
    }
    
    path.reverse()
    print(path.map { String($0) }.joined(separator: " "))
}

func toMask(_ s: String) -> Int {
    var mask = 0
    for ch in s {
        mask <<= 1
        if ch == "1" { mask |= 1 }
    }
    return mask
}