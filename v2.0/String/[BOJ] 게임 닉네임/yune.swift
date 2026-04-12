final class Trie<T: BidirectionalCollection> where T.Element: Hashable {
    class Node {
        var childList = [T.Element: Node]()
        var endOfWord: Bool

        init(_ _endOfWord: Bool) {
            endOfWord = _endOfWord
        }

        func hasChild(for c: T.Element) -> Node? {
            return childList[c]
        }
        func addChild(for key: T.Element) -> Node {
            let child = Node(false)
            childList.updateValue(child, forKey: key)
            return child
        }
    }

    let root = Node(false)

    func insert(_ word: T) -> [T.Element] {
        var prefix = [T.Element]()
        var isShortestPrefix = true
        // 단어를 입력받는다.
        // 단어의 알파벳을 순회하며
        // 루트노드 부터 해당 알파벳이 존재한다면
        // 그냥 쭉쭉 그 노드로 넘기고 현재 탐색 노드를 그 노드의 해당 자식 노드로 교체
        // 존재하지 않는다면, 탐색중인 노드에 새 자식 노드를 등록하고 해당 자식 노드로 교체
        var currentNode = root
        for c in word {
            if let child = currentNode.hasChild(for: c) {
                prefix.append(c)
                currentNode = child
                continue
            }
            currentNode = currentNode.addChild(for: c)
            guard isShortestPrefix else { continue }
            prefix.append(c)
            isShortestPrefix = false
        }
        currentNode.endOfWord = true

        return prefix
    }

    func contains(_ target: T) -> Bool {
        var currentNode = root
        for element in target {
            guard let nextNode = currentNode.hasChild(for: element) else { return false }
            currentNode = nextNode
        }
        return currentNode.endOfWord
    }

    @discardableResult
    func delete(_ target: T) -> Bool {
        var currentNode = root
        // 삭제할 대상의 마지막 문자열 노드까지 내려감
        for element in target {
            guard let nextNode = currentNode.hasChild(for: element) else { return false }
            currentNode = nextNode
        }
        // 마지막 노드까지 내려갔는데, 대상의 끝이 아니라면 삭제 불가
        guard currentNode.endOfWord else { return false }
        currentNode.endOfWord = false
        return true
    }
}


let n = Int(readLine()!)!
let trie = Trie<String>()
var wordCountMap = [String: Int]()

var answer = ""

for _ in 0..<n {
    let word = readLine()!
    let nickName = trie.insert(word)
    if let count = wordCountMap[word] {
        wordCountMap[word]! += 1
        answer.write(word + "\(count+1)\n")
    } else {
        wordCountMap[word] = 1
        answer.write(String(nickName)+"\n")
    }
}

print(answer)