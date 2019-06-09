package TrigFunctions.Hyperbolic;

import Rules.DerivationRule;
import Terms.Term;

import java.util.LinkedList;
import java.util.List;

public class SinhRule extends DerivationRule {

    public SinhRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        return rf.makeCoshRule(this.terms.get(0));
    }

    @Override
    public int getResult(List<Integer> dims) {
        return 0;
    }
}
