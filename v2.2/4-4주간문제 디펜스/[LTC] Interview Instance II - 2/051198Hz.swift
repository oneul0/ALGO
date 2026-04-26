class Solution {
    func orangesRotting(_ grid: [[Int]]) -> Int {
        var grid = grid
        let m = grid.count
        let n = grid[0].count
        
        let dr = [-1, 1, 0, 0]
        let dc = [0, 0, -1, 1]
        
        var queue = [(Int, Int)]()
        var head = 0
        
        var fresh = 0
        
        for r in 0..<m {
            for c in 0..<n {
                if grid[r][c] == 2 {
                    queue.append((r, c))
                } else if grid[r][c] == 1 {
                    fresh += 1
                }
            }
        }
        
        if fresh == 0 { return 0 }
        
        var minutes = 0
        
        while head < queue.count {
            let size = queue.count - head
            var infected = false
            
            for _ in 0..<size {
                let (r, c) = queue[head]
                head += 1
                
                for i in 0..<4 {
                    let nr = r + dr[i]
                    let nc = c + dc[i]
                    
                    guard (0..<m) ~= nr, (0..<n) ~= nc else { continue }
                    guard grid[nr][nc] == 1 else { continue }
                    
                    grid[nr][nc] = 2
                    fresh -= 1
                    queue.append((nr, nc))
                    infected = true
                }
            }
            
            if infected {
                minutes += 1
            }
        }
        
        return fresh == 0 ? minutes : -1
    }
}
