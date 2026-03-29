import Foundation

let input = FastInput()

let nStr = input.readString()!
let n = Int(nStr)!

var stationIdx = [String: Int]()
for i in 0..<n {
    if let name = input.readString() {
        stationIdx[name] = i
    }
}

var stationMask = [Int](repeating: 0, count: n)
var maskCount = [Int](repeating: 0, count: 512)
maskCount[0] = n  // 초기 상태: 모든 역은 특징 없음

var featureBit = [String: Int]()
var nextBit = 0

let rStr = input.readString()!
let r = Int(rStr)!
var result = ""

for _ in 0..<r {
    let type = input.readString()

    if type == "U" {
        let sName = input.readString()!
        let fList = input.readString()!

        let idx = stationIdx[sName]!
        let oldMask = stationMask[idx]

        var newMask = 0
        let features = fList.split(separator: ",")
        for f in features {
            let fStr = String(f)
            if featureBit[fStr] == nil {
                featureBit[fStr] = nextBit
                nextBit += 1
            }
            newMask |= (1 << featureBit[fStr]!)
        }

        maskCount[oldMask] -= 1
        maskCount[newMask] += 1
        stationMask[idx] = newMask

    } else if type == "G" {
        let fList = input.readString()!
        var queryMask = 0
        var possible = true

        let features = fList.split(separator: ",")
        for f in features {
            let fStr = String(f)
            if let bit = featureBit[fStr] {
                queryMask |= (1 << bit)
            } else {
                possible = false
                break
            }
        }

        if !possible {
            result += "0\n"
            continue
        }

        var count = 0
        for i in 0..<512 {
            if (i & queryMask) == queryMask {
                count += maskCount[i]
            }
        }
        result += "\(count)\n"
    }
}

print(result, terminator: "")

final class FastInput {
    private let buffer = FileHandle.standardInput.readDataToEndOfFile()
    private var pos = 0

    func readString() -> String? {
        while pos < buffer.count && buffer[pos] <= 32 { pos += 1 }
        if pos == buffer.count { return nil }
        let start = pos
        while pos < buffer.count && buffer[pos] > 32 { pos += 1 }
        return String(data: buffer[start..<pos], encoding: .utf8)
    }

    func readInt() -> Int {
        var res = 0
        while pos < buffer.count && (buffer[pos] < 48 || buffer[pos] > 57) { pos += 1 }
        while pos < buffer.count && buffer[pos] >= 48 && buffer[pos] <= 57 {
            res = res * 10 + Int(buffer[pos] - 48)
            pos += 1
        }
        return res
    }
}