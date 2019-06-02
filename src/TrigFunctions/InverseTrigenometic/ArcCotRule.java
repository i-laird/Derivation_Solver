package TrigFunctions.InverseTrigenometic;

import Rules.DerivationRule;
import Terms.Term;

import java.util.LinkedList;

public class ArcCotRule  extends DerivationRule {

    public ArcCotRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        return rf.makeFracRule(new Term(-1), rf.makeAdditionRule(new Term(1), rf.makePowerRule(this.terms.get(0), new Term(2))));
    }
}
