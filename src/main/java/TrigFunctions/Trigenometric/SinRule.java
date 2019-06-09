package TrigFunctions.Trigenometric;

import java.util.LinkedList;
import java.util.List;

import Rules.DerivationRule;
import Terms.Term;

public class SinRule extends DerivationRule {

    public SinRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        return DerivationRule.rf.makeCosRule(this.terms.get(0));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return Math.sin(this.terms.get(0).evaluate(dims));
    }
}
