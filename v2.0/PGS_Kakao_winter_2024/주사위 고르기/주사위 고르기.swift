// import Foundation

// func solution(_ dice:[[Int]]) -> [Int] {
//     var result = [Int]()
//     var winCount = 0
//     let n = dice.count / 2
    
//     func selectDice(_ start: Int, _ dices: [Int]) {
//         if dices.count == n {
//             var aDices = [[Int]]()
//             var bDices = [[Int]]()
            
//             for i in dice.indices {
//                 if dices.contains(i) {
//                     aDices.append(dice[i])
//                 } else {
//                     bDices.append(dice[i])
//                 }
//             }
            
//             let aSums = getAllSums(aDices)
//             let bSums = getAllSums(bDices)
            
//             let score = countAWins(aSums, bSums)
//             if score > winCount {
//                 winCount = score
//                 result = dices
//             }
//             return
//         }
//         for i in dice.indices where i >= start {
//             selectDice(i+1, dices + [i])
//         }
//     }
//     func getAllSums(_ dice: [[Int]]) -> [Int] {
//         var res = [Int]()

//         func recursion(index: Int, sum: Int) {
//             if index == dice.count {
//                 res.append(sum)
//                 return
//             }

//             for i in 0..<6 {
//                 recursion(index: index+1, sum: sum + dice[index][i])
//             }
//         }

//         recursion(index: 0, sum: 0)

//         return res.sorted()
//     }
    
//     func countAWins(_ a: [Int], _ b: [Int]) -> Int {
//         var winCount = 0
        
//         for aSum in a {
//             var low = 0, high = b.count
//             while low < high {
//                 let mid = (low + high) / 2
//                 if b[mid] < aSum {
//                     low = mid + 1
//                 } else {
//                     high = mid
//                 }
//             }
//             winCount += low
//         }
//         return winCount
//     }
    
//     selectDice(0, [])
    
//     return result.map { $0 + 1}
// }

import Foundation

func solution(_ dice:[[Int]]) -> [Int] {
    let n = dice.count
    let half = n / 2
    var result = [[Int]: Int]() // 조합별 승리 횟수 저장
    
    // 🎲 모든 주사위 조합 선택
    func select(_ start: Int, _ picked: [Int]) {
        if picked.count == half {
            var aDice = [[Int]]()
            var bDice = [[Int]]()
            
            for i in 0..<n {
                if picked.contains(i) {
                    aDice.append(dice[i])
                } else {
                    bDice.append(dice[i])
                }
            }
            
            let aDist = getAllDiceSumDP(aDice)
            let bDist = getAllDiceSumDP(bDice)
            let winCount = countAWinsDP(aDist, bDist)
            result[picked, default: 0] += winCount
            return
        }
        
        for i in start..<n {
            select(i + 1, picked + [i])
        }
    }
    
    // 🎯 DP로 “합 분포” 계산
    func getAllDiceSumDP(_ dices: [[Int]]) -> [Int: Int] {
        var dp = [0: 1] // 합 0 = 1가지
        
        for die in dices {
            var next = [Int: Int]()
            for (sum, count) in dp {
                for face in die {
                    next[sum + face, default: 0] += count
                }
            }
            dp = next
        }
        return dp
    }
    
    // ⚔️ 두 분포 간 승리 횟수 계산
    func countAWinsDP(_ a: [Int: Int], _ b: [Int: Int]) -> Int {
        let bKeys = b.keys.sorted()
        var prefix = [Int](repeating: 0, count: bKeys.count + 1)
        
        // b 합의 누적 합(= prefix sum)
        for (i, key) in bKeys.enumerated() {
            prefix[i + 1] = prefix[i] + b[key]!
        }
        
        var winCount = 0
        for (aSum, aCount) in a {
            // 이진 탐색으로 b에서 aSum보다 작은 합 개수 찾기
            var low = 0, high = bKeys.count
            while low < high {
                let mid = (low + high) / 2
                if bKeys[mid] < aSum {
                    low = mid + 1
                } else {
                    high = mid
                }
            }
            // aSum이 이기는 경우의 수 = aCount * (b의 prefix[low])
            winCount += aCount * prefix[low]
        }
        return winCount
    }
    
    select(0, [])
    
    // 가장 승리 횟수가 많은 조합 반환
    if let best = result.max(by: { $0.value < $1.value }) {
        return best.key.map { $0 + 1 }
    }
    return []
}
