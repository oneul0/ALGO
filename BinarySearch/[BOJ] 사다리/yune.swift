import Foundation

// 입력: x, y, c
let input = readLine()!.split(separator: " ").map { Double($0)! }
let x = input[0]
let y = input[1]
let c = input[2]

// 이분 탐색 범위 설정
var left = 0.0
var right = min(x, y)
var answer = 0.0

// 오차 범위
while right - left > 1e-6 {
    let mid = (left + right) / 2.0  // 현재 가정한 골목 폭 W

    // 피타고라스 정리로 높이 계산
    let h1 = sqrt(x * x - mid * mid)
    let h2 = sqrt(y * y - mid * mid)

    // 교차점 높이 계산식: c' = (h1 * h2) / (h1 + h2)
    let cPrime = (h1 * h2) / (h1 + h2)

    // c'과 실제 c 비교
    if cPrime >= c {
        // 교차 높이가 더 높다는 건, 골목이 아직 너무 좁음 → 더 넓혀야 함
        answer = mid
        left = mid
    } else {
        // 교차 높이가 낮다는 건, 골목이 너무 넓음 → 줄여야 함
        right = mid
    }
}

print(String(format: "%.3f", answer))