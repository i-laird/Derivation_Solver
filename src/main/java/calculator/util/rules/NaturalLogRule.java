/* (C)2022 */
package calculator.util.rules;

import static calculator.util.rules.RuleFactory.*;

import calculator.util.terms.Term;
import com.google.common.collect.ImmutableList;
import java.util.LinkedList;

public final class NaturalLogRule extends DerivationRule {
  public static final int ARGUMENT_INDEX = 0;

  NaturalLogRule(LinkedList<Term> l) {
    super(l);
  }

  /**
   * d/dx(lnx) = 1/x
   *
   * @return
   */
  @Override
  public Term getDerivative() {
    Term argument = this.terms.get(ARGUMENT_INDEX);
    // d/dx ln(x) = 1/x
    Term toReturn = makeFracRule(new Term(1), argument);
    if (this.negative) {
      toReturn.flipSign();
    }
    return toReturn;
  }

  @Override
  public double getResult(ImmutableList<Integer> dims) {
    return Math.log(this.terms.get(ARGUMENT_INDEX).evaluate(dims));
  }

  @Override
  public String toString() {
    return "ln (" + this.terms.get(ARGUMENT_INDEX) + " )";
  }
}
