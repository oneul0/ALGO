import Foundation

let dx = [1, 0, -1, 0]
let dy = [0, -1, 0, 1]

let n = Int(readLine()!)!

var curves = [[Position]]()

for _ in 0..<n {
    // 시작점 xy, 방향, 세대(반복횟수)
    let xydg = readLine()!.split { $0 == " " }.map { Int(String($0))! },
        x = xydg[0],
        y = xydg[1],
        d = xydg[2],
        z = xydg[3]
    
    curves.append(makeCurve(x, y, d, z))
}

// 이중배열을 탐색하며 인접한 네점 모두 true인 갯수
let answer = countSquare(curves)

print(answer)

func makeCurve(_ x: Int, _ y: Int, _ d: Int, _ g: Int) -> [Position] {
    var points = [Position(x: x, y: y, d: -1), Position(x: x + dx[d], y: y + dy[d], d: d)]

    // 어떤 방향을 시계방향 90도로 회전시킨 것은, 그 방향에서 +1 인덱스한 것이다.

    // K(K > 1)세대 드래곤 커브는 K-1세대 드래곤 커브를 끝 점을 기준으로 90도 시계 방향 회전 시킨 다음, 그것을 끝 점에 붙인 것이다.
    // 0세대 1, 1세대 1, 2세대 2, 3세대 4, 4세대 8, 5세대 16, ..., 메 새대마다 이전에 그린 꼭짓점의 제곱수만큼 꼭짓점을 더 그리게 됨
    // 즉, 배열을 회전하면서 그리지 말고 좌표의 배열로 꼭짓점들을 표현
    // 각 점들이 그려진 방향이 있어서, 그 방향을 전치시키며 마지막 원소부터 점을 찍고 거꾸로 반복하며 이어붙인다.

    var currentGen = 1
    var currentDirection = rotateDirection(d)
    var lastX = points.last!.x
    var lastY = points.last!.y

    while currentGen <= g {
        var nextPoints = points
        for point in points[1...].reversed() {
            let nextDirection = rotateDirection(point.d)
            let nextPoint = Position(x: lastX + dx[nextDirection], y: lastY + dy[nextDirection], d: nextDirection)
            lastX = lastX + dx[nextDirection]
            lastY = lastY + dy[nextDirection]
            nextPoints.append(nextPoint)
        }
        currentGen += 1
        points = nextPoints
    }

    return points
}

func countSquare(_ curves: [[Position]]) -> Int {
    var count = 0
    var grid = [[Bool]](repeating: [Bool](repeating: false, count: 101), count: 101)

    for curve in curves {
        for point in curve {
            guard isInbound(point.x, point.y) else { continue }
            grid[point.y][point.x] = true
        }
    }

    for r in 0...99 {
        for c in 0...99 {
            if grid[r][c] && grid[r+1][c] && grid[r][c+1] && grid[r+1][c+1] {
                count += 1
            }
        }
    }
    return count
}

func isInbound(_ x: Int, _ y: Int) -> Bool {
    return (0...100) ~= x && (0...100) ~= y
}

func rotateDirection(_ d: Int) -> Int {
    return (d + 1) % 4
}

struct Position {
    let x: Int
    let y: Int
    let d: Int
}