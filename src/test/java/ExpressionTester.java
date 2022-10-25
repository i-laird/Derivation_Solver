import static org.junit.jupiter.api.Assertions.assertEquals;

import calculator.service.CalculatorService;
import calculator.util.Parser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = TestingConfiguration.class)
public class ExpressionTester {

  @Autowired
  private CalculatorService calculatorServiceImpl;

  @DisplayName("Expression Testing")
  @ParameterizedTest(name = "{0}")
  @MethodSource("expression")
  public void test(String inputString, double expected) {
    double result = calculatorServiceImpl.evaluateExpression(inputString);
    assertEquals(result, expected);
  }

  private static Stream<Arguments> expression() {
    return Stream.of(
        Arguments.of("1+1", 2.0),
        Arguments.of("123*2", 246.0),
        Arguments.of("10", 10.0),
        Arguments.of("-100", -100.0),
        Arguments.of("2 * ( 3 + 5 ) / 2", 8.0),
        Arguments.of("(4+3)/(3-4)", -7.0),
        Arguments.of("-25+1", -24.0),
        Arguments.of("1 / 4", 0.25));
  }

  private static List<Integer> createSingleList(Integer xVal) {
    return Collections.singletonList(xVal);
  }
}
