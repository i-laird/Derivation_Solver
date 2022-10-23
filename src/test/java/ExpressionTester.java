import calculator.service.CalculatorService;
import calculator.util.Parser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
public class ExpressionTester {

    @DisplayName("Expression")
    @ParameterizedTest(name = "{0}")
    @MethodSource("expression")
    public void test(String inputString, double expected ){

        // TODO get this to work autowire not playing nice with spring
        //double result = calculatorService.evaluateExpression(inputString);
        InputStream stream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        Parser p = new Parser(stream);
        double result = p.getRoot().evaluate(null);
        assertEquals(result, expected);
    }

    private static Stream<Arguments> expression(){
        return Stream.of(
                //first test the polynomials
                Arguments.of("1+1",                  2.0),
                Arguments.of("123*2",                246.0),
                Arguments.of("10",                   10.0),
                Arguments.of("-100",                 -100.0),
                Arguments.of("1 / 4",                0.25)
        );
    }

    private static List<Integer> createSingleList(Integer xVal){
        return Arrays.asList(xVal);
    }
}

