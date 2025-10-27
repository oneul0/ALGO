import Foundation

let mnl = readLine()!.split { $0 == " " }.map { Int($0)! },
    m = mnl[0],
    n = mnl[1],
    l = mnl[2]

let huntPosList = readLine()!.split { $0 == " " }.map { Int($0)! }.sorted { $0 < $1 }

var animalPosList = [[Int]]()
for _ in 0..<n {
    animalPosList.append(readLine()!.split { $0 == " " }.map { Int($0)! })
}

var answer = 0

// 사대의 위치 xi와 동물의 위치 (aj, bj) 간의 거리는 |xi-aj| + bj로 계산한다.
for ab in animalPosList {
    let a = ab[0]
    let b = ab[1]

    var left = 0
    var right = m-1

    // | mid - a | <= l - b 해당 동물을 사로에서 잡을 수 있음
    // b - l <= mid - a <= l - b
    while left <= right {
        let mid = (left + right) / 2
        let x = huntPosList[mid]

        if a - (l - b) <= x && x <= a + (l - b) {
            answer += 1
            break
        } else if x < a - (l - b) {
            // 사로가 왼쪽에서 먼 경우, 즉 중심값이 동물보다 왼쪽에 있으니, 왼쪽을 중심값 가까이로 옮김
            left = mid + 1
        } else {
            // 사로가 오른쪽에서 먼 경우, 즉 중심값이 동물보다 오른쪽에 있으니, 오른쪽을 중심값 가까이로 옮김
            right = mid - 1
        }
    }
}

print(answer)