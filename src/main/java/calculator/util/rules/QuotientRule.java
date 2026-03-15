/* (C)2022 */
package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;

/**
 * QUOTIENT RULE
 *
 * <p>Used for one term divided by one term. Only accepts two terms. If you want more than two terms
 * Use Product Rule.
 *
 * @author Ian Laird
 */
public final class QuotientRule extends DerivationRule {

  public static final int NUMERATOR_POS = 0;
  public static final int DENOM_POS = 1;

  // NUMERATOR is the first term and DENOMINATOR is the second

  QuotientRule(LinkedList<Term> l) {
    super(l);
  }

  /**
   * d/dx( f(x) / g(x) ) = (f'(x)g(x) - g'(x)f(x)) / (g(x))^2
   *
   * @return
   */
  @Override
  public Term getDerivative() {
    if (this.terms.size() != 2) {
      return null;
    }

    Term f = this.terms.get(NUMERATOR_POS);
    Term g = this.terms.get(DENOM_POS);

    // (f'g - g'f) / g^2
    Term fPrimeG = makeProductRule(f.getDerivative(), g);
    Term gPrimeF = makeProductRule(g.getDerivative(), f);
    
    // We want f'g - g'f. AdditionRule will add fPrimeG and negatedGPrimeF.
    Term negatedGPrimeF = gPrimeF.flipSign();
    Term numerator = makeAdditionRule(fPrimeG, negatedGPrimeF);

    Term denom = makePowerRule(g, new Term(2));

    Term toReturn = makeFracRule(numerator, denom);
    if (this.negative) {
      toReturn.flipSign();
    }
    return toReturn;
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    double numeratorEvaluated = this.terms.get(NUMERATOR_POS).evaluate(dims);
    double denominatorEvaluated = this.terms.get(DENOM_POS).evaluate(dims);
    return numeratorEvaluated / denominatorEvaluated;
  }

  @Override
  public String toString() {
    return " ( " + this.terms.get(NUMERATOR_POS) + ") / ( " + this.terms.get(DENOM_POS) + " ) ";
  }
}
