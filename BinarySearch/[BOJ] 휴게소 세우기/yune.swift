import Foundation

let nml = readLine()!.split { $0 == " " }.map { Int($0)! },
    n = nml[0],
    m = nml[1],
    l = nml[2]

let posList = n != 0 ? [0] + readLine()!.split { $0 == " " }.map { Int($0)! }.sorted { $0 < $1 } + [l] : [0, l]

var start = 1
var end = l
var answer = 0

while start <= end {
    let mid = (start + end) / 2
    let shouldInstallCount = restCount(mid)
    // 지을 수 있는 휴게소가 m과 같거나 더 적다면, 거리를 늘려본다.
    if shouldInstallCount <= m {
        answer = mid
        // 최솟값 줄이기
        end = mid - 1
        continue
    }
    //최솟값 키우기
    start = mid + 1
}

print(answer)

// 휴게소가 더 많이 지어진다는건, 실재 최소 거리가 distance보다 크다.
// 즉 distance를 늘려야 한다.
// 휴게소가 적다는 건, 실재 최소 거리가 distance보다 작다는 뜻.
// 즉 distance를 줄여야 한다.

func restCount(_ distance: Int) -> Int {
    var count = 0
    for i in 1..<posList.count {
        let gap = posList[i] - posList[i - 1]
        count += (gap - 1) / distance
    }
    return count
}