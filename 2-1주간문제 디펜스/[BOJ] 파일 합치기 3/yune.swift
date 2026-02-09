import Foundation

final class FastScanner {
    private var data = Array(FileHandle.standardInput.readDataToEndOfFile()) + [0]
    private var idx = 0

    func readInt() -> Int {
        var num = 0
        while data[idx] == 10 || data[idx] == 32 { idx += 1 }
        while data[idx] >= 48 {
            num = num * 10 + Int(data[idx] - 48)
            idx += 1
        }
        return num
    }
}

let scanner = FastScanner()
let T = scanner.readInt()

var output = ""

for _ in 0..<T {
    let K = scanner.readInt()
    let heap = MinHeap()

    for _ in 0..<K {
        heap.push(scanner.readInt())
    }

    var total = 0

    while heap.count > 1 {
        let a = heap.pop()
        let b = heap.pop()
        let s = a + b
        total += s
        heap.push(s)
    }

    output += "\(total)\n"
}

print(output)

final class MinHeap {
    private var heap: [Int] = []

    var isEmpty: Bool { heap.isEmpty }
    var count: Int { heap.count }

    func push(_ x: Int) {
        heap.append(x)
        var i = heap.count - 1
        while i > 0 {
            let p = (i - 1) / 2
            if heap[p] <= heap[i] { break }
            heap.swapAt(p, i)
            i = p
        }
    }

    func pop() -> Int {
        let res = heap[0]
        let last = heap.removeLast()
        if !heap.isEmpty {
            heap[0] = last
            var i = 0
            while true {
                let l = i * 2 + 1
                let r = l + 1
                if l >= heap.count { break }
                var m = l
                if r < heap.count && heap[r] < heap[l] {
                    m = r
                }
                if heap[i] <= heap[m] { break }
                heap.swapAt(i, m)
                i = m
            }
        }
        return res
    }
}