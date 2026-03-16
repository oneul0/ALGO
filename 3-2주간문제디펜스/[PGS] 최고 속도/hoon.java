import java.util.*;

class Solution {
    static class Point implements Comparable<Point> {
        int x, y;
        Point(int x, int y) { this.x = x; this.y = y; }

        @Override
        public boolean equals(Object o) {
            Point p = (Point) o;
            return x == p.x && y == p.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public int compareTo(Point o) {
            if (this.x != o.x) return Long.compare(this.x, o.x);
            return Long.compare(this.y, o.y);
        }
    }

    public int[] solution(int[][] city, int[][] road) {
        int n = city.length;
        Map<Point, Integer> pointToLimit = new HashMap<>(); // 카메라별 제한 속도
        Map<Point, List<Point>> adj = new HashMap<>(); // 그래프 인접 리스트
        Set<Point> allPoints = new HashSet<>(); // road양 끝점 포함 모든 지점

        Point[] cityPoints = new Point[n];
        for (int i = 0; i < n; i++) {
            cityPoints[i] = new Point(city[i][0], city[i][1]);
            allPoints.add(cityPoints[i]);
        }

        List<Point[]> roadSegments = new ArrayList<>();
        for (int[] r : road) {
            Point p1 = new Point(r[0], r[1]);
            Point p2 = new Point(r[2], r[3]);
            Point cam = new Point((r[0]+r[2]) / 2, (r[1]+r[3]) / 2);

            allPoints.add(p1);
            allPoints.add(p2);
            allPoints.add(cam);

            // 한 지점에 여러 카메라가 있을 때는 최솟값
            pointToLimit.put(cam, Math.min(pointToLimit.getOrDefault(cam, Integer.MAX_VALUE), r[4]));
        }

        //도로 교차점 찾기(각 축에 평행하므로 수직 아니면 수평)
        for (int i = 0; i < road.length; i++) {
            for (int j = i + 1; j < road.length; j++) {
                // 수평인지 수직인지 확인 후 교차점 계산
                Point intersect = getIntersection(road[i], road[j]);
                if (intersect != null) allPoints.add(intersect);
            }
        }

        //그래프 간선 만들기
        for (int[] r : road) {
            List<Point> onRoad = new ArrayList<>();
            for (Point p : allPoints) {
                if (isPointOnLine(p, r)) onRoad.add(p);
            }
            Collections.sort(onRoad);
            for (int i = 0; i < onRoad.size() - 1; i++) {
                addEdge(adj, onRoad.get(i), onRoad.get(i + 1));
            }
        }

        return solveMaxMin(cityPoints, adj, pointToLimit);
    }

    //간선추가
    private void addEdge(Map<Point, List<Point>> adj, Point u, Point v) {
        adj.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
        adj.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
    }

    private boolean isPointOnLine(Point p, int[] r) {
        return p.x >= r[0] && p.x <= r[2] && p.y >= r[1] && p.y <= r[3];
    }

    private Point getIntersection(int[] r1, int[] r2) {
        // r1이 수평(y1==y2), r2가 수직(x1==x2)인 경우로 교차 판별
        int xMin1 = r1[0], xMax1 = r1[2], yMin1 = r1[1], yMax1 = r1[3];
        int xMin2 = r2[0], xMax2 = r2[2], yMin2 = r2[1], yMax2 = r2[3];

        int ix = Math.max(xMin1, xMin2);
        int iy = Math.max(yMin1, yMin2);

        if (ix <= Math.min(xMax1, xMax2) && iy <= Math.min(yMax1, yMax2)) {
            return new Point(ix, iy);
        }
        return null;
    }

    //minmax dijikstra
    private int[] solveMaxMin(Point[] cityPoints, Map<Point, List<Point>> adj, Map<Point, Integer> limits) {
        Map<Point, Integer> maxLimits = new HashMap<>();
        PriorityQueue<Object[]> pq = new PriorityQueue<>((a, b) -> (int)b[1] - (int)a[1]);

        Point start = cityPoints[0];
        int startLimit = limits.getOrDefault(start, Integer.MAX_VALUE);
        maxLimits.put(start, startLimit);
        pq.add(new Object[]{start, startLimit});

        while (!pq.isEmpty()) {
            Object[] cur = pq.poll();
            Point u = (Point) cur[0];
            int b = (int) cur[1];

            if (b < maxLimits.getOrDefault(u, 0)) continue;

            for (Point v : adj.getOrDefault(u, new ArrayList<>())) {
                int nextB = Math.min(b, limits.getOrDefault(v, Integer.MAX_VALUE));
                if (nextB > maxLimits.getOrDefault(v, 0)) {
                    maxLimits.put(v, nextB);
                    pq.add(new Object[]{v, nextB});
                }
            }
        }

        int[] res = new int[cityPoints.length - 1];
        for (int i = 1; i < cityPoints.length; i++) {
            int val = maxLimits.getOrDefault(cityPoints[i], 0);
            res[i - 1] = (val == Integer.MAX_VALUE) ? 0 : val;
        }
        return res;
    }
}