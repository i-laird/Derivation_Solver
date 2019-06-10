package TrigFunctions.Hyperbolic;

import Rules.DerivationRule;
import Terms.Term;
import TrigFunctions.TrigRule;

import java.util.LinkedList;
import java.util.List;

public class SechRule extends TrigRule {

    public SechRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        return rf.makeProductRule(rf.makeTanhRule(this.terms.get(0)).flipSign(), rf.makeSechRule(this.terms.get(0)));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 0;
    }
}
