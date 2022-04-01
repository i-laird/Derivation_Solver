package DerivationSolver.TrigFunctions.Trigenometric;

import java.util.LinkedList;
import java.util.List;

import DerivationSolver.Rules.DerivationRule;
import DerivationSolver.Terms.Term;
import DerivationSolver.TrigFunctions.TrigRule;

public class SecRule extends TrigRule {

    public SecRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        // d/dx sec(x) = sec(x) * tan(x)
        return DerivationRule.rf.makeProductRule(DerivationRule.rf.makeTanRule(this.terms.get(0)), DerivationRule.rf.makeSecRule(this.terms.get(0)));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 1.0 / Math.cos(this.terms.get(0).evaluate(dims));
    }
}
