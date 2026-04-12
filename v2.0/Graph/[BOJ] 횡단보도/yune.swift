import Foundation

final class FastScanner {
    private var data: [UInt8] = Array(FileHandle.standardInput.readDataToEndOfFile()) + [0]
    private var idx: Int = 0
    
    @inline(__always)
    func readInt() -> Int {
        var sign = 1
        var num = 0
        while data[idx] == 10 || data[idx] == 13 || data[idx] == 32 { idx += 1 } // skip space/newline
        if data[idx] == 45 { sign = -1; idx += 1 }
        while data[idx] >= 48 {
            num = num * 10 + Int(data[idx] - 48)
            idx += 1
        }
        return num * sign
    }
}

struct Edge { let to: Int; let idx: Int }

struct Node: Comparable {
    let time: Int
    let v: Int
    static func < (lhs: Node, rhs: Node) -> Bool { lhs.time < rhs.time }
}

final class PriorityQueue<T: Comparable> {
    private var heap = [T]()
    
    @inline(__always) var isEmpty: Bool { heap.isEmpty }
    
    @inline(__always)
    func push(_ x: T) {
        heap.append(x)
        var i = heap.count - 1
        while i > 0 {
            let p = (i - 1) >> 1
            if heap[i] < heap[p] {
                heap.swapAt(i, p)
                i = p
            } else { break }
        }
    }
    
    @inline(__always)
    func pop() -> T {
        let top = heap[0]
        let last = heap.removeLast()
        if !heap.isEmpty {
            heap[0] = last
            var i = 0
            while true {
                let l = i * 2 + 1
                if l >= heap.count { break }
                let r = l + 1
                var c = l
                if r < heap.count && heap[r] < heap[l] { c = r }
                if heap[c] < heap[i] {
                    heap.swapAt(c, i)
                    i = c
                } else { break }
            }
        }
        return top
    }
}

let fs = FastScanner()
let N = fs.readInt()
let M = fs.readInt()

var graph = Array(repeating: [Edge](), count: N + 1)
graph.reserveCapacity(N + 1)

for i in 0..<M {
    let a = fs.readInt()
    let b = fs.readInt()
    graph[a].append(Edge(to: b, idx: i))
    graph[b].append(Edge(to: a, idx: i))
}

let INF = Int.max >> 2
var dist = Array(repeating: INF, count: N + 1)
dist[1] = 0

let pq = PriorityQueue<Node>()
pq.push(Node(time: 0, v: 1))

while !pq.isEmpty {
    let cur = pq.pop()
    let u = cur.v
    let t = cur.time
    
    if dist[u] != t { continue }
    if u == N {
        print(t)
        exit(0)
    }
    
    let cycle = t / M
    let mod = t % M
    
    for e in graph[u] {
        let idx = e.idx
        
        let nextStart = (mod <= idx) ? (cycle * M + idx) : ((cycle + 1) * M + idx)
        let nt = nextStart + 1
        
        if nt < dist[e.to] {
            dist[e.to] = nt
            pq.push(Node(time: nt, v: e.to))
        }
    }
}

print(dist[N])