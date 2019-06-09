package TrigFunctions.Hyperbolic;

import Rules.DerivationRule;
import Terms.Term;

import java.util.LinkedList;
import java.util.List;

public class CoshRule  extends DerivationRule {

    public CoshRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        return rf.makeSinhRule(this.terms.get(0));
    }

    @Override
    public int getResult(List<Integer> dims) {
        return 0;
    }
}
