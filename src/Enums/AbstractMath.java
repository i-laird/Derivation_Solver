package Enums;

import Rules.RuleFactory;
import Terms.Term;

public interface AbstractMath {
    public Term getTermFromOp(Term one, Term two);
    public RuleFactory rf = RuleFactory.getFactory();
}
