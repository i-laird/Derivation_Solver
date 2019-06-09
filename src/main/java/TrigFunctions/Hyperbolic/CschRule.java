package TrigFunctions.Hyperbolic;

import Rules.DerivationRule;
import Terms.Term;

import java.util.LinkedList;
import java.util.List;

public class CschRule  extends DerivationRule {

    public CschRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        return rf.makeProductRule(rf.makeCotRule(this.terms.get(0)).flipSign(), rf.makeCschRule(this.terms.get(0)));
    }

    @Override
    public int getResult(List<Integer> dims) {
        return 0;
    }
}
