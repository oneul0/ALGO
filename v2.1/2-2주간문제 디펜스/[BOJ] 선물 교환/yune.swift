import Foundation

let N = Int(readLine()!)!

var outGraph = [[Int]](repeating: [Int](), count: N+1)
var inGraph = [[Int]](repeating: [Int](), count: N + 1)
var presentCountList = [Int](repeating: 0, count: N+1)

for i in 1...N {
    let line = readLine()!.split { $0 == " " }.map { Int(String($0))! }
    outGraph[i].append(line[0])
    outGraph[i].append(line[1])
    inGraph[line[0]].append(i)
    inGraph[line[1]].append(i)
    presentCountList[line[0]] += 1
    presentCountList[line[1]] += 1
}

bfs()

func bfs() {
    var queue = [Int]()
    var index = 0
    var isVisited = [Bool](repeating: false, count: N+1)
    for i in 1...N {
        if inGraph[i].count < 2 {
            queue.append(i)
        }
    }
    while queue.count > index {
        let currentNode = queue[index]
        if isVisited[currentNode] {
            index += 1
            continue
        }
        isVisited[currentNode] = true
        // 2개 미만의 선물을 받은 학생이 선물을 준 학생들의 선물갯수를 깎는다.
        for child in outGraph[currentNode] {
            presentCountList[child] -= 1
            if presentCountList[child] < 2 {
                queue.append(child)
            }
        }
        for parent in inGraph[currentNode] {
            queue.append(parent)
        }
        index += 1
    }
    var count = 0
    var answer = ""
    for i in 1...N {
        guard presentCountList[i] > 1 else { continue }
        count += 1
        answer += "\(i) "
    }
    print(count)
    if count > 0 {
        print(answer)
    }
}