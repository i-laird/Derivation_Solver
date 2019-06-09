import Enums.*;
import Terms.Term;
import Terms.Var;
import Terms.Variable;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {
    private boolean operatorOrFunctionSeen = true;
    Term root = null;
    public Parser(InputStream in){
        //reset the variables
        Var.reset();
        Scanner inputScan = new Scanner(new BufferedInputStream(in));
        String line = inputScan.nextLine();
        String [] parts = line.split("\\s+");
        Negative negative = null;

        //clean the input a little
        List<String> cleanedInput = cleanInput(parts);

        // first run the shunting yard algorithm
        List<AbstractMath> mappedParts = cleanedInput.stream().map(this::getMappedPart).collect(Collectors.toList());
        Queue<Wrapper>  outputParts = new LinkedList<>(); //these are those that would be written to console
        Stack<Wrapper>  stack = new Stack<>();
        Stack<Term> derivativeStack = new Stack<>();
        for(AbstractMath am: mappedParts){
            if(am.getClass() == Negative.class){
                negative = (Negative)am;
                continue;
            }
            if(am.getClass() == Num.class || am.getClass() == Variable.class){
                if(negative != null){
                    outputParts.add(new Wrapper(am, negative));
                    negative = null;
                }
                else {
                    outputParts.add(new Wrapper(am));
                }
            }
            else if(am.getClass() == Function.class) {
                stack.push(new Wrapper(am));
            }
            else if(am.getClass() == Paren.class){
                if(am == Paren.LEFT_PAREN){
                    stack.push(new Wrapper(am));
                }
                else{
                    while((stack.peek()).getAm() != Paren.LEFT_PAREN){
                        outputParts.add(stack.pop());
                    }
                }
            }
            else {
                //if not a functin or a number it must be an operator
                while(true){
                    if (stack.empty()){
                        break;
                    }
                    if((stack.peek()).getAm().getClass() != Paren.class && ((((Operator) (stack.peek()).getAm())).precedence < ((Operator) am).precedence) || (((Operator) (stack.peek()).getAm()).precedence == ((Operator) am).precedence) && ((Operator) (stack.peek()).getAm()).associativity == Operator.Associativity.LEFT) {
                        break;
                    }
                    // account for natural log being weird
                    // TODO check to see if this works
                    if ((Operator) (stack.peek()).getAm() == Operator.NAT_LOG) {
                        outputParts.add(new Wrapper(new Num(1)));
                    }
                    outputParts.add(stack.pop());
                }
                stack.push(new Wrapper(am));
            }
            if(negative != null){
                Wrapper popped = stack.pop();
                popped.setN(negative);
                negative = null;
                stack.push(popped);
            }
        }
        while(!stack.empty()){
            Wrapper part = stack.pop();
            if(part.getAm().getClass() != Paren.class) {
                outputParts.add(part);
            }
        }
        // then analyze the expression now that it is in reverse polish notation
        for (Wrapper w : outputParts){
            //see if it is just a number
            //if so just push it onto the stack
            AbstractMath part = w.getAm();
            if(part.getClass() == Num.class) {
                derivativeStack.push(new Term(((Num)part).getNum()));
            }
            else if (part.getClass() == Variable.class){
                derivativeStack.push((Variable)part);
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
            if(w.getN() != null){
                derivativeStack.push(derivativeStack.pop().flipSign());
            }
        }
        root = derivativeStack.pop();
        if(!derivativeStack.empty()){
            throw new RuntimeException("Parsing Error");
        }
    }

    public Term getRoot(){
        return this.root;
    }

    public AbstractMath getMappedPart(String s){
        if(s.matches("\\(")){
            operatorOrFunctionSeen = true;
            return Paren.LEFT_PAREN;
        }
        if(s.matches("\\)")){
            return Paren.RIGHT_PAREN;
        }
        AbstractMath returnThing = null;
        //see if it is an integeer
        if (s.matches("[+-]?\\d+")){
            operatorOrFunctionSeen = false;
            return new Num(Integer.parseInt(s));
        }
        //see if it is a negative sign
        if (operatorOrFunctionSeen && s.equals("-")){
            operatorOrFunctionSeen = false;
            return new Negative();
        }
        returnThing = Operator.getOp(s);
        if(returnThing == null){
            returnThing = Function.getFunc(s);
        }
        if(returnThing != null){
            operatorOrFunctionSeen = true;
            return returnThing;
        }
        if(s.length() != 1){
            throw new RuntimeException("This part is invalid");
        }
        operatorOrFunctionSeen = false;
        return Variable.getVariable(s.charAt(0));
    }

    public List<String> cleanInput(String [] list){
        //turn something like 3x into 3 * x
        //this makes it easier later
        List<String> returnList = new LinkedList<>();
        for (String s : list){
            if(s.matches(".*[0-9][a-z].*")){
                String [] parsedParts = s.split("(?=[a-z])", 2);
                returnList.add(parsedParts[0]);
                returnList.add("*");
                returnList.add(parsedParts[1]);
            }
            else{
                returnList.add(s);
            }
        }
        return returnList;
    }
}
