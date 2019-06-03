import Enums.*;
import Terms.Term;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {
    public Parser(InputStream in){
        Scanner inputScan = new Scanner(new BufferedInputStream(in));
        String line = inputScan.nextLine();
        String [] parts = line.split("\\s+");

        // first run the shunting yard algorithm
        List<AbstractMath> mappedParts = Arrays.stream(parts).map(Parser::getMappedPart).collect(Collectors.toList()),
                            outputParts = new LinkedList<>(); //these are those that would be written to console
        Stack<AbstractMath>  stack = new Stack<>();
        for(AbstractMath am: mappedParts){
            if(am.getClass() == Num.class){
                outputParts.add(am);
            }
            if(am.getClass() == Function.class) {
                stack.push(am);
            }
            if(am.getClass() == Paren.class){\
                if(am == Paren.LEFT_PAREN){
                    stack.push(am);
                }
                else{
                    while(stack.peek() != Paren.LEFT_PAREN){
                        outputParts.add(stack.pop());
                    }
                }
                continue;
            }
            //if not a functin or a number it must be an operator
            while(!stack.empty() && ((Operator)stack.peek()).precedence >= ((Operator)am).precedence){
                outputParts.add(stack.pop());
            }
            stack.push(am);
        }

        // then analyze the expression now that it is reverse polish notation
    }

    public static AbstractMath getMappedPart(String s){
        if(s.matches("\\(")){
            return Paren.LEFT_PAREN;
        }
        if(s.matches("\\)")){
            return Paren.RIGHT_PAREN;
        }
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
