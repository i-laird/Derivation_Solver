package DerivationSolver.Enums;

import DerivationSolver.Rules.RuleFactory;
import DerivationSolver.Terms.Term;

public interface AbstractMath {
    public Term getTermFromOp(Term one, Term two);
    public static final RuleFactory rf = RuleFactory.getFactory();
}
