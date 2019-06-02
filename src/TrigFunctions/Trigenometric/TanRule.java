package TrigFunctions.Trigenometric;

import java.util.LinkedList;
import Rules.DerivationRule;
import Terms.Term;

public class TanRule extends DerivationRule {

    public TanRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        // d/dx tan(x) = sec(x)^2
        return DerivationRule.rf.makePowerRule(DerivationRule.rf.makeSecRule(this.terms.get(0)), new Term(2));
    }
}