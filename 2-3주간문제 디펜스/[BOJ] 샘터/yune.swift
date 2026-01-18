import Foundation

let nk = readLine()!.split(separator: " ").map { Int($0)! }
let N = nk[0]
let K = nk[1]

let springs = readLine()!.split(separator: " ").map { Int($0)! }

var visited = Set<Int>()
var queue: [(Int, Int)] = []   // (position, distance)
var head = 0

for s in springs {
    visited.insert(s)
    queue.append((s, 0))
}

var remaining = K
var answer: Int64 = 0

while head < queue.count && remaining > 0 {
    let (pos, dist) = queue[head]
    head += 1

    for next in [pos - 1, pos + 1] {
        if visited.contains(next) { continue }

        visited.insert(next)
        answer += Int64(dist + 1)
        remaining -= 1

        if remaining == 0 { break }

        queue.append((next, dist + 1))
    }
}

print(answer)