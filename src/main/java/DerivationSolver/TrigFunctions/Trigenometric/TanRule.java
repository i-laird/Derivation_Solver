package DerivationSolver.TrigFunctions.Trigenometric;

import java.util.LinkedList;
import java.util.List;

import DerivationSolver.Rules.DerivationRule;
import DerivationSolver.Terms.Term;
import DerivationSolver.TrigFunctions.TrigRule;

public class TanRule extends TrigRule {

    public TanRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        // d/dx tan(x) = sec(x)^2
        return DerivationRule.rf.makePowerRule(DerivationRule.rf.makeSecRule(this.terms.get(0)), new Term(2));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.tan(this.terms.get(0).evaluate(dims));
    }
}
