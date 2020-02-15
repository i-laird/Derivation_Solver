package DerivationSolver.Enums;

import DerivationSolver.Terms.Term;

public class Negative implements AbstractMath {
    @Override
    public Term getTermFromOp(Term one, Term two) {
        return one.flipSign();
    }

}
