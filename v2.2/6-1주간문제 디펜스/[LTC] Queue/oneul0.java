//Q1. Number of Students Unable to Eat Lunch
class Solution {
    public int countStudents(int[] students, int[] sandwiches) {
        int[] count = new int[2];
        for(int s : students){
            count[s]++;
        }
        for(int s : sandwiches){
            if(count[s] == 0) break;
            count[s]--;
        }
        return count[0] + count[1];
    }
}
//Q2. Time Needed to Buy Tickets
class Solution {
    public int timeRequiredToBuy(int[] tickets, int k) {
        int idx = 0;
        int time = 0;

        while (tickets[k] > 0) {
            if (tickets[idx] > 0) {
                tickets[idx]--;
                time++;
                if (tickets[k] == 0) return time;
            }
            idx = (idx+1) % tickets.length;
        }

        return time;
    }
}
//Q3. Implement Queue using Stacks
class MyQueue {
    Deque<Integer> front;
    Deque<Integer> back;
    public MyQueue() {
        this.front = new ArrayDeque<>();
        this.back = new ArrayDeque<>();
    }

    public void push(int x) {
        front.push(x);
    }

    public int pop() {
        if(back.isEmpty()){
            while(!front.isEmpty()){
                back.push(front.pop());
            }
        }

        return back.pop();
    }

    public int peek() {
        if(back.isEmpty()){
            while(!front.isEmpty()){
                back.push(front.pop());
            }
        }
        return back.peekFirst();
    }

    public boolean empty() {
        return front.isEmpty() && back.isEmpty();
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */