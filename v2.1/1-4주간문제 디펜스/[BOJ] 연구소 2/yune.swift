import Foundation

let nm = readLine()!.split { $0 == " " }.map { Int(String($0))! },
    n = nm[0],
    m = nm[1]

let empty = 0,
    wall = 1,
    possible = 2
var possibles = [[Int]]()
var grid = [[Int]]()
var answer = Int.max

for r in 0..<n {
    let line = readLine()!.split { $0 == " " }.map { Int(String($0))! }
    for c in 0..<n {
        guard line[c] == possible else { continue }
        possibles.append([r, c])
    }
    grid.append(line)
}

combination(0, [])

print(answer == Int.max ? -1 : answer)

func combination(_ index: Int, _ selected: [[Int]]) {
    if selected.count == m {
        let time = bfs(selected, grid)
        if time != Int.max {
            answer = min(answer, time)
        }
        return
    }

    for i in index..<possibles.count {
        combination(i + 1, selected + [possibles[i]])
    }
}

func bfs(_ viruses: [[Int]], _ grid: [[Int]]) -> Int {
    var grid = grid

    let dr = [0, 0, -1, 1]
    let dc = [-1, 1, 0, 0]

    var time = 0
    var queue = viruses
    var index = 0
    var isVisited = [[Bool]](
        repeating: [Bool](
            repeating: false, 
            count: n), 
        count: n)

    for virus in viruses {
        grid[virus[0]][virus[1]] = 3
        isVisited[virus[0]][virus[1]] = true
    }

    while !queue.isEmpty {
        var nextViruses = [[Int]]()
        while queue.count > index {
            let rc = queue[index],
                r = rc[0],
                c = rc[1]
            index += 1
            isVisited[r][c] = true
            for i in 0..<4 {
                let nr = r + dr[i]
                let nc = c + dc[i]

                guard isInbound(nr, nc) else { continue }
                guard !isVisited[nr][nc] else { continue }
                guard grid[nr][nc] != 1 else { continue }
                guard grid[nr][nc] != 3 else { continue }

                grid[nr][nc] = 3
                nextViruses.append([nr, nc])
                isVisited[nr][nc] = true
            }
        }
        guard !nextViruses.isEmpty else { break }
        time += 1
        index = 0
        queue = nextViruses
    }

    for r in 0..<n {
        for c in 0..<n {
            if grid[r][c] == 0 { return Int.max }
            if grid[r][c] == 2 { return Int.max }
        }
    }

    return time
}

func isInbound(_ r: Int, _ c: Int) -> Bool {
    guard (0..<n) ~= r else { return false }
    return (0..<n) ~= c
}