package calculator.enums;

import calculator.terms.Term;

public class Negative implements AbstractMath {
    @Override
    public Term getTermFromOp(Term one, Term two) {
        return one.flipSign();
    }

}
