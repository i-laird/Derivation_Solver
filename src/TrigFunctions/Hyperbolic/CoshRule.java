package TrigFunctions.Hyperbolic;

import Rules.DerivationRule;
import Terms.Term;

import java.util.LinkedList;

public class CoshRule  extends DerivationRule {

    public CoshRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        return rf.makeSinhRule(this.terms.get(0));
    }
}
