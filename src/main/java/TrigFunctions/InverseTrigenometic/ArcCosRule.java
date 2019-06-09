package TrigFunctions.InverseTrigenometic;

import Rules.DerivationRule;
import Terms.Term;

import java.util.LinkedList;
import java.util.List;

public class ArcCosRule extends DerivationRule {

    public ArcCosRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivative() {
        return rf.makeFracRule(new Term(-1),
                rf.makePowerFracRule(
                        rf.makeAdditionRule(new Term(1),
                                rf.makePowerRule(this.terms.get(0), new Term(2)).flipSign()),
                        new Term(1),
                        new Term(2)
                ));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 0;
    }
}
