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
    Term root = null;

    /**
     * @return the derivative of the expression located at root.
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
     * @param in the inputstream from which the mathematical expression to be parsed is contained in
     *     infix notation
     *
     * @return: none
     *
     * <p>creates the tree and stores it in root.
     */
    public AbstractSyntaxTree(@NonNull InputStream in) {
        Scanner inputScan = new Scanner(new BufferedInputStream(in));
        String line = inputScan.nextLine();
        Tokenizer tokenizer = new Tokenizer();
        List<AbstractMath> tokenized = tokenizer.tokenizeExpression(line);
        Queue<Wrapper> outputPartsInPostFixNotation = Tokenizer.convertToPostFix(tokenized);
        root = evaluatePostfix(outputPartsInPostFixNotation);
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
        Stack<Term> parseTree = new Stack<>();
        for (Wrapper w : outputParts) {
            AbstractMath part = w.getAm();
            if (part.getClass() == Num.class) {
                parseTree.push(new Term(((Num) part).getNum()));
            } else if (part.getClass() == Variable.class) {
                parseTree.push((Variable) part);
            } else if (part.getClass() == Function.class) {
                Term operand = parseTree.pop();
                parseTree.push(part.getTermFromOp(operand, null));
            } else {
                Term operandOne = parseTree.pop(), operandTwo = parseTree.pop();
                parseTree.push(part.getTermFromOp(operandOne, operandTwo));
            }
            if (w.getN() != null) {
                parseTree.peek().flipSign();
            }
        }
        Term root = parseTree.pop();
        if (!parseTree.empty()) {
            throw new ParseError("Invalid Token Encountered: " + parseTree.peek());
        }
        return root;
    }

}
