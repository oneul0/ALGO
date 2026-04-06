import Foundation

let dice = readLine()!.split(separator: " ").map { Int($0)! }

var next = Array(repeating: 0, count: 33)
var blue = Array(repeating: -1, count: 33)
var score = Array(repeating: 0, count: 33)

for i in 0..<20 {
    next[i] = i + 1
    score[i] = i * 2
}

score[20] = 40
next[20] = 32

next[32] = 32

blue[5] = 21
blue[10] = 25
blue[15] = 27

score[21] = 13
next[21] = 22
score[22] = 16
next[22] = 23
score[23] = 19
next[23] = 24

score[25] = 22
next[25] = 26
score[26] = 24
next[26] = 24

score[27] = 28
next[27] = 28
score[28] = 27
next[28] = 29
score[29] = 26
next[29] = 24

score[24] = 25
next[24] = 30
score[30] = 30
next[30] = 31
score[31] = 35
next[31] = 20

var pieces = [Int](repeating: 0, count: 4)
var answer = 0

dfs(0, 0)
print(answer)

func move(_ cur: Int, _ dist: Int) -> Int {
    var cur = cur
    
    if blue[cur] != -1 {
        cur = blue[cur]
    } else {
        cur = next[cur]
    }
    
    for _ in 1..<dist {
        if cur == 32 { break }
        cur = next[cur]
    }
    
    return cur
}

func dfs(_ turn: Int, _ sum: Int) {
    if turn == 10 {
        answer = max(answer, sum)
        return
    }
    
    for i in 0..<4 {
        let cur = pieces[i]
        if cur == 32 { continue }
        
        let nxt = move(cur, dice[turn])
        
        var valid = true
        for j in 0..<4 {
            if i != j && pieces[j] == nxt && nxt != 32 {
                valid = false
                break
            }
        }
        
        if !valid { continue }
        
        pieces[i] = nxt
        dfs(turn + 1, sum + score[nxt])
        pieces[i] = cur
    }
}