package TrigFunctions.Trigenometric;

import java.util.LinkedList;

import Rules.DerivationRule;
import Terms.Term;


public class CosRule extends DerivationRule {

    public CosRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        // d/dx cos(x) = -sin(x)
        return DerivationRule.rf.makeSinRule(this.terms.get(0)).flipSign();
    }
}
