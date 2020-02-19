package DerivationSolver.Rules;

import java.util.LinkedList;
import java.util.List;

import DerivationSolver.Terms.Term;

/**
 * Log Rule
 *
 * @author Ian Laird
 */
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

    @Override
    public double getResult(List<Integer> dims) {
        return Math.log(this.terms.get(0).evaluate(dims)) / Math.log(this.terms.get(1).evaluate(dims));
    }
}
