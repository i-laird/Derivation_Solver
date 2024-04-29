package calculator.util.rules;

import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;

public abstract sealed class Rule extends Term permits DerivationRule, TrigRule {

  /**
   * gets the result of the expression using {@link Rule#getResult(ImmutableList)} and negates it if
   * necessary.
   *
   * @param dims values to use for the variables
   * @return the result
   */
  public double evaluate(ImmutableList<Integer> dims) {
    double toReturn = this.getResult(dims);
    if (this.negative) {
      toReturn = toReturn * -1;
    }
    return toReturn;
  }

  /**
   * getResult
   *
   * <p>returns the result after evaluating the expression. Does not account for the negative flag
   * and that is why this method is not intended to be called publicly.
   *
   * @param dims values to use for the variables
   * @return evaluated expression for dims
   */
  abstract double getResult(ImmutableList<Integer> dims);

  @Override
  public abstract Term getDerivative();

  @Override
  public abstract String toString();
}
