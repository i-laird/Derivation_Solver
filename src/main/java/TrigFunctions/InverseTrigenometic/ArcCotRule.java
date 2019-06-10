package TrigFunctions.InverseTrigenometic;

import Rules.DerivationRule;
import Terms.Term;
import TrigFunctions.TrigRule;

import java.util.LinkedList;
import java.util.List;

public class ArcCotRule  extends TrigRule {

    public ArcCotRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        return rf.makeFracRule(new Term(-1), rf.makeAdditionRule(new Term(1), rf.makePowerRule(this.terms.get(0), new Term(2))));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 0;
    }
}
