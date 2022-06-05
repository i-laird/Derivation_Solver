package calculator.util.rules;

import java.util.LinkedList;
import java.util.List;

import calculator.util.terms.Term;

/**
 * DerivationRule
 *
 * @author Ian Laird
 *
 * The basis for finding derivatives.
 *
 * Derivatives are found by finding the derivative of each term and then calling putTogether().
 */
public abstract class DerivationRule extends Term {

    // the terms held in the rule
    protected LinkedList<Term> terms;

    public DerivationRule(LinkedList<Term> l) {
        this.terms = l;
    }

    public DerivationRule(){}

    /**
     * {@inheritDoc}
     *
     * {@link DerivationRule} Steps:
     * 1) Find derivative of each term in the expression
     * 2) Put together these derivatives to find the derivative of the original
     * @return the derivative of the {@link DerivationRule}
     */
    @Override
    public Term getDerivative() {
        LinkedList<Term> derived = new LinkedList<>();
        terms.forEach(x -> derived.add(x.getDerivative()));
        return putTogether(terms, derived);
    }

    /**
     *
     * @param original the original terms
     * @param derived the derivation of the terms
     * @return the result
     */
    protected Term putTogether(LinkedList<Term> original, LinkedList<Term> derived){
        return null;
    }

    /**
     * gets the result of the expression using {@link calculator.util.rules.DerivationRule#getResult(List)}
     * and negates it if necessary.
     *
     * @param dims values to use for the variables
     * @return the result
     */
    public double evaluate(List<Integer> dims){
        double toReturn = this.getResult(dims);
        if(this.negative){
            toReturn = toReturn * -1;
        }
        return toReturn;
    }

    /**
     * getResult
     *
     * returns the result after evaluating the expression. Does not account for the negative flag and that is why this
     * method is not intended to be called publicly.
     *
     * @param dims values to use for the variables
     * @return evaluated expression for dims
     */
    abstract double getResult(List<Integer> dims);

    @Override
    public abstract String toString();

}
