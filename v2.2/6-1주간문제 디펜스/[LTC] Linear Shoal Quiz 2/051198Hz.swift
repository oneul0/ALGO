class Solution {
    func removeDuplicateLetters(_ s: String) -> String {
        let chars = Array(s)

        var lastIndex = [Character: Int]()

        for (i, ch) in chars.enumerated() {
            lastIndex[ch] = i
        }

        var stack = [Character]()
        var inStack = Set<Character>()

        for (i, ch) in chars.enumerated() {

            if inStack.contains(ch) {
                continue
            }

            while let top = stack.last,
                  top > ch,
                  lastIndex[top]! > i {

                stack.removeLast()
                inStack.remove(top)
            }

            stack.append(ch)
            inStack.insert(ch)
        }

        return String(stack)
    }
}