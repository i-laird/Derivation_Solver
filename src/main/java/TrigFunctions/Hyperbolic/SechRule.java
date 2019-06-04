package TrigFunctions.Hyperbolic;

import Rules.DerivationRule;
import Terms.Term;

import java.util.LinkedList;

public class SechRule extends DerivationRule {

    public SechRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        return rf.makeProductRule(rf.makeTanhRule(this.terms.get(0)).flipSign(), rf.makeSechRule(this.terms.get(0)));
    }
}
