package DerivationSolver;

import DerivationSolver.Enums.*;
import DerivationSolver.Terms.Term;
import DerivationSolver.Terms.Var;
import DerivationSolver.Terms.Variable;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Laird
 *
 * Parsers an input line to create the operation/function tree.
 *
 * STEPS:
 * 1) Uses the shunting yard algorithm to convert mathematical expression from infix to postfix notation.
 *      -order of operations is taken into account when parsing
 *      -I had to modify the standard algorithm to account for unary operators (i.e. sin() cos())
 * 2) Creates AST from the postfix expression.
 *
 * TO USE:
 * 1) create the Parser using an input stream
 * 2) Call get root to get the term at the base of the AST.
 *
 * TO GET DERIVATIVE:
 * call getDerivative on the term returned from getRoot()
 *
 * @see Term
 */
public class Parser {
    private boolean operatorOrFunctionSeen = true;

    // the root of the AST (populated by constructor)
    Term root = null;

    /**
     * @author Laird
     * @param in the inputstream from which the mathematical expression to be parsed
     *           is contained in infix notation
     * return: none
     *
     * creates the tree and stores it in root
     */
    public Parser(InputStream in){

        // used to find when to end the jurisdiction of unary operators
        // i.e. for sin(x) a mapping will be stored from sin to the end paren
        Map<AbstractMath, AbstractMath> functionToLastAppliedTerm = new HashMap<>();

        //reset the variables
        Var.reset();
        Scanner inputScan = new Scanner(new BufferedInputStream(in));
        String line = inputScan.nextLine();

        // tokenize the line by white space
        String [] parts = line.split("\\s+");
        Negative negative = null;

        // clean the tokens
        // this step further splits the tokens if the string does not delimit by white space
        List<String> cleanedInput = cleanInput(parts);

        // map each token to its associated mathematical operation
        List<AbstractMath> mappedParts = cleanedInput.stream().map(this::getMappedPart).filter(Objects::nonNull).collect(Collectors.toList());

        // remove excessive negatives
        // i.e. NEGATIVE NEGATIVE 5 -> 5
        List<AbstractMath> negFixed = removeMultipleNegatives(mappedParts);

        // holds the reverse polish notation
        Queue<Wrapper>  outputParts = new LinkedList<>();
        Stack<Wrapper>  stack = new Stack<>();
        Stack<Term> derivativeStack = new Stack<>();

        // used to iterate over the tokens
        ListIterator<AbstractMath> iter = negFixed.listIterator();

        while (iter.hasNext()){
            AbstractMath am = iter.next();

            // if this is a NEGATIVE remember it and go to next token
            if(am.getClass() == Negative.class){
                negative = (Negative)am;
                continue;
            }

            // if token is a constant or a variable
            if(am.getClass() == Num.class || am.getClass() == Variable.class){

                // if negated previously
                if(negative != null){
                    outputParts.add(new Wrapper(am, negative));
                    negative = null;
                }
                else {
                    outputParts.add(new Wrapper(am));
                }

                // see if any unary operator ends at this
                // this will only occur when the unary operator did not use parenthesis
                for(Map.Entry<AbstractMath, AbstractMath> k : functionToLastAppliedTerm.entrySet()){
                    if(am == k.getValue()){
                        outputParts.add(new Wrapper(k.getKey()));
                        functionToLastAppliedTerm.remove(k.getKey());
                        break;
                    }
                }
            }

            // if it is a unary operator figure out when the operator stops applying
            else if(am.getClass() == Function.class) {
                ListIterator<AbstractMath> iter2 = negFixed.listIterator(iter.nextIndex());

                AbstractMath next = iter2.next();

                // if the immediate following is an OPEN PAREN look for CLOSE PAREN
                if(next.getClass() == Paren.class && next == Paren.LEFT_PAREN ){

                    // stores the number of OPEN PAREN seen
                    int leftParenCount = 0;
                    while(true){
                        if(next.getClass() == Paren.class){
                            if(next == Paren.LEFT_PAREN){
                                leftParenCount++;
                            }
                            else{
                                if(leftParenCount > 1){
                                    leftParenCount--;
                                }
                                else{
                                    break;
                                }
                            }
                        }
                        next = iter2.next();
                    }
                }

                functionToLastAppliedTerm.put(am, next);
            }
            else if(am.getClass() == Paren.class){
                if(am == Paren.LEFT_PAREN){
                    stack.push(new Wrapper(am));
                }
                else{
                    while((stack.peek()).getAm() != Paren.LEFT_PAREN){
                        outputParts.add(stack.pop());
                    }
                    for(Map.Entry<AbstractMath, AbstractMath> k : functionToLastAppliedTerm.entrySet()){
                        if(am == k.getValue()){
                            outputParts.add(new Wrapper(k.getKey()));
                            functionToLastAppliedTerm.remove(k.getKey());
                            break;
                        }
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
                        if ((stack.peek()).getAm().getClass() == Paren.class || ((((Operator) (stack.peek()).getAm())).precedence < ((Operator) am).precedence) || (((Operator) (stack.peek()).getAm()).precedence == ((Operator) am).precedence) && ((Operator) (stack.peek()).getAm()).associativity == Operator.Associativity.LEFT) {
                            break;
                        }
                        // account for natural log being weird
                        // TODO check to see if this works
                        if ((Operator) am == Operator.NAT_LOG) {
                            outputParts.add(new Wrapper(new Num(1)));
                        }
                    }catch(ClassCastException e){
                        System.err.println(e);
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

    public Term getDeriv(){
        return this.root.getDerivative();
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
            parsedParts = s.split("((?<=[\\(\\)\\*\\^])|(?=[\\(\\)\\*\\^]))");

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
