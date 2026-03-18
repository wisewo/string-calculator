package calculator;

import java.util.List;

/**
 * 진입점. 흐름 조율만 담당한다.
 * 파싱, 계산, 이력 관리를 직접 구현하지 말 것.
 * 각 책임은 별도 클래스에 위임할 것.
 */
public class StringCalculator {

    private CalculationHistory history = new CalculationHistory();
    private InputParser inputParser = new InputParser();

    public double calculate(String input) {

        // 1. 파싱
        Formula formula = inputParser.parse(input);

        // 2. 계산
        Operand result =  formula.calculate();

        // 3. 이력 저장
        history.add(input);

        // 4. 결과 반환
        return result.value();
    }

    public List<String> getHistory() {
        return history.getFromRecent();
    }
}
