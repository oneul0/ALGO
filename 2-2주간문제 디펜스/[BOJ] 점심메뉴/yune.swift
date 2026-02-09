import Foundation

let fio = FileIO()

let N = fio.readInt()
let Q = fio.readInt()

var menuList = [Menu]()
for _ in 0..<N {
    menuList.append(Menu(hot: fio.readInt(), sweet: fio.readInt()))
}
menuList.sort { $0.hot < $1.hot }

var answer = ""
for _ in 0..<Q {
    let lowerHot = fio.readInt()
    let upperHot = fio.readInt()
    let lowerSweet = fio.readInt()
    let upperSweet = fio.readInt()
    let i = lowerBound(menuList, lowerHot)
    let j = upperBound(menuList, upperHot)
    let count = findMenuCount(lowerSweet, upperSweet, menuList[i..<j])
    answer += "\(count)\n"
}

print(answer)

func lowerBound(_ array: [Menu], _ target: Int) -> Int {
    var low = 0
    var high = array.count
    while low < high {
        let mid = low + (high - low) / 2
        if array[mid].hot < target {
            low = mid + 1
        } else {
            high = mid
        }
    }
    return low
}

func upperBound(_ array: [Menu], _ target: Int) -> Int {
    var low = 0
    var high = array.count
    while low < high {
        let mid = low + (high - low) / 2
        if array[mid].hot <= target {
            low = mid + 1
        } else {
            high = mid
        }
    }
    return low
}

func findMenuCount(_ lower: Int, _ upper: Int, _ menuList: ArraySlice<Menu>) -> Int {
    var count = 0
    for menu in menuList {
        guard (lower...upper) ~= menu.sweet else { continue }
        count += 1
    }

    return count
}

struct Menu {
    let hot: Int
    let sweet: Int
}

class FileIO {
    private let buffer: [UInt8]
    private var index: Int = 0

    init(fileHandle: FileHandle = FileHandle.standardInput) {
        // 파일을 한꺼번에 읽어와 버퍼에 저장
        self.buffer = Array(try! fileHandle.readToEnd()!) + [UInt8(0)]
    }

    @inline(__always) private func read() -> UInt8 {
        defer { index += 1 }
        return buffer[index]
    }

    // 정수 읽기 (Int)
    @inline(__always) func readInt() -> Int {
        var sum = 0
        var now = read()
        var isPositive = true

        while now == 10 || now == 32 { now = read() }  // 공백과 줄바꿈 무시
        if now == 45 { isPositive.toggle(); now = read() }  // 음수 처리 '-'
        while now >= 48 && now <= 57 {
            sum = sum * 10 + Int(now - 48)
            now = read()
        }

        return isPositive ? sum : -sum
    }

    // 문자열 읽기
    @inline(__always) func readString() -> String {
        var now = read()
        while now == 10 || now == 32 { now = read() }  // 공백 무시
        let beginIndex = index - 1
        while now != 10 && now != 32 && now != 0 { now = read() }

        return String(bytes: buffer[beginIndex..<(index - 1)], encoding: .ascii)!
    }
}