let n = Int(readLine()!)!

var dpMin = readLine()!.split { $0 == " " }.map { Int($0)! }
var dpMax = dpMin

for _ in 0..<n-1 {
    
    let line = readLine()!.split { $0 == " " }.map { Int($0)! }

    let m1 = min(dpMin[0], dpMin[1]) + line[0]
    let m2 = min(min(dpMin[0], dpMin[1]), dpMin[2]) + line[1]
    let m3 = min(dpMin[1], dpMin[2]) + line[2]

    dpMin[0] = m1
    dpMin[1] = m2
    dpMin[2] = m3

    let mx1: Int = max(dpMax[0], dpMax[1]) + line[0]
    let mx2 = max(max(dpMax[0], dpMax[1]), dpMax[2]) + line[1]
    let mx3 = max(dpMax[1], dpMax[2]) + line[2]

    dpMax[0] = mx1
    dpMax[1] = mx2
    dpMax[2] = mx3
}

print(dpMax.max()!, dpMin.min()!)