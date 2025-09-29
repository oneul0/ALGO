import Foundation

func readInts() -> [Int] {
    return readLine()!.split(separator: " ").map { Int($0)! }
}

func FTS() {
    let nm = readInts()
    let N = nm[0]
    let M = nm[1]
    
    var grid = [[Int]](repeating: [Int](repeating: 0, count: N), count: N)
    for i in 0..<N {
        grid[i] = readInts()
    }
    
    var spells = [(d: Int, s: Int)]()
    for _ in 0..<M {
        let ds = readInts()
        spells.append((ds[0], ds[1]))
    }
    
    let center = N / 2
    
    // 나선형 인덱스 매핑
    var idxToPos = [(Int, Int)]()
    idxToPos.append((center, center))
    
    var posToIdx = Array(repeating: Array(repeating: 0, count: N), count: N)
    
    buildSpiralMapping(N: N, center: center, idxToPos: &idxToPos, posToIdx: &posToIdx)
    
    let total = N * N
    var ball = [Int](repeating: 0, count: total)
    for idx in 1..<idxToPos.count {
        let (r, c) = idxToPos[idx]
        ball[idx] = grid[r][c]
    }
    
    var explodedCount = [Int](repeating: 0, count: 4) // index 1,2,3
    
    for (d, s) in spells {
        // 1. 블리자드
        destroyBalls(d: d, s: s, center: (center, center),
                     posToIdx: posToIdx, ball: &ball)
        // 2. 빈칸 당기기
        compactBalls(ball: &ball)
        // 3. 폭발 반복
        while true {
            let didExplode = explodeBalls(ball: &ball, explodedCount: &explodedCount)
            if !didExplode { break }
            compactBalls(ball: &ball)
        }
        // 4. 구슬 변화
        let changed = changeBalls(ball: ball, maxLen: total)
        ball = changed
    }
    
    var score = 0
    for num in 1...3 {
        score += num * explodedCount[num]
    }
    print(score)
}

func buildSpiralMapping(N: Int, center: Int,
                        idxToPos: inout [(Int, Int)],
                        posToIdx: inout [[Int]]) {
    let dr = [0, 1, 0, -1] // ← ↓ → ↑
    let dc = [-1, 0, 1, 0]
    var len = 1
    var dir = 0
    var r = center, c = center
    var idx = 1
    while true {
        for _ in 0..<2 {
            for _ in 0..<len {
                r += dr[dir]
                c += dc[dir]
                if r < 0 || r >= N || c < 0 || c >= N {
                    return
                }
                idxToPos.append((r, c))
                posToIdx[r][c] = idx
                idx += 1
            }
            dir = (dir + 1) % 4
        }
        len += 1
    }
}

func destroyBalls(d: Int, s: Int, center: (Int, Int),
                  posToIdx: [[Int]], ball: inout [Int]) {
    // d: 1=↑,2=↓,3=←,4=→
    let dr = [-1, 1, 0, 0]
    let dc = [0, 0, -1, 1]
    let (cr, cc) = center
    for dist in 1...s {
        let nr = cr + dr[d-1] * dist
        let nc = cc + dc[d-1] * dist
        if nr < 0 || nr >= posToIdx.count || nc < 0 || nc >= posToIdx.count {
            continue
        }
        let idx = posToIdx[nr][nc]
        if idx > 0 && idx < ball.count {
            ball[idx] = 0
        }
    }
}

func compactBalls(ball: inout [Int]) {
    var arr = [Int](repeating: 0, count: ball.count)
    var write = 1
    for i in 1..<ball.count {
        if ball[i] != 0 {
            arr[write] = ball[i]
            write += 1
        }
    }
    ball = arr
}

func explodeBalls(ball: inout [Int], explodedCount: inout [Int]) -> Bool {
    var didExplode = false
    let n = ball.count
    var start = 1
    while start < n {
        if ball[start] == 0 {
            start += 1
            continue
        }
        let num = ball[start]
        var end = start + 1
        while end < n && ball[end] == num {
            end += 1
        }
        let length = end - start
        if length >= 4 {
            didExplode = true
            for i in start..<end {
                ball[i] = 0
            }
            if num < explodedCount.count {
                explodedCount[num] += length
            }
        }
        start = end
    }
    return didExplode
}

func changeBalls(ball: [Int], maxLen: Int) -> [Int] {
    var newArr = [Int](repeating: 0, count: maxLen)
    var write = 1
    var i = 1
    while i < ball.count {
        if ball[i] == 0 {
            i += 1
            continue
        }
        let num = ball[i]
        var j = i + 1
        while j < ball.count && ball[j] == num {
            j += 1
        }
        let count = j - i
        if write >= maxLen { break }
        newArr[write] = count
        write += 1
        if write >= maxLen { break }
        newArr[write] = num
        write += 1
        i = j
    }
    return newArr
}

// 실행
FTS()
