package calculator;

import org.junit.jupiter.api.*;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

class StringCalculatorTest {

    private StringCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new StringCalculator();
    }

    // ── 요구사항 1: 기본 덧셈 ─────────────────────────────

    @Test
    void 빈_문자열이면_0을_반환한다() {
        assertThat(calculator.calculate("")).isEqualTo(0);
    }

    @Test
    void null이면_0을_반환한다() {
        assertThat(calculator.calculate(null)).isEqualTo(0);
    }

    @Test
    void 숫자_하나면_그대로_반환한다() {
        assertThat(calculator.calculate("5")).isEqualTo(5);
    }

    @Test
    void 쉼표_구분자로_덧셈한다() {
        assertThat(calculator.calculate("1,2")).isEqualTo(3);
        assertThat(calculator.calculate("1,2,3")).isEqualTo(6);
    }

    @Test
    void 콜론_구분자로_덧셈한다() {
        assertThat(calculator.calculate("1:2:3")).isEqualTo(6);
    }

    @Test
    void 쉼표와_콜론_혼합으로_덧셈한다() {
        assertThat(calculator.calculate("1,2:3")).isEqualTo(6);
    }

    // ── 요구사항 2: 커스텀 구분자 ────────────────────────

    @Test
    void 커스텀_구분자로_덧셈한다() {
        assertThat(calculator.calculate("//;\n1;2;3")).isEqualTo(6);
    }

    @Test
    void 커스텀_구분자_파이프를_사용한다() {
        assertThat(calculator.calculate("//|\n1|2|3")).isEqualTo(6);
    }

    // ── 요구사항 3: 연산자 지정 ──────────────────────────

    @Test
    void 덧셈_연산자를_명시적으로_지정한다() {
        assertThat(calculator.calculate("op=+|1,2,3")).isEqualTo(6);
    }

    @Test
    void 뺄셈_연산을_수행한다() {
        assertThat(calculator.calculate("op=-|10,3,2")).isEqualTo(5);
    }

    @Test
    void 곱셈_연산을_수행한다() {
        assertThat(calculator.calculate("op=*|1,2,3")).isEqualTo(6);
    }

    @Test
    void 나눗셈_연산을_수행한다() {
        assertThat(calculator.calculate("op=/|12,3,2")).isEqualTo(2);
    }

    // ── 요구사항 4: 예외 처리 ────────────────────────────

    @Test
    void 음수_입력시_예외가_발생한다() {
        assertThatThrownBy(() -> calculator.calculate("-1,2,3"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("음수는 입력할 수 없습니다");
    }

    @Test
    void 숫자가_아닌_값_입력시_예외가_발생한다() {
        assertThatThrownBy(() -> calculator.calculate("1,a,3"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("숫자가 아닌 값이 포함되어 있습니다");
    }

    @Test
    void 영으로_나누면_예외가_발생한다() {
        assertThatThrownBy(() -> calculator.calculate("op=/|10,0"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("0으로 나눌 수 없습니다");
    }

    @Test
    void 지원하지_않는_연산자_입력시_예외가_발생한다() {
        assertThatThrownBy(() -> calculator.calculate("op=%|1,2,3"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("지원하지 않는 연산자입니다");
    }

    // ── 요구사항 6: 소수점 처리 ──────────────────────────

    @Test
    void 나눗셈_결과가_소수이면_첫째자리까지_반환한다() {
        assertThat(calculator.calculate("op=/|10,3")).isEqualTo(3.3);
    }

    // ── 요구사항 7: 연산 이력 ────────────────────────────

    @Test
    void 연산_이력이_최근순으로_저장된다() {
        calculator.calculate("1,2");
        calculator.calculate("op=*|2,3");

        List<String> history = calculator.getHistory();
        assertThat(history.get(0)).contains("op=*|2,3");
        assertThat(history.get(1)).contains("1,2");
    }

    @Test
    void 연산_이력은_최대_10개까지_저장된다() {
        for (int i = 0; i < 11; i++) {
            calculator.calculate(i + ",1");
        }
        assertThat(calculator.getHistory()).hasSize(10);
    }
}
