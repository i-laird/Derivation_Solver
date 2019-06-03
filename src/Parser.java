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
        Term root = null;

        // first run the shunting yard algorithm
        List<AbstractMath> mappedParts = Arrays.stream(parts).map(Parser::getMappedPart).collect(Collectors.toList());
        Queue<AbstractMath>  outputParts = new LinkedList<>(); //these are those that would be written to console
        Stack<AbstractMath>  stack = new Stack<>();
        Stack<Term> derivativeStack = new Stack<>();
        for(AbstractMath am: mappedParts){
            if(am.getClass() == Num.class){
                outputParts.add(am);
            }
            if(am.getClass() == Function.class) {
                stack.push(am);
            }
            if(am.getClass() == Paren.class){
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
            while(!stack.empty() && ((((Operator)stack.peek()).precedence > ((Operator)am).precedence) || (((Operator)stack.peek()).precedence == ((Operator)am).precedence) && ((Operator)stack.peek()).associativity == Operator.Associativity.LEFT ) ){
                // account for natural log being weird
                // TODO check to see if this works
                if((Operator)stack.peek() == Operator.NAT_LOG){
                    outputParts.add(new Num(1));
                }
                outputParts.add(stack.pop());
            }
            stack.push(am);
        }
        while(!stack.empty()){
            outputParts.add(stack.pop());
        }

        // then analyze the expression now that it is in reverse polish notation
        for (AbstractMath part : outputParts){
            //see if it is just a number
            //if so just push it onto the stack
            if(part.getClass() == Num.class){
                derivativeStack.push(new Term(((Num)part).getNum()));
            }
            else if(part.getClass() == Function.class){
                Term operand = derivativeStack.pop();
                derivativeStack.push(part.getTermFromOp(operand, null));
            }
            //it is an operand
            else{
                Term operandOne = derivativeStack.pop(),
                        operandTwo = derivativeStack.pop();
                derivativeStack.push(part.getTermFromOp(operandOne, operandTwo));
            }
        }
        root = derivativeStack.pop();
        if(!derivativeStack.empty()){
            throw new RuntimeException("Parsing Error");
        }
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
