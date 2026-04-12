let nm = readLine()!.split { $0 == " " }.map { Int(String($0))! },
    n = nm[0],
    m = nm[1]

let dr = [0, 0, -1, 1]
let dc = [-1, 1, 0, 0]
let crossr = [-1, 1, 1, -1]
let crossc = [-1, -1, 1, 1]

var map = [[Int]]()
var currentClouds = [
    Cloud(r: n - 1, c: 0),
    Cloud(r: n - 1, c: 1),
    Cloud(r: n - 2, c: 0),
    Cloud(r: n - 2, c: 1),
]
var newClouds = [Cloud]()

for _ in 0..<n {
    let line = readLine()!.split { $0 == " " }.map { Int(String($0))! }
    map.append(line)
}

for _ in 0..<m {
    let ds = readLine()!.split { $0 == " " }.map { Int(String($0))! }
    let d = Direction(rawValue: ds[0])!
    let s = ds[1]

    moveCloud(dir: d, s: s)
    increaseWaterAmount()
    waterCopyCheat()
    makeCloud()
    disapearClouds()
    decreaseWaterAmount()
}

var answer = 0

for i in 0..<n {
    for j in 0..<n {
        answer += map[i][j]
    }
}

print(answer)

func moveCloud(dir: Direction, s: Int) {
    for i in currentClouds.indices {
        let nr = (currentClouds[i].r + (dir.sr * s) + (n * s)) % n
        let nc = (currentClouds[i].c + (dir.sc * s) + (n * s)) % n
        currentClouds[i].r = nr
        currentClouds[i].c = nc
    }
}

func increaseWaterAmount() {
    for cloud in currentClouds {
        let r = cloud.r
        let c = cloud.c
        map[r][c] += 1
    }
}

func waterCopyCheat() {
    for cloud in currentClouds {
        let r = cloud.r
        let c = cloud.c
        for i in 0..<4 {
            let diagonalr = r + crossr[i]
            let diagonalc = c + crossc[i]
            guard (0..<n) ~= diagonalr, (0..<n) ~= diagonalc else { continue }
            guard map[diagonalr][diagonalc] > 0 else { continue }
            map[r][c] += 1
        }
    }
}

func makeCloud() {
    for i in 0..<n {
        for j in 0..<n {
            if map[i][j] < 2 { continue }
            let cloud = Cloud(r: i, c: j)
            if currentClouds.contains(cloud) { continue }
            newClouds.append(cloud)
        }
    }
}

func disapearClouds() {
    currentClouds = newClouds
    newClouds = []
}

func decreaseWaterAmount() {
    for cloud in currentClouds {
        let r = cloud.r
        let c = cloud.c
        map[r][c] -= 2
    }
}

enum Direction: Int {
    case left = 1
    case leftUp
    case up
    case rightUp
    case right
    case rightDown
    case down
    case leftDown
}
extension Direction {
    var sr: Int {
        switch self {
        case .left: return 0
        case .leftUp: return -1
        case .up: return -1
        case .rightUp: return -1
        case .right: return 0
        case .rightDown: return 1
        case .down: return 1
        case .leftDown: return 1
        }
    }

    var sc: Int {
        switch self {
        case .left: return -1
        case .leftUp: return -1
        case .up: return 0
        case .rightUp: return 1
        case .right: return 1
        case .rightDown: return 1
        case .down: return 0
        case .leftDown: return -1
        }
    }
}

struct Cloud: Hashable {
    var r: Int
    var c: Int
}

struct Command {
    let direction: Direction
    let count: Int
}