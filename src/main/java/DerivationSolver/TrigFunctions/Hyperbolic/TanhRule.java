package DerivationSolver.TrigFunctions.Hyperbolic;

import DerivationSolver.Terms.Term;
import DerivationSolver.TrigFunctions.TrigRule;

import java.util.LinkedList;
import java.util.List;

public class TanhRule extends TrigRule {

    public TanhRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        return rf.makePowerRule(new Term(2), rf.makeSechRule(this.terms.get(0)));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.tanh(this.terms.get(0).evaluate(dims));
    }
}
