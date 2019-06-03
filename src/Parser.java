import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class Parser {
    public Parser(InputStream in){
        Scanner inputScan = new Scanner(new BufferedInputStream(in));
        String line = inputScan.nextLine();
        String [] parts = line.split("\\s+");

        // first run the shunting yard algorithm

        // then analyze the expression now that it is reverse polish notation
    }
}
