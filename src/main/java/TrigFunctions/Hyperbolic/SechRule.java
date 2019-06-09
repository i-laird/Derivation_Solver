package TrigFunctions.Hyperbolic;

import Rules.DerivationRule;
import Terms.Term;

import java.util.LinkedList;
import java.util.List;

public class SechRule extends DerivationRule {

    public SechRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        return rf.makeProductRule(rf.makeTanhRule(this.terms.get(0)).flipSign(), rf.makeSechRule(this.terms.get(0)));
    }

    @Override
    public int getResult(List<Integer> dims) {
        return 0;
    }
}
