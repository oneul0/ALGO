class Solution {
    func plusOne(_ digits: [Int]) -> [Int] {
        var answer = [Int]()
        var carry = 1

        for i in stride(from: digits.count-1, through: 0, by: -1) {
            var result = digits[i] + carry
            carry = 0
            if result == 10 {
                carry = 1
                result = 0
            }
            answer.append(result)
        }
        if carry == 1 { answer.append(carry) }
        
        return answer.reversed()
    }

    func validMountainArray(_ arr: [Int]) -> Bool {
        var shouldDec = false
        
        if arr.count == 1 { return false }
        
        for i in 0..<arr.count-1 {
            if arr[i] == arr[i+1] {
                return false
            }
            if i == 0 && arr[i] > arr[i+1] {
                return false
            }
            if arr[i] > arr[i+1] {
                shouldDec = true
                continue
            }
            if shouldDec && arr[i] < arr[i+1]{
                return false
            }
            if !shouldDec && arr[i] > arr[i+1]{
                return false
            }
        }
        return shouldDec
    }
}