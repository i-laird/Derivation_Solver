package DerivationSolver.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class StringToStream {

    public static InputStream convertStringToStream(String str){
        return new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
    }
}
