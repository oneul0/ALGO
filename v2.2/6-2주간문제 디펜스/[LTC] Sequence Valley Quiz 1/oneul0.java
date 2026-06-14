//Q1. Maximum Number of Eaten Apples
//사과가 썩으니까 남은 날짜에 따라 pq로 정렬
class Solution {
    class Pair implements Comparable<Pair>{
        int count;
        int expiredAt;
        Pair(int count, int expiredAt){
            this.count = count;
            this.expiredAt = expiredAt;
        }
        @Override
        public int compareTo(Pair o){
            return this.expiredAt - o.expiredAt;
        }
    }
    public int eatenApples(int[] apples, int[] days) {
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        int n = apples.length;
        int day = 0;
        int answer = 0;
        while(day<n || !pq.isEmpty()){
            if(day < n && apples[day] > 0){
                pq.offer(new Pair(apples[day], day+days[day]));
            }
            while(!pq.isEmpty() && pq.peek().expiredAt <= day){
                pq.poll();
            }
            if(!pq.isEmpty()){
                Pair cur = pq.poll();
                answer++;
                cur.count--;

                if(cur.count > 0){
                    pq.offer(cur);
                }
            }
            day++;
        }
        return answer;
    }
}

//Q2. Design Circular Queue
//큐의 동작에 따라 포인터를 front, back을 갖고
//1차원 배열을 buffer로 업데이트마다 front, back을 업데이트
class MyCircularQueue {
    int front, back, size;
    int[] buffer;

    public MyCircularQueue(int k) {
        front = 0;
        back = 0;
        size = 0;
        buffer = new int[k];
    }

    public boolean enQueue(int value) {
        if (isFull()) return false;

        buffer[back] = value;
        back = (back+1)%buffer.length;
        size++;

        return true;
    }

    public boolean deQueue() {
        if (isEmpty()) return false;

        front = (front+1)%buffer.length;
        size--;

        return true;
    }

    public int Front() {
        if (isEmpty()) return -1;

        return buffer[front];
    }

    public int Rear() {
        if (isEmpty()) return -1;

        int rearIdx = (back-1 + buffer.length)%buffer.length;
        return buffer[rearIdx];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == buffer.length;
    }
}