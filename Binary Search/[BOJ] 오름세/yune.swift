import Foundation

var answer = ""
var buffer: [UInt8] = Array(FileHandle.standardInput.readDataToEndOfFile()), byteIdx = 0; buffer.append(0)

@inline(__always) func readByte() -> UInt8 {
    defer { byteIdx += 1 }
    let bp = buffer.withUnsafeBufferPointer { $0[byteIdx] }
    if bp == 0 { print(answer); exit(0) } // 여기서 EOF 처리
    return bp
}

@inline(__always) func readInt() -> Int {
    var number = 0, byte = readByte(), isNegative = false
    while byte == 10 || byte == 32 { byte = readByte() }
    if byte == 45 { byte = readByte(); isNegative = true }
    while 48...57 ~= byte { number = number * 10 + Int(byte - 48); byte = readByte() }
    return number * (isNegative ? -1 : 1)
}

func lowerBound(_ x: Int, _ arr: [Int]) -> Int {
    var lo = 0, hi = arr.count

    while lo < hi {
        let mid = (lo + hi) / 2
        if arr[mid] < x {
            lo = mid + 1
            continue
        }
        hi = mid
    }

    return lo
}

while true {
    let n = readInt()
    var arr = [Int]()
    
    for _ in 0..<n {
        arr.append(readInt())
    }
    
    var dp = [arr[0]]
    
    for i in 1..<n {
        let index = lowerBound(arr[i], dp)
        if dp.count == index {
            dp.append(arr[i])
        }
        else {
            dp[index] = arr[i]
        }
    }
    answer += "\(dp.count)\n"
}