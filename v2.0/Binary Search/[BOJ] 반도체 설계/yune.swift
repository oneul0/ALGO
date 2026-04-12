let n = Int(readLine()!)!
let arr = readLine()!.split { $0 == " " }.map { Int(String($0))! }
var lis = [arr[0]]

// 즉 lis를 만족하기 위해 몇개의 수를 스킵해야 하냐는 문제. 전선을 잘라내야 하냐는 문제.

for x in arr.dropFirst() {
    let index = lowerBound(x, lis)
    if lis.count == index {
        lis.append(x)
        continue
    }
    lis[index] = x
}
print(lis.count)

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