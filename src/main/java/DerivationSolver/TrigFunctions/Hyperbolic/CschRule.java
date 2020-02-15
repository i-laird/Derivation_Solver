package DerivationSolver.TrigFunctions.Hyperbolic;

import DerivationSolver.Terms.Term;
import DerivationSolver.TrigFunctions.TrigRule;

import java.util.LinkedList;
import java.util.List;

public class CschRule  extends TrigRule {

    public CschRule(LinkedList<Term> l) {
        super(l);
    }

    @Override
    public Term getDerivPart() {
        return rf.makeProductRule(rf.makeCotRule(this.terms.get(0)).flipSign(), rf.makeCschRule(this.terms.get(0)));
    }

    @Override
    public double getResult(List<Integer> dims) {
        return 0;
    }
}
