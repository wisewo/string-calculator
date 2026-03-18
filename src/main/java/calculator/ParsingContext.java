package calculator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParsingContext {

    private OperationType operation = OperationType.PLUS;
    private String delimiter = ",|:";
    private String remainText;

    private static final Pattern operationPatter = Pattern.compile("op=(.+?)\\|");
    private static final Pattern delimiterPattern = Pattern.compile("//(.+?)\n");

    ParsingContext(String input) {
        this.remainText = (input == null) ? "" : input ;
    }

    public ParsingContext extractOperation() {
        Matcher matcher = operationPatter.matcher(remainText);

        if (matcher.find()) {
            String opSymbol = matcher.group(1);
            this.operation = OperationType.from(opSymbol);

            remainText = matcher.replaceFirst("");
        }

        return this;
    }

    public ParsingContext extractDelimiter(){
        Matcher matcher = delimiterPattern.matcher(remainText);

        if(matcher.find()) {
            this.delimiter = matcher.group(1);
            delimiter = Pattern.quote(delimiter);
            remainText = matcher.replaceFirst("");
        }

        return this;
    }

    public Formula toFormula() {
        List<Operand> operands = Arrays.stream(remainText.split(delimiter))
                .map(String::trim)
                .filter(s->!s.isBlank())
                .map(Operand::new)
                .toList();

        if (remainText.isBlank()) {
            return new Formula(List.of(new Operand(0.0)), OperationType.PLUS);
        }

        return new Formula(operands, operation);
    }

}
