package TrigFunctions.Trigenometric;

import java.util.LinkedList;
import java.util.List;

import Rules.DerivationRule;
import Terms.Term;

public class SecRule extends DerivationRule {

    public SecRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        // d/dx sec(x) = sec(x) * tan(x)
        return DerivationRule.rf.makeProductRule(DerivationRule.rf.makeTanRule(this.terms.get(0)), DerivationRule.rf.makeSecRule(this.terms.get(0)));
    }

    @Override
    public int getResult(List<Integer> dims) {
        return 0;
    }
}
