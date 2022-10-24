import calculator.util.Parser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ParserTester {

  @Test
  public void testParser() {
    InputStream stream = new ByteArrayInputStream("sin(x + 5)".getBytes(StandardCharsets.UTF_8));
    Parser p = new Parser(stream);
    p.getRoot();
  }
}
