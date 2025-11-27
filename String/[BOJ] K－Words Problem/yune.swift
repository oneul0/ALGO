import Foundation

func isPunc(_ s: String) -> Bool {
    return s.count == 1 && "!?,.".contains(s)
}

func capitalizeFirst(_ s: String) -> String {
    guard let first = s.first else { return s }
    let firstUp = String(first).uppercased()
    let rest = String(s.dropFirst())
    return firstUp + rest
}

func reconstruct(_ tokens: [String]) -> String {
    var out = ""
    for i in 0..<tokens.count {
        out += tokens[i]
        if i + 1 < tokens.count {
            if !isPunc(tokens[i+1]) {
                out += " "
            }
        }
    }
    return out
}

func applyOfKoreaRule(_ tokens: inout [String]) {
    var i = 0
    while i + 2 < tokens.count {
        let w = tokens[i]
        let of = tokens[i+1]
        let kr = tokens[i+2]
        if of == "of" && kr == "Korea" && !isPunc(w) {
            let newWord = "K-" + capitalizeFirst(w)
            tokens[i] = newWord
            tokens.remove(at: i+2)
            tokens.remove(at: i+1)
        } else {
            i += 1
        }
    }
}

func applyKoreaRule(_ tokens: inout [String]) {
    var i = tokens.count - 2
    while i >= 0 {
        if tokens[i] == "Korea" {
            let next = tokens[i+1]
            if !isPunc(next) {
                let newWord = "K-" + capitalizeFirst(next)
                tokens[i+1] = newWord
                tokens.remove(at: i)
            }
        }
        i -= 1
    }
}

func solveLine(_ line: String) -> String {
    var mod = ""
    for c in line {
        if !c.isLetter && !c.isNumber && c != "_" && c != "-" && !c.isWhitespace {
            mod.append(" ")
            mod.append(c)
            mod.append(" ")
        } else {
            mod.append(c)
        }
    }
    let raw = mod.trimmingCharacters(in: .whitespaces)
    let parts = raw.split { $0.isWhitespace }.map { String($0) }
    var tokens = parts

    applyOfKoreaRule(&tokens)
    applyKoreaRule(&tokens)

    return reconstruct(tokens)
}

if let nLine = readLine(), let N = Int(nLine) {
    for _ in 0..<N {
        if let line = readLine() {
            print(solveLine(line))
        }
    }
}