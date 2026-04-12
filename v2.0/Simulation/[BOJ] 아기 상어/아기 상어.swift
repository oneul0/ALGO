import Foundation

func readInts() -> [Int] {
    return readLine()!.split(separator: " ").map { Int($0)! }
}

struct Pos {
    var r: Int
    var c: Int
    var dist: Int
}

func BabyShartTrururuturu() {
    let n = Int(readLine()!)!
    var board = [[Int]](repeating: [Int](repeating: 0, count: n), count: n)
    var sharkR = 0, sharkC = 0
    for i in 0..<n {
        let row = readInts()
        for j in 0..<n {
            board[i][j] = row[j]
            if row[j] == 9 {
                sharkR = i
                sharkC = j
                board[i][j] = 0  // 상어 자리 빈칸 처리
            }
        }
    }
    
    var sharkSize = 2
    var eatCount = 0
    var totalTime = 0
    
    let dr = [-1, 0, 0, 1]
    let dc = [0, -1, 1, 0]
    
    func bfs(fromR: Int, fromC: Int) -> [[Int]] {
        var dist = [[Int]](repeating: [Int](repeating: -1, count: n), count: n)
        var q = [Pos]()
        dist[fromR][fromC] = 0
        q.append(Pos(r: fromR, c: fromC, dist: 0))
        var idx = 0
        while idx < q.count {
            let cur = q[idx]
            idx += 1
            for d in 0..<4 {
                let nr = cur.r + dr[d]
                let nc = cur.c + dc[d]
                if nr < 0 || nr >= n || nc < 0 || nc >= n { continue }
                if dist[nr][nc] != -1 { continue }
                // 지나갈 수 있는 조건 == 물고기 크기 <= 상어 크기
                if board[nr][nc] > sharkSize { continue }
                dist[nr][nc] = cur.dist + 1
                q.append(Pos(r: nr, c: nc, dist: cur.dist + 1))
            }
        }
        return dist
    }
    
    func findFish(dist: [[Int]]) -> (r: Int, c: Int, d: Int)? {
        var minDist = Int.max
        var target: (Int, Int)? = nil
        for i in 0..<n {
            for j in 0..<n {
                if dist[i][j] != -1 && board[i][j] > 0 && board[i][j] < sharkSize {
                    let d = dist[i][j]
                    if d < minDist {
                        minDist = d
                        target = (i, j)
                    } else if d == minDist {
                        // 같은 거리라면 위쪽 먼저, 왼쪽 먼저
                        if let (tr, tc) = target {
                            if i < tr || (i == tr && j < tc) {
                                target = (i, j)
                            }
                        }
                    }
                }
            }
        }
        if let (tr, tc) = target {
            return (tr, tc, minDist)
        } else {
            return nil
        }
    }
    
    while true {
        let dist = bfs(fromR: sharkR, fromC: sharkC)
        guard let fish = findFish(dist: dist) else {
            // 먹을 물고기 없으면 종료
            break
        }
        let (fr, fc, d) = fish
        // 이동 시간 추가
        totalTime += d
        // 상어 이동 & 먹기
        sharkR = fr
        sharkC = fc
        board[fr][fc] = 0
        eatCount += 1
        if eatCount == sharkSize {
            sharkSize += 1
            eatCount = 0
        }
    }
    
    print(totalTime)
}

// 실행
BabyShartTrururuturu()
