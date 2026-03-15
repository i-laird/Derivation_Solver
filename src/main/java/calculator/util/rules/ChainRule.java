/* (C)2022 */
package calculator.util.rules;

import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;

public final class ChainRule extends DerivationRule {

  private static final int REQUIRED_TERM_COUNT = 2;

  ChainRule(LinkedList<Term> l) {
    super(validateTerms(l));
  }

  private static LinkedList<Term> validateTerms(LinkedList<Term> l) {
    if (l == null || l.size() != REQUIRED_TERM_COUNT) {
      throw new IllegalArgumentException(
          "ChainRule requires exactly "
              + REQUIRED_TERM_COUNT
              + " terms (outside, inside); got "
              + (l == null ? "null" : l.size()));
    }
    return l;
  }

  @Override
  public Term getDerivative() {
    ProductRule toReturn =
        RuleFactory.makeProductRule(
            this.terms.get(0).getDerivative(), this.terms.get(1).getDerivative());
    if (this.negative) {
      toReturn.flipSign();
    }
    return toReturn;
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    return this.terms.get(0).evaluate(dims);
  }

  @Override
  public String toString() {
    return this.terms.get(0).toString();
  }
}
