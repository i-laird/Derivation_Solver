import DerivationSolver.Parser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@ExtendWith(SpringExtension.class)
public class ParserTester {

    @Test
    public void testParser(){
        InputStream stream = new ByteArrayInputStream("sin(x)".getBytes(StandardCharsets.UTF_8));
        Parser p = new Parser(stream);
        p.getRoot();
    }
}
