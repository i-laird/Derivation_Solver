package calculator.util.token;

import calculator.util.terms.Term;

public interface AbstractMath {
  Term getTermFromOp(Term one, Term two);
}
