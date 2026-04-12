let nm = readLine()!.split { $0 == " " }.map { Int(String($0))! },
    n = nm[0],
    m = nm[1]

var grid = [[String]]()
var answer = "No"

for _ in 0..<n {
    let line = readLine()!.map { String($0) }
    grid.append(line)
}

var isVisited = [[Bool]](repeating: [Bool](repeating: false, count: m), count: n)

let dx = [0, 0, -1, 1]
let dy = [-1, 1, 0, 0]

func dfs(_ target: String, _ r: Int, _ c: Int, _ pr: Int, _ pc: Int) -> Bool {
    if isVisited[r][c] { 
        answer = "Yes"
        isVisited[r][c] = true
        return true 
    }
    isVisited[r][c] = true
    for i in 0..<4 {
        let nr = r + dy[i]
        let nc = c + dx[i]
        guard (0..<n).contains(nr), (0..<m).contains(nc) else { continue }
        guard grid[nr][nc] == target else { continue }
        if nr == pr, nc == pc { continue }
        if dfs(target, nr, nc, r, c) { return true }
    }
    return false
}

outer: for i in 0..<n {
    for j in 0..<m {
        if isVisited[i][j] { continue }
        if dfs(grid[i][j], i, j, -1, -1) { break outer }
    }
}

print(answer)