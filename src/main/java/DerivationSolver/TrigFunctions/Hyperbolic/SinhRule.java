package DerivationSolver.TrigFunctions.Hyperbolic;

import DerivationSolver.Terms.Term;
import DerivationSolver.TrigFunctions.TrigRule;

import java.util.LinkedList;
import java.util.List;

public class SinhRule extends TrigRule {

    public SinhRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        return rf.makeCoshRule(this.terms.get(0));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.sinh(this.terms.get(0).evaluate(dims));
    }
}
