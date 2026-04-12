import Foundation

typealias Tuple = (Int, Int, Int)
let dx = [-1, 0, 1, 0]
let dy = [0, 1, 0, -1]


let nm = readLine()!.components(separatedBy:" ").map{Int($0)!}
let N = nm[0], M = nm[1] // x, y좌표
var arr = [[Int]]()
var cctvList = [Tuple]() // cctv를 담고있는 배열
var zeroCnt = 0 // 처음 0의 숫자
let posXRange = 0..<N 
let posYRange = 0..<M

for _ in 0..<N {
    arr.append(readLine()!.components(separatedBy:" ").map{Int($0)!})
}

for i in arr.indices {
    for j in arr[i].indices {
        if arr[i][j] >= 1 && arr[i][j] <= 5 { // cctv들은 cctv배열에 추가
            cctvList.append((arr[i][j], i, j))
        } else if arr[i][j] == 0 {
            zeroCnt += 1
        }
    }
}

// 한 줄을 방향에 맞게 처리하는 함수
func coverZeroOneline(_ pos: (Int, Int), _ dir: (Int), _ tmpArr: inout [[Int]]) -> Int {
    var posX = pos.0 + dx[dir]
    var posY = pos.1 + dy[dir]
    var coverZeroCnt = 0
    while posXRange ~= posX && posYRange ~= posY && tmpArr[posX][posY] != 6 {
        if tmpArr[posX][posY] == 0 {
            tmpArr[posX][posY] = -1
            coverZeroCnt += 1
        }
        posX = posX + dx[dir]
        posY = posY + dy[dir]
    } 
    return coverZeroCnt
}

// cctv타입에 따라 라인을 처리하는 ㅎ마수
func runCCTV(_ cctvType: Int, _ cctvPos: (Int, Int), _ dir: Int, _ tmpArr: inout [[Int]]) -> Int {
    var coverToCnt = 0
    if cctvType == 1 {
        coverToCnt += coverZeroOneline(cctvPos, dir, &tmpArr)
    } else if cctvType == 2 {
        [0, 2].forEach { dirCnt in 
            coverToCnt += coverZeroOneline(cctvPos, (dir + dirCnt) % 4, &tmpArr)              
        } 
    } else if cctvType == 3 {
        (0...1).forEach { dirCnt in 
            coverToCnt += coverZeroOneline(cctvPos, (dir + dirCnt) % 4, &tmpArr)                
        }
    } else if cctvType == 4 {
        (0...2).forEach { dirCnt in 
            coverToCnt += coverZeroOneline(cctvPos, (dir + dirCnt) % 4, &tmpArr)                
        }
    } else if cctvType == 5 {
        (0...3).forEach { dirCnt in 
            coverToCnt += coverZeroOneline(cctvPos, (dir + dirCnt) % 4, &tmpArr)                
        }
    }
    return coverToCnt
}

var Ans = Int.max
func DFS(_ cctvIdx: Int, _ coverCnt: Int, _ arr: [[Int]]) {
    if cctvIdx >= cctvList.count {
        let temp = zeroCnt - coverCnt
        Ans = Ans > temp ? temp : Ans // 현재 커버한게 더 많다면 temp가 적어지므로 적은 값이 Ans가 된다.
        return
    }
    
    let newCCTV = cctvList[cctvIdx]
    var tmpArr = arr
    for dir in 0..<4 {
        let cctvType = newCCTV.0
        let cctvPos = (newCCTV.1, newCCTV.2)
        
        let newCoverCnt = runCCTV(cctvType, cctvPos, dir, &tmpArr)
        DFS(cctvIdx+1, coverCnt + newCoverCnt, tmpArr)
        tmpArr = arr
    }
    
}

DFS(0, 0, arr)
print(Ans)