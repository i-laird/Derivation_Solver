package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;

/**
 * Log Rule
 *
 * @author Ian Laird
 */
public final class LogRule extends DerivationRule {

  public static final int BASE_INDEX = 0;
  public static final int ARGUMENT_INDEX = 1;

  LogRule(LinkedList<Term> l) {
    super(l);
  }

  /**
   * d/dx(logax) = 1 / (x ln(a))
   *
   * @return derivative
   */
  @Override
  public Term getDerivative() {

    int base = this.terms.get(BASE_INDEX).getNum();
    Term argument = this.terms.get(ARGUMENT_INDEX);

    Term denominator = makeProductRule(argument, makeNaturalLogRule(this.terms.get(BASE_INDEX)));

    return makeFracRule(denominator, new Term(1));
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    return Math.log(this.terms.get(BASE_INDEX).evaluate(dims))
        / Math.log(this.terms.get(ARGUMENT_INDEX).evaluate(dims));
  }

  @Override
  public String toString() {
    return "log " + this.terms.get(BASE_INDEX) + " ( " + this.terms.get(ARGUMENT_INDEX) + " ) ";
  }
}
