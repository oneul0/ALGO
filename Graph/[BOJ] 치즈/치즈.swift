import Foundation

let nm = readLine()!.split { $0 == " " }.map { Int(String($0))! },
    n = nm[0],
    m = nm[1]
var grid = [[Int]]()

for _ in 0..<n {
    let line = readLine()!.split { $0 == " " }.map { Int(String($0))! }
    grid.append(line)
}

let dx = [0, 0, -1, 1]
let dy = [-1, 1, 0, 0]

var answer = 0

while true {
    let meltingCheese = findMeltingCheese()
    guard !meltingCheese.isEmpty else { break }
    for pos in meltingCheese {
        grid[pos[0]][pos[1]] = 0
    }
    answer += 1
}

print(answer)

func findMeltingCheese() -> [[Int]] {
    var meltingCount = [[Int]](repeating: [Int](repeating: 0, count: m), count: n)
    var isVisited = [[Bool]](repeating: [Bool](repeating: false, count: m), count: n)

    var targetCheeseList = [[Int]]()
    var queue = [[0, 0]]
    var index = 0

    isVisited[0][0] = true
    while queue.count > index {
        let currentPos = queue[index]
        index += 1
        for i in 0..<4 {
            let nr = currentPos[0] + dx[i]
            let nc = currentPos[1] + dy[i]

            guard isInbound(nr, nc) else { continue }
            guard !isVisited[nr][nc] else { continue }

            if grid[nr][nc] == 1 {
                meltingCount[nr][nc] += 1
                if meltingCount[nr][nc] > 1 {
                    targetCheeseList.append([nr, nc])
                }
                continue
            }

            isVisited[nr][nc] = true
            queue.append([nr, nc])
        }
    }
    return targetCheeseList
}

func isInbound(_ r: Int, _ c: Int) -> Bool {
    return (0..<n) ~= r && (0..<m) ~= c
}