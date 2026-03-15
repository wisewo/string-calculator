package calculator;

import java.util.Arrays;
import java.util.function.BinaryOperator;

/**
 * 힌트: 연산자 종류를 나타내는 enum.
 * +, -, *, / 를 표현하고, 각 연산자에 맞는 계산을 수행할 수 있다.
 * 반드시 이 클래스를 사용할 필요는 없다. 자유롭게 설계할 것.
 */
public enum OperationType {
    PLUS("+", (a, b) -> a + b),
    MINUS("-", (a, b) -> a - b),
    MULTIPLIER("*", (a, b) -> a * b),
    DIVIDER("/", OperationType::divide);

    private final String symbol;
    private final BinaryOperator<Double> expression;

    OperationType(String symbol, BinaryOperator<Double> expression) {
        this.symbol = symbol;
        this.expression = expression;
    }

    public Operand operate(Operand a, Operand b) {
        Double result = expression.apply(a.value(), b.value());
        return new Operand(result);
    }

    private static Double divide(double a, double b) {
        if (Math.abs(b) < 1e-9)
            throw new IllegalArgumentException("0으로 나눌 수 없습니다");

        double dividedValue = a / b;

        return Math.round(dividedValue * 10) / 10.0;
    }

    public static OperationType from(String symbol) {
        return  Arrays.stream(values())
                .filter(type -> type.symbol.equals(symbol))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 연산자입니다"));
    }

    @Override
    public String toString() {
        return symbol;
    }
}
