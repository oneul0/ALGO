while let input = readLine(), input != "#" {
    var stack = [String]()
    var isLegal = true
    var i = input.startIndex

    while i < input.endIndex {
        if input[i] == "<" {
            guard let closeIndex = input[i...].firstIndex(of: ">") else {
                isLegal = false
                break
            }

            let tag = input[input.index(after: i)..<closeIndex]

            if tag.hasSuffix("/") {

            } else if tag.first == "/" {
                let tagName = String(tag.dropFirst())
                if stack.last == tagName {
                    stack.removeLast()
                } else {
                    isLegal = false
                    break
                }
            } else {
                let tagName = tag.split { $0 == " " }[0]
                stack.append(String(tagName))
            }

            i = input.index(after: closeIndex)
        } else {
            i = input.index(after: i)
        }
    }

    if !stack.isEmpty { 
        isLegal = false
    }
    print(isLegal ? "legal" : "illegal")
}