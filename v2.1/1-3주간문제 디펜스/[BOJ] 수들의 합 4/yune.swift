import Foundation

let first = readLine()!.split(separator: " ").map { Int($0)! }
let N = first[0]
let K = first[1]

let arr = readLine()!.split(separator: " ").map { Int($0)! }

var prefixCount = [Int: Int]()
prefixCount[0] = 1

var sum = 0
var answer = 0

for x in arr {
    sum += x
    if let cnt = prefixCount[sum - K] {
        answer += cnt
    }
    prefixCount[sum, default: 0] += 1
}

print(answer)