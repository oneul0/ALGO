# 벤치마크 6

    # **Q2. LFU Cache**

    ## 핵심 아이디어

    LRU 캐시 문제처럼 풀려고 했는데 map, list 하나로는 풀 수 없었음

    사용 횟수 기준으로 가장 적은 key 지우고

    사용 횟수 같으면 가장 오래 안 쓴 key 삭제

    가장 오래 안 쓴거면 그냥 계속 뒤로 밀면서 맨 뒤에 있는거 지우면 되는데

    이건 빈도 저장해야해서 freqMap추가함

    ```bash
    valueMap     : key -> value
    freqMap      : key -> 현재 사용 횟수
    freqKeysMap  : 사용 횟수 -> 그 횟수를 가진 key들
    ```

    ## 전체 코드

    ```java
import java.util.*;

class LFUCache {
    private final int capacity;

    //value
    private Map<Integer, Integer> valueMap;

    //freq
    private Map<Integer, Integer> freqMap;

    //freq -> keys -> freq
    private Map<Integer, LinkedHashSet<Integer>> freqKeysMap;

    // cur min freq
    private int minFreq;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.valueMap = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.freqKeysMap = new HashMap<>();
        this.minFreq = 0;
    }

    public int get(int key) {
        if (!valueMap.containsKey(key)) {
            return -1;
        }

        increaseFreq(key);

        return valueMap.get(key);
    }

    public void put(int key, int value) {
        if (capacity == 0) return;

        // 이미 존재하는 key면 value 갱신 후 사용 횟수 증가
        if (valueMap.containsKey(key)) {
            valueMap.put(key, value);
            increaseFreq(key);
            return;
        }

        // 용량이 가득 찼으면 LFU 제거
        if (valueMap.size() == capacity) {
            LinkedHashSet<Integer> minFreqKeys = freqKeysMap.get(minFreq);

            // minFreq 그룹에서 가장 오래된 key 제거
            int evictKey = minFreqKeys.iterator().next();
            minFreqKeys.remove(evictKey);

            valueMap.remove(evictKey);
            freqMap.remove(evictKey);
        }

        // 새 key 추가
        valueMap.put(key, value);
        freqMap.put(key, 1);

        freqKeysMap.putIfAbsent(1, new LinkedHashSet<>());
        freqKeysMap.get(1).add(key);

        minFreq = 1;
    }

    private void increaseFreq(int key) {
        int oldFreq = freqMap.get(key);
        int newFreq = oldFreq + 1;

        // 기존 freq 그룹에서 제거
        LinkedHashSet<Integer> oldKeys = freqKeysMap.get(oldFreq);
        oldKeys.remove(key);

        //만약 old가 min이였고 그 그룹이 비었다면 min++
        if (oldFreq == minFreq && oldKeys.isEmpty()) {
            minFreq++;
        }

        // 새 freq 그룹으로 이동
        freqMap.put(key, newFreq);

        freqKeysMap.putIfAbsent(newFreq, new LinkedHashSet<>());
        freqKeysMap.get(newFreq).add(key);
    }
}
```