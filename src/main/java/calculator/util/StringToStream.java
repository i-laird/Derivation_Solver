package calculator.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Ian Laird
 */
public class StringToStream {

    /**
     * converts a string to an input stream
     * @param str the string to convert
     * @return an input stream whose contents are the string
     */
    public static InputStream convertStringToStream(String str){
        return new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
    }
}
