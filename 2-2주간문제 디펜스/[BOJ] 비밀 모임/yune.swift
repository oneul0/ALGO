import Foundation

let INF = Int.max / 4

let T = Int(readLine()!)!

for _ in 0..<T {
    // 방 갯수 N, 간선 갯수 M
    let NM = readLine()!.split { $0 == " " }.map { Int(String($0))! },
        N = NM[0],
        M = NM[1]

    var graph = [[Edge]](repeating: [Edge](), count: N+1)
    for _ in 0..<M {
        // a와 b는 비밀통로로 연결된 두 방의 번호이며
        // c는 a와 b를 연결하는 비밀통로의 길이이다.
        let abc = readLine()!.split { $0 == " " }.map { Int(String($0))! },
            a = abc[0],
            b = abc[1],
            c = abc[2]
        graph[a].append(Edge(to: b, cost: c))
        graph[b].append(Edge(to: a, cost: c))
    }

    let K = Int(readLine()!)!
    let locationList = readLine()!.split { $0 == " " }.map { Int(String($0))! }
    
    var distList = [[Int]]()
    for location in locationList {
        let dist = dijkstra(location, graph, N)
        distList.append(dist)
    }
    
    var minDist = Int.max
    var roomNumber = Int.max
    for j in 1...N {
        var sum = 0
        for i in 0..<locationList.count {
            sum += distList[i][j]
        }
        if sum < minDist {
            minDist = sum
            roomNumber = j
        }
        if sum == minDist, j < roomNumber {
            roomNumber = j
        }
    }
    print(roomNumber)
}

func dijkstra(_ start: Int, _ graph: [[Edge]], _ n: Int) -> [Int] {
    var dist = [Int](repeating: INF, count: n + 1)
    dist[start] = 0

    var pq = MinHeap()
    pq.push(HeapNode(node: start, dist: 0))

    while !pq.isEmpty {
        let cur = pq.pop()!
        let u = cur.node
        let d = cur.dist

        if dist[u] < d { continue }

        for e in graph[u] {
            let v = e.to
            let nd = d + e.cost

            if nd < dist[v] {
                dist[v] = nd
                pq.push(HeapNode(node: v, dist: nd))
            }
        }
    }

    return dist
}

struct Edge {
    let to: Int
    let cost: Int
}

struct HeapNode {
    let node: Int
    let dist: Int
}

struct MinHeap {
    var heap: [HeapNode] = []

    mutating func push(_ x: HeapNode) {
        heap.append(x)
        var i = heap.count - 1
        while i > 0 {
            let p = (i - 1) / 2
            if heap[p].dist <= heap[i].dist { break }
            heap.swapAt(p, i)
            i = p
        }
    }

    mutating func pop() -> HeapNode? {
        guard !heap.isEmpty else { return nil }
        if heap.count == 1 { return heap.removeLast() }

        let ret = heap[0]
        heap[0] = heap.removeLast()

        var i = 0
        while true {
            let l = i * 2 + 1
            let r = i * 2 + 2
            var smallest = i

            if l < heap.count && heap[l].dist < heap[smallest].dist {
                smallest = l
            }
            if r < heap.count && heap[r].dist < heap[smallest].dist {
                smallest = r
            }
            if smallest == i { break }
            heap.swapAt(i, smallest)
            i = smallest
        }
        return ret
    }

    var isEmpty: Bool {
        heap.isEmpty
    }
}