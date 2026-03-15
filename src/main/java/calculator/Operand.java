package calculator;

import java.text.DecimalFormat;

public record Operand(double value) {

    public Operand(String input) {
        this(converse(input));
    }

    private static Double converse(String input) {
        try {
            double v = Double.parseDouble(input);
            if (v < 0) throw new IllegalArgumentException("음수는 입력할 수 없습니다");
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자가 아닌 값이 포함되어 있습니다");
        }
    }

    @Override
    public String toString() {
        if ((long) value == value) return String.format("%d", (long)value);

        return String.valueOf(value);
    }
}
