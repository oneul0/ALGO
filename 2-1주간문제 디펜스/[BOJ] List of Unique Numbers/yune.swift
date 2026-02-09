import Foundation

let N = Int(readLine()!)!
let arr = readLine()!.split(separator: " ").map { Int($0)! }

var visited = Array(repeating: false, count: 100001)
var left = 0
var result = 0

for right in 0..<N {
    while visited[arr[right]] {
        visited[arr[left]] = false
        left += 1
    }

    visited[arr[right]] = true
    result += right - left + 1
}

print(result)