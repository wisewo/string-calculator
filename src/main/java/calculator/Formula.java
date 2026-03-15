package calculator;

import java.util.List;
import java.util.stream.Collectors;

public record Formula(
        List<Operand> operands,
        OperationType operator
) {

    public Operand calculate() {
        Operand result = operands.get(0);

        for(int i = 1; i < operands.size(); ++i) {
            result = operator.operate(result, operands.get(i));
        }

        return result;
    }

    @Override
    public String toString() {
        return operands.stream()
                .map(Operand::toString)
                .collect(Collectors.joining(" " + operator + " "));
    }
}
