package calculator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 힌트: 입력 문자열 파싱을 담당하는 클래스.
 * 구분자 추출, 숫자 토큰 분리, 연산자 추출 등을 여기서 처리할 수 있다.
 * 반드시 이 클래스를 사용할 필요는 없다. 자유롭게 설계할 것.
 */
public class InputParser {

    private OperationType operation = OperationType.PLUS;
    private String delimiter = ",|:";

    public Formula parse(String input) {

        if (input == null || input.isBlank()) {
            return new Formula(List.of(new Operand(0.0)), OperationType.PLUS);
        }

        String removedCustomOperation = checkCustomOperation(input);
        String removedCustomDelimiter = checkCustomDelimiter(removedCustomOperation);

        List<Operand> operands = Arrays.stream(removedCustomDelimiter.split(delimiter))
                .map(String::trim)
                .filter(s->!s.isBlank())
                .map(Operand::new)
                .toList();

        return new Formula(operands, operation);
    }

    private String checkCustomOperation(String input) {
        Pattern pattern = Pattern.compile("op=(.+?)\\|");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String opSymbol = matcher.group(1);
            this.operation = OperationType.from(opSymbol);

            return matcher.replaceFirst("");
        }

        return input;
    }

    private String checkCustomDelimiter(String input) {
        Pattern pattern = Pattern.compile("//(.+?)\n");
        Matcher matcher = pattern.matcher(input);

        if(matcher.find()) {
            this.delimiter = matcher.group(1);
            delimiter = Pattern.quote(delimiter);
            return matcher.replaceFirst("");
        }

        return input;
    }
}
