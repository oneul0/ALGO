class Solution {
    public int[] canSeePersonsCount(int[] heights) {
        int n = heights.length;
        int[] answer = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = n-1; i >= 0; i--) {
            int count = 0;

            //만난거보다 작으면 다 빼버림
            while (!stack.isEmpty() && stack.peek() < heights[i]) {
                stack.pop();
                count++;
            }

            if (!stack.isEmpty()) {
                count++;
            }

            answer[i] = count;
            stack.push(heights[i]);
        }

        return answer;
    }
}