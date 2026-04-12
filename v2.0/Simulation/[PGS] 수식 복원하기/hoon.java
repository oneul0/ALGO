import java.util.*;

class Solution {
    class Expression {
        String operand1, operand2;
        String operation;
        String result;

        public Expression(String operand1, String operation, String operand2, String result) {
            this.operand1 = operand1;
            this.operation = operation;
            this.operand2 = operand2;
            this.result = result;
        }

        public String getExpression() {
            return operand1 + " " + operation + " " + operand2 + " = " + result;
        }
    }

    public String[] solution(String[] expressions) {
        List<Expression> knownExpressions = new ArrayList<>();
        List<Expression> unknownExpressions = new ArrayList<>();
        int maxDigit = 0;

        // 모든 식을 파싱하고 최대 숫자 찾기
        for (String expression : expressions) {
            String[] parts = expression.split(" ");
            Expression exp = new Expression(parts[0], parts[1], parts[2], parts[4]);

            if (parts[4].equals("X")) {
                unknownExpressions.add(exp);
            } else {
                knownExpressions.add(exp);
            }

            // 알려진 식과 모르는 식 모두에서 최대 숫자 찾기
            for (int i = 0; i < parts.length; i++) {
                if (i == 3) continue;
                if (!parts[i].equals("X")) {
                    for (char c : parts[i].toCharArray()) {
                        if (Character.isDigit(c)) {
                            maxDigit = Math.max(maxDigit, c - '0');
                        }
                    }
                }
            }
        }

        // 유효한 진법 후보 리스트
        List<Integer> validBases = new ArrayList<>();
        for (int base = maxDigit + 1; base <= 9; base++) {
            validBases.add(base);
        }

        // 알려진 수식을 기반으로 유효한 진법 필터링
        filterBases(knownExpressions, validBases);

        // 방정식 풀기
        List<String> results = new ArrayList<>();
        for (Expression exp : unknownExpressions) {
            results.add(findSolution(exp, validBases));
        }

        return results.toArray(new String[0]);
    }

    // 유효한 진법만 남기는 함수
    public void filterBases(List<Expression> knownExpressions, List<Integer> bases) {
        Iterator<Integer> it = bases.iterator();
        while (it.hasNext()) {
            int base = it.next();
            boolean isValidBase = true;
            for (Expression exp : knownExpressions) {
                if (!validateEquation(exp, base)) {
                    isValidBase = false;
                    break;
                }
            }
            if (!isValidBase) {
                it.remove();
            }
        }
    }

    // 주어진 진법에서 수식이 성립하는지
    public boolean validateEquation(Expression exp, int base) {
        try {
            // 각 숫자가 해당 진법에서 유효한지
            if (!isValidInBase(exp.operand1, base) ||
                !isValidInBase(exp.operand2, base) ||
                !isValidInBase(exp.result, base)) {
                return false;
            }

            int op1 = baseConvert(exp.operand1, base);
            int op2 = baseConvert(exp.operand2, base);
            int result = baseConvert(exp.result, base);
            return calculate(op1, op2, exp.operation) == result;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // 숫자가 해당 진법에서 유효한지
    public boolean isValidInBase(String numStr, int base) {
        for (char c : numStr.toCharArray()) {
            if (Character.isDigit(c)) {
                if (c - '0' >= base) {
                    return false;
                }
            }
        }
        return true;
    }

    // 수식에 대한 결과 찾기(모르는 수식용)
    public String findSolution(Expression exp, List<Integer> bases) {
        Set<String> possibleResults = new HashSet<>();

        for (int base : bases) {
            try {
                // 각 숫자가 해당 진법에서 유효한지
                if (!isValidInBase(exp.operand1, base) ||
                    !isValidInBase(exp.operand2, base)) {
                    continue;
                }

                int op1 = baseConvert(exp.operand1, base);
                int op2 = baseConvert(exp.operand2, base);
                int calculatedResult = calculate(op1, op2, exp.operation);

                // 음수 무시
                if (calculatedResult < 0) {
                    continue;
                }

                String convertedResult = Integer.toString(calculatedResult, base);
                possibleResults.add(convertedResult.toUpperCase());
            } catch (NumberFormatException e) {
                // 변환 실패 시 무시
            }
        }

        if (possibleResults.isEmpty()) {
            return exp.operand1 + " " + exp.operation + " " + exp.operand2 + " = X";
        } else if (possibleResults.size() == 1) {
            String result = possibleResults.iterator().next();
            return exp.operand1 + " " + exp.operation + " " + exp.operand2 + " = " + result;
        } else {
            // 여러 가능한 결과가 있으면 "?"
            exp.result = "?";
            return exp.getExpression();
        }
    }

    // 10진법 계산
    public int calculate(int op1, int op2, String operation) {
        switch (operation) {
            case "+": return op1 + op2;
            case "-": return op1 - op2;
            default: return -1;
        }
    }

    // n진법 -> 10진법 변환
    public int baseConvert(String numStr, int base) {
        return Integer.parseInt(numStr, base);
    }
}