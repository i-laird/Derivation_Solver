import static org.junit.jupiter.api.Assertions.assertEquals;

import calculator.service.CalculatorService;
import com.google.common.collect.ImmutableList;
import java.util.Collections;
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
public class DerivativeTester {

  @Autowired private CalculatorService calculatorServiceImpl;

  private static final int DOES_NOT_AFFECT_DERIVATIVE_CALCULATION = 3;

  @DisplayName("Single Derivative Testing")
  @ParameterizedTest(name = "{0}")
  @MethodSource("singleVariable")
  public void test1(String inputString, ImmutableList<Integer> evaluationPoints, double expected) {
    double result =
        (calculatorServiceImpl.evaluateDerivative(inputString, evaluationPoints)).result;
    assertEquals(expected, result);
  }

  private static Stream<Arguments> singleVariable() {
    return Stream.of(
        // first test the polynomials
        Arguments.of("     1     ", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), 0.0),
        Arguments.of(
            "            -     1 ", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), 0.0),
        Arguments.of("1", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), 0.0),
        Arguments.of("10", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), 0.0),
        Arguments.of("-100", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), 0.0),
        Arguments.of(
            "1000 + 10000 - 1 + 0", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), 0.0),
        Arguments.of("0", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), 0.0),
        Arguments.of("x", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), 1.0),
        Arguments.of("- x", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), -1.0),
        Arguments.of("-100x", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), -100),
        Arguments.of("- x + x", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), 0.0),
        Arguments.of("-10x+5x", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), -5.0),
        Arguments.of("-10x*5x", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), -300.0),
        Arguments.of("0x", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), 0.0),
        Arguments.of("1x", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), 1.0),
        Arguments.of("2x", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), 2.0),
        Arguments.of("2 * x", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), 2.0),
        Arguments.of("2x - 3x", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), -1.0),
        Arguments.of("2x + 3x - 16", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), 5.0),
        Arguments.of(
            "- 2x + 3x - 16", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), 1.0),
        Arguments.of("- 2x - - 3x", createSingleList(DOES_NOT_AFFECT_DERIVATIVE_CALCULATION), 1.0),
        Arguments.of("2x + 3x * 5x", createSingleList(1), 32.0),
        Arguments.of("2x * 3x * 5x", createSingleList(2), 360.0),
        Arguments.of("2x * 3x * 5x + x ^ 4", createSingleList(2), 392.0),
        Arguments.of("2x * 3x * 5x - x ^ 4", createSingleList(2), 328.0),
        Arguments.of("5x - x ^ 4", createSingleList(2), -27.0),
        Arguments.of("x ^ 2", createSingleList(1), 2.0),
        Arguments.of("x ^ 2", createSingleList(-1), -2.0),
        Arguments.of("x ^ 3", createSingleList(5), 75.0),
        Arguments.of("x ^ 3 + x ^ 2 - 5", createSingleList(2), 16.0),
        Arguments.of("- x ^ 3 + x ^ 2 - 5", createSingleList(2), -8.0),
        Arguments.of("ln x", createSingleList(1), 1.0),
        Arguments.of("ln x", createSingleList(100), 1.0 / 100),
        Arguments.of("- sin x", createSingleList(3), -1 * Math.cos(3.0)),
        Arguments.of("sin x", createSingleList(1), Math.cos(1.0)),
        Arguments.of("cos x", createSingleList(3), -1 * Math.sin(3)),
        Arguments.of("tan x", createSingleList(2), Math.pow(1.0 / Math.cos(2), 2)),
        Arguments.of("sin x + x ^ 3", createSingleList(2), Math.cos(2.0) + 12),
        Arguments.of(
            "sin x + x ^ 2 - cos x", createSingleList(3), Math.cos(3.0) + 6 + Math.sin(3.0)),
        Arguments.of(
            "sin x * x ^ 2", createSingleList(3), (Math.cos(3.0) * 9) + (6 * Math.sin(3.0))),
        Arguments.of(
            "sin ( x ) * x ^ 2", createSingleList(3), (Math.cos(3.0) * 9) + (6 * Math.sin(3.0))),
        Arguments.of("sin(x)*x^2", createSingleList(3), (Math.cos(3.0) * 9) + (6 * Math.sin(3.0))),

        // cos x * x^-2 + -2x^-3 * sin x
        Arguments.of(
            "sin(x) / x^2",
            createSingleList(3),
            (Math.cos(3.0) * (1.0 / 9)) - (2.0 * (1.0 / 27.0) * Math.sin(3.0))),
        Arguments.of(
            "sin(x) / cos(x)",
            createSingleList(3),
            (Math.pow(Math.sin(3.0), 2) / Math.pow(Math.cos(3.0), 2)) + 1),
        Arguments.of(
            "tan(x) / cos(x)",
            createSingleList(3),
            ((Math.sin(3.0) * Math.tan(3.0)) + (Math.cos(3.0) * (Math.pow(1.0 / Math.cos(3.0), 2))))
                / Math.pow(Math.cos(3.0), 2)),
        Arguments.of(
            "(tan(x))^2 / sec(x)",
            createSingleList(3),
            ((2 * Math.pow(1.0 / Math.cos(3.0), 3) * Math.tan(3.0))
                    - (((1.0 / Math.cos(3.0)) * Math.pow(Math.tan(3.0), 3))))
                / (Math.pow(1.0 / Math.cos(3.0), 2))),
        Arguments.of("sin( x^2)", createSingleList(3), 2 * 3.0 * Math.cos(9.0)),
        Arguments.of(
            "sin( x + cos(x))",
            createSingleList(3),
            (1 - Math.sin(3.0)) * Math.cos(Math.cos(3.0) + 3.0)),
        Arguments.of(
            "sin( x - cos(x))",
            createSingleList(3),
            (1 + Math.sin(3.0)) * Math.cos(Math.cos(3.0) - 3.0)),
        Arguments.of(
            "sin( x + -cos(x))",
            createSingleList(3),
            (1 + Math.sin(3.0)) * Math.cos(Math.cos(3.0) - 3.0)),
        Arguments.of(
            "tan( x + -cos(x))",
            createSingleList(3),
            (1 + Math.sin(3.0)) * Math.pow(1.0 / Math.cos(Math.cos(3.0) - 3.0), 2)),
        Arguments.of(
            "14x^3 + 135x - sin x + cos(x^4)",
            createSingleList(3),
            (-4.0 * Math.pow(3.0, 3) * Math.sin(Math.pow(3.0, 4)))
                - Math.cos(3.0)
                + (42 * Math.pow(3.0, 2))
                + 135),
        Arguments.of(
            "135x - sin x + cos(x^4)",
            createSingleList(3),
            (-4.0 * Math.pow(3.0, 3) * Math.sin(Math.pow(3.0, 4))) - Math.cos(3.0) + 135));
  }

  private static ImmutableList<Integer> createSingleList(Integer xVal) {
    return ImmutableList.copyOf(Collections.singletonList(xVal));
  }
}
