let nk = readLine()!.split { $0 == " " }.map { Int(String($0))! },
    n = nk[0],
    k = nk[1]

var answer = ""
var history = [Int](repeating: -1, count: 200002)
var isVisited = [Bool](repeating: false, count: 200002)

func bfs() {
    var queue = [Int]()
    var index = 0
    queue.append(n)
    isVisited[n] = true

    while queue.count > index {
        let currentPoint = queue[index]

        if currentPoint == k {
            var shortestHistory = [k]
            var i = k
            while i != n {
                let prev = history[i]
                shortestHistory.append(prev)
                i = prev
            }
            answer.write("\(shortestHistory.count-1)\n")
            answer.write(shortestHistory.reversed().map { String($0) }.joined(separator: " "))
            return
        }
        
        let teleport = currentPoint * 2
        if (0...100000).contains(teleport), !isVisited[teleport] {
            queue.append(teleport)
            isVisited[teleport] = true
            history[teleport] = currentPoint
        }

        let go = currentPoint + 1
        if (0...100000).contains(go), !isVisited[go]  { 
            queue.append(go)
            isVisited[go] = true
            history[go] = currentPoint
        }

        let back = currentPoint - 1
        if (0...100000).contains(back), !isVisited[back] {
            queue.append(back)
            isVisited[back] = true
            history[back] = currentPoint
        }
        index += 1
    }
    
    return
}

bfs()
print(answer)