package TrigFunctions.Trigenometric;

import java.util.LinkedList;

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
}
