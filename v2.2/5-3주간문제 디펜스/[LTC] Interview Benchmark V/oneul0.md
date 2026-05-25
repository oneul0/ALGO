# 벤치마크 5

    # **Q3. GCD Sort of an Array**

    ## 핵심 아이디어

gcd(nums[i], nums[j]) > 1인 애들끼리 직접 비교해서 swap 가능한지 보려고 했는데 터짐

두 수의 gcd > 1이라는 건 결국 공통 소인수가 있다는 뜻임

```bash
1. nums에서 최댓값을 찾는다
2. 0 ~ max까지 DSU 배열을 만든다
3. 각 숫자를 소인수분해한다
4. 숫자와 그 숫자의 소인수를 union 한다
5. nums를 복사해서 정렬한다
6. 각 위치마다 비교한다
```

유니온 파인드로 풀 수 있음

근데 parents가 소인수 기준임

## 전체 코드

```java
class Solution {
    int[] parents;

    public boolean gcdSort(int[] nums) {
        int max = 0;

        for (int num : nums) {
            max = Math.max(max, num);
        }

        parents = new int[max + 1];

        for (int i = 0; i <= max; i++) {
            parents[i] = i;
        }

        for (int num : nums) {
            unionWithPrimeFactors(num);
        }

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        // 원래 위치의 값이 정렬 후 와야 할 값과 같은 그룹인지 확인
        for (int i = 0; i < nums.length; i++) {
            if (find(nums[i]) != find(sorted[i])) {
                return false;
            }
        }

        return true;
    }

    private void unionWithPrimeFactors(int num) {
        int original = num;

        for (int factor = 2; factor * factor <= num; factor++) {
            if (num % factor == 0) {
                union(original, factor);

                while (num % factor == 0) {
                    num /= factor;
                }
            }
        }

        // 마지막에 남은 num이 소인수인 경우
        if (num > 1) {
            union(original, num);
        }
    }

    public int find(int x) {
        if (parents[x] == x) {
            return x;
        }

        return parents[x] = find(parents[x]);
    }

    public void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA == rootB) {
            return;
        }

        if (rootA > rootB) {
            parents[rootA] = rootB;
        } else {
            parents[rootB] = rootA;
        }
    }
}
```