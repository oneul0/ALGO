import Foundation

let file = FileIO()

let N = file.readInt()
let M = file.readInt()
let R = file.readInt()
let C = file.readInt()

let dr = [0, 0, 1, -1]
let dc = [1, -1, 0, 0]

var rooms = [Room]()
for _ in 0..<R {
    rooms.append(Room(r: file.readInt() - 1, c: file.readInt() - 1, p: file.readInt()))
}

var dist = Array(repeating: Array(repeating: -1, count: M), count: N)
var queue = [(Int, Int)]()
var head = 0

for _ in 0..<C {
    let r = file.readInt() - 1
    let c = file.readInt() - 1
    dist[r][c] = 0
    queue.append((r, c))
}



while head < queue.count {
    let (r, c) = queue[head]
    head += 1

    for i in 0..<4 {
        let nr = r + dr[i]
        let nc = c + dc[i]

        if nr >= 0 && nr < N && nc >= 0 && nc < M {
            if dist[nr][nc] == -1 {
                dist[nr][nc] = dist[r][c] + 1
                queue.append((nr, nc))
            }
        }
    }
}

var minScore = Int.max
for room in rooms {
    let score = dist[room.r][room.c] * room.p
    if score < minScore {
        minScore = score
    }
}

print(minScore)

final class FileIO {
    private var buffer = [UInt8]()
    private var index = 0

    init(fileHandle: FileHandle = FileHandle.standardInput) {
        buffer = Array(fileHandle.readDataToEndOfFile()) + [UInt8(0)]
    }

    @inline(__always) private func read() -> UInt8 {
        defer { index += 1 }
        return buffer[index]
    }

    @inline(__always) func readInt() -> Int {
        var sum = 0
        var now = read()
        var isNegative = false
        while now == 10 || now == 32 { now = read() }
        if now == 45 { isNegative = true; now = read() }
        while now >= 48 && now <= 57 {
            sum = sum * 10 + Int(now - 48)
            now = read()
        }
        return isNegative ? -sum : sum
    }
}


struct Room {
    let r: Int, c: Int, p: Int
}