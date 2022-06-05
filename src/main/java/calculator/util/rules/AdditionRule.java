package calculator.util.rules;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import calculator.util.terms.Term;

/**
 * AdditionRule
 * @author Ian Laird
 *
 * Represents an ADDITION of terms
 *
 * d/dx(x + y) = d/dx(x) + d/dx(y
 */
public final class AdditionRule extends DerivationRule {

    /**
     * d/dx(x + y) = d/dx(x) + d/dx(y)
     *
     * The derivative of the addition of terms is equal to the addition of the derivative of each term.
     *
     * @param original the original terms to be added
     * @param derived the derivative of each
     * @return the addition of all of the derived terms
     */
    @Override
    protected Term putTogether(LinkedList<Term> original, LinkedList<Term> derived){
        return new AdditionRule(derived);
    }

    public AdditionRule(LinkedList<Term> l) {
        super(l);
    }

    /**
     * Adds a term to the addition Rule
     * @param t the term to add
     * @return this
     */
    public AdditionRule addTerm(Term t){
        this.terms.add(t);
        return this;
    }

    /**
     * evaluates the addition rule by adding each term
     * @param dims the variable values to use when evaulating
     * @return the result
     */
    public double getResult(List<Integer> dims){
        return this.terms.stream().map(x -> x.evaluate(dims)).reduce(0.0, Double::sum);
    }


    /**
     * tostring
     *
     * Calls tostring on each term and joins them together with a + symbol
     */
    @Override
    public String toString() {
        return terms.stream().map(Object::toString).collect(Collectors.joining(" + "));
    }

}
