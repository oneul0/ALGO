import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let n = input[0], m = input[1]

var board = [[Int]]()
for _ in 0..<n {
    board.append(readLine()!.map { Int(String($0))! })
}

var visited = Array(repeating: Array(repeating: false, count: m), count: n)
var heap = MinHeap<Node>()

let dx = [1, -1, 0, 0]
let dy = [0, 0, 1, -1]

for i in 0..<n {
    for j in 0..<m {
        if i == 0 || i == n-1 || j == 0 || j == m-1 {
            heap.push(Node(height: board[i][j], x: i, y: j))
            visited[i][j] = true
        }
    }
}

var result = 0

while !heap.isEmpty {
    let cur = heap.pop()!

    for d in 0..<4 {
        let nx = cur.x + dx[d]
        let ny = cur.y + dy[d]

        if nx < 0 || ny < 0 || nx >= n || ny >= m { continue }
        if visited[nx][ny] { continue }

        visited[nx][ny] = true

        let nextHeight = board[nx][ny]

        if nextHeight < cur.height {
            result += cur.height - nextHeight
        }

        heap.push(Node(
            height: max(cur.height, nextHeight),
            x: nx,
            y: ny
        ))
    }
}

print(result)

struct Node: Comparable {
    let height: Int
    let x: Int
    let y: Int

    static func < (lhs: Node, rhs: Node) -> Bool {
        return lhs.height < rhs.height
    }
}

struct MinHeap<T: Comparable> {
    private var heap = [T]()

    var isEmpty: Bool { heap.isEmpty }

    mutating func push(_ value: T) {
        heap.append(value)
        siftUp(heap.count - 1)
    }

    mutating func pop() -> T? {
        if heap.count == 1 { return heap.removeLast() }
        guard !heap.isEmpty else { return nil }

        let value = heap[0]
        heap[0] = heap.removeLast()
        siftDown(0)
        return value
    }

    private mutating func siftUp(_ i: Int) {
        var child = i
        while child > 0 {
            let parent = (child - 1) / 2
            if heap[child] < heap[parent] {
                heap.swapAt(child, parent)
                child = parent
            } else { break }
        }
    }

    private mutating func siftDown(_ i: Int) {
        var parent = i
        while true {
            let left = parent * 2 + 1
            let right = parent * 2 + 2
            var candidate = parent

            if left < heap.count && heap[left] < heap[candidate] {
                candidate = left
            }
            if right < heap.count && heap[right] < heap[candidate] {
                candidate = right
            }

            if candidate == parent { break }
            heap.swapAt(parent, candidate)
            parent = candidate
        }
    }
}