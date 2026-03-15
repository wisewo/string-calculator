package calculator;

public class TestMain {
    public static void main(String[] args) {
        StringCalculator calculator = new StringCalculator();

        System.out.println(calculator.calculate("op=/|10,3"));
    }
}
