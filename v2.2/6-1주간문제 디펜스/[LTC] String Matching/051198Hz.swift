class Solution {
    func repeatedSubstringPattern(_ s: String) -> Bool {
        return (s + s).dropFirst().dropLast().contains(s)
    }

    func rotateString(_ s: String, _ goal: String) -> Bool {
        guard s.count == goal.count else {
            return false
        }

        return (s + s).contains(goal)
    }

    func repeatedStringMatch(_ a: String, _ b: String) -> Int {
        let setA = Set(a)
        let setB = Set(b)

        if setB.count > setA.count { return -1 }
        if setB.count == 1 && setA.count == 1 {
            return Int(ceil(Float(b.count) / Float(a.count)))
        }

        let maxTries = max(2, (b.count / a.count) + 2)

        var s = ""
        for i in 0..<maxTries {
            s += a

            if s.count < b.count { continue }
            if s.contains(b) { return i + 1}
        }

        return -1        
    }
}

class Example {
    func repeatedSubstringPattern(_ s: String) -> Bool {
        let chars = Array(s.utf8)
        let n = chars.count
        guard n > 1 else { return false }
        
        var lps = Array(repeating: 0, count: n)
        var length = 0
        var i = 1
        
        while i < n {
            if chars[i] == chars[length] {
                length += 1
                lps[i] = length
                i += 1
            } else if length > 0 {
                length = lps[length - 1]
            } else {
                lps[i] = 0
                i += 1
            }
        }
        
        let lastLPS = lps[n - 1]
        return lastLPS > 0 && n % (n - lastLPS) == 0
    }
}