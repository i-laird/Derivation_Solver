package calculator.util.token;

import calculator.util.terms.Term;

public class Negative implements AbstractMath {
    @Override
    public Term getTermFromOp(Term one, Term two) {
        return one.flipSign();
    }

}
