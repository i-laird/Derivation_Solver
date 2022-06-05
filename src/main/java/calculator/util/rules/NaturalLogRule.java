package calculator.util.rules;

import java.util.LinkedList;
import java.util.List;

import calculator.util.terms.Term;
import static calculator.util.rules.RuleFactory.*;


public final class NaturalLogRule extends DerivationRule {
    public static final int ARGUMENT_INDEX = 0;

    NaturalLogRule(LinkedList<Term> l) {
        super(l);
    }

    /**
     * d/dx(lnx) = 1/x
     * @return
     */
    @Override
    public Term getDerivative() {

        Term argument = this.terms.get(ARGUMENT_INDEX);
        return makeFracRule(argument, new Term(1));
    }

    @Override
    protected Term putTogether(LinkedList<Term> original, LinkedList<Term> derived) {
        return null;
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.log(this.terms.get(ARGUMENT_INDEX).evaluate(dims));
    }

    @Override
    public String toString() {
        return "ln (" + this.terms.get(ARGUMENT_INDEX) + " )";
    }
}
