package Rules;

import java.util.LinkedList;
import Terms.Term;


public class NaturalLogRule extends DerivationRule {
    public NaturalLogRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        Term inside = this.terms.get(0);
        return rf.makeFracRule(new Term(1), inside);
    }
}
