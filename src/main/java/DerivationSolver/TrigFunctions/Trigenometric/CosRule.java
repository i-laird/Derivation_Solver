package DerivationSolver.TrigFunctions.Trigenometric;

import java.util.LinkedList;
import java.util.List;

import DerivationSolver.Rules.DerivationRule;
import DerivationSolver.Terms.Term;
import DerivationSolver.TrigFunctions.TrigRule;


public class CosRule extends TrigRule {

    public CosRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        // d/dx cos(x) = -sin(x)
        return DerivationRule.rf.makeSinRule(this.terms.get(0)).flipSign();
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.cos(this.terms.get(0).evaluate(dims));
    }
}
