import Foundation

let n = Int(readLine()!)!
let k = Int(readLine()!)!

var answer = 0

var lo = 1, hi = n*n

while lo <= hi {
    let mid = (lo + hi) / 2
    let count = countLessEqual(mid)
    // 어떤 수 x보다 작은 수의 갯수가 k와 같거나 더 많으므로, 더 작은 x에 대해서도 이를 만족하는 지 검사
    if count >= k {
        answer = mid
        hi = mid - 1
    // 어떤 수 x보다 작은 수의 갯수가 k보다 적으므로, 더 큰 x에 대해서 이를 만족하는 지 검사
    } else {
        lo = mid + 1
    }
}

print(answer)

func countLessEqual(_ x: Int) -> Int {
    var count = 0

    for i in 1...n {
        count += min(x/i, n)
    }

    return count
}