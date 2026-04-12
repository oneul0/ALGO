import java.util.*;
import java.io.*;

public class Main {

    // 문자열의 패턴을 반환 (같은 문자는 같은 번호)
    static List<Integer> getPattern(String s) {
        Map<Character, Integer> mapping = new HashMap<>();
        List<Integer> pattern = new ArrayList<>();
        int counter = 0;

        for (char c : s.toCharArray()) {
            if (!mapping.containsKey(c)) {
                mapping.put(c, counter++);
            }
            pattern.add(mapping.get(c));
        }

        return pattern;
    }

    // 암호문과 해독문이 유효한 매핑인지 확인
    static boolean isValidMapping(String encrypted, String decrypted) {
        if (encrypted.length() != decrypted.length()) {
            return false;
        }

        if (!getPattern(encrypted).equals(getPattern(decrypted))) {
            return false;
        }

        Map<Character, Character> encToDec = new HashMap<>();
        Map<Character, Character> decToEnc = new HashMap<>();

        for (int i = 0; i < encrypted.length(); i++) {
            char e = encrypted.charAt(i);
            char d = decrypted.charAt(i);

            if (encToDec.containsKey(e)) {
                if (encToDec.get(e) != d) {
                    return false;
                }
            } else {
                encToDec.put(e, d);
            }

            if (decToEnc.containsKey(d)) {
                if (decToEnc.get(d) != e) {
                    return false;
                }
            } else {
                decToEnc.put(d, e);
            }
        }

        return true;
    }

    static Map<Character, Character> getMapping(String encrypted, String decrypted) {
        Map<Character, Character> mapping = new HashMap<>();
        for (int i = 0; i < encrypted.length(); i++) {
            mapping.put(encrypted.charAt(i), decrypted.charAt(i));
        }
        return mapping;
    }

    static String decodeMessage(String message, List<Map<Character, Character>> mappings) {
        StringBuilder result = new StringBuilder();

        for (char c : message.toCharArray()) {
            // 각 매핑에서 c가 매핑될 수 있는 문자들의 집합
            List<Set<Character>> possibleSets = new ArrayList<>();

            for (Map<Character, Character> mapping : mappings) {
                Set<Character> possible = new HashSet<>();

                if (mapping.containsKey(c)) {
                    // 이미 매핑이 정해진 경우
                    possible.add(mapping.get(c));
                } else {
                    // 매핑이 없는 경우, 아직 사용되지 않은 모든 문자가 가능
                    Set<Character> usedChars = new HashSet<>(mapping.values());
                    Set<Character> usedEncrypted = mapping.keySet();

                    // 역매핑 확인
                    Map<Character, Character> reverseMapping = new HashMap<>();
                    for (Map.Entry<Character, Character> entry : mapping.entrySet()) {
                        reverseMapping.put(entry.getValue(), entry.getKey());
                    }

                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        // ch가 이미 다른 암호문자에 매핑되지 않았고
                        // c가 아직 암호문에서 사용되지 않았다면 가능
                        if (!reverseMapping.containsKey(ch)) {
                            possible.add(ch);
                        }
                    }
                }

                possibleSets.add(possible);
            }

            // 모든 매핑에서 공통으로 가능한 문자들의 교집합
            Set<Character> intersection = new HashSet<>(possibleSets.get(0));
            for (int i = 1; i < possibleSets.size(); i++) {
                intersection.retainAll(possibleSets.get(i));
            }

            // 가능한 해독 결과가 정확히 1개인 경우
            if (intersection.size() == 1) {
                result.append(intersection.iterator().next());
            } else {
                result.append('?');
            }
        }

        return result.toString();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            int N = Integer.parseInt(br.readLine());
            List<String> encryptedList = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                encryptedList.add(br.readLine().trim());
            }

            String decrypted = br.readLine().trim();
            String X = br.readLine().trim();

            List<Map<Character, Character>> validMappings = new ArrayList<>();

            for (String encrypted : encryptedList) {
                if (isValidMapping(encrypted, decrypted)) {
                    Map<Character, Character> mapping = getMapping(encrypted, decrypted);
                    validMappings.add(mapping);
                }
            }

            if (validMappings.isEmpty()) {
                output.append("IMPOSSIBLE\n");
            } else {
                String result = decodeMessage(X, validMappings);
                output.append(result).append("\n");
            }
        }

        System.out.print(output);
    }
}