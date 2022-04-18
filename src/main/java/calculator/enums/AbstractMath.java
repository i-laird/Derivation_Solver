package calculator.enums;

import calculator.terms.Term;

public interface AbstractMath {
    public Term getTermFromOp(Term one, Term two);
}
