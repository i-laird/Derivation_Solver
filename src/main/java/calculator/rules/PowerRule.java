package calculator.rules;

import java.util.LinkedList;
import java.util.List;

import calculator.terms.Term;
import static calculator.rules.RuleFactory.*;

/**
 * POWER RULE
 *
 * Used for when one term is raised to a power of another term
 */
public class PowerRule extends DerivationRule {

    // POW is the first in the terms
    public static final int POW_POS = 0;

    // BASE is the second in the terms
    public static final int BASE_POS = 1;

    PowerRule(LinkedList<Term> l) {
        super(l);
    }

    /**
     * d/dx(x^n) = (n-1) * x
     *
     * @return the antiderivative
     */
    @Override
    public Term getDerivative() {

        int pow = this.terms.get(POW_POS).getNum();
        Term base = this.terms.get(BASE_POS);

        //if it is not zero we just do a simple multiplication
        if (pow != 0){
            return makeProductRule(this.negative ? new Term(pow * -1) : new Term(pow), makePowerRule(new Term(pow - 1), base));
        }

        return new Term(1);
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.pow(this.terms.get(1).evaluate(dims), this.terms.get(0).evaluate(dims));
    }

}
