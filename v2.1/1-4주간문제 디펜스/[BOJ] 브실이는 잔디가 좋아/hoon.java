import java.io.*;
import java.util.*;

public class Main {
    static final int DAYS_IN_WEEK = 7;
    static int n, w;

    static class Record implements Comparable<Record> {
        String name;
        int longestStreak;
        int usedFreeze;
        int startDayOfStreak;
        int xCnt;

        Record(String name, int longestStreak, int usedFreeze, int startDayOfStreak, int xCnt) {
            this.name = name;
            this.longestStreak = longestStreak;
            this.usedFreeze = usedFreeze;
            this.startDayOfStreak = startDayOfStreak;
            this.xCnt = xCnt;
        }

        public boolean isSameRankStat(Record o) {
            return this.longestStreak == o.longestStreak &&
                this.usedFreeze == o.usedFreeze &&
                this.startDayOfStreak == o.startDayOfStreak &&
                this.xCnt == o.xCnt;
        }

        @Override
        public int compareTo(Record o) {
            if (this.longestStreak != o.longestStreak) return o.longestStreak - this.longestStreak;
            if (this.usedFreeze != o.usedFreeze) return this.usedFreeze - o.usedFreeze;
            if (this.startDayOfStreak != o.startDayOfStreak) return this.startDayOfStreak - o.startDayOfStreak;
            if (this.xCnt != o.xCnt) return this.xCnt - o.xCnt;

            return this.name.compareTo(o.name);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());

        List<Record> friends = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String name = br.readLine();
            String[] lines = new String[DAYS_IN_WEEK];
            for (int r = 0; r < DAYS_IN_WEEK; r++) {
                lines[r] = br.readLine();
            }

            String streak = makeStreakLog(lines);
            Record friendRecord = analyzeRecord(name, streak);
            friends.add(friendRecord);
        }

        Collections.sort(friends);

        StringBuilder sb = new StringBuilder();
        int rank = 1;

        sb.append(rank).append(". ").append(friends.get(0).name).append("\n");

        for (int i = 1; i < friends.size(); i++) {
            Record prev = friends.get(i - 1);
            Record cur = friends.get(i);

            if (!cur.isSameRankStat(prev)) {
                rank++;
            }

            sb.append(rank).append(". ").append(cur.name).append("\n");
        }
        System.out.print(sb);
    }

    private static String makeStreakLog(String[] lines) {
        StringBuilder sb = new StringBuilder();
        for (int col = 0; col < w; col++) {
            for (int row = 0; row < DAYS_IN_WEEK; row++) {
                sb.append(lines[row].charAt(col));
            }
        }
        return sb.toString();
    }

    private static Record analyzeRecord(String name, String streak) {
        int curXCnt = 0;
        int longestStreak = 0;
        int usedFreeze = 0;
        int startDayOfStreak = -1;

        int len = streak.length();
        int day = 0;

        while (day < len) {
            char c = streak.charAt(day);

            if (c == 'X') {
                curXCnt++;
                day++;
                continue;
            }

            int tempStartDay = day;
            StringBuilder tempChunk = new StringBuilder();
            while (day < len && streak.charAt(day) != 'X') {
                tempChunk.append(streak.charAt(day));
                day++;
            }

            String chunkStr = tempChunk.toString();
            int firstO = chunkStr.indexOf('O');

            if (firstO == -1) continue;

            int lastO = chunkStr.lastIndexOf('O');

            String validStreak = chunkStr.substring(firstO, lastO + 1);

            int oCnt = 0;
            int fCnt = 0;
            for (char ch : validStreak.toCharArray()) {
                if (ch == 'O') oCnt++;
                else if (ch == 'F') fCnt++;
            }

            boolean isBetterStreak = (oCnt > longestStreak);
            boolean isSameStreakBetterFreeze = (oCnt == longestStreak && longestStreak > 0 && fCnt < usedFreeze);

            if (startDayOfStreak == -1 || isBetterStreak || isSameStreakBetterFreeze) {
                longestStreak = oCnt;
                usedFreeze = fCnt;
                startDayOfStreak = tempStartDay + firstO;
            }
        }

        if (startDayOfStreak == -1) {
            startDayOfStreak = 0;
        }

        return new Record(name, longestStreak, usedFreeze, startDayOfStreak, curXCnt);
    }
}