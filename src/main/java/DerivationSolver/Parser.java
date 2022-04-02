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

        //reset the variable
        Var.reset();

        Scanner inputScan = new Scanner(new BufferedInputStream(in));
        String line = inputScan.nextLine();

        // tokenize the expression
        List<AbstractMath> tokenized = tokenizeExpression(line);

        // convert the expression to postfix notation
        Queue<Wrapper>  outputParts = convertToPostFix(tokenized);

        // evaluate the post fix expression
        root = evaluatePostfix(outputParts);
    }

    /**
     * @return the root of the mathematical expression tree
     */
    public Term getRoot(){
        return this.root;
    }

    /**
     * @return the derivative of the expression
     */
    public Term getDeriv(){
        return this.root.getDerivative();
    }

    /**
     * tokenize the expression
     * @param expression the expression to tokenize
     * @return in order syntax and numbers
     */
    private List<AbstractMath> tokenizeExpression(String expression){
        // tokenize the line by white space
        String [] parts = expression.split("\\s+");

        // clean the tokens
        // this step further splits the tokens if the string does not delimit by white space
        List<String> cleanedInput = cleanInput(parts);

        // map each token to its associated mathematical operation
        List<AbstractMath> mappedParts = cleanedInput
                .stream()
                .map(this::getMappedPart) // converts to syntax enum i.e. sin -> SIN
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // remove excessive negatives
        // i.e. NEGATIVE NEGATIVE 5 -> 5
        return removeMultipleNegatives(mappedParts);
    }

    /**
     * @param list an array of strings to be cleaned
     * @return splits monomials
     *
     * example: 3x into 3 * x
     */
    public List<String> cleanInput(String [] list){

        // create a stack that will hold the items that need to be processed
        Stack<String> processStack = new Stack<>();

        for(int i = list.length - 1; i >= 0; i--){
            processStack.push(list[i]);
        }

        List<String> returnList = new LinkedList<>();

        while (!processStack.isEmpty()){
            String s = processStack.pop();
            String [] parsedParts = null;

            // split parens out i.e. sin( -> sin (
            parsedParts = s.split("((?<=[\\(\\)\\*\\^])|(?=[\\(\\)\\*\\^]))");

            if(parsedParts.length > 1) {
                for(int i = parsedParts.length - 1; i >= 0; i--){
                    processStack.push(parsedParts[i]);
                }
                continue;
            }

            //turn something like 3x into 3 * x
            if (s.matches(".*[0-9][a-z].*")) {
                splitByRegex(s,2,"(?=[a-z])",processStack, "*" );
                continue;
            }

            // split out NEGATIVE signs
            if(splitByRegex(s,null,"((?=-)|(?<=-))",processStack, null)){
                continue;
            }

            returnList.add(s);
        }
        return returnList;
    }

    private static boolean splitByRegex(String toSplit, Integer splitLimit, String regex, Stack<String> processStack, String separator){
        String [] parsedParts = Objects.nonNull(splitLimit) ? toSplit.split(regex, splitLimit) : toSplit.split(regex);
        if(parsedParts.length > 1) {
            for(int i = parsedParts.length - 1; i >= 0; i--){
                processStack.push(parsedParts[i]);
                if (i > 0 && Objects.nonNull(separator) && !separator.equals("")) {
                    processStack.push(separator);
                }
            }
            return true;
        }
        return false;
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
     * @param l the list of math terms in order
     * @return double negatives are removed, and negated subtraction
     *  becomes addition
     */
    public List<AbstractMath> removeMultipleNegatives(List<AbstractMath> l){
        List<AbstractMath> returnList = new LinkedList<>();
        Queue<AbstractMath> suspicious = new LinkedList<>();
        for (AbstractMath am : l){
            //when this happens it is time to clear out the queue
            if(am.getClass() != Negative.class && am != Operator.SUBTRACT && am != Operator.ADD ){
                if (!suspicious.isEmpty()) {
                    AbstractMath topElem = suspicious.remove();
                    //this needs to become an addition
                    if (topElem == Operator.SUBTRACT || topElem == Operator.ADD) {
                        while (!suspicious.isEmpty()) {
                            AbstractMath removed = suspicious.remove();
                            if(removed != Operator.ADD) {
                                topElem = (topElem == Operator.SUBTRACT ? Operator.ADD : Operator.SUBTRACT);
                            }
                        }
                        returnList.add(topElem);
                    } else if (topElem.getClass() == Negative.class) {
                        int count = 1;
                        for(AbstractMath am2 : suspicious){
                            if(am2 != Operator.ADD);
                            count += 1;
                        }
                        if (count % 2 == 1) {
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

    /**
     * converts a list of tokens to postfix notation
     * @param tokens the tokens
     * @return post fix notation
     */
    private static Queue<Wrapper> convertToPostFix(List<AbstractMath> tokens){
        int numRightParenEncountered = 0;
        Queue<Wrapper>  outputParts = new LinkedList<>();
        Negative negative = null;

        // used to iterate over the tokens
        ListIterator<AbstractMath> iter = tokens.listIterator();

        // used to find when to end the jurisdiction of unary operators
        // i.e. for sin(x) a mapping will be stored from sin to the end paren
        Map<AbstractMath, Integer> functionToLastAppliedTerm = new HashMap<>();

        Stack<Wrapper>  stack = new Stack<>();

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
                for(Map.Entry<AbstractMath, Integer> k : functionToLastAppliedTerm.entrySet()){
                    if(numRightParenEncountered == k.getValue()){
                        outputParts.add(new Wrapper(k.getKey()));
                        functionToLastAppliedTerm.remove(k.getKey());
                        break;
                    }
                }
            }

            // if it is a unary operator figure out when the operator stops applying
            else if(am.getClass() == Function.class) {
                int tempRightParenCount = 0;
                ListIterator<AbstractMath> iter2 = tokens.listIterator(iter.nextIndex());

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
                                ++tempRightParenCount;
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

                functionToLastAppliedTerm.put(am, numRightParenEncountered + tempRightParenCount);
            }

            // if the token is a paren
            else if(am.getClass() == Paren.class){

                // if it is an opening paren push it onto the stack
                if(am == Paren.LEFT_PAREN){
                    stack.push(new Wrapper(am));
                }

                // if it is a RIGHT paren
                else{
                    ++numRightParenEncountered;

                    //  keep popping from the stack until a LEFT paren is encountered
                    while((stack.peek()).getAm() != Paren.LEFT_PAREN){
                        outputParts.add(stack.pop());
                    }

                    // get rid of this left paran
                    stack.pop();

                    // see if a unary operator ended at this point
                    for(Map.Entry<AbstractMath, Integer> k : functionToLastAppliedTerm.entrySet()){
                        if(numRightParenEncountered == k.getValue()){
                            outputParts.add(new Wrapper(k.getKey()));
                            functionToLastAppliedTerm.remove(k.getKey());
                            break;
                        }
                    }
                }
            }

            // if an operator
            else {

                //TODO comment this part
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

            // if negative preceded this negate the top of the stack
            if(negative != null){
                Wrapper popped = stack.pop();
                popped.setN(negative);
                negative = null;
                stack.push(popped);
            }
        }

        // if there are things left on the stack put them all into the queue
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
        return outputParts;
    }

    /**
     * analyze the expression now that it is in reverse polish notation
     * Steps:
     *      1) if a number or variable push onto the stack
     *      2) if an operator pop the correct number of terms from the stack (one or two)
     *      3) set these as the children of the operator
     *      4) push the operator back onto the stack
     *      5) go until only one element on the stack
     * @param outputParts the tokens in post fix order
     * @return
     */
    private static Term evaluatePostfix(Queue<Wrapper>  outputParts){

        // used to built the parse tree
        Stack<Term> parseTree = new Stack<>();

        for (Wrapper w : outputParts){

            AbstractMath part = w.getAm();

            // if it is a number push it onto the stack
            if(part.getClass() == Num.class) {
                parseTree.push(new Term(((Num)part).getNum()));
            }

            // if it is a variable push it onto the stack
            else if (part.getClass() == Variable.class){
                parseTree.push((Variable)part);
            }

            // if it is a unary operator pop one element off of the stack
            else if(part.getClass() == Function.class){
                Term operand = parseTree.pop();
                parseTree.push(part.getTermFromOp(operand, null));
            }

            //it is an operand pop two items off of the stack
            else{
                Term operandOne = parseTree.pop(),
                        operandTwo = parseTree.pop();
                parseTree.push(part.getTermFromOp(operandOne, operandTwo));
            }

            // if necessary flip the sign of the top of the stack
            if(w.getN() != null){
                parseTree.peek().flipSign();
            }
        }

        Term root = parseTree.pop();

        // if there is still something in the stack after popping the root there was an ERROR
        if(!parseTree.empty()){
            throw new RuntimeException("Parsing Error");
        }

        // the last element of the stack is the root of the tree
        return root;
    }
}
