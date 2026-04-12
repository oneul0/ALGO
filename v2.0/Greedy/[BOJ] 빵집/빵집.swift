import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let R = input[0]
let C = input[1]

var map = Array(repeating: Array(repeating: Character("."), count: C), count: R)

for i in 0..<R {
    map[i] = Array(readLine()!)
}

let dr = [-1, 0, 1]
let dc = [1, 1, 1]

var answer = 0

func dfs(_ r: Int, _ c: Int) -> Bool {
    if c == C - 1 {
        return true
    }

    for i in 0..<3 {
        let nr = r + dr[i]
        let nc = c + dc[i]

        if nr < 0 || nr >= R || nc >= C { continue }
        if map[nr][nc] != "." { continue }

        map[nr][nc] = "x"
        if dfs(nr, nc) {
            return true
        }
    }
    return false
}

for r in 0..<R {
    if map[r][0] == "." {
        map[r][0] = "x"
        if dfs(r, 0) {
            answer += 1
        }
    }
}

print(answer)