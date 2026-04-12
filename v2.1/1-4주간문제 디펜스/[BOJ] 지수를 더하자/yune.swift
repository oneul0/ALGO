import Foundation

let N = Int(readLine()!)!
let P = readLine()!.split(separator: " ").map { Int($0)! }
let K = Int(readLine()!)!

var answer = 0

for p in P {
    var cur = p
    while cur <= K {
        answer += K / cur
        if cur > K / p { break }
        cur *= p
    }
}

print(answer)