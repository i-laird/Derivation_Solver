package calculator.util.ast;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import calculator.exception.ParseError;
import calculator.util.StringToStream;
import calculator.util.terms.Term;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TokenizerTest {

  private final Tokenizer tokenizer = new Tokenizer();

  @Test
  void tokenizeExpression_validSingleVariable_doesNotThrow() {
    assertDoesNotThrow(() -> tokenizer.tokenizeExpression("x"));
  }

  @Test
  void tokenizeExpression_validNumber_doesNotThrow() {
    assertDoesNotThrow(() -> tokenizer.tokenizeExpression("42"));
  }

  @Test
  void tokenizeExpression_validExpression_doesNotThrow() {
    assertDoesNotThrow(() -> tokenizer.tokenizeExpression("x + 1"));
  }

  @Test
  void tokenizeExpression_invalidMultiCharVariable_throwsParseError() {
    ParseError thrown =
        assertThrows(ParseError.class, () -> tokenizer.tokenizeExpression("xy"));
    assertEquals("Invalid term seen: xy", thrown.getMessage());
  }

  @Test
  void tokenizeExpression_invalidUnknownToken_throwsParseError() {
    ParseError thrown =
        assertThrows(ParseError.class, () -> tokenizer.tokenizeExpression("sinx"));
    assertEquals("Invalid term seen: sinx", thrown.getMessage());
  }

  @ParameterizedTest
  @ValueSource(strings = {"x", "2*x", "x^2", "sin x", "x + 1 - 2"})
  void abstractSyntaxTree_buildsAndEvaluates_forValidExpressions(String expression) {
    AbstractSyntaxTree ast =
        new AbstractSyntaxTree(StringToStream.convertStringToStream(expression));
    Term root = ast.getRoot();
    root.evaluate(Map.of('x', 1));
    assertDoesNotThrow(() -> ast.getDeriv('x'));
  }

  @Test
  void abstractSyntaxTree_invalidExpression_throwsParseError() {
    assertThrows(
        ParseError.class,
        () ->
            new AbstractSyntaxTree(
                new ByteArrayInputStream("invalid token here".getBytes())));
  }

  @Test
  void convertToPostFix_validTokenList_producesPostfixQueue() {
    List<calculator.util.token.AbstractMath> tokens = tokenizer.tokenizeExpression("x + 1");
    var postfix = Tokenizer.convertToPostFix(tokens);
    assertEquals(3, postfix.size());
  }
}
