package Rules;

import java.util.LinkedList;
import Terms.Term;


public class LogRule extends DerivationRule {
    public LogRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        int base = this.terms.get(0).getNum();
        Term inside = this.terms.get(1);
        return rf.makeFracRule(new Term(1), rf.makeProductRule(inside, rf.makeNaturalLogRule(this.terms.get(0))));
    }
}
