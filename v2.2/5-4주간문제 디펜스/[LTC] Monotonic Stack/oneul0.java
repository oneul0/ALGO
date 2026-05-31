//Q1. Final Prices With a Special Discount in a Shop
//처음 만난 작거나 같은 수를 할인해줌
//스택으로 간단하게 해결
class Solution {
    public int[] finalPrices(int[] prices) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] result = new int[prices.length];

        for (int i = 0; i < prices.length; i++) {
            while (!stack.isEmpty() && prices[stack.peek()] >= prices[i]) {
                int top = stack.pop();
                result[top] = prices[top] - prices[i];
            }

            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int cur = stack.pop();
            result[cur] = prices[cur];
        }

        return result;
    }
}

//Q2. Daily Temperatures
//더 높은 기온이 될 때까지 걸리는 시간
//스택으로 간단하게 풀이 가능
//현재 만난 기온이 stack top 보다 크다면 pop
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] answer = new int[temperatures.length];
        for(int i = 0; i<temperatures.length; i++){
            while(!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]){
                int prevIdx = stack.pop();
                answer[prevIdx] = i-prevIdx;
            }
            stack.push(i);
        }
        return answer;
    }
}

//Q3. Largest Rectangle in Histogram
//하나씩 담아가면서 최대 높이가 가장 커지는 경우 찾기
class Solution {
    public int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;

        for (int i = 0; i <= heights.length; i++) {
            int curHeight = (i == heights.length) ? 0 : heights[i];

            while (!stack.isEmpty() && curHeight < heights[stack.peek()]) {
                int top = stack.pop();
                int height = heights[top];

                int width = 0;
                if (stack.isEmpty()) {
                    width = i;
                } else {
                    width = i - stack.peek() - 1;
                }

                maxArea = Math.max(maxArea, height * width);
            }

            stack.push(i);
        }

        return maxArea;
    }
}