package DerivationSolver.Rules;

import java.util.LinkedList;
import java.util.List;

import DerivationSolver.Terms.Term;


public class NaturalLogRule extends DerivationRule {
    public NaturalLogRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        Term inside = this.terms.get(0);
        return rf.makeFracRule(inside, new Term(1));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.log(this.terms.get(1).evaluate(dims));
    }
}
