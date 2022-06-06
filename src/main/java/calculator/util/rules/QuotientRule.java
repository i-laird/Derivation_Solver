package calculator.util.rules;

import java.util.LinkedList;
import java.util.List;

import calculator.util.terms.Term;
import static calculator.util.rules.RuleFactory.*;

/**
 * QUOTIENT RULE
 *
 * Used for one term divided by one term. Only accepts two terms. If you want more than two terms
 * Use Product Rule.
 *
 * @author Ian Laird
 */
public final class QuotientRule extends DerivationRule {

    public final static int NUMERATOR_POS = 1;
    public final static int DENOM_POS = 0;

    // NUMERATOR is the second term and DENOMINATOR is the first

    /**
     * d/dx( f(x) / g(x) ) = (f'(x)g(x) - g'(x)f(x)) / (g(x))^2
     * @return
     */
    @Override
    public Term getDerivative() {
        if (this.terms.size() != 2){
            return null;
        }

        Term numerator = makeAdditionRule(
                makeProductRule(this.terms.get(NUMERATOR_POS).getDerivative(), this.terms.get(DENOM_POS)),
                makeProductRule(this.terms.get(DENOM_POS).getDerivative(), this.terms.get(NUMERATOR_POS)).flipSign());

        Term denom = makePowerRule(new Term(2), this.terms.get(DENOM_POS));

        return makeFracRule(denom, numerator);
    }

    QuotientRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public double getResult(List<Integer> dims) {
        double numeratorEvaluated = this.terms.get(NUMERATOR_POS).evaluate(dims);
        double denominatorEvaluated = this.terms.get(DENOM_POS).evaluate(dims);
        return numeratorEvaluated / denominatorEvaluated;
    }

    @Override
    public String toString() {
        return " ( " + this.terms.get(NUMERATOR_POS) + ") / ( " + this.terms.get(DENOM_POS) + " ) ";
    }
}
