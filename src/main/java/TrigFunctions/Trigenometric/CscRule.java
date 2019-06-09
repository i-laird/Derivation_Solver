package TrigFunctions.Trigenometric;

import java.util.LinkedList;
import java.util.List;

import Rules.DerivationRule;
import Terms.Term;


public class CscRule extends DerivationRule {

    public CscRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        // d/dx csc(x) = -csc(x) * cot(x)
        return DerivationRule.rf.makeProductRule(DerivationRule.rf.makeCscRule(this.terms.get(0)).flipSign(), DerivationRule.rf.makeCotRule(this.terms.get(0)));
    }

    @Override
    public int getResult(List<Integer> dims) {
        return 0;
    }
}
