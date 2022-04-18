package calculator.util.token;

import calculator.util.terms.Term;

public interface AbstractMath {
    public Term getTermFromOp(Term one, Term two);
}
