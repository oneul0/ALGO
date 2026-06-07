class Solution {
    func detectCapitalUse(_ word: String) -> Bool {
        
        func isUppercased(_ c: Character) -> Bool {
            return !Array("abcdefghijklmnopqrstuvwxyz").contains(c)
        }

        let arr = Array(word)
        var isCapitalWords = false

        for (i, c) in arr.reversed().enumerated() {
            if i == 0, isUppercased(c) {
                isCapitalWords = true
                continue
            }
            if i == arr.count-1 && isCapitalWords && !isUppercased(c) {
                return false
            }
            //소문자인데, 대문자여야 하는 경우
            if isCapitalWords && !isUppercased(c) {
                return false
            }
            // 대문자인데, 소문자여야 하는 경우
            if !isCapitalWords && isUppercased(c) && i != arr.count-1 {
                return false
            }
        }

        return true
    }

    func licenseKeyFormatting(_ s: String, _ k: Int) -> String {
        var key = s.uppercased().split { $0 == "-"}.joined()
        
        var answer = [String]()
        var group = ""
        var count = 0

        for c in key.reversed() {
            count += 1

            group.append(c)

            if count % k == 0 {
                answer.append(String(group.reversed()))
                group = ""
                count = 0
            }
        }

        if !group.isEmpty {
            answer.append(String(group.reversed()))
        }

        return answer.reversed().joined(separator: "-")
    }
    
    func maskPII(_ s: String) -> String {
        if s.contains("@") {
            let email = s.lowercased()

            let parts = email.split(separator: "@")
            let name = String(parts[0])
            let domain = String(parts[1])

            let first = name.first!
            let last = name.last!

            return "\(first)*****\(last)@\(domain)"
        }

        let digits = s.filter(\.isNumber)

        let countryLength = digits.count - 10
        let last4 = digits.suffix(4)

        if countryLength == 0 {
            return "***-***-\(last4)"
        }

        return "+" +
            String(repeating: "*", count: countryLength) +
            "-***-***-\(last4)"
    }
}

class Example {
    func licenseKeyFormatting(_ s: String, _ k: Int) -> String {
        let s = s.replacing("-", with: "").uppercased()
        var r = s.count % k == 0 ? "" : s.prefix(s.count % k) + "-"
        var z = s.dropFirst(s.count % k)
        while !z.isEmpty {
            r += z.prefix(k) + "-"
            z = z.dropFirst(k)
        }
        return String(r.dropLast())
    }

    func detectCapitalUse(_ word: String) -> Bool {
        word.allSatisfy { $0.isLowercase }
        || word.allSatisfy { $0.isUppercase }
        || word.first?.isUppercase == true && word.dropFirst(1).allSatisfy { $0.isLowercase }
    }
}