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
public abstract sealed class DerivationRule extends Rule permits
        AdditionRule, QuotientRule, ProductRule, PowerRule, PowerFracRule, ChainRule, NaturalLogRule, LogRule {

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
    protected abstract Term putTogether(LinkedList<Term> original, LinkedList<Term> derived);

    @Override
    public abstract String toString();

}
