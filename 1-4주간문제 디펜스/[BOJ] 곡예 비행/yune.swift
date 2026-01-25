import Foundation

let NEG = Int.min / 4
let nm = readLine()!.split { $0 == " " }.map { Int(String($0))! },
    n = nm[0],
    m = nm[1]

var table = [[Int]]()
for _ in 0..<n {
    let line = readLine()!.split { $0 == " " }.map { Int(String($0))! }
    table.append(line)
}    

var upDP = [[Int]](repeating: [Int](repeating: NEG, count: m), count: n)
for r in stride(from: n-1, through: 0, by: -1) {
    for c in 0..<m {
        if r == n-1, c == 0 {
            upDP[r][c] = table[r][c]
            continue
        }
        let fromLeft = c - 1 >= 0 ? upDP[r][c-1] : NEG
        let fromDown = r + 1 < n ? upDP[r+1][c] : NEG
        upDP[r][c] = table[r][c] + max(fromDown, fromLeft)
    }
}


var downDP = [[Int]](repeating: [Int](repeating: NEG, count: m), count: n)
for r in stride(from: n - 1, through: 0, by: -1) {
    for c in stride(from: m - 1, through: 0, by: -1) {
        if r == n - 1, c == m - 1 {
            downDP[r][c] = table[r][c]
            continue
        }
        let fromRight = c + 1 < m ? downDP[r][c+1] : NEG
        let fromDown = r + 1 < n ? downDP[r + 1][c] : NEG
        downDP[r][c] = table[r][c] + max(fromDown, fromRight)
    }
}

var answer = Int.min

for i in 0..<n {
    for j in 0..<m {
        answer = max(answer, upDP[i][j] + downDP[i][j])
    }
}

print(answer)