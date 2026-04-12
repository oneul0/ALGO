import Foundation

let input = readLine()!.split(separator: " ").map { Int($0)! }
let r = input[0] - 1
let c = input[1] - 1
let k = input[2]

var A = [[Int]]()

for _ in 0..<3 {
    A.append(readLine()!.split(separator: " ").map { Int($0)! })
}

func operateR() {
    var newA = [[Int]]()
    var maxLength = 0
    
    for row in A {
        var counter = [Int: Int]()
        for num in row where num != 0 {
            counter[num, default: 0] += 1
        }
        
        let sorted = counter.sorted {
            if $0.value == $1.value {
                return $0.key < $1.key
            }
            return $0.value < $1.value
        }
        
        var newRow = [Int]()
        for (num, count) in sorted {
            newRow.append(num)
            newRow.append(count)
        }
        
        if newRow.count > 100 {
            newRow = Array(newRow.prefix(100))
        }
        
        maxLength = max(maxLength, newRow.count)
        newA.append(newRow)
    }
    
    for i in 0..<newA.count {
        while newA[i].count < maxLength {
            newA[i].append(0)
        }
    }
    
    A = newA
}

func operateC() {
    let rows = A.count
    let cols = A[0].count
    var transposed = [[Int]](repeating: [Int](repeating: 0, count: rows), count: cols)
    
    for i in 0..<rows {
        for j in 0..<cols {
            transposed[j][i] = A[i][j]
        }
    }
    
    A = transposed
    operateR()
    
    let newRows = A.count
    let newCols = A[0].count
    var original = [[Int]](repeating: [Int](repeating: 0, count: newRows), count: newCols)
    
    for i in 0..<newRows {
        for j in 0..<newCols {
            original[j][i] = A[i][j]
        }
    }
    
    A = original
}

var time = 0

while time <= 100 {
    if r < A.count && c < A[0].count && A[r][c] == k {
        print(time)
        exit(0)
    }
    
    if time == 100 { break }
    
    if A.count >= A[0].count {
        operateR()
    } else {
        operateC()
    }
    
    time += 1
}

print(-1)