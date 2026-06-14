class Solution {
    func reformatDate(_ date: String) -> String {
        let parts = date.split(separator: " ").map(String.init)

        let day = Int(parts[0].filter(\.isNumber))!

        let months = [
            "Jan": "01",
            "Feb": "02",
            "Mar": "03",
            "Apr": "04",
            "May": "05",
            "Jun": "06",
            "Jul": "07",
            "Aug": "08",
            "Sep": "09",
            "Oct": "10",
            "Nov": "11",
            "Dec": "12"
        ]

        let month = months[parts[1]]!
        let year = parts[2]

        return "\(year)-\(month)-\(String(format: "%02d", day))"
    }

    func maxRepeating(_ sequence: String, _ word: String) -> Int {
        for k in stride(from: sequence.count / word.count,
                        through: 1,
                        by: -1) {
            if sequence.contains(String(repeating: word, count: k)) {
                return k
            }
        }

        return 0
    }
}