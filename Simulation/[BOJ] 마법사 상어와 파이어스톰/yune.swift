import Foundation

let nq = readLine()!.split { $0 == " " }.map { Int(String($0))! }
var n = Int(pow(2.0, Double(nq[0])))
let q = nq[1]

var map = [[Int]]()
for _ in 0..<n {
    let line = readLine()!.split { $0 == " " }.map { Int(String($0))! }
    map.append(line)
}

let ls = readLine()!.split { $0 == " " }.map { Int(String($0))! }

for l in ls {
    // 1. 2^L x 2^L 크기의 부분 격자들을 시계 방향으로 90도 회전
    if l > 0 {
        let rotateSize = Int(pow(2.0, Double(l)))
        map = rotateMatrix(matrix: map, rotateSize: rotateSize)
    }

    // 2. 얼음이 녹는 과정 (동시성 고려)
    map = meltIce(matrix: map)
}

// 3. 최종 결과 계산
let totalIce = sumAllElements(in: map)
let largestMass = findLargestMass(in: map)

print(totalIce)
print(largestMass)


func rotateMatrix(matrix: [[Int]], rotateSize: Int) -> [[Int]] {
    let size = matrix.count
    var newMatrix = matrix // 회전 결과를 담을 새로운 배열

    // 쿼드 트리 분할 및 회전
    for r in stride(from: 0, to: size, by: rotateSize) {
        for c in stride(from: 0, to: size, by: rotateSize) {
            rotateSubMatrixInPlace(&newMatrix, startX: c, startY: r, size: rotateSize)
        }
    }
    return newMatrix
}

func rotateSubMatrixInPlace<T>(_ matrix: inout [[T]], startX: Int, startY: Int, size: Int) {
    // 1. 전치 (Transpose)
    for row in 0..<size {
        for col in row..<size {
            let temp = matrix[startY + row][startX + col]
            matrix[startY + row][startX + col] = matrix[startY + col][startX + row]
            matrix[startY + col][startX + row] = temp
        }
    }
    // 2. 각 행을 뒤집기 (Column Reversal)
    for row in 0..<size {
        for col in 0..<size / 2 {
            let temp = matrix[startY + row][startX + col]
            matrix[startY + row][startX + col] = matrix[startY + row][startX + size - 1 - col]
            matrix[startY + row][startX + size - 1 - col] = temp
        }
    }
}

func meltIce(matrix: [[Int]]) -> [[Int]] {
    let rows = matrix.count
    let cols = matrix[0].count
    var newMatrix = matrix // 녹은 결과를 담을 새로운 배열

    let directions = [(-1, 0), (1, 0), (0, -1), (0, 1)]

    for r in 0..<rows {
        for c in 0..<cols {
            // 얼음이 있는 칸만 확인
            guard matrix[r][c] > 0 else { continue }
            var adjacentCount = 0

            for (dr, dc) in directions {
                let neighborR = r + dr
                let neighborC = c + dc

                // 인접 칸이 유효 범위 내에 있고, 얼음이 있다면 카운트
                if neighborR >= 0 && neighborR < rows && neighborC >= 0 && neighborC < cols && matrix[neighborR][neighborC] > 0 {
                    adjacentCount += 1
                }
            }

            // 인접한 얼음 칸이 3개 미만이면 녹임
            if adjacentCount < 3 {
                newMatrix[r][c] -= 1
            }
        }
    }
    return newMatrix
}

func sumAllElements(in matrix: [[Int]]) -> Int {
    return matrix.reduce(0) { total, row in
        total + row.reduce(0, +)
    }
}

func findLargestMass(in matrix: [[Int]]) -> Int {
    guard !matrix.isEmpty else { return 0 }
    let rows = matrix.count
    let cols = matrix[0].count
    var visited = Array(repeating: Array(repeating: false, count: cols), count: rows)
    var maxMass = 0
    let directions = [(0, 1), (0, -1), (1, 0), (-1, 0)]

    for r in 0..<rows {
        for c in 0..<cols {
            if matrix[r][c] > 0 && !visited[r][c] {
                var currentMass = 0
                var queue = [(row: Int, col: Int)]()
                
                queue.append((r, c))
                visited[r][c] = true
                
                var queueIndex = 0
                while queueIndex < queue.count {
                    let current = queue[queueIndex]
                    queueIndex += 1

                    let currentRow = current.row
                    let currentCol = current.col
                    currentMass += 1 // 덩어리 크기를 1씩 증가
                    
                    for (dr, dc) in directions {
                        let nextRow = currentRow + dr
                        let nextCol = currentCol + dc
                        
                        if nextRow >= 0 && nextRow < rows && nextCol >= 0 && nextCol < cols &&
                            !visited[nextRow][nextCol] && matrix[nextRow][nextCol] > 0 {
                            
                            visited[nextRow][nextCol] = true
                            queue.append((row: nextRow, col: nextCol))
                        }
                    }
                }
                maxMass = max(maxMass, currentMass)
            }
        }
    }
    return maxMass
}