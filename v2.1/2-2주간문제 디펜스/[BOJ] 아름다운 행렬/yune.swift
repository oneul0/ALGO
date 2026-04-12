import Foundation

let N = readLine()!.split(separator: " ").map { Int($0)! }[0]
var a = [[Int]](repeating: [Int](repeating: 0, count: N + 2), count: N + 2)

for i in 1...N {
    let row = readLine()!.split(separator: " ").map { Int($0)! }
    for j in 1...N {
        a[i][j] = row[j - 1]
    }
}

var diag1 = [[Int]](repeating: [Int](repeating: 0, count: N + 2), count: N + 2)
var diag2 = [[Int]](repeating: [Int](repeating: 0, count: N + 2), count: N + 2)

for i in 1...N {
    for j in 1...N {
        diag1[i][j] = a[i][j] + diag1[i - 1][j - 1]
        diag2[i][j] = a[i][j] + diag2[i - 1][j + 1]
    }
}

var answer = Int.min

for i in 1...N {
    for j in 1...N {
        let maxSize = min(N - i + 1, N - j + 1)
        if maxSize < 2 { continue }

        for k in 2...maxSize {
            let A = diag1[i + k - 1][j + k - 1] - diag1[i - 1][j - 1]
            if j + k - 1 <= N {
                let B = diag2[i + k - 1][j] - diag2[i - 1][j + k]
                answer = max(answer, A - B)
            }
        }
    }
}

print(answer)