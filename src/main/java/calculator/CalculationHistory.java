package calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 힌트: 연산 이력을 관리하는 클래스.
 * 일급 컬렉션으로 구현해볼 것.
 * 반드시 이 클래스를 사용할 필요는 없다. 자유롭게 설계할 것.
 */
public class CalculationHistory {

    private int count = 0;
    private int point = 0;
    private final int capacity = 10;
    private final String[] entries = new String[capacity];

    public void add(String input) {
        entries[point] = input;

        point = (point + 1) % capacity;

        if (count < capacity)
            ++count;
    }

    public List<String> getFromRecent() {
        int start = (count < capacity) ? 0 : point;

        List<String> listFromOld = new ArrayList<>(count);

        for (int i = 0; i < count; ++i) {
            int targetIndex = (start + i) % capacity;
            listFromOld.add(entries[targetIndex]);
        }

        Collections.reverse(listFromOld);

        return listFromOld;
    }
}
