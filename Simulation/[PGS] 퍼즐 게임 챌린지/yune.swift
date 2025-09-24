import Foundation

func solution(_ diffs: [Int], _ times: [Int], _ limit: Int64) -> Int {
    let limit = Int(limit)
    var low = 1
    var high = 100001
    var answer = high
    
    // 주어진 숙련도로 모든 퍼즐을 시간 내에 풀 수 있는지 확인하는 함수
    func canSolve(level: Int) -> Bool {
        var totalTime = 0
        
        for i in 0..<diffs.count {
            let diff = diffs[i]
            let timeCur = times[i]
            
            if diff > level {
                let timePrev = (i > 0) ? times[i-1] : 0
                totalTime += (timeCur + timePrev) * (diff - level)
            }
            totalTime += timeCur
            
            if totalTime > limit {
                return false
            }
        }
        return true
    }
    
    while low <= high {
        let mid = (low + high) / 2
        
        if canSolve(level: mid) {
            answer = mid
            high = mid - 1
        } else {
            low = mid + 1
        }
    }
    
    return answer
}