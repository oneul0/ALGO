import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let N = input[0]
let K = input[1]

var A = Array(repeating: Array(repeating: 0, count: N), count: N)
for i in 0..<N {
    A[i] = readLine()!.split(separator: " ").map { Int($0)! }
}

var seq = Array(repeating: Array(repeating: 0, count: 20), count: 3)
seq[1] = readLine()!.split(separator: " ").map { Int($0)! - 1 }
seq[2] = readLine()!.split(separator: " ").map { Int($0)! - 1 }

var used = Array(repeating: false, count: N)
var win = false

func fight(_ x: Int, _ y: Int, _ a: Int, _ b: Int) -> Int {
    if A[a][b] == 2 { return x }
    if A[a][b] == 0 { return y }
    return max(x, y)   // 무승부 → 순서상 뒤
}

func dfs(_ p1: Int, _ p2: Int,
         _ w1: Int, _ w2: Int, _ w3: Int,
         _ i2: Int, _ i3: Int) {

    if win { return }
    if w1 >= K {
        win = true
        return
    }
    if w2 >= K || w3 >= K { return }

    if p1 == 0 || p2 == 0 {
        for i in 0..<N {
            if used[i] { continue }
            used[i] = true

            let a = (p1 == 0) ? i : seq[p1][p1 == 1 ? i2 : i3]
            let b = (p2 == 0) ? i : seq[p2][p2 == 1 ? i2 : i3]

            let winner = fight(p1, p2, a, b)

            var nw1 = w1, nw2 = w2, nw3 = w3
            var ni2 = i2, ni3 = i3

            if winner == 0 { nw1 += 1 }
            else if winner == 1 { nw2 += 1 }
            else { nw3 += 1 }

            if p1 == 1 { ni2 += 1 }
            if p1 == 2 { ni3 += 1 }
            if p2 == 1 { ni2 += 1 }
            if p2 == 2 { ni3 += 1 }

            let rest = 3 - p1 - p2
            dfs(winner, rest, nw1, nw2, nw3, ni2, ni3)

            used[i] = false
        }
    } else {
        let a = seq[p1][p1 == 1 ? i2 : i3]
        let b = seq[p2][p2 == 1 ? i2 : i3]

        let winner = fight(p1, p2, a, b)

        var nw1 = w1, nw2 = w2, nw3 = w3
        var ni2 = i2, ni3 = i3

        if winner == 0 { nw1 += 1 }
        else if winner == 1 { nw2 += 1 }
        else { nw3 += 1 }

        if p1 == 1 { ni2 += 1 }
        if p1 == 2 { ni3 += 1 }
        if p2 == 1 { ni2 += 1 }
        if p2 == 2 { ni3 += 1 }

        let rest = 3 - p1 - p2
        dfs(winner, rest, nw1, nw2, nw3, ni2, ni3)
    }
}

dfs(0, 1, 0, 0, 0, 0, 0)
print(win ? 1 : 0)