let n = Int(readLine()!)!

struct Job {
    var childs = [Int]()
    var indegree = 0
    var duration = 0
}
var jobs = [Job](repeating: Job(), count: n+1)

for i in 1...n {
    let jobInput = readLine()!.split { $0 == " " }.map { Int(String($0))! },
        duration = jobInput[0],
        prevJobs = jobInput[1],
        childJobs = jobInput[2...]
    jobs[i].duration = duration
    jobs[i].indegree = prevJobs

    for jobNumber in childJobs {
        jobs[jobNumber].childs.append(i)
    }
}

func solution() -> Int {
    var queue = [Int]()
    var index = 0
    var finishTimes = [Int](repeating: 0, count: n+1)
    for i in 1...n {
        if jobs[i].indegree == 0 {
            queue.append(i)
            finishTimes[i] = jobs[i].duration
        }
    }
    
    while queue.count > index{
        let currentJobNumber = queue[index]
        index += 1
        for childJobNumber in jobs[currentJobNumber].childs {
            // a b c -> d 일 때, c가 마지막 선행 작업일 경우
            // 아래 코드가 큐에 넣을 때 진행되면 c의 작업시간으로만 계산됨
            // 그러나 a나 b가 더 오랜 작업시간을 갖을 수 있으므로
            // 매번 작업 최댓값을 갱신해줌
            finishTimes[childJobNumber] = max(finishTimes[childJobNumber], finishTimes[currentJobNumber] + jobs[childJobNumber].duration)
            jobs[childJobNumber].indegree -= 1
            if jobs[childJobNumber].indegree == 0 {
                queue.append(childJobNumber)
            }
        }
    }
    
    return finishTimes.max()!
}

let answer = solution()

print(answer)