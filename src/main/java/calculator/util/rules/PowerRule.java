package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;

/**
 * POWER RULE
 *
 * <p>Used for when one term is raised to a power of another term
 */
public final class PowerRule extends DerivationRule {

  // POW is the first in the terms
  public static final int POW_POS = 0;

  // BASE is the second in the terms
  public static final int BASE_POS = 1;

  PowerRule(LinkedList<Term> l) {
    super(l);
  }

  /**
   * d/dx(x^n) = (n-1) * x
   *
   * @return the antiderivative
   */
  @Override
  public Term getDerivative() {

    Term pow = this.terms.get(POW_POS);
    Term base = this.terms.get(BASE_POS);

    // if it is not zero we just do a simple multiplication
    if (pow.getClass() == Term.class && pow.getNum() != 0) {
      int p = pow.getNum();
      return makeProductRule(
          this.negative ? new Term(p * -1) : new Term(p),
          makePowerRule(base, new Term(p - 1)));
    }

    return new Term(0);
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    return Math.pow(this.terms.get(BASE_POS).evaluate(dims), this.terms.get(POW_POS).evaluate(dims));
  }

  @Override
  public String toString() {
    return this.terms.get(BASE_POS) + " ^ ( " + this.terms.get(POW_POS) + " ) ";
  }
}
