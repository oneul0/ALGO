import Foundation

// 주어진 문자열과 진법을 10진수로 변환하는 함수
// 진법에 맞지 않는 숫자가 포함되면 nil을 반환합니다.
func convertToBase10(_ s: String, base: Int) -> Int? {
    for char in s {
        guard let digit = Int(String(char)) else { return nil }
        if digit >= base {
            return nil
        }
    }
    return Int(s, radix: base)
}

func solution(_ expressions: [String]) -> [String] {
    var minBase = 2
    
    // 1. 모든 수식에 사용된 가장 큰 숫자를 찾아 최소 진법을 결정합니다.
    for expr in expressions {
        for char in expr {
            if char.isNumber {
                minBase = max(minBase, Int(String(char))! + 1)
            }
        }
    }
    
    // 2. 모든 온전한 수식을 만족하는 유효 진법을 모두 찾습니다.
    var validBases = Set<Int>()
    for base in minBase...9 {
        var isBaseValidForAll = true
        for expr in expressions {
            if !expr.contains("X") {
                let components = expr.components(separatedBy: " ")
                let A_str = components[0]
                let op = components[1]
                let B_str = components[2]
                let C_str = components[4]
                
                guard let valA = convertToBase10(A_str, base: base),
                      let valB = convertToBase10(B_str, base: base),
                      let valC = convertToBase10(C_str, base: base) else {
                    isBaseValidForAll = false
                    break
                }
                
                if op == "+" {
                    if valA + valB != valC {
                        isBaseValidForAll = false
                        break
                    }
                } else { // "-"
                    if valA - valB != valC {
                        isBaseValidForAll = false
                        break
                    }
                }
            }
        }
        if isBaseValidForAll {
            validBases.insert(base)
        }
    }
    
    // 3. X 수식만 복원하여 결과를 반환합니다.
    var restoredXExpressions = [String]()
    for expr in expressions {
        if expr.contains("X") {
            let components = expr.components(separatedBy: " ")
            let A_str = components[0]
            let op = components[1]
            let B_str = components[2]
            
            var possibleResults = Set<String>()
            for base in validBases {
                guard let valA = convertToBase10(A_str, base: base),
                      let valB = convertToBase10(B_str, base: base) else { continue }
                
                var result: Int
                if op == "+" {
                    result = valA + valB
                } else {
                    result = valA - valB
                }
                
                let result_str = String(result, radix: base)
                possibleResults.insert(result_str)
            }
            
            if possibleResults.count == 1 {
                if let result_str = possibleResults.first, let base_val = validBases.first {
                    // 기댓값은 10진수가 아닌, 진법 표기법에 맞는 값이어야 함
                    restoredXExpressions.append("\(A_str) \(op) \(B_str) = \(result_str)")
                }
            } else {
                restoredXExpressions.append("\(A_str) \(op) \(B_str) = ?")
            }
        }
    }
    
    return restoredXExpressions
}