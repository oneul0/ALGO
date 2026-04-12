let nmk = readLine()!.split { $0 == " " }.map { Int(String($0))! },
    n = nmk[0],
    m = nmk[1],
    k = nmk[2]

var currentMap = [[[Fireball]]](repeating: [[Fireball]](repeating: [Fireball](), count: n), count: n)
var nextMap = [[[Fireball]]](repeating: [[Fireball]](repeating: [Fireball](), count: n), count: n)
var answer = 0

let evenDirections = [0, 2, 4, 6].map { Direction(rawValue: $0)! }
let oddDirections = [1, 3, 5, 7].map { Direction(rawValue: $0)! }

for _ in 0..<m {
    let rcmsd = readLine()!.split { $0 == " " }.map { Int(String($0))! }
    let FireBall = Fireball(m: rcmsd[2], s: rcmsd[3], d: Direction(rawValue: rcmsd[4])!)
    currentMap[rcmsd[0]-1][rcmsd[1]-1].append(FireBall)
}

for _ in 0..<k {
    moveFireball()
    integrateFireball()
}

for i in 0..<n {
    for j in 0..<n {
        let sum = currentMap[i][j].map { $0.m }.reduce(0, +)
        answer += sum
    }
}

print(answer)

func moveFireball() {
    for i in 0..<n {
        for j in 0..<n {
            if currentMap[i][j].isEmpty { continue }
            for fireball in currentMap[i][j] {
                let nr = (i + (fireball.d.sr * fireball.s) + (n * fireball.s)) % n
                let nc = (j + (fireball.d.sc * fireball.s) + (n * fireball.s)) % n
                let newFireball = Fireball(m: fireball.m, s: fireball.s, d: fireball.d)
                nextMap[nr][nc].append(newFireball)
            }
        }
    }
    currentMap = [[[Fireball]]](repeating: [[Fireball]](repeating: [Fireball](), count: n), count: n)
}

func integrateFireball() {
    for i in 0..<n {
        for j in 0..<n {
            if nextMap[i][j].count < 2 {
                if let fireball = nextMap[i][j].first {
                    currentMap[i][j].append(fireball)
                }
                continue
            }
            var m = 0
            var s = 0
            var isContainsOdd = false
            var isContainsEven = false

            for fireball in nextMap[i][j] {
                m += fireball.m
                s += fireball.s
                if fireball.d.rawValue % 2 == 0 {
                    isContainsEven = true
                } else {
                    isContainsOdd = true
                }
            }
            guard m / 5 > 0 else { continue }
            
            let directions: [Direction] =  {
                if (isContainsEven && isContainsOdd) == false {
                    return evenDirections
                }
                return oddDirections
            }()
            
            for direction in directions {
                currentMap[i][j].append(Fireball(m: m/5, s: s/nextMap[i][j].count, d: direction))
            }
        }
    }
    nextMap = [[[Fireball]]](repeating: [[Fireball]](repeating: [Fireball](), count: n), count: n)
}


enum Direction: Int {
    case up = 0
    case rightUp
    case right
    case rightDown
    case down
    case leftDown
    case left
    case leftUp
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

struct Fireball: Hashable {
    var m: Int
    var s: Int
    var d: Direction
}