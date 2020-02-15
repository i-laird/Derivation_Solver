import Enums.*;
import Terms.Term;
import Terms.Var;
import Terms.Variable;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Laird
 *
 * Parsers an input line to create the operation/function tree.
 *
 * Uses the shunting yard algorithm so that the expression can be converted to
 * reverse polish notation. The tree is then constructed.
 *
 * The ouput of the parser is a root that can then be grabbed with getRoot.
 * This can then have its derivative taken, or it can be directly evaluated as it.
 *
 * @see Term
 */
public class Parser {
    private boolean operatorOrFunctionSeen = true;
    Term root = null;

    /**
     * @author Laird
     * @param in the inputstream from which the mathematical expression to be parsed
     *           is contained
     * return: none
     *
     * created the tree and stores it in root
     */
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
        List<AbstractMath> mappedParts = cleanedInput.stream().map(this::getMappedPart).filter(Objects::nonNull).collect(Collectors.toList());
        List<AbstractMath> negFixed = removeMultipleNegatives(mappedParts);
        Queue<Wrapper>  outputParts = new LinkedList<>(); //these are those that would be written to console
        Stack<Wrapper>  stack = new Stack<>();
        Stack<Term> derivativeStack = new Stack<>();
        for(AbstractMath am: negFixed){
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
                    try {
                        if ((stack.peek()).getAm().getClass() != Paren.class && ((((Operator) (stack.peek()).getAm())).precedence < ((Operator) am).precedence) || (((Operator) (stack.peek()).getAm()).precedence == ((Operator) am).precedence) && ((Operator) (stack.peek()).getAm()).associativity == Operator.Associativity.LEFT) {
                            break;
                        }
                        // account for natural log being weird
                        // TODO check to see if this works
                        if ((Operator) am == Operator.NAT_LOG) {
                            outputParts.add(new Wrapper(new Num(1)));
                        }
                    }catch(ClassCastException e){}
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
                try {
                    if ((Operator) part.getAm() == Operator.NAT_LOG) {
                        outputParts.add(new Wrapper(new Num(1)));
                    }
                }catch(ClassCastException e){}
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

    /**
     * @return the root of the mathematical expression tree
     */
    public Term getRoot(){
        return this.root;
    }

    /**
     * @author laird
     * @param s the string to be evaluated
     * @return the abstract math part corresponding to the string
     *
     * caution: if s is only white space or empty null is returned
     */
    public AbstractMath getMappedPart(String s){
        //get rid of anything that is just white space
        if(s.matches("\\s+") || s.isEmpty()){
            return null;
        }
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
            throw new RuntimeException("This part is invalid: " + s);
        }
        operatorOrFunctionSeen = false;
        return Variable.getVariable(s.charAt(0));
    }

    /**
     * @param list an array of strings to be cleaned
     * @return splits monomials
     *
     * example: 3x into 3 * x
     */
    public List<String> cleanInput(String [] list){

        // stick the strings on in the order of a stack
        Stack<String> strings = new Stack<>();
        for(int i = list.length - 1; i >= 0; i--){
            strings.push(list[i]);
        }

        List<String> returnList = new LinkedList<>();

        while (!strings.isEmpty()){
            String s = strings.pop();
            String [] parsedParts = null;

            // split based on a non space followed by a paren sinx -> sin x
            parsedParts = s.split(".*[^ ](?=[\\(\\)])");

            if(parsedParts.length > 1) {
                for(int i = parsedParts.length - 1; i >= 0; i--){
                    strings.push(parsedParts[i]);
                }
                continue;
            }

            //turn something like 3x into 3 * x
            if (s.matches(".*[0-9][a-z].*")) {
                parsedParts = s.split("(?=[a-z])", 2);
                strings.push(parsedParts[1]);
                strings.push("*");
                strings.push(parsedParts[0]);
                continue;
            }
            returnList.add(s);
        }
        return returnList;
    }

    /**
     * @param l the list of math terms in order
     * @return double negatives are removed, and negated subtraction
     *  becomes addition
     */
    public List<AbstractMath> removeMultipleNegatives(List<AbstractMath> l){
        List<AbstractMath> returnList = new LinkedList<>();
        Queue<AbstractMath> suspicious = new LinkedList<>();
        for (AbstractMath am : l){
            //when this happens it is time to clear out the queue
            if(am.getClass()  != Negative.class && am != Operator.SUBTRACT ){
                if (!suspicious.isEmpty()) {
                    AbstractMath topElem = suspicious.remove();
                    //this needs to become an addition
                    if (topElem == Operator.SUBTRACT) {
                        while (!suspicious.isEmpty()) {
                            suspicious.remove();
                            topElem = (topElem == Operator.SUBTRACT ? Operator.ADD : Operator.SUBTRACT);
                        }
                        returnList.add(topElem);
                    } else if (topElem.getClass() == Negative.class) {
                        int size = suspicious.size() + 1;
                        if (size % 2 == 1) {
                            returnList.add(topElem);
                        }
                    }
                }
                returnList.add(am);
            }
            else{
                suspicious.add(am);
            }
        }
        return returnList;
    }
}
