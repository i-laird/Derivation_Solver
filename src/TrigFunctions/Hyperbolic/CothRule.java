package TrigFunctions.Hyperbolic;

import Rules.DerivationRule;
import Terms.Term;

import java.util.LinkedList;

public class CothRule  extends DerivationRule {

    public CothRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        return rf.makeAdditionRule(new Term(1), rf.makePowerRule(rf.makeCothRule(this.terms.get(0)), new Term(2)).flipSign());
    }
}