import Foundation

var ans = 0
var b = Array(repeating: Array(repeating: 0, count: 6), count: 4)
var g = Array(repeating: Array(repeating: 0, count: 4), count: 6)

let tc = Int(readLine()!)!

for _ in 0..<tc {
    let input = readLine()!.split(separator: " ").map { Int($0)! }
    let t = input[0]
    let x = input[1]
    let y = input[2]

    moveBlue(t, x)
    moveGreen(t, y)
}

var cntB = 0
var cntG = 0

for i in 0..<4 {
    for j in 2..<6 {
        if b[i][j] == 1 {
            cntB += 1
        }
    }
}

for i in 2..<6 {
    for j in 0..<4 {
        if g[i][j] == 1 {
            cntG += 1
        }
    }
}

print(ans)
print(cntB + cntG)

func checkBlue() {
    for j in 2..<6 {
        var cnt = 0
        for i in 0..<4 {
            if b[i][j] == 1 {
                cnt += 1
            }
        }
        if cnt == 4 {
            removeBlue(j)
            ans += 1
        }
    }
}

func checkGreen() {
    for i in 2..<6 {
        var cnt = 0
        for j in 0..<4 {
            if g[i][j] == 1 {
                cnt += 1
            }
        }
        if cnt == 4 {
            removeGreen(i)
            ans += 1
        }
    }
}

func removeBlue(_ idx: Int) {
    for j in stride(from: idx, to: 0, by: -1) {
        for i in 0..<4 {
            b[i][j] = b[i][j - 1]
        }
    }
    for i in 0..<4 {
        b[i][0] = 0
    }
}

func removeGreen(_ idx: Int) {
    for i in stride(from: idx, to: 0, by: -1) {
        for j in 0..<4 {
            g[i][j] = g[i - 1][j]
        }
    }
    for j in 0..<4 {
        g[0][j] = 0
    }
}

func moveBlue(_ t: Int, _ x: Int) {
    var y = 1

    if t == 1 || t == 2 {
        while true {
            if y + 1 > 5 || b[x][y + 1] == 1 {
                b[x][y] = 1
                if t == 2 {
                    b[x][y - 1] = 1
                }
                break
            }
            y += 1
        }
    } else {
        while true {
            if y + 1 > 5 || b[x][y + 1] != 0 || b[x + 1][y + 1] != 0 {
                b[x][y] = 1
                b[x + 1][y] = 1
                break
            }
            y += 1
        }
    }

    checkBlue()

    for j in 0..<2 {
        for i in 0..<4 {
            if b[i][j] == 1 {
                removeBlue(5)
                break
            }
        }
    }
}

func moveGreen(_ t: Int, _ y: Int) {
    var x = 1

    if t == 1 || t == 3 {
        while true {
            if x + 1 > 5 || g[x + 1][y] == 1 {
                g[x][y] = 1
                if t == 3 {
                    g[x - 1][y] = 1
                }
                break
            }
            x += 1
        }
    } else {
        while true {
            if x + 1 > 5 || g[x + 1][y] == 1 || g[x + 1][y + 1] == 1 {
                g[x][y] = 1
                g[x][y + 1] = 1
                break
            }
            x += 1
        }
    }

    checkGreen()

    for i in 0..<2 {
        for j in 0..<4 {
            if g[i][j] == 1 {
                removeGreen(5)
                break
            }
        }
    }
}