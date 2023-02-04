package calculator.util.ast;

import calculator.exception.ParseError;
import calculator.util.Wrapper;
import calculator.util.terms.Term;
import calculator.util.terms.Variable;
import calculator.util.token.AbstractMath;
import calculator.util.token.Function;
import calculator.util.token.Num;
import lombok.NonNull;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class AbstractSyntaxTree {

    // the root of the AST (populated by constructor)
    Term root = null;

    /**
     * @return the derivative of the expression
     */
    public Term getDeriv() {
        return this.root.getDerivative();
    }

    /**
     * @return the root of the mathematical expression tree
     */
    public Term getRoot() {
        return this.root;
    }

    /**
     * @author Laird
     * @param in the inputstream from which the mathematical expression to be parsed is contained in
     *     infix notation return: none
     *
     * <p>creates the tree and stores it in root
     */
    public AbstractSyntaxTree(@NonNull InputStream in) {

        Scanner inputScan = new Scanner(new BufferedInputStream(in));
        String line = inputScan.nextLine();
        Tokenizer tokenizer = new Tokenizer();

        // tokenize the expression
        // performs some sanitizing such as removing excess negatives
        // and adding parens as necessary
        List<AbstractMath> tokenized = tokenizer.tokenizeExpression(line);

        // convert the expression to postfix notation
        Queue<Wrapper> outputParts = Tokenizer.convertToPostFix(tokenized);

        // evaluate the post fix expression
        root = evaluatePostfix(outputParts);
    }

    /**
     * analyze the expression now that it is in reverse polish notation Steps: 1) if a number or
     * variable push onto the stack 2) if an operator pop the correct number of terms from the stack
     * (one or two) 3) set these as the children of the operator 4) push the operator back onto the
     * stack 5) go until only one element on the stack
     *
     * @param outputParts the tokens in post fix order
     * @return
     */
    private static Term evaluatePostfix(@NonNull Queue<Wrapper> outputParts) {

        // used to built the parse tree
        Stack<Term> parseTree = new Stack<>();

        for (Wrapper w : outputParts) {

            AbstractMath part = w.getAm();

            // if it is a number push it onto the stack
            if (part.getClass() == Num.class) {
                parseTree.push(new Term(((Num) part).getNum()));
            }

            // if it is a variable push it onto the stack
            else if (part.getClass() == Variable.class) {
                parseTree.push((Variable) part);
            }

            // if it is a unary operator pop one element off of the stack
            else if (part.getClass() == Function.class) {
                Term operand = parseTree.pop();
                parseTree.push(part.getTermFromOp(operand, null));
            }

            // it is an operand pop two items off of the stack
            else {
                Term operandOne = parseTree.pop(), operandTwo = parseTree.pop();
                parseTree.push(part.getTermFromOp(operandOne, operandTwo));
            }

            // if necessary flip the sign of the top of the stack
            if (w.getN() != null) {
                parseTree.peek().flipSign();
            }
        }

        Term root = parseTree.pop();

        // if there is still something in the stack after popping the root there was an ERROR
        if (!parseTree.empty()) {
            throw new ParseError("Invalid Token Encountered: " + parseTree.peek());
        }

        // the last element of the stack is the root of the tree
        return root;
    }

}
