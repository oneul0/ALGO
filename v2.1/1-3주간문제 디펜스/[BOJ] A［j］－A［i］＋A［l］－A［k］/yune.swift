import Foundation

final class FastScanner {
    private var data: [UInt8] = Array(FileHandle.standardInput.readDataToEndOfFile()) + [0]
    private var idx: Int = 0

    @inline(__always)
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
let n = scanner.readInt()

let NEG_INF = -1_000_000_000_000

var dp0 = NEG_INF
var dp1 = NEG_INF
var dp2 = NEG_INF
var dp3 = NEG_INF

for _ in 0..<n {
    let v = scanner.readInt()

    let newDp3 = max(dp3, dp2 + v)
    let newDp2 = max(dp2, dp1 - v)
    let newDp1 = max(dp1, dp0 + v)
    let newDp0 = max(dp0, -v)

    dp3 = newDp3
    dp2 = newDp2
    dp1 = newDp1
    dp0 = newDp0
}

print(dp3)