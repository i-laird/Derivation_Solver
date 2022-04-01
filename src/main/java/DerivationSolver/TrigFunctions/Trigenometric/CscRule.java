package DerivationSolver.TrigFunctions.Trigenometric;

import java.util.LinkedList;
import java.util.List;

import DerivationSolver.Rules.DerivationRule;
import DerivationSolver.Terms.Term;
import DerivationSolver.TrigFunctions.TrigRule;


public class CscRule extends TrigRule {

    public CscRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        // d/dx csc(x) = -csc(x) * cot(x)
        return DerivationRule.rf.makeProductRule(DerivationRule.rf.makeCscRule(this.terms.get(0)).flipSign(), DerivationRule.rf.makeCotRule(this.terms.get(0)));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 1.0 / Math.sin(this.terms.get(0).evaluate(dims));
    }
}
