import Enums.AbstractMath;
import Enums.Function;
import Enums.Num;
import Enums.Operator;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Parser {
    public Parser(InputStream in){
        Scanner inputScan = new Scanner(new BufferedInputStream(in));
        String line = inputScan.nextLine();
        String [] parts = line.split("\\s+");

        // first run the shunting yard algorithm
        List<AbstractMath> mappedParts = Arrays.stream(parts).map(Operator::getOp).collect(Collectors.toList());

        // then analyze the expression now that it is reverse polish notation
    }

    public AbstractMath getMappedPart(String s){
        AbstractMath returnThing = null;
        //see if it is an integeer
        if (s.matches("[+-]?\\d+")){
            return new Num(Integer.parseInt(s));
        }
        returnThing = Operator.getOp(s);
        if(returnThing != null){
            return returnThing;
        }
        return Function.getFunc(s);
    }
}
