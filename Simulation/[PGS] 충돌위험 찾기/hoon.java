import java.util.*;

class Solution {
    Map<Integer, Point> pointMap = new HashMap<>();
    List<HashMap<Point, Integer>> pathCount = new ArrayList<>();
    int answer = 0;

    class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point)) return false;
            Point p = (Point) o;
            return this.x == p.x && this.y == p.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public void movePath(int[] route) {
        int time = 0;
        Point finalPoint = null;

        for (int i = 0; i < route.length - 1; i++) {
            Point start = new Point(pointMap.get(route[i]).x, pointMap.get(route[i]).y);
            Point end = pointMap.get(route[i + 1]);
            finalPoint = new Point(end.x, end.y);

            while (!(start.x == end.x && start.y == end.y)) {
                pathCount.get(time).putIfAbsent(new Point(start.x, start.y), 0);
                int cnt = pathCount.get(time).get(new Point(start.x, start.y)) + 1;
                pathCount.get(time).put(new Point(start.x, start.y), cnt);
                if (cnt == 2) answer++;

                int dx = end.x - start.x;
                int dy = end.y - start.y;

                if (dx != 0) start.x += dx > 0 ? 1 : -1;
                else if (dy != 0) start.y += dy > 0 ? 1 : -1;

                time++;
            }
        }

        pathCount.get(time).putIfAbsent(finalPoint, 0);
        int cnt = pathCount.get(time).get(finalPoint) + 1;
        pathCount.get(time).put(finalPoint, cnt);
        if (cnt == 2) answer++;
    }

    public int solution(int[][] points, int[][] routes) {
        for (int i = 0; i < points.length; i++) {
            pointMap.put(i + 1, new Point(points[i][0], points[i][1]));
        }

        for (int i = 0; i < 100001; i++) {
            pathCount.add(new HashMap<>());
        }

        for (int[] route : routes) {
            movePath(route);
        }

        return answer;
    }
}
