//Q1. Minimum Absolute Difference
//pair 요소의 차의 절댓값이 가장 작은 pair 찾기
//정답
class Solution {
	public List<List<Integer>> minimumAbsDifference(int[] arr) {
		Arrays.sort(arr);

		List<List<Integer>> result = new ArrayList<>();
		long minDiff = Long.MAX_VALUE;

		for (int i = 1; i < arr.length; i++) {
			long diff = (long) arr[i] - arr[i - 1];

			if (diff < minDiff) {
				minDiff = diff;
				result.clear();
				result.add(Arrays.asList(arr[i - 1], arr[i]));
			} else if (diff == minDiff) {
				result.add(Arrays.asList(arr[i - 1], arr[i]));
			}
		}

		return result;
	}
}

//오답
//무식하게 때려넣고 다시 정렬해야해서 시간초과 남..
class Solution {
	class Pair implements Comparable<Pair>{
		int a, b;
		int diff;
		Pair(int a, int b){
			this.a = a;
			this.b = b;
			this.diff = Math.abs(a-b);
		}
		@Override
		public int compareTo(Pair o){
			return this.diff - o.diff;
		}
	}

	public List<List<Integer>> minimumAbsDifference(int[] arr) {
		PriorityQueue<Pair> pq = new PriorityQueue<>();
		for(int i = 0; i<arr.length-1; i++){
			for(int j = i+1; j<arr.length; j++){
				pq.offer(new Pair(Math.min(arr[i], arr[j]), Math.max(arr[i], arr[j])));
			}
		}

		List<List<Integer>> result= new ArrayList<>();
		int minVal = pq.peek().diff;
		while(!pq.isEmpty() && minVal==pq.peek().diff){
			List<Integer> tmp = new ArrayList<>();
			Pair cur = pq.poll();
			tmp.add(cur.a);
			tmp.add(cur.b);
			result.add(tmp);
		}
		result.sort((a, b) -> {
			if (!a.get(0).equals(b.get(0))) {
				return a.get(0) - b.get(0);
			}
			return a.get(1) - b.get(1);
		});
		return result;
	}
}

//Q2. Reduction Operations to Make the Array Elements Equal
//가장 큰 수 다음으로 작은 수를 찾아서 그 수로 변경해갔을 때
//모든 수가 동일할 때까지 걸리는 횟수 구하는 문제
class Solution {
	public int reductionOperations(int[] nums) {
		Arrays.sort(nums);
		int count = 0;
		int diffCount = 0;
		for(int i = 1; i<nums.length; i++){
			if(nums[i] != nums[i-1]) diffCount++;
			count+=diffCount;
		}
		return count;
	}
}

//업데이트별로 정렬하면 시간 터짐
//가장 작은 수 제외하고 서로 다른 수가 몇 개 있는지

//Q3. Merge Intervals
//범위 겹치면 합치는 문제
class Solution {
	public int[][] merge(int[][] intervals) {
		Arrays.sort(intervals, (a, b) -> {
			if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
			return Integer.compare(a[1], b[1]);
		});

		List<int[]> result = new ArrayList<>();
		int[] cur = intervals[0];

		for (int i = 1; i < intervals.length; i++) {
			if (cur[1] >= intervals[i][0]) {
				cur[1] = Math.max(cur[1], intervals[i][1]);
			} else {
				result.add(cur);
				cur = intervals[i];
			}
		}

		result.add(cur);
		int[][] ans = new int[result.size()][2];

		for (int i = 0; i < result.size(); i++) {
			ans[i] = result.get(i);
		}

		return ans;
	}
}
