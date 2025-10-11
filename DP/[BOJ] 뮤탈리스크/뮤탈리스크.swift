import Foundation

let n = Int(readLine()!)
var hp = readLine()!.split { $0 == " " }.map { Int(String($0))! }
while hp.count < 3 { 
    hp.append(0) 
}
hp.sort(by: >)

let (A, B, C) = (hp[0], hp[1], hp[2])

var dp = Array(
    repeating: Array(
        repeating: Array(repeating: -1, count: 61),
        count: 61
    ),
    count: 61
)

let attacks = [
    [9, 3, 1], [9, 1, 3],
    [3, 9, 1], [3, 1, 9],
    [1, 9, 3], [1, 3, 9],
]

// DFS로 SCV의 깍인 체력 패턴별로 dp를 구한다음
// 상위(체력이 얼마 안깍인 상태에서 하위 상태 트리로 내려가려고 하는 트리의 위치)일 때 연산을 생략

func dfs(_ a: Int, _ b: Int, _ c: Int) -> Int {
    let aa = max(0, a)
    let bb = max(0, b)
    let cc = max(0, c)

    if aa == 0 && bb == 0 && cc == 0 { return 0 }

    if dp[aa][bb][cc] != -1 { return dp[aa][bb][cc] }

    var result = Int.max

    for atk in attacks {
        let na = max(0, aa - atk[0])
        let nb = max(0, bb - atk[1])
        let nc = max(0, cc - atk[2])
        let value = 1 + dfs(na, nb, nc)
        if value < result { result = value }
    }

    dp[aa][bb][cc] = result
    return result
}

print(dfs(A, B, C))