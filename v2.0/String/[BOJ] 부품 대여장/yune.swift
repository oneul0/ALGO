import Foundation

func toMinutes(_ dateStr: String, _ timeStr: String) -> Int {
    let ds = dateStr.split(separator: "-").map { Int($0)! }
    let ts = timeStr.split(separator: ":").map { Int($0)! }
    let year = ds[0]
    let month = ds[1]
    let day = ds[2]
    let hour = ts[0]
    let minute = ts[1]

    let monthDays = [0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    var totalDays = 0
    for m in 1..<month {
        totalDays += monthDays[m]
    }
    totalDays += (day - 1)
    let totalMinutes = totalDays * 24 * 60 + hour * 60 + minute
    return totalMinutes
}

let line = readLine()!.split(separator: " ")
let N = Int(line[0])!
let Lstr = String(line[1])
let F = Int(line[2])!

// Lstr = "DDD/HH:MM"
let parts = Lstr.split(separator: "/")
let d = Int(parts[0])!
let hm = parts[1].split(separator: ":")
let hh = Int(hm[0])!
let mm = Int(hm[1])!
let allowedMinutes = d * 24 * 60 + hh * 60 + mm

var rentRecord = [String: Int]()

var fines = [String: Int]()

for _ in 0..<N {
    let entry = readLine()!.split(separator: " ")
    let dateStr = String(entry[0])
    let timeStr = String(entry[1])
    let item = String(entry[2])
    let name = String(entry[3])
    let key = item + " " + name
    let now = toMinutes(dateStr, timeStr)

    if let start = rentRecord[key] {
        // 반납
        let diff = now - start
        if diff > allowedMinutes {
            let late = diff - allowedMinutes
            fines[name, default: 0] += late * F
        }
        rentRecord.removeValue(forKey: key)
    } else {
        // 대여
        rentRecord[key] = now
    }
}

if fines.isEmpty {
    print("-1")
} else {
    for name in fines.keys.sorted() {
        print(name, fines[name]!)
    }
}