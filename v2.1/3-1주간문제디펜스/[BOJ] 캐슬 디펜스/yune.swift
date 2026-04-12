import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! },
    N = input[0],
    M = input[1],
    D = input[2]

var origin = [[Int]]()
for _ in 0..<N {
    origin.append(readLine()!.split(separator: " ").map { Int($0)! })
}

var result = 0

func simulate(_ archers: [Int]) -> Int {
    var board = origin
    var kill = 0
    
    while true {
        var targets = Set<[Int]>()
        
        for a in archers {
            var best: (dist:Int, r:Int, c:Int)? = nil
            
            for r in 0..<N {
                for c in 0..<M {
                    if board[r][c] == 1 {
                        let dist = abs(N - r) + abs(a - c)
                        
                        if dist <= D {
                            if best == nil ||
                               dist < best!.dist ||
                               (dist == best!.dist && c < best!.c) {
                                best = (dist, r, c)
                            }
                        }
                    }
                }
            }
            
            if let b = best {
                targets.insert([b.r, b.c])
            }
        }
        
        for t in targets {
            if board[t[0]][t[1]] == 1 {
                board[t[0]][t[1]] = 0
                kill += 1
            }
        }
        
        board.removeLast()
        board.insert(Array(repeating: 0, count: M), at: 0)
        
        if !board.flatMap({$0}).contains(1) {
            break
        }
    }
    
    return kill
}

for i in 0..<M {
    for j in i+1..<M {
        for k in j+1..<M {
            result = max(result, simulate([i,j,k]))
        }
    }
}

print(result)