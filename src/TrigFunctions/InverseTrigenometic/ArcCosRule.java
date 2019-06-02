package TrigFunctions.InverseTrigenometic;

import Rules.DerivationRule;
import Terms.Term;

import java.util.LinkedList;

public class ArcCosRule extends DerivationRule {

    public ArcCosRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        // d/dx tan(x) = sec(x)^2
        return DerivationRule.rf.makePowerRule(DerivationRule.rf.makeSecRule(this.terms.get(0)), new Term(2));
    }
}
