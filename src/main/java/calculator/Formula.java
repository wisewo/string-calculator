package calculator;

import java.util.List;
import java.util.stream.Collectors;

public record Formula(
        List<Operand> operands,
        OperationType operator
) {

    public Operand calculate() {

        return operands.stream()
                .reduce(operator::operate)
                .orElse(new Operand(0.0));
    }

    @Override
    public String toString() {
        return operands.stream()
                .map(Operand::toString)
                .collect(Collectors.joining(" " + operator + " "));
    }
}
