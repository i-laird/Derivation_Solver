package DerivationSolver.Rules;

import java.util.LinkedList;
import java.util.List;

import DerivationSolver.Terms.Term;


public class NaturalLogRule extends DerivationRule {
    public static final int ARGUMENT_INDEX = 0;

    public NaturalLogRule(LinkedList<Term> l) {
        super(l);
    }

    /**
     * d/dx(lnx) = 1/x
     * @return
     */
    @Override
    public Term getDerivative() {

        Term argument = this.terms.get(ARGUMENT_INDEX);
        return rf.makeFracRule(argument, new Term(1));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.log(this.terms.get(ARGUMENT_INDEX).evaluate(dims));
    }
}
