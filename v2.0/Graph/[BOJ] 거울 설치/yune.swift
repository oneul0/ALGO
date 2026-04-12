import Foundation

struct MinHeap<T: Comparable> {
    private var elements: [T] = []

    var isEmpty: Bool {
        return elements.isEmpty
    }

    mutating func insert(_ element: T) {
        elements.append(element)
        siftUp(from: elements.count - 1)
    }

    mutating func removeMin() -> T? {
        if elements.isEmpty { return nil }
        if elements.count == 1 { return elements.removeLast() }

        elements.swapAt(0, elements.count - 1)
        let min = elements.removeLast()
        siftDown(from: 0)
        return min
    }

    private mutating func siftUp(from index: Int) {
        var child = index
        var parent = (child - 1) / 2

        while child > 0 && elements[child] < elements[parent] {
            elements.swapAt(child, parent)
            child = parent
            parent = (child - 1) / 2
        }
    }

    private mutating func siftDown(from index: Int) {
        var parent = index
        let count = elements.count

        while true {
            var leftChild = parent * 2 + 1
            var rightChild = parent * 2 + 2
            var candidate = parent

            if leftChild < count && elements[leftChild] < elements[candidate] {
                candidate = leftChild
            }

            if rightChild < count && elements[rightChild] < elements[candidate] {
                candidate = rightChild
            }

            if candidate == parent {
                break
            }

            elements.swapAt(parent, candidate)
            parent = candidate
        }
    }
}

struct State: Comparable {
    let cost: Int
    let r: Int
    let c: Int
    let dir: Int

    static func < (lhs: State, rhs: State) -> Bool {
        return lhs.cost < rhs.cost
    }
}

func installMirror() {
    guard let line = readLine(), let N = Int(line) else { return }

    var board: [[Character]] = []
    var doors: [(r: Int, c: Int)] = []

    for r in 0..<N {
        if let line = readLine() {
            let row = Array(line)
            board.append(row)
            for c in 0..<N {
                if row[c] == "#" {
                    doors.append((r: r, c: c))
                }
            }
        }
    }
    
    let dr = [-1, 0, 1, 0] // 상(0), 우(1), 하(2), 좌(3)
    let dc = [0, 1, 0, -1]
    // 꺾이는 방향: [현재 방향] -> [90도 좌, 90도 우]
    let turn = [[1, 3], [0, 2], [1, 3], [0, 2]] 
    let INF = Int.max / 2
    
    // dist[r][c][d] = (r, c)에 방향 d로 도달하는 최소 거울 개수
    var dist: [[[Int]]] = Array(repeating: Array(repeating: Array(repeating: INF, count: 4), count: N), count: N)
    var pq = MinHeap<State>() 
    
    let startDoor = doors[0]
    let endDoor = doors[1]
    
    for d in 0..<4 {
        dist[startDoor.r][startDoor.c][d] = 0
        pq.insert(State(cost: 0, r: startDoor.r, c: startDoor.c, dir: d))
    }
    
    while let current = pq.removeMin() {
        let (cost, r, c, d) = (current.cost, current.r, current.c, current.dir)
        
        if cost > dist[r][c][d] { continue }
        
        let nr = r + dr[d]
        let nc = c + dc[d]
        
        if nr >= 0 && nr < N && nc >= 0 && nc < N && board[nr][nc] != "*" {
            let nextCell = board[nr][nc]
            
            let nextCost = cost
            let nextDir = d
            
            if nextCost < dist[nr][nc][nextDir] {
                dist[nr][nc][nextDir] = nextCost
                pq.insert(State(cost: nextCost, r: nr, c: nc, dir: nextDir))
            }
            
            if nextCell == "!" {
                let mirrorCost = cost + 1
                
                for nextD in turn[d] {
                    if mirrorCost < dist[nr][nc][nextD] {
                        dist[nr][nc][nextD] = mirrorCost
                        pq.insert(State(cost: mirrorCost, r: nr, c: nc, dir: nextD))
                    }
                }
            }
        }
    }
    
    var minMirrors = INF
    for d in 0..<4 {
        minMirrors = min(minMirrors, dist[endDoor.r][endDoor.c][d])
    }
    
    print(minMirrors == INF ? 0 : minMirrors)
}

installMirror()