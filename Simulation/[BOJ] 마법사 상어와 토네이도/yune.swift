let n = Int(readLine()!)!

var map = [[Int]]()
let range = (2...n+1)

input()

let move: [Direction] = [.left, .down, .right, .up]
var moveCount = 0
var distance = 1
var currentr = n/2 + 2
var currentc = n/2 + 2
var currentMoveIndex = 0
let sample = [0.02, 0.1, 0.07, 0.01, 005, 0.1, 0.07, 0.01, 0.02]
let possibillitysLeft = [[0, 0, 0.02, 0, 0],
                         [0, 0.1, 0.07, 0.01, 0],
                         [0.05, 0, 0, 0, 0],
                         [0, 0.1, 0.07, 0.01, 0],
                         [0, 0, 0.02, 0, 0]]
let possibillitysUp = [[0, 0, 0.05, 0, 0],
                       [0, 0.1, 0, 0.1, 0],
                       [0.02, 0.07, 0, 0.07, 0.02],
                       [0, 0.01, 0, 0.01, 0],
                       [0, 0, 0, 0, 0]]
let possibillitysRight = [[0, 0, 0.02, 0, 0],
                          [0, 0.01, 0.07, 0.1, 0],
                          [0, 0, 0, 0, 0.05],
                          [0, 0.01, 0.07, 0.1, 0],
                          [0, 0, 0.02, 0, 0]]
let possibillitysDown = [[0, 0, 0, 0, 0],
                         [0, 0.01, 0, 0.01, 0],
                         [0.02, 0.07, 0, 0.07, 0.02],
                         [0, 0.1, 0, 0.1, 0],
                         [0, 0, 0.05, 0, 0]]
let dr = [0, 1, 0, -1]
let dc = [-1, 0, 1, 0]

let possibillitys = [possibillitysLeft, possibillitysDown, possibillitysRight, possibillitysUp]

move: while !(currentr == 2 && currentc == 2) {
    moveCount += 1
    for _ in 0..<distance {
        let nr = currentr + dr[move[currentMoveIndex].rawValue]
        let nc = currentc + dc[move[currentMoveIndex].rawValue]
        
        scatterDust(nr, nc, move[currentMoveIndex])
        
        currentr = nr
        currentc = nc
        if currentr == 2 && currentc == 2 { break move }
    }
    currentMoveIndex = (currentMoveIndex + 1) % 4
    if moveCount == 2 {
        moveCount = 0
        distance += 1
    }
}

let answer = dustOutbounded()
print(answer)


func input() {
    map.append([Int](repeating: 0, count: n + 4))
    map.append([Int](repeating: 0, count: n + 4))
    
    for _ in 0..<n {
        let line = [0, 0] + readLine()!.split { $0 == " " }.map { Int(String($0))! } + [0, 0]
        map.append(line)
    }
    
    map.append([Int](repeating: 0, count: n + 4))
    map.append([Int](repeating: 0, count: n + 4))
}

func scatterDust(_ r: Int, _ c: Int, _ direction: Direction) {
    let currentDust = Double(map[r][c])
    var sampleDust = 0
    for i in sample.indices {
        sampleDust += Int(currentDust * sample[i])
    }
    if sampleDust > 0 {
        var sumScatterdDust = 0
        for i in 0..<5 {
            for j in 0..<5 {
                let scattend = Int(currentDust * possibillitys[direction.rawValue][i][j])
                map[r-2+i][c-2+j] += scattend
                sumScatterdDust += scattend
            }
        }
        let nr = r + dr[direction.rawValue]
        let nc = c + dc[direction.rawValue]
        map[nr][nc] += map[r][c] - sumScatterdDust
    }else {
        let nr = r + dr[direction.rawValue]
        let nc = c + dc[direction.rawValue]
        map[nr][nc] += map[r][c]
    }
    map[r][c] = 0
}

func dustOutbounded() -> Int {
    var dust = 0
    
    for i in 0..<n+4 {
        for j in 0..<n+4 {
            if range ~= i && range ~= j { continue }
            dust += map[i][j]
        }
    }
    return dust
}

enum Direction: Int {
    case left = 0
    case down
    case right
    case up
}