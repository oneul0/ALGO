import Foundation

// MARK: - FileIO

//func stringToAscii(_ str: String) -> Int {
//    str.map { $0.asciiValue! }.map { Int($0) }.reduce(0) {$0 + $1}
//}

struct FileIO {
    private let buffer:[UInt8]
    private var index: Int = 0
    
    init(fileHandle: FileHandle = FileHandle.standardInput) {
        
        buffer = Array(try! fileHandle.readToEnd()!)+[UInt8(0)] // 인덱스 범위 넘어가는 것 방지
    }
    
    @inline(__always) private mutating func read() -> UInt8 {
        defer { index += 1 }
        
        return buffer[index]
    }
    
    @inline(__always) mutating func readInt() -> Int {
        var sum = 0
        var now = read()
        var isPositive = true
        
        while now == 10
                || now == 32 { now = read() } // 공백과 줄바꿈 무시
        if now == 45 { isPositive.toggle(); now = read() } // 음수 처리
        while now >= 48, now <= 57 {
            sum = sum * 10 + Int(now-48)
            now = read()
        }
        
        return sum * (isPositive ? 1:-1)
    }
    
    mutating func readIntArray(_ K: Int) -> [Int] {
        var array = [Int]()
        
        for _ in 0..<K {
            array.append(readInt())
        }
        
        return array
    }
    
    @inline(__always) mutating func readString() -> String {
        var now = read()
        
        while now == 10 || now == 32 { now = read() } // 공백과 줄바꿈 무시
        let beginIndex = index-1
        
        while now != 10,
              now != 32,
              now != 0 { now = read() }
        
        return String(bytes: Array(buffer[beginIndex..<(index-1)]), encoding: .ascii)!
    }
    
    @inline(__always) mutating func readLine() -> String {
        var now = read()
        
        while now == 10 { now = read() } // 공백과 줄바꿈 무시
        let beginIndex = index-1
        
        while now != 10,
              now != 0 { now = read() }
        
        return String(bytes: Array(buffer[beginIndex..<(index-1)]), encoding: .ascii)!
    }
    
    @inline(__always) mutating func readString() -> Int {
        var str = 0
        var now = read()
        while now == 10 || now == 32 { now = read() } // 공백과 줄바꿈 무시
        while now != 10 && now != 32 && now != 0 {
            str += Int(now)
            now = read()
        }
        return str
    }
    
    mutating func readStringArray(_ K: Int) -> [String] {
        var array = [String]()
        
        for _ in 0..<K {
            array.append(readString())
        }
        
        return array
    }
    
    @inline(__always) mutating func readByteSequenceWithoutSpaceAndLineFeed() -> [UInt8] {
        var now = read()
        
        while now == 10 || now == 32 { now = read() } // 공백과 줄바꿈 무시
        let beginIndex = index-1
        
        while now != 10,
              now != 32,
              now != 0 { now = read() }
        
        return Array(buffer[beginIndex..<(index-1)])
    }
}

//    while !((0..<n)~=idx) { //인덱스가 0..<n에 속하지 않을 때 까지, 즉 0..<n의 범위에 들어올 때 까지
//        idx = (idx+n) % n
//    } //양수인 n에 대하여, 인덱스가 음수이면 인덱스 % n 은 음수인 인덱스를 그대로 반환한다.
//deque 쓰는것 보다 insert 쓰는게 훨 빠른듯,,ㅠ
// let (N, P) = (io.readInt(), io.readInt())
// 동시에 두 변수 입력받을 수 있음.
// [].allsatisfy { 만족해야 할 조건문 }
// 열고 닫는 괄호가 있고, 이를 제외하고는 stack에서 숫자만 사용해도 좋은 경우, 괄호를 유니크한 수(ex:0)으로 취급하여 괄호와 숫자를 구분할 수 있음
// 반복문 앞에 outer: 등으로 라벨링을 하면 continue outer등으로 다중 반복문일 때 해당 라벨의 다음 반복으로 넘어감
//
//var file = FileIO()
//String(repeating: "long ", count: Int(readLine()!)!) 특정 문자열로 반복되는 문자열을 만들 수 있음
//let numbers = [1, 2, 3, 4, 5]
////앞에서 2개 가져오기
//print(numbers.prefix(2))    // Prints "[1, 2]"
////최대 개수 넘어도 상관없음
//print(numbers.prefix(10))   // Prints "[1, 2, 3, 4, 5]"
////뒤에서 2개 가져오기
//print(numbers.suffix(2))    // Prints "[4, 5]"
////최대 개수 넘어도 상관없음
//print(numbers.suffix(10))   // Prints "[1, 2, 3, 4, 5]"

//한번에 String으로 만들어 출력하기 print("\(poses.map{ String(dict[$0]!) }.joined(separator: " "))")
// 문자열 안에 문자열이 포함여부 String.contains(String) == ( self.range(of: other) != nil )
//swift 적당히 큰수 Decimal

//let seq = file.readIntArray(number)
//lazy var dp = seq
//보다
//let seq = file.readIntArray(number)
//var dp = [Int](repeating: 0, count: number)
//...
//for i in seq.indices{
//dp[i] = seq[i]
//가 더 빠름
//
//var file = FileIO()
//let number = file.readInt()
//struct IncreaseSeq{
//    var length = Int.min
//    let seq = file.readIntArray(number)
//    var dp = [Int](repeating: 0, count: number)
//    mutating func getMaxLength()->Int{
//        for i in seq.indices{
//            dp[i] = seq[i]
//            for j in seq.indices where j < i{
//                if seq[j] < seq[i]{ dp[i] = max(dp[i], dp[j] + seq[i] ) }
//            }
//        }
//        return dp.max()!
//    }
//}
//var increaseSeq = IncreaseSeq()
//print(increaseSeq.getMaxLength())
var file = FileIO()
let number = file.readInt()
var t = [Int](repeating: 0, count: number + 1) //소요시간
var p = [Int](repeating: 0, count: number + 1) //이득
var dp = [Int](repeating: 0, count: number + 1)
for i in 0..<number{
    t[i] = file.readInt()
    p[i] = file.readInt()
}
for i in stride(from: number-1, through: 0, by: -1){
    if t[i] + i > number {
        dp[i] = dp[i+1]
        continue
    }
    dp[i] = max( p[i] + dp[i+t[i]], dp[i+1] )
//    dp[i] = max( p[i] + dp[i+t[i]], dp[i+1] ) -> dp[i+1]에서 인덱스 오류가 발생할 수 있음,
//    마지막값이 if문을 통과하고 이 코드를 실행할 때 t값이 2 이상이면 인덱스 에러가 발생함

}
print(dp[0])
