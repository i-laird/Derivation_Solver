package DerivationSolver.TrigFunctions.Hyperbolic;

import DerivationSolver.Terms.Term;
import DerivationSolver.TrigFunctions.TrigRule;

import java.util.LinkedList;
import java.util.List;

public class CoshRule  extends TrigRule {

    public CoshRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        return rf.makeSinhRule(this.terms.get(0));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.cosh(this.terms.get(0).evaluate(dims));
    }
}
