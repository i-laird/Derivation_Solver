package calculator.util.token;

import calculator.util.terms.Term;

public enum Paren implements AbstractMath {
    LEFT_PAREN, RIGHT_PAREN;
    public Term getTermFromOp(Term one, Term two){
        return null;
    }

}
