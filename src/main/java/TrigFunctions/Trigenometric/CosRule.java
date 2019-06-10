package TrigFunctions.Trigenometric;

import java.util.LinkedList;
import java.util.List;

import Rules.DerivationRule;
import Terms.Term;
import TrigFunctions.TrigRule;


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
