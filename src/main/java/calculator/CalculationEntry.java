package calculator;

public record CalculationEntry(
        Formula formula,
        Operand result
) {
    @Override
    public String toString() {
        return String.format("%s = %s", formula, result);
    }
}
