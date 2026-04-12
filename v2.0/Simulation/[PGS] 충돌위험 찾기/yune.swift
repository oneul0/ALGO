import Foundation

func solution(_ points: [[Int]], _ routes: [[Int]]) -> Int {
    
    // 포인트 번호를 인덱스로 사용하기 위한 맵
    let pointMap = Dictionary(uniqueKeysWithValues: (1...points.count).map { ($0, points[$0-1]) })
    
    // 로봇들의 시간별 위치를 저장할 딕셔너리
    var robotPaths: [Int: [[Int]]] = [:]
    
    // 각 로봇의 이동 경로를 시간 순서대로 계산하여 저장
    for (robotIndex, route) in routes.enumerated() {
        var currentPath: [[Int]] = []
        
        var startPoint = pointMap[route[0]]!
        currentPath.append(startPoint) // 0초 위치 추가
        
        for i in 0..<route.count - 1 {
            let start = pointMap[route[i]]!
            let end = pointMap[route[i+1]]!
            
            // r 좌표 먼저 이동
            var r = start[0]
            while r != end[0] {
                r += (end[0] > r) ? 1 : -1
                currentPath.append([r, start[1]])
            }
            
            // c 좌표 이동
            var c = start[1]
            while c != end[1] {
                c += (end[1] > c) ? 1 : -1
                currentPath.append([r, c])
            }
        }
        robotPaths[robotIndex] = currentPath
    }
    
    // 모든 로봇이 운송을 마칠 때까지 필요한 최대 시간 계산
    var maxTime = 0
    for path in robotPaths.values {
        maxTime = max(maxTime, path.count)
    }
    
    // 시간대별 충돌 횟수 계산
    var collisionCount = 0
    var checkedCollisions: Set<String> = [] // 중복 충돌 방지를 위한 Set
    
    for time in 0..<maxTime {
        var timeCollisions: [String: Int] = [:] // 특정 시간대의 위치별 로봇 수
        
        for robotIndex in 0..<routes.count {
            if let path = robotPaths[robotIndex], time < path.count {
                let location = path[time]
                let locationString = "\(location[0]),\(location[1])"
                timeCollisions[locationString, default: 0] += 1
            }
        }
        
        // 충돌 위험이 있는 위치 확인
        for (location, count) in timeCollisions {
            if count >= 2 {
                // 이전에 확인하지 않은 충돌만 계산
                if !checkedCollisions.contains("\(time)-\(location)") {
                    collisionCount += 1
                    checkedCollisions.insert("\(time)-\(location)")
                }
            }
        }
    }
    
    return collisionCount
}