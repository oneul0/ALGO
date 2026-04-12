import Foundation

struct Tree {
    var age: Int
}

func readInts() -> [Int] {
    return readLine()!.split(separator: " ").map { Int($0)! }
}

func FTS() {
    let nmk = readInts()
    let N = nmk[0]
    let M = nmk[1]
    let K = nmk[2]
    
    var A = [[Int]](repeating: [Int](repeating: 0, count: N), count: N)
    for i in 0..<N {
        A[i] = readInts()
    }
    
    // 현재 땅의 양분
    var nutrients = [[Int]](repeating: [Int](repeating: 5, count: N), count: N)
    
    // 각 칸의 나무들, 나이 오름차순
    var trees = Array(repeating: Array(repeating: [Int](), count: N), count: N)
    
    for _ in 0..<M {
        let line = readInts()
        let x = line[0]-1
        let y = line[1]-1
        let z = line[2]
        trees[x][y].append(z)
    }
    
    // 초기 입력 정렬
    for i in 0..<N {
        for j in 0..<N {
            trees[i][j].sort()
        }
    }
    
    // 8방향
    let dx = [-1,-1,-1,0,0,1,1,1]
    let dy = [-1,0,1,-1,1,-1,0,1]
    
    for _ in 0..<K {
        // 봄 & 여름
        var deadNutrients = [[Int]](repeating: [Int](repeating: 0, count: N), count: N)
        
        for i in 0..<N {
            for j in 0..<N {
                if trees[i][j].isEmpty { continue }
                
                var newTrees = [Int]()
                var deadSum = 0
                
                // 나이가 어린 순서대로 양분 섭취
                for age in trees[i][j] {
                    if nutrients[i][j] >= age {
                        nutrients[i][j] -= age
                        newTrees.append(age+1)
                    } else {
                        // 여름에 양분으로 변환
                        deadSum += age / 2
                    }
                }
                trees[i][j] = newTrees
                deadNutrients[i][j] = deadSum
            }
        }
        
        // 여름: 죽은 나무가 양분으로 변환
        for i in 0..<N {
            for j in 0..<N {
                nutrients[i][j] += deadNutrients[i][j]
            }
        }
        
        // 가을: 번식
        var toAdd = Array(repeating: Array(repeating: 0, count: N), count: N)
        
        for i in 0..<N {
            for j in 0..<N {
                if trees[i][j].isEmpty { continue }
                for age in trees[i][j] {
                    if age % 5 == 0 {
                        for d in 0..<8 {
                            let nx = i + dx[d]
                            let ny = j + dy[d]
                            if nx < 0 || nx >= N || ny < 0 || ny >= N { continue }
                            toAdd[nx][ny] += 1
                        }
                    }
                }
            }
        }
        
        for i in 0..<N {
            for j in 0..<N {
                if toAdd[i][j] > 0 {
                    // 새 나무(나이 1)를 앞쪽에 추가하여 나이 순서 유지
                    trees[i][j].insert(contentsOf: Array(repeating: 1, count: toAdd[i][j]), at: 0)
                }
            }
        }
        
        // 겨울: 양분 추가
        for i in 0..<N {
            for j in 0..<N {
                nutrients[i][j] += A[i][j]
            }
        }
    }
    
    // 살아있는 나무 수 출력
    var total = 0
    for i in 0..<N {
        for j in 0..<N {
            total += trees[i][j].count
        }
    }
    print(total)
}

FTS()
