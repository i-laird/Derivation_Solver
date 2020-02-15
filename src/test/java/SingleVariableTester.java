import Terms.Term;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class SingleVariableTester {

    private static int DOES_NOT_MATTER = 3;

    @DisplayName("Single Derivatives")
    @ParameterizedTest(name = "{0}")
    @MethodSource("singleVariable")
    public void test1(String inputString, List<Integer> evaluationPoints,  double result ){
        InputStream stream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        Parser p = new Parser(stream);
        Term root = p.getRoot();
        Term deriv = root.getDerivative();
        double evaluatedNum = deriv.evaluate(evaluationPoints);
        assertEquals(evaluatedNum, result);
    }

    private static Stream<Arguments> singleVariable(){
        return Stream.of(
                //first test the polynomials
                Arguments.of("     1     ",          createSingleList(DOES_NOT_MATTER), 0.0),
                Arguments.of("1",                    createSingleList(DOES_NOT_MATTER), 0.0),
                Arguments.of("10",                   createSingleList(DOES_NOT_MATTER), 0.0),
                Arguments.of("-100",                 createSingleList(DOES_NOT_MATTER), 0.0),
                Arguments.of("1000 + 10000 - 1 + 0", createSingleList(DOES_NOT_MATTER), 0.0),
                Arguments.of("0",                    createSingleList(DOES_NOT_MATTER), 0.0),
                Arguments.of("x",                    createSingleList(DOES_NOT_MATTER), 1.0),
                Arguments.of("- x",                  createSingleList(DOES_NOT_MATTER), -1.0),
                Arguments.of("- x + x",              createSingleList(DOES_NOT_MATTER), 0.0),
                Arguments.of("0x",                   createSingleList(DOES_NOT_MATTER), 0.0),
                Arguments.of("1x",                   createSingleList(DOES_NOT_MATTER), 1.0),
                Arguments.of("2x",                   createSingleList(DOES_NOT_MATTER), 2.0),
                Arguments.of("2 * x",                createSingleList(DOES_NOT_MATTER), 2.0),
                Arguments.of("2x - 3x",              createSingleList(DOES_NOT_MATTER), -1.0),
                Arguments.of("2x + 3x - 16",         createSingleList(DOES_NOT_MATTER), 5.0),
                Arguments.of("- 2x + 3x - 16",       createSingleList(DOES_NOT_MATTER), 1.0),
                Arguments.of("- 2x - - 3x",          createSingleList(DOES_NOT_MATTER), 1.0),


                Arguments.of("2x + 3x * 5x",         createSingleList(1),          32.0),
                Arguments.of("2x * 3x * 5x",         createSingleList(2),          360.0),
                Arguments.of("2x * 3x * 5x + x ^ 4", createSingleList(2),          392.0),
                Arguments.of("2x * 3x * 5x - x ^ 4", createSingleList(2),          328.0),
                Arguments.of("x ^ 2",                createSingleList(1),          2.0),
                Arguments.of("x ^ 2",                createSingleList(-1),         -2.0),
                Arguments.of("x ^ 3",                createSingleList(5),          75.0),
                Arguments.of("x ^ 3 + x ^ 2 - 5",    createSingleList(2),          16.0),
                Arguments.of("- x ^ 3 + x ^ 2 - 5",  createSingleList(2),          -8.0),

                Arguments.of("ln x",                 createSingleList(1),          1.0 ),
                Arguments.of("ln x",                 createSingleList(100),        1.0 / 100 ),
                Arguments.of("sin x",                createSingleList(1),          Math.cos(1.0)),
                Arguments.of("cos x",                createSingleList(3),          -1 * Math.sin(3)),
                Arguments.of("tan x",                createSingleList(2),          Math.pow(2, 1.0 / Math.sin(2))),

                Arguments.of("sin x + x ^ 3",        createSingleList(2),          Math.cos(2.0) + 12),
                Arguments.of("sin x + x ^ 2 - cos x",createSingleList(3),          Math.cos(3.0) + 6 + Math.sin(3.0)),
                Arguments.of("sin x * x ^ 2",        createSingleList(3),         (Math.cos(3.0) * 9) + ( 6 * Math.sin(3.0))),
                Arguments.of("sin ( x ) * x ^ 2",        createSingleList(3),         (Math.cos(3.0) * 9) + ( 6 * Math.sin(3.0)))

        );
    }

    private static List<Integer> createSingleList(Integer xVal){
        return Arrays.asList(xVal);
    }
}
