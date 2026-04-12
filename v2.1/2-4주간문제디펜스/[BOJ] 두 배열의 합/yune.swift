import Foundation

let T = Int(readLine()!)!

let n = Int(readLine()!)!
let A = readLine()!.split(separator: " ").map { Int($0)! }

let m = Int(readLine()!)!
let B = readLine()!.split(separator: " ").map { Int($0)! }

var subA = [Int]()
for i in 0..<n {
    var sum = 0
    for j in i..<n {
        sum += A[j]
        subA.append(sum)
    }
}

var subB = [Int]()
for i in 0..<m {
    var sum = 0
    for j in i..<m {
        sum += B[j]
        subB.append(sum)
    }
}

subB.sort()

func lowerBound(_ arr: [Int], _ target: Int) -> Int {
    var left = 0
    var right = arr.count
    while left < right {
        let mid = (left + right) / 2
        if arr[mid] < target {
            left = mid + 1
        } else {
            right = mid
        }
    }
    return left
}

func upperBound(_ arr: [Int], _ target: Int) -> Int {
    var left = 0
    var right = arr.count
    while left < right {
        let mid = (left + right) / 2
        if arr[mid] <= target {
            left = mid + 1
        } else {
            right = mid
        }
    }
    return left
}

var answer = 0

for a in subA {
    let target = T - a
    let lb = lowerBound(subB, target)
    let ub = upperBound(subB, target)
    answer += ub - lb
}

print(answer)