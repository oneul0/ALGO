import Foundation

final class FastIO {
    private var data: [UInt8] = Array(FileHandle.standardInput.readDataToEndOfFile()) + [0]
    private var idx: Int = 0
    func readInt() -> Int {
        var num = 0
        var sign = 1
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

let io = FastIO()
let N = io.readInt()

let INF = Int.max / 2

var happy = [Int](repeating: 0, count: N)
var fatigue = [Int](repeating: 0, count: N)

for i in 0..<N {
    happy[i] = io.readInt()
    fatigue[i] = io.readInt()
}

// prefix
var youngMinHappy = [Int](repeating: INF, count: N)
var youngMaxFatigue = [Int](repeating: 0, count: N)

for i in 0..<N {
    if i > 0 {
        youngMinHappy[i] = youngMinHappy[i - 1]
        youngMaxFatigue[i] = youngMaxFatigue[i - 1]
    }
    if happy[i] != 0 {
        youngMinHappy[i] = min(youngMinHappy[i], happy[i])
    }
    if fatigue[i] != 0 {
        youngMaxFatigue[i] = max(youngMaxFatigue[i], fatigue[i])
    }
}

// suffix
var oldMaxHappy = [Int](repeating: 0, count: N)
var oldMinFatigue = [Int](repeating: INF, count: N)

for i in stride(from: N - 1, through: 0, by: -1) {
    if i < N - 1 {
        oldMaxHappy[i] = oldMaxHappy[i + 1]
        oldMinFatigue[i] = oldMinFatigue[i + 1]
    }
    if happy[i] != 0 {
        oldMaxHappy[i] = max(oldMaxHappy[i], happy[i])
    }
    if fatigue[i] != 0 {
        oldMinFatigue[i] = min(oldMinFatigue[i], fatigue[i])
    }
}

var answer = -1

for K in 1..<(N) {
    let minYoungHappy = youngMinHappy[K - 1]
    let maxYoungFatigue = youngMaxFatigue[K - 1]
    let maxOldHappy = oldMaxHappy[K]
    let minOldFatigue = oldMinFatigue[K]

    if minYoungHappy > maxOldHappy && maxYoungFatigue < minOldFatigue {
        answer = K
    }
}

print(answer)