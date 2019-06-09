package TrigFunctions.Trigenometric;

import java.util.LinkedList;
import java.util.List;

import Rules.DerivationRule;
import Terms.Term;


public class CotRule extends DerivationRule {

    public CotRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        // d/dx cot(x) = -csc(x)^2
        return DerivationRule.rf.makePowerRule(DerivationRule.rf.makeCscRule(this.terms.get(0)), new Term(2).flipSign());
    }

    @Override
    public int getResult(List<Integer> dims) {
        return 0;
    }
}
