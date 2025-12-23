import Foundation

let firstLine = readLine()!.split(separator: " ").map { Int($0)! }
let N = firstLine[0]
let K = firstLine[1]
let B = firstLine[2]

var broken = Array(repeating: 0, count: N + 1)

for _ in 0..<B {
    let idx = Int(readLine()!)!
    broken[idx] = 1
}

var currentBroken = 0
for i in 1...K {
    currentBroken += broken[i]
}

var answer = currentBroken

if K < N {
    for i in K+1...N {
        currentBroken += broken[i]
        currentBroken -= broken[i - K]
        answer = min(answer, currentBroken)
    }
}

print(answer)