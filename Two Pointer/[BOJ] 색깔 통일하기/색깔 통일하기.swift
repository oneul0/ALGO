import Foundation

final class FastIO {
    private var data: [UInt8] = Array(FileHandle.standardInput.readDataToEndOfFile()) + [0]
    private var idx: Int = 0
    
    func readInt() -> Int {
        var num = 0
        var sign = 1
        
        while idx < data.count && (data[idx] == 10 || data[idx] == 13 || data[idx] == 32) {
            idx += 1
        }
        
        if idx < data.count && data[idx] == 45 {
            sign = -1
            idx += 1
        }
        
        while idx < data.count && data[idx] >= 48 {
            num = num * 10 + Int(data[idx] - 48)
            idx += 1
        }
        
        return num * sign
    }
}

let io = FastIO()

let N = io.readInt()
let C = io.readInt() - 1

if N == 1 {
    _ = io.readInt()
    print(1)
    print(0)
    exit(0)
}

var button = Array(repeating: 0, count: N + 2)
for i in 1...N {
    button[i] = io.readInt()
}

var sum = Array(repeating: [Int64](repeating: 0, count: 2), count: N + 2)

// 오른쪽 누적합
for i in 2...N {
    let prev = button[i - 1]
    let cur = button[i]
    
    let cost: Int
    if prev <= cur {
        cost = cur - prev
    } else {
        cost = (C - prev) + cur + 1
    }
    
    sum[i][0] = sum[i - 1][0] + Int64(cost)
}

// 왼쪽 누적합
var i = N - 1
while i >= 1 {
    let next = button[i + 1]
    let cur = button[i]
    
    let cost: Int
    if next <= cur {
        cost = cur - next
    } else {
        cost = (C - next) + cur + 1
    }
    
    sum[i][1] = sum[i + 1][1] + Int64(cost)
    i -= 1
}

var bestCost = Int64.max
var bestIndex = 1

for i in 1...N {
    let right = sum[N][0] - sum[i][0]
    let left = sum[1][1] - sum[i][1]
    let cur = max(right, left)
    
    if cur < bestCost {
        bestCost = cur
        bestIndex = i
    }
}

print(bestIndex)
print(bestCost)