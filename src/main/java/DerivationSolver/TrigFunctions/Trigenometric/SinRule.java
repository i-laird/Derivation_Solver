package DerivationSolver.TrigFunctions.Trigenometric;

import java.util.LinkedList;
import java.util.List;

import DerivationSolver.Rules.DerivationRule;
import DerivationSolver.Terms.Term;
import DerivationSolver.TrigFunctions.TrigRule;

public class SinRule extends TrigRule {

    public SinRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        return DerivationRule.rf.makeCosRule(this.terms.get(0));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.sin(this.terms.get(0).evaluate(dims));
    }
}
