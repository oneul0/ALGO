import Foundation

let input = FastInput()
let N = input.readInt()

var A = Array(repeating: Array(repeating: Int64(0), count: N), count: N)

for i in 0..<N {
    for j in 0..<N {
        A[i][j] = Int64(input.readInt())
    }
}

var row = Array(repeating: Int64(0), count: N)
var col = Array(repeating: Int64(0), count: N)
var diag = [Int64(0), Int64(0)]

for i in 0..<N {
    for j in 0..<N {
        let v = A[i][j]
        row[i] += v
        col[j] += v
        if i == j { diag[0] += v }
        if i + j == N - 1 { diag[1] += v }
    }
}

var visited = Array(repeating: Array(repeating: false, count: N), count: N)

var usedRow = Array(repeating: false, count: N)
var usedCol = Array(repeating: false, count: N)
var usedDiag = [false, false]

var pq = MinHeap<Line>()

for i in 0..<N {
    pq.push(Line(cost: row[i], type: 0, idx: i))
}
for j in 0..<N {
    pq.push(Line(cost: col[j], type: 1, idx: j))
}
pq.push(Line(cost: diag[0], type: 2, idx: 0))
pq.push(Line(cost: diag[1], type: 3, idx: 0))

var result: [Int64] = []
var completed = 0
var currentTime: Int64 = 0

while completed < 2 * N + 2 {
    guard let top = pq.pop() else { break }

    let cost = top.cost
    let type = top.type
    let idx = top.idx

    if type == 0 && usedRow[idx] { continue }
    if type == 1 && usedCol[idx] { continue }
    if type == 2 && usedDiag[0] { continue }
    if type == 3 && usedDiag[1] { continue }

    if type == 0 && row[idx] != cost { continue }
    if type == 1 && col[idx] != cost { continue }
    if type == 2 && diag[0] != cost { continue }
    if type == 3 && diag[1] != cost { continue }

    currentTime += cost

    if type == 0 {
        usedRow[idx] = true
        let i = idx
        for j in 0..<N {
            if visited[i][j] { continue }
            visited[i][j] = true
            let v = A[i][j]

            row[i] -= v
            col[j] -= v
            if i == j { diag[0] -= v }
            if i + j == N - 1 { diag[1] -= v }

            pq.push(Line(cost: col[j], type: 1, idx: j))
            if i == j { pq.push(Line(cost: diag[0], type: 2, idx: 0)) }
            if i + j == N - 1 { pq.push(Line(cost: diag[1], type: 3, idx: 0)) }
        }
    } else if type == 1 {
        usedCol[idx] = true
        let j = idx
        for i in 0..<N {
            if visited[i][j] { continue }
            visited[i][j] = true
            let v = A[i][j]

            row[i] -= v
            col[j] -= v
            if i == j { diag[0] -= v }
            if i + j == N - 1 { diag[1] -= v }

            pq.push(Line(cost: row[i], type: 0, idx: i))
            if i == j { pq.push(Line(cost: diag[0], type: 2, idx: 0)) }
            if i + j == N - 1 { pq.push(Line(cost: diag[1], type: 3, idx: 0)) }
        }
    } else if type == 2 {
        usedDiag[0] = true
        for i in 0..<N {
            let j = i
            if visited[i][j] { continue }
            visited[i][j] = true
            let v = A[i][j]

            row[i] -= v
            col[j] -= v
            diag[0] -= v
            if i + j == N - 1 { diag[1] -= v }

            pq.push(Line(cost: row[i], type: 0, idx: i))
            pq.push(Line(cost: col[j], type: 1, idx: j))
            if i + j == N - 1 { pq.push(Line(cost: diag[1], type: 3, idx: 0)) }
        }
    } else {
        usedDiag[1] = true
        for i in 0..<N {
            let j = N - 1 - i
            if visited[i][j] { continue }
            visited[i][j] = true
            let v = A[i][j]

            row[i] -= v
            col[j] -= v
            diag[1] -= v
            if i == j { diag[0] -= v }

            pq.push(Line(cost: row[i], type: 0, idx: i))
            pq.push(Line(cost: col[j], type: 1, idx: j))
            if i == j { pq.push(Line(cost: diag[0], type: 2, idx: 0)) }
        }
    }

    completed += 1
    result.append(currentTime)
}

let output = result.map { String($0) }.joined(separator: "\n")
print(output)

final class FastInput {
    private var data: [UInt8] = Array(FileHandle.standardInput.readDataToEndOfFile())
    private var idx: Int = 0

    func readInt() -> Int {
        var sign = 1
        var num = 0

        while data[idx] == 10 || data[idx] == 13 || data[idx] == 32 { idx += 1 }

        if data[idx] == 45 {
            sign = -1
            idx += 1
        }

        while data[idx] >= 48 {
            num = num * 10 + Int(data[idx] - 48)
            idx += 1
        }

        return num * sign
    }
}

struct Line: Comparable {
    let cost: Int64
    let type: Int  // 0: row, 1: col, 2: diag1, 3: diag2
    let idx: Int

    static func < (lhs: Line, rhs: Line) -> Bool {
        if lhs.cost != rhs.cost { return lhs.cost < rhs.cost }
        if lhs.type != rhs.type { return lhs.type < rhs.type }
        return lhs.idx < rhs.idx
    }
}

// 최소 힙
struct MinHeap<T: Comparable> {
    private var heap: [T] = []

    var isEmpty: Bool { heap.isEmpty }

    mutating func push(_ x: T) {
        heap.append(x)
        var i = heap.count - 1
        while i > 0 {
            let p = (i - 1) >> 1
            if heap[p] <= heap[i] { break }
            heap.swapAt(p, i)
            i = p
        }
    }

    mutating func pop() -> T? {
        if heap.isEmpty { return nil }
        if heap.count == 1 { return heap.removeLast() }

        let ret = heap[0]
        heap[0] = heap.removeLast()

        var i = 0
        while true {
            let l = i * 2 + 1
            let r = i * 2 + 2
            var smallest = i

            if l < heap.count && heap[l] < heap[smallest] { smallest = l }
            if r < heap.count && heap[r] < heap[smallest] { smallest = r }

            if smallest == i { break }
            heap.swapAt(i, smallest)
            i = smallest
        }

        return ret
    }
}