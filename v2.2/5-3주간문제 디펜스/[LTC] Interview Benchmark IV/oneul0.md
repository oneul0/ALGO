# 벤치마크 4

    # Q1. **Longest Consecutive Sequence**

    ## 핵심 아이디어

정렬되지 않은 배열 nums에서 값이 연속되는 가장 긴 수열의 길이를 구하는 문제
배열에서 붙어 있을 필요 없고 값만 연속되면 됨

```
    1. 모든 숫자를 HashSet에 넣고
2. 어떤 숫자가 연속 수열의 시작점인지 확인 num-1이 없으면 시작점
3. 시작점에서만 num+1, num+2 ... 확인
4. 가장 긴 길이 반환
```

    ## 전체 코드

```java
class Solution {
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int answer = 0;

        for (int num : set) {
            // num이 연속 수열의 시작점일 때만 탐색
            if (!set.contains(num - 1)) {
                int current = num;
                int length = 1;

                while (set.contains(current + 1)) {
                    current++;
                    length++;
                }

                answer = Math.max(answer, length);
            }
        }

        return answer;
    }
}
```

    ---

    # Q3. **Special Binary String**

    ## 핵심 아이디어

괄호문제처럼 풀면 됨

1+내부 special string+0 이런 느낌

균형을 정수로 조절
문자열을 왼쪽부터 보면서 balance를 계산함

balance == 0 되면 하나의 독립적인 부분 문자열 완성이라고 볼 수 있음

그리고 special string이어도 내부에 special string로 또 나눌 수 있는 경우 존재

같은 깊이에 있는 special substring들을 사전순으로 가장 크게 정렬

```bash
1. balance를 이용해서 독립적인 special substring을 찾는다
2. 각 special substring의 바깥 1과 0을 제거한다
3. 내부 문자열을 재귀적으로 다시 최대로 만든다
4. 다시 1 + 내부 결과 + 0 형태로 감싼다
5. 같은 깊이에 있는 조각들을 사전순 내림차순으로 정렬한다
6. 정렬된 조각들을 이어 붙인다
```

    ## 전체 코드

```java
class Solution {
    public String makeLargestSpecial(String s) {
        List<String> list = new ArrayList<>();

        int balance = 0;
        int start = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                balance++;
            } else {
                balance--;
            }

            //하나의 독립적인 special substring 완성
            if (balance == 0) {
                String inner = s.substring(start + 1, i);

                String special = "1" + makeLargestSpecial(inner) + "0";
                list.add(special);

                start = i + 1;
            }
        }

        //내림차순
        Collections.sort(list, Collections.reverseOrder());

        StringBuilder sb = new StringBuilder();

        for (String str : list) {
            sb.append(str);
        }

        return sb.toString();
    }
}
```