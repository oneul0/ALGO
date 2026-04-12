import Foundation

let N = Int(readLine()!)!
var result = [[Int]]()

for _ in 0..<N {
    result.append(readLine()!.split(separator: " ").map { Int($0)! })
}

var players = [2,3,4,5,6,7,8,9]
var answer = 0

func simulate(_ order: [Int]) -> Int {
    
    var score = 0
    var hitter = 0
    
    for inning in 0..<N {
        
        var outs = 0
        var b1 = 0, b2 = 0, b3 = 0
        
        while outs < 3 {
            
            let player = order[hitter] - 1
            let play = result[inning][player]
            
            switch play {
            case 0:
                outs += 1
                
            case 1:
                score += b3
                b3 = b2
                b2 = b1
                b1 = 1
                
            case 2:
                score += b3 + b2
                b3 = b1
                b2 = 1
                b1 = 0
                
            case 3:
                score += b3 + b2 + b1
                b3 = 1
                b2 = 0
                b1 = 0
                
            case 4:
                score += b3 + b2 + b1 + 1
                b1 = 0
                b2 = 0
                b3 = 0
                
            default:
                break
            }
            
            hitter = (hitter + 1) % 9
        }
    }
    
    return score
}

func dfs(_ depth: Int, _ used: inout [Bool], _ arr: inout [Int]) {
    
    if depth == 9 {
        answer = max(answer, simulate(arr))
        return
    }
    
    if depth == 3 {
        arr[depth] = 1
        dfs(depth+1, &used, &arr)
        return
    }
    
    for i in 0..<8 {
        if !used[i] {
            used[i] = true
            arr[depth] = players[i]
            dfs(depth+1, &used, &arr)
            used[i] = false
        }
    }
}

var used = [Bool](repeating: false, count: 8)
var order = [Int](repeating: 0, count: 9)

dfs(0, &used, &order)

print(answer)