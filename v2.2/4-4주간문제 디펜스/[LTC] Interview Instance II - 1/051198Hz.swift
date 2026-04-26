class Solution {
    func numIslands(_ grid: [[Character]]) -> Int {
        func bfs(_ row: Int, _ col: Int) {
            let dr = [-1, 1, 0, 0]
            let dc = [0, 0, -1, 1]

            var queue = [[row, col]]
            var head = 0

            while queue.count > head {
                let rc = queue[head]
                for i in 0..<4 {
                    let nr = rc[0] + dr[i]
                    let nc = rc[1] + dc[i]
                    guard (0..<grid.count) ~= nr, (0..<grid[0].count) ~= nc else { continue }
                    guard grid[nr][nc] == "1" else { continue }
                    guard !isVisited[nr][nc] else { continue }
                    queue.append([nr, nc])
                    isVisited[nr][nc] = true
                }
                head += 1
            }
        }

        var isVisited = Array(repeating: Array(repeating: false, count: grid[0].count),
                                count: grid.count)
        var islandCount = 0

        for r in 0..<grid.count {
            for c in 0..<grid[0].count {
                guard !isVisited[r][c] else { continue }
                guard grid[r][c] == "1" else { continue }
                isVisited[r][c] = true
                bfs(r, c)
                islandCount += 1
            }
        }

        return islandCount
    }
}
